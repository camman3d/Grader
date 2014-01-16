package gradingTools.comp410.a1_vipQueue.tests.stack;

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
public class StackPushPopTestCase extends BasicTestCase {

    public StackPushPopTestCase() {
        super("Push and pop test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "Stack" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName("Stack", autoGrade);
        if (classDescription.isEmpty())
            return fail("No stack class");
        Class<?> _class = classDescription.get().getJavaClass();

        try {
            // Create the stack
            StackRepresentation stack = new StackRepresentation(_class, autoGrade);
            stack.instantiate();

            // Push some numbers
            stack.push(2);
            stack.push(4);
            stack.push(6);
            stack.push(8);
            stack.push(10);
            stack.push(12);

            // Pop them and check
            double passes = 0;
            String notes = "";
            if ((Integer) stack.pop() == 12)
                passes++;
            else
                notes += "Expected pop to return 12\n";
            if ((Integer) stack.pop() == 10)
                passes++;
            else
                notes += "Expected pop to return 10\n";
            if ((Integer) stack.pop() == 8)
                passes++;
            else
                notes += "Expected pop to return 8\n";
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
            return partialPass(passes / 6, notes);

        } catch (NoSuchMethodException e) {
            return fail("Missing methods in stack class");
        } catch (InvocationTargetException e) {
            return fail("Stack method threw an error.");
        } catch (InstantiationException e) {
            return fail("Unable to create stack (invalid constructor?)");
        } catch (IllegalAccessException e) {
            return fail("Invalid privacy in stack.");
        }

    }


}
