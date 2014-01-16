package gradingTools.comp410.a1_vipQueue.tests.stack;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
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
public class StackUsesLinkedListTestCase extends BasicTestCase {

    public StackUsesLinkedListTestCase() {
        super("Stack uses linked list test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "Stack" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName("Stack", autoGrade);
        if (classDescription.isEmpty())
            return fail("No stack class");
        Class<?> _class = classDescription.get().getJavaClass();

        // Find the "Node" class
        Option<ClassDescription> nodeClassDescription = ClassFinder.get(project).findByName("Node", autoGrade);
        if (nodeClassDescription.isEmpty())
            return fail("No node class");
        Class<?> nodeClass = nodeClassDescription.get().getJavaClass();

        try {
            // Create the stack
            StackRepresentation stack = new StackRepresentation(_class, autoGrade);
            stack.instantiate();

            // Get the node property
            Field nodeField = null;
            for (Field field : _class.getDeclaredFields())
                if (field.getType() == nodeClass)
                    nodeField = field;
            if (nodeField == null)
                return fail("There is no node property");
            nodeField.setAccessible(true);
            Object nodeValue = nodeField.get(stack.getInstantiation());

            // Make sure that it's null
            if (nodeValue != null)
                return fail("Node should be null before adding");

            // Push something and then check again
            stack.push(23);
            nodeValue = nodeField.get(stack.getInstantiation());
            if (nodeValue != null)
                return pass();
            return fail("Adding to the stack should change the node property");

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
