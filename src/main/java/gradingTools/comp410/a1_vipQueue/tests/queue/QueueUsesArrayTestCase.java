package gradingTools.comp410.a1_vipQueue.tests.queue;

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
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueUsesArrayTestCase extends BasicTestCase {

    public QueueUsesArrayTestCase() {
        super("Queue uses an array test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "Queue" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName("Queue", autoGrade);
        if (classDescription.isEmpty())
            return fail("No queue class");
        Class<?> _class = classDescription.get().getJavaClass();

        try {
            // Create the queue
            QueueRepresentation queue = new QueueRepresentation(_class, autoGrade);
            queue.instantiate();

            // Get the array field
            Field arrayField = queue.getArrayField();
            if (arrayField == null)
                return fail("There's no array field");
            arrayField.setAccessible(true);
            Object[] elements = (Object[]) arrayField.get(queue.getInstantiation());
            Object[] initialFieldValue = new Object[elements.length];
            System.arraycopy(elements, 0, initialFieldValue, 0, elements.length);

            // Enqueue something and check to see if only one element of the array has changed
            queue.enqueue(11);
            int changed = 0;
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] != initialFieldValue[i])
                    changed++;
            }

            if (changed == 0)
                return fail("Array property not used.");
            if (changed == 1)
                return pass();
            return fail("More than one element of array property was changed. There should only be one.");

        } catch (NoSuchMethodException e) {
            return fail("Missing methods in queue class");
        } catch (InvocationTargetException e) {
            return fail("Queue method threw an error.");
        } catch (InstantiationException e) {
            return fail("Unable to create queue (invalid constructor?)");
        } catch (IllegalAccessException e) {
            return fail("Invalid privacy in queue.");
        } catch (NoSuchFieldException e) {
            return fail("Missing array field in class");
        }
    }
}
