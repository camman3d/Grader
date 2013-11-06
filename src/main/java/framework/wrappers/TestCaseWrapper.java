package framework.wrappers;

import framework.grading.testing.*;
import framework.project.Project;
import grader.assignment.GradingFeature;
import grader.checkers.*;
import grader.checkers.CheckResult;

/**
 * This wraps a feature in a test case so the "grader" checkers can run in the "framework" system.
 */
public class TestCaseWrapper extends BasicTestCase {

    private GradingFeature feature;
    private FeatureChecker featureChecker;

    public TestCaseWrapper(GradingFeature feature) {
        super(feature.getFeature() + " test case");
        this.feature = feature;
        featureChecker = feature.getFeatureChecker();
    }

    /**
     * This should only ever be used with a
     * @param project The project to test
     * @param autoGrade
     * @return
     * @throws NotAutomatableException
     * @throws NotGradableException
     */
    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        featureChecker.setProject(((ProjectWrapper) project).getProject());

        CheckResult result = featureChecker.check();
        if (result == null)
            throw new NotGradableException();
        String notes = "";
        for (String line : result.getLog())
            notes += (notes.isEmpty() ? "\n" : "") + line;
        return partialPass(result.getScore() / feature.getMax(), notes);
    }
}
