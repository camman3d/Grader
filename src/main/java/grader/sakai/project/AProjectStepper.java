package grader.sakai.project;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;
import grader.assignment.GradingFeature;
import grader.documents.DocumentDisplayerRegistry;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;
import util.annotations.*;
import util.misc.AClearanceManager;
import util.misc.Common;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AProjectStepper extends AClearanceManager implements ProjectStepper {
	PropertyChangeSupport propertyChangeSupport =new PropertyChangeSupport(this);
	public final static long  UI_LOAD_TIME = 10*1000;
	boolean firstTime;
	List<OEFrame>  oldList;
	Window[] oldWindows;
	String name = "";
	SakaiProjectDatabase projectDatabase;
	double score;
	List<String> documents;
	int nextDocumentIndex = 0;
	FeatureGradeRecorder featureGradeRecorder;
	
	String onyen = "";
	
	SakaiProject project;
//	FinalGradeRecorder gradeRecorder;
	boolean hasMoreSteps = true;
	FinalGradeRecorder totalScoreRecorder;
	boolean manualOnyen;
	
//	FinalGradeRecorder gradeRecorder() {
//		return projectDatabase.getGradeRecorder();
//	}
//	FinalGradeRecorder totalScoreRecorder() {
//		return projectDatabase.getTotalScoreRecorder();
//	}
	public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) {
		projectDatabase = aProjectDatabase;
//		gradeRecorder = aProjectDatabase.getGradeRecorder();
		featureGradeRecorder = aProjectDatabase.getFeatureGradeRecorder();
		totalScoreRecorder = aProjectDatabase.getTotalScoreRecorder();
		registerWithGradingFeatures();
		
	}
	boolean runExecuted;
	
	boolean runAttempted() {
		return runExecuted|| isAutoRun();
	}
	
	@ComponentWidth(150)
	@Row(0)
	public String getOnyen() {
		return onyen;
	}
	
	public void setOnyen(String anOnyen) {
//		project = projectDatabase.getProject(anOnyen);
		setProject(anOnyen);
		projectDatabase.resetRunningProject(project);

		if (autoRun)
		projectDatabase.runProject(anOnyen, project);
		manualOnyen = true;
	
//		projectDatabase.runProjectInteractively(anOnyen, this);
//		onyen = anOnyen;
//		setProject( projectDatabase.getProject(anOnyen));
//		projectDatabase.
		
		
	}
	public void setProject(String anOnyen) {
		onyen = anOnyen;
		setProject( projectDatabase.getProject(anOnyen));
				
	}
	boolean autoRun = false;
	public boolean isAutoRun() {
		return autoRun;
		
	}
    public void setAutoRun(boolean newVal) {
    	autoRun = newVal;
		
	}
    public void autoRun() {
    	autoRun = !autoRun;
    }
	@Row(1)
	@ComponentWidth(150)

	public String getName() {
		return name;
	}
	
	public void setName(String newVal) {
		name = newVal;
//		System.out.println("name changed to" + newVal);
//		propertyChangeSupport.firePropertyChange("Name", null, newVal);
		notifyPreconditionChanged();
//		System.out.println("precondition notified");
	}
	
	void notifyPreconditionChanged() {
		propertyChangeSupport.firePropertyChange("this", null, this);

	}
	@Row(2)
	@ComponentWidth(150)

	public double getScore() {
		return score;
	}
	void registerWithGradingFeatures() {
		if (projectDatabase == null)
			return;
		List<GradingFeature> gradingFeatures = projectDatabase.getGradingFeatures();
		for (GradingFeature aGradingFeature:gradingFeatures) {
			aGradingFeature.addPropertyChangeListener(this);
		}
	}
	void resetGradingFeatures() {
		if (projectDatabase == null)
			return;
		
		List<GradingFeature> gradingFeatures = projectDatabase.getGradingFeatures();
		for (GradingFeature aGradingFeature:gradingFeatures) {
//			double lastScore = featureGradeRecorder.getGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), aGradingFeature.getFeature());
			double lastScore = getGrade(aGradingFeature.getFeature());

			aGradingFeature.initScore(lastScore);
			aGradingFeature.setProject(project);
		}
	}
    void setScore() {
    	List<GradingFeature> gradingFeatures = projectDatabase.getGradingFeatures();
    	double aScore = 0;
		for (GradingFeature aGradingFeature:gradingFeatures) {
			aScore += aGradingFeature.getScore();
		}
		setScore(aScore);
	}
    @Visible(false)
    public SakaiProject getProject() {
    	return project;
    }
	public static void writeScores(ProjectStepper aProjectStepper) {
		aProjectStepper.getProjectDatabase().getScoreFeedback().writeScores(aProjectStepper);
//		if (aProjectStepper.getProject() == null) return;
//		FileProxy feedbackFolder = aProjectStepper.getProject().getStudentAssignment().getFeedbackFolder();
//		String totalScoresFile = feedbackFolder.getAbsoluteName() + "/" + ASakaiProjectDatabase.DEFAULT_SCORE_FILE_NAME;
//		try {
//		Common.writeFile(totalScoresFile, scoresText(aProjectStepper).toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
//	public static void writeScores(ProjectStepper aProjectStepper) {
//		if (project == null) return;
//		FileProxy feedbackFolder = project.getStudentAssignment().getFeedbackFolder();
//		String totalScoresFile = feedbackFolder.getAbsoluteName() + "/" + ASakaiProjectDatabase.DEFAULT_SCORE_FILE_NAME;
//		try {
//		Common.writeFile(totalScoresFile, scoresText().toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	public static StringBuffer scoresText(ProjectStepper aProjectStepper) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Total Score:" + aProjectStepper.getScore());
    	List<GradingFeature> gradingFeatures = aProjectStepper.getProjectDatabase().getGradingFeatures();

		 for (GradingFeature aGradingFeature:gradingFeatures) {
			stringBuffer.append("\n");
			stringBuffer.append(aGradingFeature.toString());
		}
		 return stringBuffer;
		
	}

	@Visible(false)
	public void setProject(SakaiProject newVal) {
		writeScores(this);
		runExecuted = false;
		project = newVal;
		if (project == null) {
			System.out.println("No project submitted for dewan");
			return;
		}
//		setName(project.getStudentAssignment().getStudentDescription());
		setName(project.getStudentAssignment().getStudentName());

		documents = project.getStudentAssignment().getDocuments();
		resetGradingFeatures();
//		documents.remove(project.getOutputFileName());
//		documents.remove(project.getSourceFileName());
		nextDocumentIndex = 0;
		if (totalScoreRecorder != null)
			setInternalScore(getGrade());

//			setInternalScore(gradeRecorder.getGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen()));
		
	}
	
	public boolean preOutput() {
//		return project.canBeRun();
		return runAttempted() && project.canBeRun();

	}

	public boolean preRun() {
		return project.canBeRun() && !autoRun && !runExecuted;
	}
	@Row(3)
	@ComponentWidth(100)

	public void run() {
		runExecuted = true;
		projectDatabase.runProject(onyen, project);
		project.setHasBeenRun(true);
		for (GradingFeature gradingFeature:projectDatabase.getGradingFeatures()) {
			if (gradingFeature.isAutoGradable()) {
				gradingFeature.firePropertyChange("this", null, gradingFeature);
			}
		}

	}
	@Row(4)
	@ComponentWidth(100)
	
	public void output() {
		project.setHasBeenRun(true);

		projectDatabase.displayOutput();

	}
	
	public boolean preSources() {
//		return project.canBeRun();
		return project.runChecked() || project.hasBeenRun();
//		return true;

	}
	@Row(5)
	@ComponentWidth(100)
	public void sources() {
		project.setHasBeenRun(true);

		project.displaySource(projectDatabase);
		
	}
	@Row(6)
	@ComponentWidth(100)
	public void nextDocument() {
		if (nextDocumentIndex >= documents.size()) {
			System.out.println("No documents to display");
			return;
		}
		String nextDocument = documents.get(nextDocumentIndex);
		nextDocumentIndex++;
		if (nextDocumentIndex == documents.size())
			nextDocumentIndex = 0;
		DocumentDisplayerRegistry.display(nextDocument);
			
		
		
	}
	@Row(7)
	@ComponentWidth(100)
	public void comments() {
		DocumentDisplayerRegistry.display(project.getStudentAssignment().getCommentsFileName());
	}
	
	
	void setInternalScore(double newVal) {
		score = newVal;
		propertyChangeSupport.firePropertyChange("Score", null, newVal);
	}
	void setGrade(double newVal) {
//		gradeRecorder.setGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), newVal);
//		featureGradeRecorder.setGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), newVal);
		totalScoreRecorder.setGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), newVal);
	
	}
	
	double getGrade() {
		return totalScoreRecorder.getGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen());

	}
	void setGrade(String aFeature, double newVal) {
		featureGradeRecorder.setGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), aFeature, newVal);

	}
	double getGrade(String aFeature) {
		return featureGradeRecorder.getGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), aFeature);

	}
	public void setScore(double newVal) {
		setInternalScore(newVal);
		if (totalScoreRecorder != null)

//		if (gradeRecorder != null)
			setGrade(newVal);
//			gradeRecorder.setGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), newVal);
//		score = newVal;
//		propertyChangeSupport.firePropertyChange("Score", null, newVal);
	}
	
	
	public  boolean preGetGradingFeatures() {
		return projectDatabase != null && projectDatabase.getGradingFeatures().size() > 0;
	}
	public boolean preAutoGrade() {
		return project.runChecked() && project.canBeRun() && preGetGradingFeatures();
	}
