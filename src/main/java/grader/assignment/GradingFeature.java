package grader.assignment;

import grader.checkers.FeatureChecker;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import util.models.PropertyListenerRegisterer;

import java.beans.PropertyChangeListener;

public interface GradingFeature extends PropertyListenerRegisterer {

	public String getFeature();
	public double getMax();
	public double getScore();
	public void setScore(double score);
	public void purseSetScore(double score);
	public void initScore(double score);


	public boolean isAutoGradable();
	public void autoGrade();
	public void setProject(SakaiProject aProject);
	void removePropertyChangeListener(PropertyChangeListener aListener);
	public void reset();
	void comment();
	public boolean isExtraCredit() ;
	
	public String[] getOutputFiles() ;
	public void setOutputFiles(String[] outputFiles) ;
	public String[] getInputFiles() ;
	public void setInputFiles(String[] inputFiles) ;
	public String getFeedbackFileName();
	public boolean isGraded();
	public void setGraded(boolean newValue) ;
	public void firePropertyChange(String aName, Object anOldValue, Object aNewValue);
	public FeatureChecker getFeatureChecker() ;
	public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase);

}
