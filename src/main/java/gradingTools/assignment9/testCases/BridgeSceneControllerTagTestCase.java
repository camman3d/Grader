package gradingTools.assignment9.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/6/13
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BridgeSceneControllerTagTestCase extends BasicTestCase {
    public BridgeSceneControllerTagTestCase() {
        super("Bridge scene controller tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        if (project.getClassesManager().get().findByTag("Bridge Scene Controller").isEmpty())
            return fail("No class found with tag \"Bridge Scene Controller\"");
        else
            return pass();
    }
}