//	
	@Row(8)
	@ComponentWidth(100)

	public void autoGrade() {
		project.setHasBeenRun(true);
		for (GradingFeature gradingFeature:projectDatabase.getGradingFeatures()) {
			if (gradingFeature.isAutoGradable()) {
				gradingFeature.autoGrade();
			}
		}
		
	}
	
	@Row(9)
	public List<GradingFeature> getGradingFeatures() {
		if (projectDatabase != null)
		return projectDatabase.getGradingFeatures();
		else return null;
		
	}
	public boolean preProceed() {
		return hasMoreSteps || manualOnyen;
	}
	@Row(10)
	@ComponentWidth(100)
	public synchronized void proceed() {
		super.proceed();
		if (manualOnyen)
			writeScores(this);
		manualOnyen = false;
	
		
	}
	public boolean hasMoreSteps() {
		return hasMoreSteps;
	}
	
	public void setHasMoreSteps(boolean newVal) {
		if (hasMoreSteps == newVal) return;
		 hasMoreSteps = newVal;
		 if (hasMoreSteps == false)
				writeScores(this);

			notifyPreconditionChanged();

	}
	
	@Override
	public synchronized void waitForClearance() {
		
		

		
		super.waitForClearance();
		if (oldWindows != null && oldList != null) {// somebody went before me, get rid of their windows
//			System.out.println("dispoing old windows");
			List<OEFrame> newList = new ArrayList( uiFrameList.getList());
			


			for (OEFrame frame:newList) {
				if (oldList.contains(frame))
					continue;
				frame.dispose(); // will this work
			}
			Window[] newWindows =	Window.getWindows();
			
			
			for (Window frame:newWindows) {
				if (Common.containsReference(oldWindows, frame)) {
					continue;
				}
				frame.dispose();
			}
		}
//		System.out.println("picking up old windows");
//		List<OEFrame>  oldList = new ArrayList( uiFrameList.getList());
//		Window[] oldWindows =	Window.getWindows();
		oldList = new ArrayList( uiFrameList.getList());
		oldWindows =	Window.getWindows();
		 // get rid of previous project's windows
//		if (oldWindows != null || oldList != null) {
		
		}
		
//		}

		
	@Override
	public void addPropertyChangeListener(PropertyChangeListener aListener) {
		propertyChangeSupport.addPropertyChangeListener(aListener);
	}
	
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof GradingFeature && evt.getPropertyName().equalsIgnoreCase("score")) {
			GradingFeature aGradingFeature = (GradingFeature) evt.getSource();
//			setInternalScore(aGradingFeature.getScore());
			setScore();
			setGrade(aGradingFeature.getFeature(), aGradingFeature.getScore());
		}
		
	}


//	List<OEFrame> newList = new ArrayList( uiFrameList.getList());
	

//
//	for (OEFrame frame:newList) {
//		if (oldList.contains(frame))
//			continue;
//		frame.dispose(); // will this work
//	}
//	Window[] newWindows =	Window.getWindows();
//	
//	
//	for (Window frame:newWindows) {
//		if (Common.containsReference(oldWindows, frame)) {
//			continue;
//		}
//		frame.dispose();
//	}
	@Override
	@Visible(false)
	public SakaiProjectDatabase getProjectDatabase() {
		// TODO Auto-generated method stub
		return projectDatabase;
	}
		
	
	public static void main (String[] args) {
		ObjectEditor.edit(new AProjectStepper());
	}

	
	
	
	
}
