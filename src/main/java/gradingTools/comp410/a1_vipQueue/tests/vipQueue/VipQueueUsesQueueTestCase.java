package gradingTools.comp410.a1_vipQueue.tests.vipQueue;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import gradingTools.comp410.a1_vipQueue.tests.queue.QueueRepresentation;
import scala.Option;
import tools.classFinder2.ClassFinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/14/14
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class VipQueueUsesQueueTestCase extends BasicTestCase {

    public VipQueueUsesQueueTestCase() {
        super("VipQueue uses Queue test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "VipQueue" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName("VipQueue", autoGrade);
        if (classDescription.isEmpty())
            return fail("No vip queue class");
        Class<?> _class = classDescription.get().getJavaClass();

        // Find the "Queue" class
        Option<ClassDescription> queueClassDescription = ClassFinder.get(project).findByName("Queue", autoGrade);
        if (queueClassDescription.isEmpty())
            return fail("No queue class");
        Class<?> queueClass = queueClassDescription.get().getJavaClass();

        try {
            // Create the queue
            VipQueueRepresentation vipQueue = new VipQueueRepresentation(_class, autoGrade);
            vipQueue.instantiate();

            // Get the queue property of the vip queue
            Field queueField = null;
            for (Field field : _class.getDeclaredFields())
                if (field.getType() == queueClass)
                    queueField = field;
            if (queueField == null)
                return fail("There is no queue field");
            queueField.setAccessible(true);
            QueueRepresentation queue = new QueueRepresentation(queueClass, autoGrade);
            queue.setInstantiation(queueField.get(vipQueue.getInstantiation()));

            // Enqueue some numbers in the vip queue
            vipQueue.enqueue(2);
            vipQueue.enqueue(4);
            vipQueue.enqueue(6);

            // Enqueue some numbers in the normal queue just to make sure stuff doesn't break
            queue.enqueue(1);
            queue.enqueue(3);
            queue.enqueue(5);

            // Dequeue them from the queue and check
            double passes = 0;
            String notes = "";
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
            return partialPass(passes / 3, notes);

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
