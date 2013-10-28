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
public class CannedTestCase1 extends BasicTestCase {

    public CannedTestCase1() {
        super("Multiple grade example");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        return partialPass(0.5, "Here is a reason.");
    }
}
