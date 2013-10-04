package framework.grading.testing;

import framework.project.Project;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Checkable {

    /**
     * This checks the test cases against the project
     *
     * @param points    The max points to award
     * @param testCases The test cases to test
     * @param project   The project the test cases will be checked against
     * @return The results of the check
     */
    protected CheckResult check(double points, List<TestCase> testCases, Project project) {
        double pointWeight = points / testCases.size();
        CheckResult result = new CheckResult(pointWeight);
        for (TestCase testCase : testCases) {
            TestCaseResult testResult = testCase.test(project);
            result.save(testResult);
        }
        return result;
    }

    /**
     * This is the publicly available check method, to be implemented by the extender
     *
     * @param project The project to check
     * @return The results of the check
     */
    public abstract CheckResult check(Project project);
}
