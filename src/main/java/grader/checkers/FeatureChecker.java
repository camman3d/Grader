package grader.checkers;

import grader.assignment.GradingFeature;
import grader.sakai.project.SakaiProject;

public interface FeatureChecker {
	public CheckResult check();
	public void setProject(SakaiProject aProject); // TODO: I don't think this should go here
	void init(GradingFeature aFeature);
	public boolean isOverridable();
	public void setOverridable(boolean newVal) ;


}
