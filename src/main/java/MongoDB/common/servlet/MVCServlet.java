package MongoDB.common.servlet;

import MongoDB.common.annotation.ModelDTO;
import MongoDB.common.utils.CXFUtils;
import MongoDB.common.utils.CommonUtils;
import MongoDB.common.utils.ServletUtils;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Created by xiexw on 2017/8/9.
 * servlet入口
 */

@WebServlet(name = "MVCServlet", urlPatterns = {"*.do"}, loadOnStartup = 0,
        initParams = {
                @WebInitParam(name = "controllerPrefix", value = "MongoDB.controller."),
                @WebInitParam(name = "controllerSuffix", value = "Controller"),
                @WebInitParam(name = "jspPrefix", value = "/WEB-INF/pages/"),
                @WebInitParam(name = "jspSuffix", value = ".jsp")
        }
)

@MultipartConfig
public class MVCServlet extends HttpServlet {

    //controller前后缀
    private String CONTROLLER_PREFIX = null;
    private String CONTROLLER_SUFFIX = null;

    //jsp文件前后缀
    private String JSP_PREFIX = null;
    private String JSP_SUFFIX = null;

    //模拟MVC扫描controller
    private static String[] controllers = {"MusicController", "UserController"};

    //初始化各controller中所有方法
    private static HashMap<String, HashMap<String, Method>> ctrlMap;

    //通过spring提供的类反射获取参数名称
    private static ParameterNameDiscoverer parameterNameDiscoverer = ServletUtils.CONTEXT.getBean(ParameterNameDiscoverer.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        CONTROLLER_PREFIX = config.getInitParameter("controllerPrefix");
        CONTROLLER_SUFFIX = config.getInitParameter("controllerSuffix");
        JSP_PREFIX = config.getInitParameter("jspPrefix");
        JSP_SUFFIX = config.getInitParameter("jspSuffix");
        ctrlMap = ServletUtils.initController(CONTROLLER_PREFIX, controllers);
        //cxf
//        CXFUtils.initCXF();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String contextPath = request.getContextPath();
        //请求相对路径
        String servletPath = request.getServletPath();

        //获取一级请求
        String firstURL = servletPath.substring(1, servletPath.lastIndexOf(".do"));
        //获取二级请求
        String secondURL = "";
        if (firstURL.contains("/")) {
            secondURL = firstURL.substring(firstURL.indexOf("/") + 1, firstURL.length());
            firstURL = firstURL.substring(0, firstURL.indexOf("/"));
        }

        //对应controller类
        String className = firstURL.substring(0, 1).toUpperCase()
                + firstURL.substring(1, firstURL.length())
                + CONTROLLER_SUFFIX;

        //获取controller对应方法集
        HashMap<String, Method> methodMap = ctrlMap.get(className);
        try {
//            实例化controller对象
//            controller = Class.forName(CONTROLLER_PREFIX + className).newInstance();

//            注入service对象
//            ServletUtils.getService(controller);

            //注入controller对象
            Object controller = ServletUtils.CONTEXT.getBean(Class.forName(CONTROLLER_PREFIX + className));

            //获取相应mapping的方法
            Method method = methodMap.get(secondURL);

            //设定response编码
            response.setContentType("text/html;charset=utf-8");

            //接口定义的参数
            Parameter[] methodParams = method.getParameters();
            String[] methodParamNames = parameterNameDiscoverer.getParameterNames(method);

            //注入参数值
            Object[] objects = new Object[methodParams.length];
            objects[0] = request;
            objects[1] = response;

            //去除req, resp
            Map<String, Integer> indexes = new HashMap<>();
            for (int i = 0; i < methodParamNames.length; i++) {
                String paramName = methodParamNames[i];
                if (!paramName.equals("request") && !paramName.equals("response")) {
                    indexes.put(paramName, i);
                }
            }

            //注入请求参数
            Map<String, String[]> requestParams = request.getParameterMap();
            String[] value;
            if (indexes.size() > 0) {
                Object dto = null;
                Integer dtoIndex = 0;
                //获取请求的所有参数名称
                for (String param : indexes.keySet()) {
                    Integer index = indexes.get(param);
                    Parameter parameter = methodParams[index];
                    //获取参数类型
                    String paramType = parameter.getType().getSimpleName();
                    if (parameter.isAnnotationPresent(ModelDTO.class)) {
                        dto = parameter.getType().newInstance();
                        dtoIndex = index;
                        Field[] dtoFields = dto.getClass().getFields();
                        for (Field dtoField : dtoFields) {
                            dtoField.setAccessible(true);
                            value = requestParams.get(dtoField.getName());
                            if (value != null) {
                                dtoField.set(dto, CommonUtils.castValue(dtoField.getType().getSimpleName(), value[0]));
                            }
                        }
                    } else {
                        if (requestParams.get(param) == null || requestParams.get(param)[0].equals("")) {
                            objects[index] = null;
                        } else {
                            value = requestParams.get(param);
                            objects[index] = CommonUtils.castValue(paramType, value[0]);
                        }
                    }
                }
                if (dto != null) {
                    objects[dtoIndex] = dto;
                }
            }


            //调用相应接口
            Object returnObject = method.invoke(controller, objects);

            //参照MVC return方法
            if (returnObject != null && returnObject.getClass() == String.class) {
                StringBuilder page = new StringBuilder();
                page.append(JSP_PREFIX);
                page.append(returnObject.toString());
                if (!returnObject.toString().contains(".")) {
//                String prefix = request.getRequestURL().toString().replace(servletPath, "") + "/";
//                String page = prefix + returnObject.toString() + JSP_SUFFIX;
//                System.out.println(page);
                    page.append(JSP_SUFFIX);
                }
                request.getRequestDispatcher(page.toString()).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.setError(response, e.getMessage());
        }
    }
}