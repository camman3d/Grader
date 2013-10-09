package grader.assignment;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import grader.checkers.CheckResult;
import grader.checkers.FeatureChecker;
import grader.documents.DocumentDisplayerRegistry;
import grader.file.FileProxy;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Label;
import util.annotations.Position;
import util.annotations.Row;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Visible;
import util.misc.TeePrintStream;
import util.models.AListenableVector;
import util.models.ListenableVector;
import util.trace.Tracer;
import bus.uigen.ObjectEditor;
import bus.uigen.attributes.AttributeNames;
// a feature to be implemented by a student
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AGradingFeature implements GradingFeature{
	public static final String FEEDBACK_FILE_PREFIX = "Feeback-";
	public static final String FEEDBACK_FILE_SUFFIX = ".txt";

	String featureName = "Some feature";
//	String comment = " ";
	double maxScore;
	double score;
	boolean graded;
	boolean autoGraded;
	boolean extraCredit;
	FileProxy feedbackFolder;
	FeatureChecker featureChecker;
	String feedbackFileName;
	SakaiProject project;
	SakaiProjectDatabase projectDatabase;
	String[] outputFiles;
	String[] inputFiles;
	GradingFeature linkedFeature;
	



	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	public AGradingFeature(String aFeature, double aMaxScore) {
		featureName = aFeature;
		score = aMaxScore;
		maxScore = aMaxScore;
//		score = aMaxScore;
//		maxScore = aMaxScore;
//		if (feedbackFolder != null)
//		feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + aFeature + FEEDBACK_FILE_SUFFIX ;
//		featureChecker = aFeatureChecker;
		
	}
	@Visible(false)
	public GradingFeature getLinkedFeature()  {
		return linkedFeature;
	}
	public void setLinkedFeature(GradingFeature aGradingFeature)  {
		 linkedFeature = aGradingFeature;
	}
	public AGradingFeature(String aFeature, double aMaxScore, boolean anExtraCredit) {
		this(aFeature, aMaxScore);
		extraCredit = anExtraCredit;

//		featureName = aFeature;
//		score = aMaxScore;
//		maxScore = aMaxScore;
//		score = aMaxScore;
//		maxScore = aMaxScore;
//		if (feedbackFolder != null)
//		feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + aFeature + FEEDBACK_FILE_SUFFIX ;
//		featureChecker = aFeatureChecker;
		
	}
	public String toString() {
		return "Feature:" + featureName + "\tMax:" + maxScore + "\tScore: " + score +  (extraCredit?"\tExtra":"");
		
	}
	public AGradingFeature(String aFeature, double aMaxScore, FeatureChecker aFeatureChecker) {
		featureName = aFeature;
		maxScore = aMaxScore;
		score = aMaxScore;
		featureChecker = aFeatureChecker;
		featureChecker.init(this);
//		score = aMaxScore;
//		maxScore = aMaxScore;
//		if (feedbackFolder != null)
//		feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + aFeature + FEEDBACK_FILE_SUFFIX ;
//		featureChecker = aFeatureChecker;
		
	}
	public AGradingFeature(String aFeature, double aMaxScore, FeatureChecker aFeatureChecker, boolean anExtraCredit) {
		this(aFeature, aMaxScore, aFeatureChecker);
		extraCredit = anExtraCredit;
//		score = aMaxScore;
//		maxScore = aMaxScore;
//		if (feedbackFolder != null)
//		feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + aFeature + FEEDBACK_FILE_SUFFIX ;
//		featureChecker = aFeatureChecker;
		
	}
//	@Override
//	public void init(FeatureChecker aFeatureChecker) {
////		maxScore = aMaxScore;
////		if (feedbackFolder != null)
////		feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + aFeature + FEEDBACK_FILE_SUFFIX ;
//		featureChecker = aFeatureChecker;
//		
//	}
	@Visible(false)
	public FeatureChecker getFeatureChecker() {
		return featureChecker;
	}
	@Visible(false)
	public String[] getOutputFiles() {
		return outputFiles;
	}
	public void setOutputFiles(String[] outputFiles) {
		this.outputFiles = outputFiles;
	}
	@Visible(false)
	public String[] getInputFiles() {
		return inputFiles;
	}
	public void setInputFiles(String[] inputFiles) {
		this.inputFiles = inputFiles;
	}
	@Label("Extra")
	@ComponentWidth(60)
	@Position(4)

	public boolean isExtraCredit() {
		return extraCredit;
	}
//
//	public void setExtraCredit(boolean newValue) {
//		 extraCredit = newValue;
//	}
	
	public boolean preAutoGrade() {
//		return featureChecker != null || cannotAutoGrade;
		return featureChecker != null &&
				!cannotAutoGrade && 
				!graded && 
				project != null && 
				project.hasBeenRun() && 
				project.canBeRun();

	}
	
	public void setProject(SakaiProject aProject) {
		project = aProject;
		if (featureChecker != null)
		featureChecker.setProject(aProject);
		feedbackFolder = aProject.getStudentAssignment().getFeedbackFolder();
		feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + featureName + FEEDBACK_FILE_SUFFIX ;
		graded = false;
		cannotAutoGrade = false;
//		score = 0;
		firePropertyChange("this", null, this);

	}
	
	public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) {
		projectDatabase = aProjectDatabase;
	}
	
	
	@Visible(false)
	public String getFeedbackFileName() {
		return feedbackFileName;
	}
	
	static void recordAutoGrade(GradingFeature aGradingFature, CheckResult result ) {
		try {
//			String feedbackFileName = feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + featureName + FEEDBACK_FILE_SUFFIX ;

			
//			CheckResult result = featureChecker.check();
			FileOutputStream fileOutputStream = new FileOutputStream(aGradingFature.getFeedbackFileName());
//			TeePrintStream outStream = new TeePrintStream(fileOutputStream);
			PrintStream outStream = new PrintStream(fileOutputStream);

			outStream.println("Score:" + result.getScore());
//			System.out.println("Score:" + result.getScore());

//			purseSetScore(result.getScore());
			for (String string:result.getLog()) {
				outStream.println(string);
				System.out.println(string);
			}
			outStream.close();
//			System.out.println("closing output");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	boolean cannotAutoGrade;
	
	public void autoGrade() {
		
//		try {
			if (cannotAutoGrade)
				return;
			
			CheckResult result = featureChecker.check();
			if (result == null) {
				Tracer.error("Could not autograde:" + this.getFeature());
				cannotAutoGrade = true;
				pureSetScore(0);

				return;
			}
			pureSetScore(result.getScore());
//			recordAutoGrade(this, result);
			projectDatabase.getAutoFeedback().recordAutoGrade(this, result);
			

//			FileOutputStream fileOutputStream = new FileOutputStream(feedbackFileName);
//			PrintStream outStream = new PrintStream(fileOutputStream);
//
//			outStream.println("Score:" + result.getScore());
////			System.out.println("Score:" + result.getScore());
//
//			purseSetScore(result.getScore());
//			for (String string:result.getLog()) {
//				outStream.println(string);
//				System.out.println(string);
//			}
//			outStream.close();
////			System.out.println("closing output");
			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	static void comment(GradingFeature aGradingFeature) {
		DocumentDisplayerRegistry.display(aGradingFeature.getFeedbackFileName());
	}
	
	@Override
	public void comment() {
//		DocumentDisplayerRegistry.display(feedbackFileName);
//		comment(this);
		projectDatabase.getManualFeedback().comment(this);
	}
	@Position(0)
	@ComponentWidth(100)
	public String getFeature() {
		return featureName;
	}
//	public void setFeature(String feature) {
//		this.feature = feature;
//	}
	@Position(1)
	@ComponentWidth(30)
	public double getMax() {
		return maxScore;
	}
//	public void setMax(int maxScore) {
//		this.maxScore = maxScore;
//	}
	@Position(2)
	@ComponentWidth(40)
	public double getScore() {
		return score;
	}
	public void reset() {
		pureSetScore(maxScore);
	}
	public boolean preSetScore() {
		return  featureChecker == null || preAutoGrade();
	}
	public void setScore(double score) {
		pureSetScore(score);
		if (score != maxScore)
		comment();
		
	}
	public void initScore(double score) {
//		graded = true;
//		double oldScore = this.score;
		this.score = score;
//		propertyChangeSupport.firePropertyChange("Score", oldScore, score);
//		firePropertyChange("Graded", null, true);

		
		
	}
	public void pureSetScore(double score) {
		pureSetGraded(true);
//		graded = true;
//		if (linkedFeature != null) {
//			linkedFeature.setGraded(true);
//		}
		
		double oldScore = this.score;
		this.score = score;
//		propertyChangeSupport.firePropertyChange("Score", oldScore, score);
//		firePropertyChange("Graded", null, true);
		propertyChangeSupport.firePropertyChange("Score", oldScore, score);
		firePropertyChange("Graded", null, true);
//		firePropertyChange("this", null, this);

		
		
	}
//	public boolean preIsGraded() {
//		return !graded;
//	}
	@util.annotations.Explanation("test")
	@Position(5)
	@ComponentWidth(70)
	public boolean isGraded() {
		return graded;
	}
	public boolean preSetGraded() {
//		return (featureChecker == null || (project != null && project.hasBeenRun() && project.canBeRun() && preAutoGrade();
//		return (featureChecker == null || (project != null && project.hasBeenRun() && project.canBeRun() && preAutoGrade();

		return preSetScore();

	}
	public void pureSetGraded(boolean newValue) {
		if (graded == newValue)
			return;
	
		graded = newValue;
		if (linkedFeature != null && graded && !cannotAutoGrade) {
			linkedFeature.setGraded(true);
		}
		if (cannotAutoGrade) {
			propertyChangeSupport.firePropertyChange("this", null, this); // our companion has been graded, so we are also, and let the preconditions update
			return;
		}
//		propertyChangeSupport.firePropertyChange("this", null, this); // our companion has been graded, so we are also, and let the preconditions update

		
	}
	public void setGraded(boolean newValue) {
		if (graded == newValue)
			return;
		graded = newValue;
//		pureSetGraded(newValue);
	
//		graded = newValue;
//		if (linkedFeature != null && graded) {
//			linkedFeature.setGraded(true);
//		}
//		if (cannotAutoGrade) {
//			propertyChangeSupport.firePropertyChange("this", null, this); // our companion has been graded, so we are also, and let the preconditions update
//			return;
//		}
//		;
		if (featureChecker != null) {
			autoGrade();
		} else {
			correct();
		}
//		firePropertyChange("Graded", null, true);

	}
	
	
//	public boolean isGraded() {
//		return graded;
//	}
//	@Column(3)
	@Position(3)

	public void correct() {
		pureSetScore(maxScore);
	}
	@Override
	@Position(3)
	@Label("Auto")
	@ComponentWidth(60)
	public boolean isAutoGradable() {
		return featureChecker != null;
	}
//	
//	public String getComment() {
//		return comment;
//	}
////	@Column(4)
//	public void setComment(String aComment) {
//		comment = aComment;
//	}
	
	
@Override
public void addPropertyChangeListener(PropertyChangeListener aListener) {
	propertyChangeSupport.addPropertyChangeListener(aListener);
	
	
}
@Override
public void removePropertyChangeListener(PropertyChangeListener aListener) {
	propertyChangeSupport.removePropertyChangeListener(aListener);

	
}

public void firePropertyChange(String aName, Object anOldValue, Object aNewValue) {
	propertyChangeSupport.firePropertyChange(aName, anOldValue, aNewValue);
}

public static void main (String[] args) {
	ObjectEditor.setMethodAttribute(AGradingFeature.class, "correct", AttributeNames.SHOW_BUTTON, true);
	ListenableVector list = new AListenableVector();
	GradingFeature gradingFeature = new AGradingFeature("foo", 50);
//	gradingFeature.init( null);
	list.add(gradingFeature);
	gradingFeature = new  AGradingFeature("bar", 100);
//	gradingFeature.init(null);
	list.add(gradingFeature);
//	list.add(new AGradingFeature("bar").init (100, null);
//	ObjectEditor.edit(new AFeature("foo", 50));
	ObjectEditor.edit(list);
}
	

}
