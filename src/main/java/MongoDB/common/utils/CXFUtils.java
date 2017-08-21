package MongoDB.common.utils;

import MongoDB.service.impl.CXFServiceImpl;

import javax.xml.ws.Endpoint;

/**
 * Created by xiexw on 2017/8/15.
 */
public class CXFUtils {

    public static void initCXF() {
        CXFServiceImpl impl = new CXFServiceImpl();
        String url = "http://172.16.10.12:8080/cxf";
        Endpoint.publish(url, impl);
    }

}
