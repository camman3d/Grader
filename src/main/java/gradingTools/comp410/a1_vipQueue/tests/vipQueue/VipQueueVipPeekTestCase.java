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
public class VipQueueVipPeekTestCase extends BasicTestCase {

    public VipQueueVipPeekTestCase() {
        super("VipQueue peek w/ enqueueVip test case");
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

            // Enqueue something then peek
            double passes = 0;
            String notes = "";
            queue.vipEnqueue(7);
            if ((Integer) queue.peek() == 7)
                passes++;
            else
                notes += "Peek should return the first item in the queue.\n";

            // Peek again and you should get the same thing
            if ((Integer) queue.peek() == 7)
                passes++;
            else
                notes += "Calling peek again should return the same thing.\n";

            return partialPass(passes / 2, notes);

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