package gradingTools.comp410.a1_vipQueue.tests.queue;

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
public class QueueIsEmptyTestCase extends BasicTestCase {

    public QueueIsEmptyTestCase() {
        super("Queue isEmpty test case");
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

            // Queue should be empty
            double passes = 0;
            String notes = "";
            if (queue.isEmpty())
                passes++;
            else
                notes += "Queue should be empty before adding.\n";

            // Enqueue something then check
            queue.enqueue(3);
            if (!queue.isEmpty())
                passes++;
            else
                notes += "Queue should not be empty after adding.\n";

            return partialPass(passes / 2, notes);

        } catch (NoSuchMethodException e) {
            return fail("Missing methods in queue class");
        } catch (InvocationTargetException e) {
            return fail("Queue method threw an error.");
        } catch (InstantiationException e) {
            return fail("Unable to create queue (invalid constructor?)");
        } catch (IllegalAccessException e) {
            return fail("Invalid privacy in queue.");
        }
    }

}