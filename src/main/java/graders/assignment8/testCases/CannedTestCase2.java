package graders.assignment8.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/28/13
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CannedTestCase2 extends BasicTestCase {

    public CannedTestCase2() {
        super("Multiple grade example");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (autoGrade)
            throw new NotAutomatableException();

        return pass();
    }
}
