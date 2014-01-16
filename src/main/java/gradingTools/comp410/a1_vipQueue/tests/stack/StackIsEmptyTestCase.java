package gradingTools.comp410.a1_vipQueue.tests.stack;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import gradingTools.comp410.a1_vipQueue.tests.queue.QueueRepresentation;
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
public class StackIsEmptyTestCase extends BasicTestCase {

    public StackIsEmptyTestCase() {
        super("Stack isEmpty test case");
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

            // stack should be empty
            double passes = 0;
            String notes = "";
            if (stack.isEmpty())
                passes++;
            else
                notes += "Stack should be empty before adding.\n";

            // Push something then check
            stack.push(3);
            if (!stack.isEmpty())
                passes++;
            else
                notes += "Stack should not be empty after adding.\n";

            return partialPass(passes / 2, notes);

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
