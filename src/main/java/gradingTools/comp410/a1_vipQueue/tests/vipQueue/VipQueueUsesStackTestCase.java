package gradingTools.comp410.a1_vipQueue.tests.vipQueue;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import gradingTools.comp410.a1_vipQueue.tests.queue.QueueRepresentation;
import gradingTools.comp410.a1_vipQueue.tests.stack.StackRepresentation;
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
public class VipQueueUsesStackTestCase extends BasicTestCase {

    public VipQueueUsesStackTestCase() {
        super("VipQueue uses Stack test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "VipQueue" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName("VipQueue", autoGrade);
        if (classDescription.isEmpty())
            return fail("No vip queue class");
        Class<?> _class = classDescription.get().getJavaClass();

        // Find the "Stack" class
        Option<ClassDescription> stackClassDescription = ClassFinder.get(project).findByName("Stack", autoGrade);
        if (stackClassDescription.isEmpty())
            return fail("No queue class");
        Class<?> stackClass = stackClassDescription.get().getJavaClass();

        try {
            // Create the queue
            VipQueueRepresentation vipQueue = new VipQueueRepresentation(_class, autoGrade);
            vipQueue.instantiate();

            // Get the stack property of the vip queue
            Field stackField = null;
            for (Field field : _class.getDeclaredFields())
                if (field.getType() == stackClass)
                    stackField = field;
            if (stackField == null)
                return fail("There is no queue field");
            stackField.setAccessible(true);
            StackRepresentation stack = new StackRepresentation(stackClass, autoGrade);
            stack.setInstantiation(stackField.get(vipQueue.getInstantiation()));

            // Push some numbers in the normal stack just to make sure stuff doesn't break
            stack.push(1);
            stack.push(3);
            stack.push(5);

            // VipEnqueue some numbers in the vip queue
            vipQueue.vipEnqueue(2);
            vipQueue.vipEnqueue(4);
            vipQueue.vipEnqueue(6);

            // Pop them from the stack and check
            double passes = 0;
            String notes = "";
            if ((Integer) stack.pop() == 6)
                passes++;
            else
                notes += "Expected pop to return 6\n";
            if ((Integer) stack.pop() == 4)
                passes++;
            else
                notes += "Expected pop to return 4\n";
            if ((Integer) stack.pop() == 2)
                passes++;
            else
                notes += "Expected pop to return 2\n";

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
