package Mode;

/**
 * Created by xiexw on 2017/8/22.
 * 外观模式（门面模式）Demo
 */
public class FacadeMode {

    private static class A {
        private void start() {
            System.out.println("start A...OK");
        }
        private void end() {
            System.out.println("end A...OK");
        }
    }

    private static class B {
        private void start() {
            System.out.println("start B...OK");
        }
        private void end() {
            System.out.println("end B...OK");
        }
    }

    private static class C {
        private void start() {
            System.out.println("start C...OK");
        }
        private void end() {
            System.out.println("end C...OK");
        }
    }

    private static class ABC {
        private A a;
        private B b;
        private C c;

        //创建实例时初始化对象
        private ABC() {
            a = new A();
            b = new B();
            c = new C();
        }

        private void start() {
            a.start();
            b.start();
            c.start();
        }

        private void end() {
            a.end();
            b.end();
            c.end();
        }
    }

    public static void main(String[] args) {
        ABC abc = new ABC();
        System.out.println("====start====");
        abc.start();
        System.out.println("====end====");
        abc.end();
    }
}
