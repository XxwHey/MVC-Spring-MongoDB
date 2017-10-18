package Mode;

/**
 * Created by xiexw on 2017/8/23.
 * 装饰者模式Demo
 */
public class DecoratorMode {

    private interface A {

        void getA();

    }

    private static class AImpl implements A {

        @Override
        public void getA() {
            System.out.println("get A...OK");
        }

    }

    private static abstract class Decorator extends AImpl {
        private A a = new AImpl();

        public void getA() {
            a.getA();
        }
    }

    private static class DecoratorA extends Decorator {
        public void getA() {
            super.getA();
        }
    }

    private static class DecoratorB extends Decorator {
        public void getA() {
            super.getA();
            getB();
        }

        private void getB() {
            System.out.println("get B...OK");
        }
    }

    public static void main(String[] args) {
        DecoratorA a = new DecoratorA();
        a.getA();
        System.out.println("=================");
        DecoratorB b = new DecoratorB();
        b.getA();
    }

}
