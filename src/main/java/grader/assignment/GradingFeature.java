package grader.assignment;

import java.beans.PropertyChangeListener;

import util.models.PropertyListenerRegisterer;
import grader.checkers.FeatureChecker;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;

public interface GradingFeature extends PropertyListenerRegisterer{
//	public void init(FeatureChecker aFeatureChecker);

	public String getFeature();
//	public void setFeature(String feature);
	public double getMax() ;
//	public void setMax(int maxScore) ;
	public double getScore() ;
	public void setScore(double score) ;
	public void pureSetScore(double score) ;
	public void initScore(double score) ;


	public boolean isAutoGradable();
	public void autoGrade();
	public void setProject(SakaiProject aProject);
	void removePropertyChangeListener(PropertyChangeListener aListener);
	public void reset();
	void comment();
	public boolean isExtraCredit() ;
	
//	public void setExtraCredit(boolean newValue) ;
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
	public GradingFeature getLinkedFeature() ;
	public void setLinkedFeature(GradingFeature aGradingFeature)  ;
	public void pureSetGraded(boolean newValue);








//	public boolean isGraded() ;
}
