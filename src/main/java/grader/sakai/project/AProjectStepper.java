package grader.sakai.project;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import grader.assignment.GradingFeature;
import grader.assignment.GradingFeatureList;
import grader.documents.DocumentDisplayerRegistry;
import grader.file.FileProxyUtils;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;
import util.annotations.*;
import util.misc.AClearanceManager;
import util.misc.Common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.List;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AProjectStepper extends AClearanceManager implements ProjectStepper {
    public final static long UI_LOAD_TIME = 10 * 1000;

    PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    boolean firstTime;
    String name = "";
    SakaiProjectDatabase projectDatabase;
    double score;
    List<String> documents;
    int nextDocumentIndex = 0;
    FeatureGradeRecorder featureGradeRecorder;
    String onyen = "";
    SakaiProject project;
    boolean hasMoreSteps = true;
    FinalGradeRecorder totalScoreRecorder;
    boolean manualOnyen;
    String logFile, gradedFile, skippedFile;
    boolean runExecuted;
    boolean autoRun = false;

    public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) {
        projectDatabase = aProjectDatabase;
        featureGradeRecorder = aProjectDatabase.getFeatureGradeRecorder();
        totalScoreRecorder = aProjectDatabase.getTotalScoreRecorder();
        registerWithGradingFeatures();
        logFile = aProjectDatabase.getAssigmentDataFolder().getLogFileName();
        gradedFile = aProjectDatabase.getAssigmentDataFolder().getGradedIdFileName();
        skippedFile = aProjectDatabase.getAssigmentDataFolder().getSkippedIdFileName();
    }

    boolean runAttempted() {
        return runExecuted || isAutoRun();
    }

    @ComponentWidth(150)
    @Row(0)
    public String getOnyen() {
        return onyen;
    }

    public void setOnyen(String anOnyen) {
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
    }

    public boolean setProject(String anOnyen) {
        onyen = anOnyen;
        return setProject(projectDatabase.getProject(anOnyen));
    }

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
        notifyPreconditionChanged();
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
        for (GradingFeature aGradingFeature : gradingFeatures) {
            aGradingFeature.addPropertyChangeListener(this);
        }
    }

    void resetGradingFeatures() {
        if (projectDatabase == null)
            return;

        List<GradingFeature> gradingFeatures = projectDatabase.getGradingFeatures();
        for (GradingFeature aGradingFeature : gradingFeatures) {
            double lastScore = getGrade(aGradingFeature.getFeature());
            aGradingFeature.initScore(lastScore);
            aGradingFeature.setProject(project);
        }
    }

    void setScore() {
        List<GradingFeature> gradingFeatures = projectDatabase.getGradingFeatures();
        double aScore = 0;
        for (GradingFeature aGradingFeature : gradingFeatures) {
            aScore += aGradingFeature.getScore();
        }
        setScore(aScore);
    }

    @Visible(false)
    public SakaiProject getProject() {
        return project;
    }

    @Override
    public void display() {
        OEFrame oeFrame = ObjectEditor.edit(this);
        oeFrame.setLocation(700, 500);
        oeFrame.setSize(500, 700);
//        return oeFrame;
    }

    public static void writeScores(ProjectStepper aProjectStepper) {
        aProjectStepper.getProjectDatabase().getScoreFeedback().writeScores(aProjectStepper);
    }

    public static StringBuffer scoresText(ProjectStepper aProjectStepper) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Total Score:" + aProjectStepper.getScore());
        List<GradingFeature> gradingFeatures = aProjectStepper.getProjectDatabase().getGradingFeatures();

        for (GradingFeature aGradingFeature : gradingFeatures) {
            stringBuffer.append("\n");
            stringBuffer.append(aGradingFeature.toString());
        }
        return stringBuffer;
    }

    @Visible(false)
    public boolean setProject(SakaiProject newVal) {
        if (newVal == null) return false;
        writeScores(this);
        runExecuted = false;
        project = newVal;
        if (project == null) {
            System.out.println("No project submitted for dewan");
            return false;
        }
        setName(project.getStudentAssignment().getStudentName());
        documents = project.getStudentAssignment().getDocuments();
        resetGradingFeatures();
        nextDocumentIndex = 0;
        if (totalScoreRecorder != null)
            setInternalScore(getGrade());

        return true;
    }

    public boolean preOutput() {
        return runAttempted() && project.canBeRun();
    }

    public boolean preRun() {
        return project.canBeRun() && !autoRun;
    }

    @Row(3)
    @ComponentWidth(100)
    public void run() {
        runExecuted = true;
        projectDatabase.runProject(onyen, project);
        project.setHasBeenRun(true);
        for (GradingFeature gradingFeature : projectDatabase.getGradingFeatures()) {
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
        return project.runChecked() || project.hasBeenRun();
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
            setGrade(newVal);
    }

    public boolean preGetGradingFeatures() {
        return projectDatabase != null && projectDatabase.getGradingFeatures().size() > 0;
    }

    public boolean preAutoGrade() {
        return project.runChecked() && project.canBeRun() && preGetGradingFeatures();
    }

    @Row(8)
    @ComponentWidth(100)
    public void autoGrade() {
        project.setHasBeenRun(true);
        for (GradingFeature gradingFeature : projectDatabase.getGradingFeatures()) {
            if (gradingFeature.isAutoGradable()) {
                gradingFeature.autoGrade();
            }
        }
    }

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
    public boolean isAllGraded() {
        return getGradingFeatures().isAllGraded();
    }

    @Row(11)
    @ComponentWidth(100)
    public synchronized void proceed() {
        super.proceed();
        if (manualOnyen)
            writeScores(this);
        manualOnyen = false;
        try {
//			Common.appendText(logFile, onyen + " Skipped " + Common.currentTimeAsDate() + "\n\r");
            Common.appendText(gradedFile, onyen + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Row(12)
    @ComponentWidth(100)
    public synchronized void skip() {
        proceed();
        try {
//			Common.appendText(logFile, onyen + " Skipped " + Common.currentTimeAsDate() + "\n");
            Common.appendText(skippedFile, onyen + "\n");
            List<String> list = FileProxyUtils.toList(new File(logFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener aListener) {
        propertyChangeSupport.addPropertyChangeListener(aListener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof GradingFeature && evt.getPropertyName().equalsIgnoreCase("score")) {
            GradingFeature aGradingFeature = (GradingFeature) evt.getSource();
            setScore();
            setGrade(aGradingFeature.getFeature(), aGradingFeature.getScore());
        }
        propertyChangeSupport.firePropertyChange("this", null, this); // an event from grading features, perhaps our precondition chnaged such as auoGraded
    }

    @Override
    @Visible(false)
    public SakaiProjectDatabase getProjectDatabase() {
        return projectDatabase;
    }
}
