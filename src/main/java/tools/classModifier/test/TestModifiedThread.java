package tools.classModifier.test;

import util.trace.TraceableLog;
import util.trace.TraceableLogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/5/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestModifiedThread {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        System.out.println("runCount: " + Thread.class.getField("runCount").get(null));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world");
            }
        }).start();

        Thread.sleep(50);
        System.out.println("runCount: " + Thread.class.getField("runCount").get(null));
    }
}
