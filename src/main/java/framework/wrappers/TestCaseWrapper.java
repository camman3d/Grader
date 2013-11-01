package framework.wrappers;

import framework.grading.testing.*;
import framework.project.Project;
import grader.assignment.GradingFeature;
import grader.checkers.*;
import grader.checkers.CheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/31/13
 * Time: 10:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseWrapper extends BasicTestCase {

    private GradingFeature feature;
    private FeatureChecker featureChecker;

    public TestCaseWrapper(GradingFeature feature) {
        super(feature.getFeature() + " test case");
        this.feature = feature;
        featureChecker = feature.getFeatureChecker();
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        CheckResult result = featureChecker.check();
        if (result == null)
            throw new NotGradableException();
        String notes = "";
        for (String line : result.getLog())
            notes += (notes.isEmpty() ? "\n" : "") + line;
        return partialPass(result.getScore() / feature.getMax(), notes);
    }
}
