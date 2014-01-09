package examples.checkers.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/6/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class FailingTestCase extends BasicTestCase {
    public FailingTestCase() {
        super("Failing test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        return fail("Just for fun.", autoGrade);
    }
}
