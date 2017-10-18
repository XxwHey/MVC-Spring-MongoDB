package Mode;

/**
 * Created by xiexw on 2017/8/22.
 * 抽象工厂模式Demo
 */
public class FactoryMode {

    //"SubA"产品接口
    private interface SubA {

    }

    private static class SubAforA implements SubA {
        private SubAforA() {
            System.out.println("createSubA...OK");
        }
    }

    private static class SubAforB implements SubA {
        private SubAforB() {
            System.out.println("createSubA...OK");
        }
    }

    //"SubB"产品接口
    private interface SubB {

    }

    private static class SubBforA implements SubB {
        private SubBforA() {
            System.out.println("createSubB...OK");
        }
    }

    private static class SubBforB implements SubB {
        private SubBforB() {
            System.out.println("createSubB...OK");
        }
    }

    //创建工厂生产SubA，SubB
    @SuppressWarnings("unused")
    private interface Factory {

        SubA createSubA();

        SubB createSubB();

    }

    private static class FactoryA implements Factory {

        private FactoryA() {
            System.out.println("start creating A...");
        }

        @Override
        public SubA createSubA() {
            return new SubAforA();
        }

        @Override
        public SubB createSubB() {
            return new SubBforA();
        }

    }

    private static class FactoryB implements Factory {

        private FactoryB() {
            System.out.println("start creating B...");
        }

        @Override
        public SubA createSubA() {
            return new SubAforB();
        }

        @Override
        public SubB createSubB() {
            return new SubBforB();
        }

    }

    public static void main(String[] args) {
        //A
        FactoryA a = new FactoryA();
        a.createSubA();
        a.createSubB();

        //B
        FactoryB b = new FactoryB();
        b.createSubA();
        b.createSubB();
    }

}
