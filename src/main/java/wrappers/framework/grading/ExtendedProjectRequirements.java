package wrappers.framework.grading;

import framework.grading.FrameworkProjectRequirements;
import wrappers.framework.grading.testing.FeatureWrapper;
import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.checkers.FeatureChecker;

/**
 * This is similar to {@link FrameworkProjectRequirements} but extends it to allow for adding of
 * {@link grader.assignment.GradingFeature} and {@link grader.checkers.FeatureChecker} objects.
 */
public class ExtendedProjectRequirements extends FrameworkProjectRequirements {

    public void addFeature(GradingFeature feature) {
        addFeature(new FeatureWrapper(feature));
    }

    public void addFeature(String name, double points, FeatureChecker checker) {
        addFeature(name, points, false, checker);
    }

    public void addFeature(String name, double points, boolean extraCredit, FeatureChecker checker) {
        addFeature(new AGradingFeature(name, points, checker, extraCredit));
    }

}
