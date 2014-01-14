package comp410.vipQueue.tests;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/13/14
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class UsesArrayTestCase extends BasicTestCase {

    public UsesArrayTestCase() {
        super("Uses array test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Option<ClassDescription> arrayQueue = project.getClassesManager().get().findByClassName("VIPQueueArray");
        if (arrayQueue.isEmpty())
            throw new NotGradableException();

        // Use reflection to look at the fields. We are looking for an array
        Class<?> _class = arrayQueue.get().getJavaClass();
        Field[] fields = _class.getDeclaredFields();
        boolean arrayFound = false;
        for (Field field : fields)
            arrayFound |= field.getType().isArray();

        if (arrayFound)
            return pass();
        return fail("No array field");
    }


}
