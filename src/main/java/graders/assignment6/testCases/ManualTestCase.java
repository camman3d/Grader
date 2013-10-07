package graders.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ManualTestCase extends BasicTestCase {
    public ManualTestCase(String name) {
        super(name);
    }

    @Override
    public TestCaseResult test(Project project) throws NotAutomatableException {
        throw new NotAutomatableException();
    }
}
