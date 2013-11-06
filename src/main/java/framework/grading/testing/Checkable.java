package framework.grading.testing;

import framework.project.Project;

import java.util.List;

/**
 * The idea for this class is that features and restrictions both check their test cases. This handles that process.
 */
public abstract class Checkable implements Gradable {

    /**
     * This checks the test cases against the project
     *
     * @param points    The max points to award
     * @param testCases The test cases to test
     * @param project   The project the test cases will be checked against
     * @return The results of the check
     */
    protected CheckResult check(double points, List<TestCase> testCases, Project project, boolean autoMode) {
        double pointWeight = points / testCases.size();
        CheckResult result = new CheckResult(pointWeight, this);
        try {
            for (TestCase testCase : testCases) {
                TestCaseResult testResult = testCase.test(project, autoMode);
                result.save(testResult);
            }
            result.setStatus(CheckResult.CheckStatus.Successful);
            return result;
        } catch (NotAutomatableException e) {
            return new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, this);
        } catch (Exception e) {
//            e.printStackTrace();
            return new CheckResult(0, "", CheckResult.CheckStatus.Failed, this);
        }
    }


    public CheckResult check(Project project) {
        return check(project, true);
    }

    /**
     * This is the publicly available check method, to be implemented by the extender
     *
     * @param project The project to check
     * @param autoMode If we are auto grading, that is, to display GUIs/user interaction
     * @return The results of the check
     */
    public abstract CheckResult check(Project project, boolean autoMode);


}
