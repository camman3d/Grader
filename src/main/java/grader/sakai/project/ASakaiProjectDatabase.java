package grader.sakai.project;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;
import grader.assignment.*;
import grader.documents.AWordDocumentDisplayer;
import grader.documents.DocumentDisplayer;
import grader.documents.DocumentDisplayerRegistry;
import grader.feedback.*;
import grader.file.RootFolderProxy;
import grader.project.AMainClassFinder;
import grader.project.AProject;
import grader.project.MainClassFinder;
import grader.project.source.ClassesTextManager;
import grader.sakai.*;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FeatureGradeRecorderSelector;
import grader.spreadsheet.FinalGradeRecorder;
import util.misc.Common;
import util.trace.Tracer;

import java.awt.*;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.List;

public class ASakaiProjectDatabase implements SakaiProjectDatabase {
    public static final String DEFAULT_ASSIGNMENT_DATA_FOLDER = "C:/Users/dewan/Downloads/GraderData";
    public static final String DEFAULT_SCORE_FILE_NAME = "scores.txt";

    Map<String, SakaiProject> onyenToProject = new HashMap();
    String bulkAssignmentsFolderName;
    BulkAssignmentFolder bulkFolder;
    String assignmentsDataFolderName = DEFAULT_ASSIGNMENT_DATA_FOLDER;
    AssignmentDataFolder assignmentDataFolder;
    FinalGradeRecorder gradeRecorder;
    FinalGradeRecorder totalScoreRecorder;
    GradingFeatureList gradingFeatures = new AGradingFeatureList();
    String sourceSuffix = ClassesTextManager.DEFAULT_SOURCES_FILE_SUFFIX;
    String outputSuffix = AProject.DEFAULT_OUTPUT_FILE_SUFFIX;
    ScoreFeedback scoreFeedback;
    AutoFeedback autoFeedback;
    ManualFeedback manualFeedback;
    SourceDisplayer sourceDisplayer;
    MainClassFinder mainClassFinder;
    String assignmentName, mixedCaseAssignmentName;
    String[] outputFiles;
    String outputFileName;
    String[] inputFiles;
    String[][] args;
    PrintStream origOut;
    InputStream origIn;
    List<OEFrame> oldList;
    Window[] oldWindows;
    boolean projectsMade;
    GenericStudentAssignmentDatabase<StudentCodingAssignment> studentAssignmentDatabase;

    protected DocumentDisplayer wordSourceCodeDisplayer = new AWordDocumentDisplayer();
    protected FeatureGradeRecorder featureGradeRecorder;

    public ASakaiProjectDatabase(String aBulkAssignmentsFolderName,
                                 String anAssignmentsDataFolderName) {
        sourceSuffix = sourceSuffix();
        outputSuffix = outputSuffix();
        bulkAssignmentsFolderName = aBulkAssignmentsFolderName;
        assignmentsDataFolderName = anAssignmentsDataFolderName;
        maybeMakeProjects();
        featureGradeRecorder = createFeatureGradeRecorder();
        gradeRecorder = createFinalGradeRecorder();
        totalScoreRecorder = createTotalScoreRecorder();
        autoFeedback = createAutoFeedback();
        manualFeedback = createManualFeedback();
        scoreFeedback = createScoreFeedback();
        sourceDisplayer = createSourceDisplayer();
        mainClassFinder = createMainClassFinder();
        initInputFiles();
    }

    public AutoFeedback getAutoFeedback() {
        return autoFeedback;
    }

    public ManualFeedback getManualFeedback() {
        return manualFeedback;
    }

    public ScoreFeedback getScoreFeedback() {
        return scoreFeedback;
    }

    public SourceDisplayer getSourceDisplayer() {
        return sourceDisplayer;
    }

    protected AutoFeedback createAutoFeedback() {
        return new AnAutoFeedbackManager();
    }

    protected ManualFeedback createManualFeedback() {
        return new AManualFeedbackManager();
    }

    protected ScoreFeedback createScoreFeedback() {
        return new AScoreFeedbackFileWriter();
    }

    protected SourceDisplayer createSourceDisplayer() {
        return new AnAllTextSourceDisplayer();
    }

