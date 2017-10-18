package Mode;

import MongoDB.service.IMusicService;
import MongoDB.service.impl.MusicServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiexw on 2017/8/22.
 * 动态代理模式Demo
 */
public class ProxyMode {

    private static class proxyHandler implements InvocationHandler {

        private Object target;

        private proxyHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("开始代理");
            Object result = method.invoke(target, args);
            System.out.println("代理结束");
            return result;
        }

        private Object getProxy() {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?>[] interfaces = target.getClass().getInterfaces();
            return Proxy.newProxyInstance(loader, interfaces, this);
        }
    }

    public static void main(String[] args) {
        IMusicService service = new MusicServiceImpl();
        proxyHandler handler = new proxyHandler(service);
        IMusicService proxy = (IMusicService) handler.getProxy();
        proxy.sayHello();
    }
}
