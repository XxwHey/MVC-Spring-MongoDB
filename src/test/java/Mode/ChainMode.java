package Mode;

/**
 * Created by xiexw on 2017/8/22.
 * 责任链模式Demo
 */
public class ChainMode {

    static abstract class Handler {

        private Handler next;

        Handler getNext() {
            return next;
        }

        void setNext(Handler next) {
            this.next = next;
        }

        public abstract void doNext(double number);

    }

    //一级
    private static class A extends Handler {
        @Override
        public void doNext(double number) {
            if (number < 10) {
                System.out.println("已审批");
            } else {
                if (getNext() != null) {
                    System.out.println("移交至B级审批");
                    getNext().doNext(number);
                }
            }
        }
    }

    //二级
    private static class B extends Handler {
        @Override
        public void doNext(double number) {
            if (number < 100) {
                System.out.println("已审批");
            } else {
                if (getNext() != null) {
                    System.out.println("移交至C级审批");
                    getNext().doNext(number);
                }
            }
        }
    }

    //三级
    private static class C extends Handler {
        @Override
        public void doNext(double number) {
            if (number < 1000) {
                System.out.println("已审批");
            } else {
                System.out.println("审批不通过");
                if (getNext() != null) {
                    System.out.println("移交至上级审批");
                    getNext().doNext(number);
                }
            }
        }
    }

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        C c = new C();
        a.setNext(b);
        b.setNext(c);
        c.setNext(null);
        //审批流程
        a.doNext(9.9);
        a.doNext(10.9);
        a.doNext(100.2);
        a.doNext(998.5);
        a.doNext(1001.2);
    }
}
