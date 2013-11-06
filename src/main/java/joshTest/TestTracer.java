package joshTest;

import util.misc.ThreadSupport;
import util.trace.*;

import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/1/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestTracer {

    public static void main(String[] args) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                TraceableLog log1 = TraceableLogFactory.getTraceableLog();
//                System.out.println(log1.getLog().size());
//                ThreadSupport.sleep(1000);
//                TraceableLog log2 = TraceableLogFactory.getTraceableLog();
//                System.out.println(log2.getLog().size());
//            }
//        }).start();


        TraceableBus.addTraceableListener(new TraceableListener() {
            @Override
            public void newEvent(Exception e) {
                System.out.println(e);
            }
        });

        TraceableBus.newEvent(new TraceableInfo("Foo bar"));
    }

}
