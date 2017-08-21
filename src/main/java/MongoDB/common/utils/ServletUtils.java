package MongoDB.common.utils;

import MongoDB.common.annotation.AutowireService;
import org.json.JSONObject;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by xiexw on 2017/8/10.
 * Servlet工具类
 */
public class ServletUtils {

    //获取spring配置文件
    public static ConfigurableApplicationContext CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");

    /**
     * 初始化所有controller
     *
     * @param controllerPrefix controller类前缀
     * @param controllers controller集合
     * @return ctrlMap 各controller对应的方法集
     */
    public static HashMap<String, HashMap<String, Method>> initController(String controllerPrefix, String[] controllers) {
        HashMap<String, HashMap<String, Method>> ctrlMap = new HashMap<>();
        for (String controller : controllers) {
            ctrlMap.put(controller, getMethodMap(controllerPrefix + controller));
        }
        return ctrlMap;
    }

    /**
     * 获取所有requestMapping接口
     *
     * @param controllerClass controller类名
     * @return methodMap 当前接口的方法集
     */
    private static HashMap<String, Method> getMethodMap(String controllerClass) {
        HashMap<String, Method> methodMap = new HashMap<>();
        try {
            Class clazz = Class.forName(controllerClass);
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                    methodMap.put(String.valueOf(mapping.value()[0]), method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return methodMap;
    }

    /**
     * 注入service对象（仿照spring依赖注入，若已交给spring管理则不使用该方法）
     * 依赖注入自定义注解：{@link AutowireService}
     *
     * @param controller 实例化的controller对象
     */
    public static void getService(Object controller) {
        try {
            //获取service对象
            Class clazz = controller.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                //判断是否带有注释
                if (field.isAnnotationPresent(AutowireService.class)) {
                    //获取当前对象的类（接口）
                    Class<?> c = field.getType();
                    String cName = c.getName();

                    //获取当前接口的实现类
                    String impl = cName.substring(cName.lastIndexOf("."), cName.length()).substring(2) + "Impl";
                    Class serviceImpl = Class.forName("MongoDB.service.impl." + impl);

                    //设定private可访问
                    field.setAccessible(true);

                    //注入属性
                    field.set(controller, CONTEXT.getBean(serviceImpl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回数据
     *
     * @param object 返回对象
     */
    public static void setWrite(HttpServletResponse response, Object object) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            JSONObject json = new JSONObject();
            if (object.getClass() == String.class) {
                json.put("result", "error");
                json.put("msg", object.toString());
            } else {
                json.put("result", "success");
                json.put("data", object);
            }
            writer.print(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            getErrorMessage(e.toString());
        }
    }

    /**
     * 返回错误信息
     *
     * @param errorMsg 错误信息
     */
    private static void getErrorMessage(String errorMsg) {
        JSONObject json = new JSONObject();
        json.put("result", "error");
        json.put("msg", errorMsg);
    }

    public static void setError(HttpServletResponse response, String exception) {
        try {
            PrintWriter writer = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("msg", exception);
            writer.print(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            getErrorMessage(e.toString());
        }
    }

}