    protected FinalGradeRecorder createFinalGradeRecorder() {
        return featureGradeRecorder;
    }

    protected FeatureGradeRecorder createFeatureGradeRecorder() {
        return FeatureGradeRecorderSelector.createFeatureGradeRecorder(this);
    }

    protected FinalGradeRecorder createTotalScoreRecorder() {
        return featureGradeRecorder;
    }

    public FinalGradeRecorder getTotalScoreRecorder() {
        return totalScoreRecorder;
    }

    public void setTotalScoreRecorder(FinalGradeRecorder newVal) {
        totalScoreRecorder = newVal;
    }

    public String sourceSuffix() {
        return ClassesTextManager.DEFAULT_SOURCES_FILE_SUFFIX;
    }

    public String outputSuffix() {
        return AProject.DEFAULT_OUTPUT_FILE_SUFFIX;
    }

    public GradingFeatureList getGradingFeatures() {
        return gradingFeatures;
    }

    protected MainClassFinder createMainClassFinder() {
        return new AMainClassFinder();
    }

    public void addGradingFeatures(List<GradingFeature> aGradingFeatures) {
        for (GradingFeature aGradingFeature : aGradingFeatures) {
            aGradingFeature.setInputFiles(inputFiles);
            gradingFeatures.add(aGradingFeature);
            aGradingFeature.setProjectDatabase(this);
            if (aGradingFeature.isAutoGradable() && aGradingFeature.getFeatureChecker().isOverridable()) {
                GradingFeature manualFeature = new AGradingFeature(
                        "Override" + aGradingFeature.getFeature(), aGradingFeature.getMax(),
                        aGradingFeature.isExtraCredit()
                );
                manualFeature.setProjectDatabase(this);
                gradingFeatures.add(manualFeature);
                aGradingFeature.setLinkedFeature(manualFeature);
                manualFeature.setLinkedFeature(aGradingFeature);
            }
        }
    }

    public BulkAssignmentFolder getBulkAssignmentFolder() {
        return bulkFolder;
    }

    public AssignmentDataFolder getAssigmentDataFolder() {
        return assignmentDataFolder;
    }

    public Set<String> getOnyens() {
        return onyenToProject.keySet();
    }

    public Collection<SakaiProject> getProjects() {
        return onyenToProject.values();
    }

    @Override
    public FinalGradeRecorder getGradeRecorder() {
        return gradeRecorder;
    }

    @Override
    public FeatureGradeRecorder getFeatureGradeRecorder() {
        return featureGradeRecorder;
    }

