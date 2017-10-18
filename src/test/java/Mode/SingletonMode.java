package Mode;

/**
 * Created by xiexw on 2017/8/23.
 * 单例模式Demo
 */
public class SingletonMode {

    private static class Singleton {

        private Singleton() {

        }

        private static class SingletonInit {
            private final static Singleton singleton = new Singleton();
        }

        private static Singleton getInstance() {
            return SingletonInit.singleton;
        }

        private String getName() {
            return this.getClass().getTypeName();
        }

    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton.getName());
    }

}
