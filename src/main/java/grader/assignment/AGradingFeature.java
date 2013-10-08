package grader.assignment;

import grader.checkers.CheckResult;
import grader.checkers.FeatureChecker;
import grader.documents.DocumentDisplayerRegistry;
import grader.file.FileProxy;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import util.annotations.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * A feature to be implemented by a student
 * <p/>
 * I think that this class should only be concerned about the feature and checking, not feedback.
 * Also, I think that the feature is independent of projects. The projects can be passed in via visitor pattern
 */
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AGradingFeature implements GradingFeature {

    public static final String FEEDBACK_FILE_PREFIX = "Feeback-";
    public static final String FEEDBACK_FILE_SUFFIX = ".txt";

    String featureName = "Some feature";
    double maxScore;
    double score;
    boolean graded;
    boolean autoGraded;
    boolean extraCredit;
    FileProxy feedbackFolder; // TODO: I don't think this belongs here
    FeatureChecker featureChecker;
    String feedbackFileName; // TODO: I don't think this belongs here
    SakaiProject project; // TODO: I don't think this belongs here
    SakaiProjectDatabase projectDatabase; // TODO: I don't think this belongs here
    String[] outputFiles;
    String[] inputFiles;
    PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public AGradingFeature(String aFeature, double aMaxScore) {
        featureName = aFeature;
        score = 0;
        maxScore = aMaxScore;
    }

    public AGradingFeature(String aFeature, double aMaxScore, boolean anExtraCredit) {
        this(aFeature, aMaxScore);
        extraCredit = anExtraCredit;
    }

    public String toString() {
        return "Feature:" + featureName + "\tMax:" + maxScore + "\tScore: " + score + (extraCredit ? "\tExtra" : "");

    }

    public AGradingFeature(String aFeature, double aMaxScore, FeatureChecker aFeatureChecker) {
        featureName = aFeature;
        maxScore = aMaxScore;
        score = aMaxScore;
        featureChecker = aFeatureChecker;
        featureChecker.init(this);
    }

    public AGradingFeature(String aFeature, double aMaxScore, FeatureChecker aFeatureChecker, boolean anExtraCredit) {
        this(aFeature, aMaxScore, aFeatureChecker);
        extraCredit = anExtraCredit;
    }

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

    public boolean preAutoGrade() {
        return featureChecker != null;
    }

    // TODO: I don't think this belongs here
    public void setProject(SakaiProject aProject) {
        project = aProject;
        if (featureChecker != null)
            featureChecker.setProject(aProject);
        feedbackFolder = aProject.getStudentAssignment().getFeedbackFolder();
        feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + featureName + FEEDBACK_FILE_SUFFIX;
        graded = false;
        firePropertyChange("this", null, this);

    }

    // TODO: I don't think this belongs here
    public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) {
        projectDatabase = aProjectDatabase;
    }

    // TODO: I don't think this belongs here
    @Visible(false)
    public String getFeedbackFileName() {
        return feedbackFileName;
    }

    // TODO: I don't think this belongs here
    static void recordAutoGrade(GradingFeature aGradingFature, CheckResult result) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(aGradingFature.getFeedbackFileName());
            PrintStream outStream = new PrintStream(fileOutputStream);
            outStream.println("Score:" + result.getScore());
            for (String string : result.getLog()) {
                outStream.println(string);
                System.out.println(string);
            }
            outStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // TODO: I think the project database should be passed in
    public void autoGrade() {
        CheckResult result = featureChecker.check();
        purseSetScore(result.getScore());
        projectDatabase.getAutoFeedback().recordAutoGrade(this, result);
    }

//    static void comment(GradingFeature aGradingFeature) {
//        DocumentDisplayerRegistry.display(aGradingFeature.getFeedbackFileName());
//    }

    // TODO: I think the comment should be saved in the result
    @Override
    public void comment() {
        projectDatabase.getManualFeedback().comment(this);
    }

    @Position(0)
    @ComponentWidth(100)
    public String getFeature() {
        return featureName;
    }

    @Position(1)
    @ComponentWidth(30)
    public double getMax() {
        return maxScore;
    }

    @Position(2)
    @ComponentWidth(40)
    public double getScore() {
        return score;
    }

    public void reset() {
        purseSetScore(maxScore);
    }

    public boolean preSetScore() {
        return !preAutoGrade();
    }

    public void setScore(double score) {
        purseSetScore(score);
        if (score != maxScore)
            comment();

    }

    public void initScore(double score) {
        this.score = score;
    }

    public void purseSetScore(double score) {
        graded = true;
        double oldScore = this.score;
        this.score = score;
        propertyChangeSupport.firePropertyChange("Score", oldScore, score);
        firePropertyChange("Graded", null, true);
    }

    @Position(5)
    @ComponentWidth(70)
    public boolean isGraded() {
        return graded;
    }

    public boolean preSetGraded() {
        return featureChecker == null || (project != null && project.hasBeenRun());
    }

    public void setGraded(boolean newValue) {
        graded = newValue;
        if (featureChecker != null) {
            autoGrade();
        } else {
            correct();
        }
    }

    @Position(3)
    public void correct() {
        purseSetScore(maxScore);
    }

    @Override
    @Position(3)
    @Label("Auto")
    @ComponentWidth(60)
    public boolean isAutoGradable() {
        return featureChecker != null;
    }

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

//    public static void main(String[] args) {
//        ObjectEditor.setMethodAttribute(AGradingFeature.class, "correct", AttributeNames.SHOW_BUTTON, true);
//        ListenableVector list = new AListenableVector();
//        GradingFeature gradingFeature = new AGradingFeature("foo", 50);
////	gradingFeature.init( null);
//        list.add(gradingFeature);
//        gradingFeature = new AGradingFeature("bar", 100);
////	gradingFeature.init(null);
//        list.add(gradingFeature);
////	list.add(new AGradingFeature("bar").init (100, null);
////	ObjectEditor.edit(new AFeature("foo", 50));
//        ObjectEditor.edit(list);
//    }


}