    SakaiProject makeProject(StudentCodingAssignment anAssignment) {
        RootFolderProxy projectFolder = anAssignment.getProjectFolder();

        if (projectFolder == null) {
            System.out.println("No project folder found for:"
                    + anAssignment.getOnyen() + " "
                    + anAssignment.getStudentName());
            return null;
        }
        if (!anAssignment.isSubmitted()) {
            System.out.println("Assignment not submitted:"
                    + anAssignment.getOnyen() + " "
                    + anAssignment.getStudentName());

            return null;
        }

        SakaiProject aProject;
        try {
            aProject = new ASakaiProject(anAssignment, sourceSuffix, outputSuffix);
            return aProject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Project lastProject;
    public SakaiProject runProject(String anOnyen) {
        SakaiProject aProject = getProject(anOnyen);
        runProject(anOnyen, aProject);
        return aProject;
    }

    public void setRunParameters(SakaiProject aProject) {
    }

    protected String getAssignmentName() {
        if (assignmentName == null)
            assignmentName = this.getBulkAssignmentFolder().getAssignmentName().replaceAll("\\s", "");
        return assignmentName;
    }

    protected String getMixedCaseAssignmentName() {
        if (mixedCaseAssignmentName == null)
            mixedCaseAssignmentName = this.getBulkAssignmentFolder().getMixedCaseAssignmentName().replaceAll("\\s", "");
        return mixedCaseAssignmentName;
    }

    public String getOutputFileName(SakaiProject aProject, String anInputFile) {
        String inQualifier = "";
        if (anInputFile != null) {
            inQualifier = Common.toFilePrefix(Common.absoluteNameToLocalName(anInputFile)) + "_";
        }
        String outputFileName = aProject.getOutputFolder() + "/" + inQualifier + getDefaultOutputFileName();
        return outputFileName;
    }

    public String getDefaultOutputFileName() {
        return AProject.DEFAULT_OUTPUT_FILE_PREFIX + outputSuffix;
    }

    public String[] getOutputFileNames(SakaiProject aProject, String[] anInputFiles) {
        String[] anOutputFiles = new String[anInputFiles.length];
        for (int i = 0; i < anInputFiles.length; i++) {
            anOutputFiles[i] = getOutputFileName(aProject, anInputFiles[i]);
        }
        return anOutputFiles;
    }

    public String getClassName() {
        return "main." + getMixedCaseAssignmentName();
    }

    protected String[][] getArgs(String[] anInputFiles) {
        String[][] args = new String[anInputFiles.length][];
        for (int i = 0; i < anInputFiles.length; i++) {
            args[i] = getArgs(anInputFiles[i]);
        }
        return args;
    }

    protected String[] getArgs(String inputFile) {
        return new String[]{};
    }

    public String[] getInputFiles() {
        return inputFiles;
    }

    protected void initInputFiles() {
        Set<String> inputFilesSet = assignmentDataFolder.getInputFiles();
        inputFiles = new String[inputFilesSet.size()];
        int nextFileIndex = 0;
        for (String inputFile : inputFilesSet) {
            inputFiles[nextFileIndex] = inputFile;
            nextFileIndex++;
        }
    }

    @Override
    public SakaiProject runProject(String anOnyen, SakaiProject aProject) {
        if (aProject != null) {
            System.out.println("Trying to run:" + anOnyen + " " + aProject.getStudentAssignment().getStudentName());
            String assignmentName = getMixedCaseAssignmentName();
            String mainClassName = getClassName();
            Set<String> inputFilesSet = assignmentDataFolder.getInputFiles();
            outputFiles = getOutputFileNames(aProject, inputFiles);
            outputFileName = aProject.getOutputFileName();
            String[][] strings = getArgs(inputFiles);
            aProject.setRunParameters(mainClassName, strings, inputFiles, outputFiles, mainClassFinder);
            Thread thread = aProject.runProject();
        }
        return aProject;
    }


    @Override
    public void resetRunningProject(SakaiProject aProject) {
        for (GradingFeature gradingFeature : gradingFeatures) {
            gradingFeature.setProject(aProject);
            gradingFeature.setOutputFiles(outputFiles);
        }
    }

    public ProjectStepper createAndDisplayProjectStepper() {
        ProjectStepper aProjectStepper = createProjectStepper();
        aProjectStepper.setProjectDatabase(this);
        aProjectStepper.display();
//        displayProjectStepper(aProjectStepper);
        return aProjectStepper;
    }

    public ProjectStepper createProjectStepper() {
        return ProjectStepperFactory.create();
    }

    public void runProjectInteractively(String anOnyen) {
        runProjectInteractively(anOnyen, createAndDisplayProjectStepper());
    }

    public void runProjectsInteractively() {
        ProjectStepper aProjectStepper = createAndDisplayProjectStepper();
        maybeMakeProjects();
        Set<String> onyens = new HashSet(onyenToProject.keySet());
        aProjectStepper.setHasMoreSteps(true);
        for (String anOnyen : onyens) {
            runProjectInteractively(anOnyen, aProjectStepper);
        }
        aProjectStepper.setHasMoreSteps(false);
    }

    public void recordWindows() {
        oldList = new ArrayList(uiFrameList.getList());
        oldWindows = Window.getWindows();
    }

    public void clearWindows() {
        if (oldWindows != null && oldList != null) {// somebody went before me, get rid of their windows
            List<OEFrame> newList = new ArrayList(uiFrameList.getList());
            for (OEFrame frame : newList) {
                if (oldList.contains(frame))
                    continue;
                frame.dispose(); // will this work
            }
            Window[] newWindows = Window.getWindows();

            for (Window frame : newWindows) {
                if (Common.containsReference(oldWindows, frame)) {
                    continue;
                }
                frame.dispose();
            }
        }
    }

    public void runProjectInteractively(String anOnyen, ProjectStepper aProjectStepper) {
        SakaiProject aProject = getProject(anOnyen);
        origOut = System.out;
        origIn = System.in;
        if (aProjectStepper.isAutoRun()) {
            runProject(anOnyen, aProject);
        }
        recordWindows();
        aProjectStepper.setProject(anOnyen);
        aProjectStepper.waitForClearance();
        resetIO();
        clearWindows();
    }

    public void resetIO() {
        if (System.in != origIn) {
            System.setIn(origIn);
        }
        if (System.out != origOut) {
            System.out.close();
            System.setOut(origOut);
        }
    }

    public void displayOutput() {
        resetIO();
        if (outputFiles.length == 0) {
            DocumentDisplayerRegistry.display(outputFileName);
            return;
        }
        for (String anOutputFileName : outputFiles) {
            System.out.println("Displaying output from:" + anOutputFileName);
            DocumentDisplayerRegistry.display(anOutputFileName);
        }
    }

    public SakaiProject getProject(String aName) {
        SakaiProject project = onyenToProject.get(aName);
        if (project == null) {
            StudentCodingAssignment aStudentAssignment = getStudentAssignment(aName);
            if (aStudentAssignment == null) {
                Tracer.error("No project for student:" + aName);
                return null;
            }
            project = makeProject(aStudentAssignment);
            if (project != null) {
                onyenToProject.put(aStudentAssignment.getOnyen(), project);
            }
        }
        return project;
    }

    public StudentCodingAssignment getStudentAssignment(String anOnyen) {
        GenericStudentAssignmentDatabase<StudentCodingAssignment> aStudentAssignmentDatabase = getStudentAssignmentDatabase();
        Collection<StudentCodingAssignment> studentAssignments = aStudentAssignmentDatabase.getStudentAssignments();
        for (StudentCodingAssignment anAssignment : studentAssignments) {
            if (anAssignment.getOnyen().equals(anOnyen))
                return anAssignment;
        }
        return null;
    }

    public GenericStudentAssignmentDatabase<StudentCodingAssignment> getStudentAssignmentDatabase() {
        if (studentAssignmentDatabase == null) {
            studentAssignmentDatabase = new ASakaiStudentCodingAssignmentsDatabase(bulkFolder);
        }
        return studentAssignmentDatabase;
    }

    public void maybeMakeProjects() {
        if (projectsMade)
            return;
        projectsMade = true;
        bulkFolder = new ASakaiBulkAssignmentFolder(bulkAssignmentsFolderName);
        String assignmentName = bulkFolder.getAssignmentName();
        String specificAssignmentDataFolderName = assignmentsDataFolderName + "/" + assignmentName;
        assignmentDataFolder = new AnAssignmenDataFolder(specificAssignmentDataFolderName, bulkFolder.getSpreadsheet());
        if (!assignmentDataFolder.exists()) {
            System.out.println("Expecting assignment data folder:" + specificAssignmentDataFolderName);
        }
        GenericStudentAssignmentDatabase<StudentCodingAssignment> aStudentAssignmentDatabase = getStudentAssignmentDatabase();
        System.out.println(aStudentAssignmentDatabase.getStudentIds());
        Collection<StudentCodingAssignment> studentAssignments = aStudentAssignmentDatabase.getStudentAssignments();
        for (StudentCodingAssignment anAssignment : studentAssignments) {
            RootFolderProxy projectFolder = anAssignment.getProjectFolder();
            if (!assignmentDataFolder.getStudentIDs().contains(
                    anAssignment.getOnyen()))
                continue;
            SakaiProject project = makeProject(anAssignment);
            if (project != null) {
                onyenToProject.put(anAssignment.getOnyen(), project);
            }
        }
    }

    public void setGradeRecorder(FinalGradeRecorder gradeRecorder) {
        this.gradeRecorder = gradeRecorder;
    }

    public void setFeatureGradeRecorder(
            FeatureGradeRecorder featureGradeRecorder) {
        this.featureGradeRecorder = featureGradeRecorder;
    }
}
