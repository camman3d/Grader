package gradingTools.assignment11.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class PassAndFailTestCase extends BasicTestCase {
    public PassAndFailTestCase() {
        super("Pass and fail test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        return pass();
    }
}
