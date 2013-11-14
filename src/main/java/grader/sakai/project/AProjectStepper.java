package grader.sakai.project;

import grader.assignment.GradingFeature;
import grader.assignment.GradingFeatureList;
import grader.documents.DocumentDisplayerRegistry;
import grader.file.FileProxy;
import grader.file.FileProxyUtils;
import grader.project.Project;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;
import grader.spreadsheet.FinalGradeRecorderFactory;
import grader.spreadsheet.TotalScoreRecorderSelector;

import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;
import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Visible;
import util.misc.AClearanceManager;
import util.misc.Common;
import util.misc.ThreadSupport;
import util.trace.Tracer;
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AProjectStepper extends AClearanceManager implements ProjectStepper{
	PropertyChangeSupport propertyChangeSupport =new PropertyChangeSupport(this);
	public final static long  UI_LOAD_TIME = 10*1000;
	boolean firstTime;
//	List<OEFrame>  oldList;
//	Window[] oldWindows;
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
	String logFile, gradedFile, skippedFile;
	
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
		logFile = aProjectDatabase.getAssigmentDataFolder().getLogFileName();
		gradedFile = aProjectDatabase.getAssigmentDataFolder().getGradedIdFileName();
		skippedFile = aProjectDatabase.getAssigmentDataFolder().getSkippedIdFileName();
//		recordWindows(); // the first project does not wait so we need to record here
		
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
		String oldOnyen = onyen;
		boolean retVal = setProject(anOnyen);
		if (!retVal) {
			onyen = oldOnyen;
			propertyChangeSupport.firePropertyChange("onyen", null, onyen);
			return;
		}
		projectDatabase.resetRunningProject(project);

		if (autoRun)
		projectDatabase.runProject(anOnyen, project);
		manualOnyen = true;
	
//		projectDatabase.runProjectInteractively(anOnyen, this);
//		onyen = anOnyen;
//		setProject( projectDatabase.getProject(anOnyen));
//		projectDatabase.
		
		
	}
	public boolean setProject(String anOnyen) {
		
		onyen = anOnyen;
		return setProject( projectDatabase.getProject(anOnyen));
		
				
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

//			aGradingFeature.initScore(lastScore);
			aGradingFeature.setProject(project);
//			aGradingFeature.initScore(lastScore); // this resets

		}
		for (GradingFeature aGradingFeature:gradingFeatures) {
//			double lastScore = featureGradeRecorder.getGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), aGradingFeature.getFeature());
			double lastScore = getGrade(aGradingFeature.getFeature());

//			aGradingFeature.initScore(lastScore);
//			aGradingFeature.setProject(project);
			aGradingFeature.initScore(lastScore);

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
	public boolean setProject(SakaiProject newVal) {
		if (newVal == null) {
            // Josh: I added this so that an event will file when there is no project.
            propertyChangeSupport.firePropertyChange("Score", null, 0);
            return false;
        }
		writeScores(this);
		runExecuted = false;
		project = newVal;
		if (project == null) {
			System.out.println("No project submitted for dewan");
			return false;
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
		return true;
	}
	
	public boolean preOutput() {
//		return project.canBeRun();
		return runAttempted() && project.canBeRun();

	}

	public boolean preRun() {
		return project.canBeRun() && !autoRun
//				&& !runExecuted
				;
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
	@Override
	public void setScore(double newVal) {
		setInternalScore(newVal);
		if (totalScoreRecorder != null)

//		if (gradeRecorder != null)
			setGrade(newVal);
//			gradeRecorder.setGrade(project.getStudentAssignment().getStudentName(), project.getStudentAssignment().getOnyen(), newVal);
//		score = newVal;
//		propertyChangeSupport.firePropertyChange("Score", null, newVal);
	}
	
	@Override
	public  boolean preGetGradingFeatures() {
		return projectDatabase != null && projectDatabase.getGradingFeatures().size() > 0;
	}
	@Override
	public boolean preAutoGrade() {
		return project.runChecked() && project.canBeRun() && preGetGradingFeatures();
	}
//	
	@Row(8)
	@ComponentWidth(100)
	@Override
	public void autoGrade() {
		project.setHasBeenRun(true);
		for (GradingFeature gradingFeature:projectDatabase.getGradingFeatures()) {
			if (gradingFeature.isAutoGradable()) {
				gradingFeature.autoGrade();
			}
		}
		
	}
	@Override
	@Row(9)
	public GradingFeatureList getGradingFeatures() {
		if (projectDatabase != null)
		return projectDatabase.getGradingFeatures();
		else return null;
		
	}
	public boolean preProceed() {
		return (hasMoreSteps || manualOnyen) && isAllGraded();
	}
	public boolean preSkip() {
		return !preProceed();
	}
	@Row(10)
	@Override
	public boolean isAllGraded() {
		return getGradingFeatures().isAllGraded();
	}
	
	
	
//	@Row(11)
//	@ComponentWidth(100)
	@Visible(false)
	public synchronized void proceed() {
		super.proceed();
		if (manualOnyen)
			writeScores(this);
		manualOnyen = false;
		try {
//			Common.appendText(logFile, onyen + " Skipped " + Common.currentTimeAsDate() + "\n\r");
			Common.appendText(gradedFile, onyen + "\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

//	@Row(12)
//	@ComponentWidth(100)
//	public synchronized void skip() {
//		proceed();
//		try {
//			Common.appendText(logFile, onyen + " Skipped " + Common.currentTimeAsDate() + "\n");
//			Common.appendText(skippedFile, onyen + "\n");
//			List<String> list = FileProxyUtils.toList(new File(logFile));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// should put person in skipped list
//		
//	}
	@Override
	public boolean preNext() {
		return !preDone() && nextOnyenIndex < onyens.size() - 1 ;
	}
	@Row(12)
	@ComponentWidth(100)
	@Override
	public synchronized void next() {
		
		try {
//			Common.appendText(logFile, onyen + " Skipped " + Common.currentTimeAsDate() + "\n");
			Common.appendText(skippedFile, onyen + "\n");
			List<String> list = FileProxyUtils.toList(new File(logFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		move(true);
		// should put person in skipped list
		
	}
	@Override
	public boolean prePrevious() {
		return nextOnyenIndex > 0;
	}
	@Row(13)
	@ComponentWidth(100)
	@Override
	public synchronized void previous() {
		
//		try {
//			Common.appendText(logFile, onyen + " Skipped " + Common.currentTimeAsDate() + "\n");
//			Common.appendText(skippedFile, onyen + "\n");
//			List<String> list = FileProxyUtils.toList(new File(logFile));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		move(false);
		// should put person in skipped list
		
	}
	
	String filter = "";
	@Row(14)
	@ComponentWidth(150)
	@Override
	public String getNavigationFilter() {
		return projectDatabase.getNavigationFilter();
	}
	@Override
	public void setNavigationFilter(String newVal) {
		 projectDatabase.setNavigationFilter(newVal);
		 configureNavigationList();
		 runProjectsInteractively();
	}
	@Override
	public boolean hasMoreSteps() {
		return hasMoreSteps;
	}
	@Override
	public void setHasMoreSteps(boolean newVal) {
		if (hasMoreSteps == newVal) return;
		 hasMoreSteps = newVal;
		 if (hasMoreSteps == false)
				writeScores(this);

			notifyPreconditionChanged();

	}
	
//	public void recordWindows() {
//		oldList = new ArrayList( uiFrameList.getList());
//		oldWindows =	Window.getWindows();
//	}
//	public void clearWindows() {
//		if (oldWindows != null && oldList != null) {// somebody went before me, get rid of their windows
////			System.out.println("dispoing old windows");
//			List<OEFrame> newList = new ArrayList( uiFrameList.getList());
//			
//
//
//			for (OEFrame frame:newList) {
//				if (oldList.contains(frame))
//					continue;
//				frame.dispose(); // will this work
//			}
//			Window[] newWindows =	Window.getWindows();
//			
//			
//			for (Window frame:newWindows) {
//				if (Common.containsReference(oldWindows, frame)) {
//					continue;
//				}
//				frame.dispose();
//			}
//		}
//	}
	
	@Override
	public synchronized void waitForClearance() {
		
		

		
		super.waitForClearance();
//		if (oldWindows != null && oldList != null) {// somebody went before me, get rid of their windows
////			System.out.println("dispoing old windows");
//			List<OEFrame> newList = new ArrayList( uiFrameList.getList());
//			
//
//
//			for (OEFrame frame:newList) {
//				if (oldList.contains(frame))
//					continue;
//				frame.dispose(); // will this work
//			}
//			Window[] newWindows =	Window.getWindows();
//			
//			
//			for (Window frame:newWindows) {
//				if (Common.containsReference(oldWindows, frame)) {
//					continue;
//				}
//				frame.dispose();
//			}
//		}
//		clearWindows();
//		recordWindows();

//		oldList = new ArrayList( uiFrameList.getList());
//		oldWindows =	Window.getWindows();

		
		}
		


		
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
		propertyChangeSupport.firePropertyChange("this", null, this); // an event from grading features, perhaps our precondition chnaged such as auoGraded
		
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
	List<String> onyens;
	int nextOnyenIndex = 0;
	String nextOnyen;
	@Override
	public void configureNavigationList() {
		onyens = projectDatabase.getOnyenNavigationList();
		nextOnyenIndex = 0;
		hasMoreSteps = true;
	}
	@Override
	public boolean preRunProjectsInteractively() {
		return onyens !=null && nextOnyenIndex < onyens.size();
	}
	@Override
	public void runProjectsInteractively() {
//		onyens = projectDatabase.getOnyenNavigationList();
		
//		nextOnyenIndex = 0;
//		if (nextOnyenIndex >= onyens.size())
//			return;
		if (!preRunProjectsInteractively()) {
			Tracer.error("Projects not configured");
			hasMoreSteps  = false;
			return;
		}
		String anOnyen = onyens.get(nextOnyenIndex);
		SakaiProject aProject = projectDatabase.getProject(anOnyen);
		projectDatabase.initIO();

		projectDatabase.recordWindows();		
		setProject(anOnyen);
		if (isAutoRun())
			projectDatabase.runProject(anOnyen, aProject);		
		
		setProject(anOnyen);
		
		

		
		
	}
	@Override
	public boolean preDone() {
		return preProceed();
	}
	@Override
	public synchronized void move(boolean forward) {
		projectDatabase.resetIO();
		projectDatabase.clearWindows();
		if (forward) {
		nextOnyenIndex++;
	
		if (nextOnyenIndex >= onyens.size()) {
			hasMoreSteps  = false;
			return;
		}
		} else {
			nextOnyenIndex--;
			if (nextOnyenIndex < 0) {
				hasMoreSteps  = false;
				return;
			}
			
		}
		String anOnyen = onyens.get(nextOnyenIndex);
		SakaiProject aProject = projectDatabase.getProject(anOnyen);
		projectDatabase.initIO();
		projectDatabase.recordWindows();		
		setProject(anOnyen);
		if (isAutoRun())
			projectDatabase.runProject(anOnyen, aProject);		
		
//		setProject(anOnyen);
	}
	
	@Override
	@Row(11)
	@ComponentWidth(100)
	public synchronized void done() {
		if (manualOnyen)
			writeScores(this);
		try {
//			Common.appendText(logFile, onyen + " Skipped " + Common.currentTimeAsDate() + "\n\r");
			Common.appendText(gradedFile, onyen + "\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		move(true);
//		projectDatabase.resetIO();
//		projectDatabase.clearWindows();
//		nextOnyenIndex++;
//		if (nextOnyenIndex >= onyens.size()) {
//			hasMoreSteps  = false;
//			return;
//		}
//		String anOnyen = onyens.get(nextOnyenIndex);
//		SakaiProject aProject = projectDatabase.getProject(anOnyen);
//		projectDatabase.initIO();
//		projectDatabase.recordWindows();		
//		setProject(anOnyen);
//		if (isAutoRun())
//			projectDatabase.runProject(anOnyen, aProject);		
//		
//		setProject(anOnyen);

		
		
		
	
		
	}
		
	
	public static void main (String[] args) {
		ObjectEditor.edit(new AProjectStepper());
	}

	

	
	
	
	
}
