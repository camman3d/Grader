package grader.checkers;

import grader.assignment.GradingFeature;
import grader.sakai.project.SakaiProject;

public interface FeatureChecker {
    public CheckResult check();

    public void setProject(SakaiProject aProject);

    void init(GradingFeature aFeature);

    public boolean isOverridable();

    public void setOverridable(boolean newVal);


}
