package wrappers.grader.checkers;

import framework.grading.testing.Checkable;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import framework.utils.GradingEnvironment;
import framework.utils.GradingManifest;
import wrappers.framework.project.ProjectWrapper;
import grader.checkers.ACheckResult;
import grader.checkers.CheckResult;

/**
 * This wraps a {@link Checkable} in a feature checker so as to handle the exceptions.
 */
public class FeatureCheckerWrapper extends ErrorHandlingFeatureChecker {

    private Checkable checkable;

    public FeatureCheckerWrapper(Checkable checkable) {
        this.checkable = checkable;
        setOverridable(false);
    }

    @Override
    protected CheckResult doCheck() throws Exception {

        Project project = new ProjectWrapper(this.project, GradingManifest.getActiveManifest().getProjectName());
        framework.grading.testing.CheckResult checkResult = checkable.check(project, false);

        CheckResult result = new ACheckResult();

        // Check the test status. Only accept successes
        if (checkResult.getStatus() != framework.grading.testing.CheckResult.CheckStatus.Successful)
            throw new NotGradableException();

        result.setScore(checkResult.getScore());

        // Add general notes
        if (!checkResult.getNotes().isEmpty())
            result.getLog().add(checkResult.getNotes());

        // Add notes from test case results
        for (TestCaseResult testCaseResult : checkResult.getResults()) {
            if (!testCaseResult.getNotes().isEmpty())
                result.getLog().add(testCaseResult.getNotes());
        }

        return result;
    }
}
