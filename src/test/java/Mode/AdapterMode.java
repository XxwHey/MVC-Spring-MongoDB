package Mode;

/**
 * Created by xiexw on 2017/8/23.
 * 适配器模式Demo
 */
public class AdapterMode {

    //对象适配器
    private static class A {
        private void adaptA() {
            System.out.println("adapt for A...OK");
        }
    }

    private static class Adapter {

        private A a;

        private Adapter() {
            a = new A();
        }

        private void adaptA() {
            this.a.adaptA();
        }

        private void adaptB() {
            System.out.println("adapt for B...OK");
        }

    }

    //缺省适配器
    @SuppressWarnings("unused")
    private interface ABCDE {
        void A();
        void B();
        void C();
        void D();
        void E();
        void getName();
    }

    private static abstract class AdapterForC implements ABCDE {
        @Override
        public void A() {}

        @Override
        public void B() {}

        @Override
        public void C() {}

        @Override
        public void D() {}

        @Override
        public void E() {}

        @Override
        public void getName() {

        }
    }

    private static class GetC extends AdapterForC {
        public void C() {
            System.out.println("adapter for C...OK");
        }

        public void getName() {
            System.out.println("target: C");
        }
    }

    public static void main(String[] args) {
        //对象适配器模式
        Adapter adapter = new Adapter();
        adapter.adaptA();
        adapter.adaptB();

        //缺省适配器模式
        GetC c = new GetC();
        c.C();
        c.getName();
    }
}
