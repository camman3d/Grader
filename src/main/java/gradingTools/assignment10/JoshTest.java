package gradingTools.assignment10;

import bus.uigen.ObjectEditor;
import bus.uigen.sadapters.BeanToRecord;
import bus.uigen.trace.ConcreteStructureNodeCreated;
import bus.uigen.trace.LogicalStructureNodeCreated;
import util.trace.TraceableLog;
import util.trace.TraceableLogFactory;
import util.trace.Tracer;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class JoshTest {

    public static void main(String[] args) {

        TraceableLog log = TraceableLogFactory.getTraceableLog();

        String obj = "Hello world";
        ObjectEditor.edit(obj);
        bus.uigen.trace.IllegalComponentAddPosition.newExample(123, null);
//        Tracer.warning("Holy moley this is a warning!!!");

        // Look for warnings
        for (Exception e : log.getLog()) {
                System.out.println(e);
        }
        System.out.println(log.getLog().size());


    }
}
