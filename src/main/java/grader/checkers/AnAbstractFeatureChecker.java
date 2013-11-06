package grader.checkers;

import grader.assignment.GradingFeature;
import grader.sakai.project.SakaiProject;


public abstract class AnAbstractFeatureChecker implements FeatureChecker {
    protected GradingFeature feature;
    boolean overridable = true;
    protected SakaiProject project;

    @Override
    public void init(GradingFeature aFeature) {
        feature = aFeature;
    }

    public void setProject(SakaiProject aProject) {
        project = aProject;
    }

    public boolean isOverridable() {
        return overridable;
    }

    public void setOverridable(boolean newVal) {
        overridable = newVal;
    }

}
