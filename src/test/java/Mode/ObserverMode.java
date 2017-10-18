package Mode;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by xiexw on 2017/8/22.
 * 观察者模式Demo
 */
public class ObserverMode {

    //被观察者类
    private static class DemoObservable extends Observable {

        private Integer data = 2;

        private Integer getData() {
            return data;
        }

        private void setData(Integer data) {
            if (!this.data.equals(data)) {
                this.data = data;
                setChanged();
                //调用setChanged后该方法才有效
                notifyObservers();
            }
        }
    }

    //观察者类
    private static class DemoObserver implements Observer {

        //加入被观察者
        private DemoObserver(DemoObservable observable) {
            observable.addObserver(this);
        }

        @Override
        public void update(Observable observable, Object arg) {
            System.out.println("Data changed：data = " + ((DemoObservable) observable).getData());
        }
    }

    public static void main(String[] args) {
        DemoObservable observable = new DemoObservable();
        DemoObserver server = new DemoObserver(observable);
        try {
            for (int i = 0; i < 4; i++) {
                observable.setData(i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("======手动update======");
        server.update(observable, server);
    }
}
