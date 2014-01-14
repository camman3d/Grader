package comp410.vipQueue.tests;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/13/14
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnqueueArrayTestCase extends BasicTestCase {

    public EnqueueArrayTestCase() {
        super("Enqueue array test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Option<ClassDescription> arrayQueue = project.getClassesManager().get().findByClassName("VIPQueueArray");
        if (arrayQueue.isEmpty())
            throw new NotGradableException();

        // Find the array field
        Class<?> _class = arrayQueue.get().getJavaClass();
        Field[] fields = _class.getDeclaredFields();
        Field arrayField = null;
        for (Field field : fields)
            if (field.getType().isArray())
                arrayField = field;

        // Now create an instance of the queue and enqueue something
        try {
            Object queue = _class.getConstructor(int.class).newInstance(10);
            Method enqueueMethod = _class.getMethod("enqueue", int.class);
            Method enqueueVIPMethod = _class.getMethod("enqueueVIP", int.class);

            // Invoke both the enqueue and the enqueueVIP methods a few times
            enqueueMethod.invoke(queue, 2);
            enqueueVIPMethod.invoke(queue, 3);
            enqueueMethod.invoke(queue, 12);
            enqueueVIPMethod.invoke(queue, 13);
            enqueueMethod.invoke(queue, 22);
            enqueueVIPMethod.invoke(queue, 23);

            // Now check the results
            double passCount = 0;
            String notes = "";
            int[] values = (int[]) arrayField.get(queue);

            if (values.length == 6)
                passCount++;
            else
                notes += "Expecting 6 items in array.\n";

            if (values[0] == 23)
                passCount++;
            else
                notes += "Expecting item at index 0 to be 23\n";

            if (values[1] == 13)
                passCount++;
            else
                notes += "Expecting item at index 1 to be 13\n";

            if (values[2] == 3)
                passCount++;
            else
                notes += "Expecting item at index 2 to be 33\n";

            if (values[3] == 2)
                passCount++;
            else
                notes += "Expecting item at index 3 to be 2\n";

            if (values[4] == 12)
                passCount++;
            else
                notes += "Expecting item at index 4 to be 12\n";

            if (values[5] == 22)
                passCount++;
            else
                notes += "Expecting item at index 5 to be 22\n";

            return partialPass(passCount / 7, notes);

        } catch (Exception e) {
            throw new NotGradableException();
        }
    }


}
