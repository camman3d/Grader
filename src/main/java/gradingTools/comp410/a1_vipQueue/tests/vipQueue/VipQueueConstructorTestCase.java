package gradingTools.comp410.a1_vipQueue.tests.vipQueue;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;
import tools.classFinder2.ClassFinder;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/17/14
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class VipQueueConstructorTestCase extends BasicTestCase {

    public VipQueueConstructorTestCase() {
        super("Stack constructor test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "Queue" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName("VipQueue", autoGrade);
        if (classDescription.isEmpty())
            return fail("No VipQueue class");
        Class<?> _class = classDescription.get().getJavaClass();

        try {
            _class.getConstructor(Integer.class);
            return pass();
        } catch (NoSuchMethodException e) {
            try {
                _class.getConstructor(int.class);
                return pass();
            } catch (NoSuchMethodException e2) {
                return fail("No VipQueue(int) constructor");
            }
        }
    }
}
