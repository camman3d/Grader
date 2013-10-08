package grader.sakai.project;

import util.misc.ClearanceManager;
import util.models.PropertyListenerRegisterer;

import java.beans.PropertyChangeListener;


public interface ProjectStepper  extends ClearanceManager, PropertyListenerRegisterer, PropertyChangeListener{
	public void setProject(SakaiProject newVal) ;
	
	public void output();
	
	public void sources() ;
	public double getScore() ;
	public void setScore(double newVal) ;
	public  void waitForClearance() ;
	public SakaiProjectDatabase getProjectDatabase() ;

	public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) ;
	public void setOnyen(String anOnyen) ;
	public void setProject(String anOnyen) ;
	public boolean isAutoRun() ;
    public void setAutoRun(boolean newVal);
    public void autoRun() ;
    public boolean hasMoreSteps();
	
	public void setHasMoreSteps(boolean newVal);
    public SakaiProject getProject();

	

}
