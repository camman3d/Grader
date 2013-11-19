package wrappers.framework.grading.testing;

import framework.grading.testing.Feature;
import grader.assignment.GradingFeature;

/**
 * This allows for adding GradingFeatures as a Feature
 */
public class FeatureWrapper extends Feature {

    public FeatureWrapper(GradingFeature gradingFeature) {
        super(gradingFeature.getFeature(), gradingFeature.getMax(), gradingFeature.isExtraCredit(), new TestCaseWrapper(gradingFeature));
    }
}
