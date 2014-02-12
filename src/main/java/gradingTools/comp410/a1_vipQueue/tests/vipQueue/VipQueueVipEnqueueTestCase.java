package gradingTools.comp410.a1_vipQueue.tests.vipQueue;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;
import tools.classFinder2.ClassFinder;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/14/14
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class VipQueueVipEnqueueTestCase extends BasicTestCase {

    public VipQueueVipEnqueueTestCase() {
        super("VipQueue vipEnqueue test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "VipQueue" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName("VipQueue", autoGrade);
        if (classDescription.isEmpty())
            return fail("No vip queue class");
        Class<?> _class = classDescription.get().getJavaClass();

        try {
            // Create the queue
            VipQueueRepresentation queue = new VipQueueRepresentation(_class, autoGrade);
            queue.instantiate();

            // Enqueue some numbers normally
            queue.enqueue(2);
            queue.enqueue(4);
            queue.enqueue(6);

            // Enqueue some number VIP
            queue.vipEnqueue(11);
            queue.vipEnqueue(13);
            queue.vipEnqueue(15);

            // Dequeue them and check
            double passes = 0;
            String notes = "";
            if ((Integer) queue.dequeue() == 15)
                passes++;
            else
                notes += "Expected dequeue to return 15\n";
            if ((Integer) queue.dequeue() == 13)
                passes++;
            else
                notes += "Expected dequeue to return 13\n";
            if ((Integer) queue.dequeue() == 11)
                passes++;
            else
                notes += "Expected dequeue to return 11\n";
            if ((Integer) queue.dequeue() == 2)
                passes++;
            else
                notes += "Expected dequeue to return 2\n";
            if ((Integer) queue.dequeue() == 4)
                passes++;
            else
                notes += "Expected dequeue to return 4\n";
            if ((Integer) queue.dequeue() == 6)
                passes++;
            else
                notes += "Expected dequeue to return 6\n";

            // Return the test results
            return partialPass(passes / 6, notes);

        } catch (NullPointerException e) {
            return fail("Got a null pointer exception.");
        } catch (NoSuchMethodException e) {
            return fail("Missing methods in vip queue class");
        } catch (InvocationTargetException e) {
            return fail("Vip queue method threw an error.");
        } catch (InstantiationException e) {
            return fail("Unable to create vip queue (invalid constructor?)");
        } catch (IllegalAccessException e) {
            return fail("Invalid privacy in vip queue.");
        }

    }


}
