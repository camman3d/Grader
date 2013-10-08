package grader.sakai.project;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import grader.assignment.AGradingFeature;
import grader.assignment.AnAssignmenDataFolder;
import grader.assignment.AssignmentDataFolder;
import grader.assignment.GradingFeature;
import grader.documents.AWordDocumentDisplayer;
import grader.documents.DocumentDisplayer;
import grader.documents.DocumentDisplayerRegistry;
import grader.feedback.*;
import grader.file.RootFolderProxy;
import grader.project.AProject;
import grader.project.source.ClassesTextManager;
import grader.sakai.*;
import grader.spreadsheet.*;
import util.misc.Common;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class ASakaiProjectDatabase implements SakaiProjectDatabase {
	public static final String DEFAULT_ASSIGNMENT_DATA_FOLDER = "C:/Users/dewan/Downloads/GraderData";
	public static final String DEFAULT_SCORE_FILE_NAME= "scores.txt";

	Map<String, SakaiProject> onyenToProject = new HashMap();
	String bulkAssignmentsFolderName;
	BulkAssignmentFolder bulkFolder;
	String assignmentsDataFolderName = DEFAULT_ASSIGNMENT_DATA_FOLDER;
	AssignmentDataFolder assignmentDataFolder;
	// String outputFileName;
	FinalGradeRecorder gradeRecorder;
	FinalGradeRecorder totalScoreRecorder;
	protected FeatureGradeRecorder featureGradeRecorder;
	List<GradingFeature> gradingFeatures = new AGradingFeatureList();
	String sourceSuffix = ClassesTextManager.DEFAULT_SOURCES_FILE_SUFFIX;
	String outputSuffix = AProject.DEFAULT_OUTPUT_FILE_SUFFIX;
	ScoreFeedback scoreFeedback;
	AutoFeedback autoFeedback;
	ManualFeedback manualFeedback;
	SourceDisplayer sourceDisplayer;

	public ASakaiProjectDatabase(String aBulkAssignmentsFolderName,
			String anAssignmentsDataFolderName) {
		sourceSuffix = sourceSuffix();
		outputSuffix = outputSuffix();
		bulkAssignmentsFolderName = aBulkAssignmentsFolderName;
		assignmentsDataFolderName = anAssignmentsDataFolderName;
		maybeMakeProjects();
		// gradeRecorder = new
		// ASakaiSpreadsheetGradeRecorder(bulkFolder.getSpreadsheet());
		// gradeRecorder = new
		// ASakaiCSVFinalGradeManager(bulkFolder.getSpreadsheet());
		// gradeRecorder = new ASakaiCSVFinalGradeManager(this);
		gradeRecorder = createFinalGradeRecorder();
		featureGradeRecorder = createFeatureGradeRecorder();
//		totalScoreRecorder = createTotalScoreRecorder();
		totalScoreRecorder = createTotalScoreRecorder();
		autoFeedback = createAutoFeedback();
		manualFeedback = createManualFeedback();
		scoreFeedback = createScoreFeedback();
		sourceDisplayer = createSourceDisplayer();
		

		
//		maybeMakeProjects();


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
		return new AnAutoFeedbackFileWriter();
	}
	
	protected ManualFeedback createManualFeedback() {
		return new AManualFeedbackFileDisplayer();
	}
	
	protected ScoreFeedback createScoreFeedback() {
		return new AScoreFeedbackFileWriter();
	}

	protected SourceDisplayer createSourceDisplayer() {
		return new AnAllTextSourceDisplayer();
	}


	protected FinalGradeRecorder createFinalGradeRecorder() {
		return FinalGradeRecorderSelector.createFinalGradeRecorder(this);
	}

	protected FeatureGradeRecorder createFeatureGradeRecorder() {
		return FeatureGradeRecorderSelector.createFeatureGradeRecorder(this);
	}

	protected FinalGradeRecorder createTotalScoreRecorder() {
//		return featureGradeRecorder;
		return TotalScoreRecorderSelector.createFinalGradeRecorder(this);
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

	public List<GradingFeature> getGradingFeatures() {
		return gradingFeatures;
	}

	public void addGradingFeatures(List<GradingFeature> aGradingFeatures) {
		for (GradingFeature aGradingFeature : aGradingFeatures) {
			aGradingFeature.setInputFiles(inputFiles);
			gradingFeatures.add(aGradingFeature);
			aGradingFeature.setProjectDatabase(this);
			if (aGradingFeature.isAutoGradable() && aGradingFeature.getFeatureChecker().isOverridable()) {
				GradingFeature manualFeature = new AGradingFeature(
						"Override" + aGradingFeature.getFeature(), aGradingFeature.getMax(), aGradingFeature.isExtraCredit());
				gradingFeatures.add(manualFeature);
				
				
			}
		}
//		gradingFeatures.addAll(aGradingFeatures);
		

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
		// List<OEFrame> oldList = new ArrayList( uiFrameList.getList());

		if (!anAssignment.isSubmitted()) {
			System.out.println("Assignment not submitted:"
					+ anAssignment.getOnyen() + " "
					+ anAssignment.getStudentName());

			return null;
		}

		// List<OEFrame> newList = new ArrayList( uiFrameList.getList());
		// for (OEFrame frame:newList) {
		// if (oldList.contains(frame))
		// continue;
		// frame.dispose(); // will this work
		//
		SakaiProject aProject;
		try {
			aProject = new ASakaiProject(anAssignment, sourceSuffix,
					outputSuffix);
			return aProject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Project lastProject;
	public SakaiProject runProject(String anOnyen) {
		SakaiProject aProject = getProject(anOnyen);
		// if (aProject != null) {
		//
		// String[] strings = {};
		//
		// outputFileName = //aProject.getOutputFileName();
		// aProject.getOutputFolder() + "/" + AProject.DEFAULT_OUTPUT_FILE_NAME;
		// System.out.println("Trying to run:" + anOnyen + " " +
		// aProject.getStudentAssignment().getStudentName());
		// String assignmentName =
		// this.getBulkAssignmentFolder().getAssignmentName().replaceAll("\\s","");
		// Thread thread = aProject.run("main." + assignmentName, strings, null,
		// outputFileName);
		//
		// resetRunningProject(aProject);
		// // return aProject;
		// }
		runProject(anOnyen, aProject);
		return aProject;
	}

	public void setRunParameters(SakaiProject aProject) {

	}

	String assignmentName;

	protected String getAssignmentName() {
		if (assignmentName == null)
			assignmentName = this.getBulkAssignmentFolder().getAssignmentName()
					.replaceAll("\\s", "");
		return assignmentName;
	}

	public String getOutputFileName(SakaiProject aProject, String anInputFile) {
		String inQualifier = "";
		if (anInputFile != null) {
			inQualifier = Common.toFilePrefix(Common
                    .absoluteNameToLocalName(anInputFile)) + "_";
		}
		String outputFileName = // aProject.getOutputFileName();
		aProject.getOutputFolder() + "/" + inQualifier
				+ getDefaultOutputFileName();
		return outputFileName;

	}

	public String getDefaultOutputFileName() {
		return AProject.DEFAULT_OUTPUT_FILE_PREFIX + outputSuffix;
	}

	public String[] getOutputFileNames(SakaiProject aProject,
			String[] anInputFiles) {
		String[] anOutputFiles = new String[anInputFiles.length];
		for (int i = 0; i < anInputFiles.length; i++) {
			anOutputFiles[i] = getOutputFileName(aProject, anInputFiles[i]);
		}
		return anOutputFiles;
		/*
		 * String inQualifier = ""; if (anInputFile != null) inQualifier =
		 * anInputFile + "_"; outputFileName = //aProject.getOutputFileName();
		 * aProject.getOutputFolder() + "/" + inQualifier +
		 * AProject.DEFAULT_OUTPUT_FILE_NAME; return outputFileName;
		 */

	}

	public String getClassName() {
		return "main." + assignmentName;
	}

	protected String[][] getArgs(String[] anInputFiles) {
		String[][] args = new String[anInputFiles.length][];
		for (int i = 0; i < anInputFiles.length; i++) {
			args[i] = getArgs(anInputFiles[i]);
		}
		return args;
	}

	protected String[] getArgs(String inputFile) {
		return new String[] {};
	}

	String[] outputFiles;
	String[] inputFiles;
	String[][] args;

	public String[] getInputFiles() {
		return inputFiles;
	}
	
	protected void initInputFiles() {
        // [Josh] I commented this out 'cuz it was throwing null pointer exceptions
//		Set<String> inputFilesSet = assignmentDataFolder.getInputFiles();
//		inputFiles = new String[inputFilesSet.size()];
//		int nextFileIndex = 0;
//		for (String inputFile : inputFilesSet) {
//			inputFiles[nextFileIndex] = inputFile;
//			nextFileIndex++;
//
//		}
	}

	@Override
	public SakaiProject runProject(String anOnyen, SakaiProject aProject) {
		// SakaiProject aProject = getProject(anOnyen);
		if (aProject != null) {

			// String[] strings = {};

			// outputFileName = //aProject.getOutputFileName();
			// aProject.getOutputFolder() + "/" +
			// AProject.DEFAULT_OUTPUT_FILE_NAME;
			System.out.println("Trying to run:" + anOnyen + " "
					+ aProject.getStudentAssignment().getStudentName());
			// String assignmentName =
			// this.getBulkAssignmentFolder().getAssignmentName().replaceAll("\\s","");
			String assignmentName = getAssignmentName();
			String mainClassName = getClassName();
			Set<String> inputFilesSet = assignmentDataFolder.getInputFiles();
//			inputFiles = new String[inputFilesSet.size()];
//			int nextFileIndex = 0;
//			for (String inputFile : inputFilesSet) {
//				inputFiles[nextFileIndex] = inputFile;
//				nextFileIndex++;
//
//			}
			outputFiles = getOutputFileNames(aProject, inputFiles);
			String[][] strings = getArgs(inputFiles);

			aProject.setRunParameters(mainClassName, strings, inputFiles,
					outputFiles);
			Thread thread = aProject.runProject();

			//
			// if (inputFiles.size() > 0) {
			// for (String inputFile:inputFiles) {
			// if (!aProject.canBeRun()) break;
			// aProject.setRunParameters (mainClassName, strings, inputFile,
			// outputFileName);
			//
			// // Thread thread = aProject.run("main." + assignmentName,
			// strings, null, outputFileName);
			// // Thread thread = aProject.run(mainClassName, strings, null,
			// outputFileName);
			// Thread thread = aProject.runProject();
			// System.out.println("returned from run");
			//
			// }
			// } else {
			//
			// aProject.setRunParameters (mainClassName, strings, null,
			// outputFileName);
			//
			//
			// // Thread thread = aProject.run("main." + assignmentName,
			// strings, null, outputFileName);
			// // Thread thread = aProject.run(mainClassName, strings, null,
			// outputFileName);
			// Thread thread = aProject.runProject();
			// }

			resetRunningProject(aProject);
			// return aProject;
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
		// OEFrame oeFrame = ObjectEditor.edit(clearanceManager);
		// oeFrame.setLocation(800, 500);
		// oeFrame.setSize(400, 400);
		displayProjectStepper(aProjectStepper);
		return aProjectStepper;
	}

	public OEFrame displayProjectStepper(ProjectStepper aProjectStepper) {

		OEFrame oeFrame = ObjectEditor.edit(aProjectStepper);
		oeFrame.setLocation(700, 500);
		oeFrame.setSize(500, 700);
		return oeFrame;
	}

	public ProjectStepper createProjectStepper() {
		return new AProjectStepper();
	}

	public void runProjectInteractively(String anOnyen) {
		runProjectInteractively(anOnyen, createAndDisplayProjectStepper());

	}

	public void runProjectsInteractively() {
		ProjectStepper aProjectStepper = createAndDisplayProjectStepper();
		maybeMakeProjects();
//		aProjectStepper.setProjectDatabase(this);
		Set<String> onyens = new HashSet(onyenToProject.keySet());
		aProjectStepper.setHasMoreSteps(true);

		for (String anOnyen : onyens) {
			runProjectInteractively(anOnyen, aProjectStepper);
		}
		aProjectStepper.setHasMoreSteps(false);

	}

	PrintStream origOut;
	InputStream origIn;

	// SakaiProject nextProject;
	// String onyen;
	// ProjectStepper projectStepper;

	public void runProjectInteractively(String anOnyen,
			ProjectStepper aProjectStepper) {
		SakaiProject aProject = getProject(anOnyen);

		origOut = System.out;
		origIn = System.in;
		// onyen = anOnyen;
		// projectStepper = aProjectStepper;
		// nextProject = runProject(onyen);
		// aProjectStepper.waitForClearance();

		// SakaiProject aProject = runProject(anOnyen);
		if (aProjectStepper.isAutoRun()) {
			runProject(anOnyen, aProject);
		}

		// aProject.displaySourceInWord();
		// aProjectStepper.setProject(aProject);
		aProjectStepper.setProject(anOnyen);
		// } else {
		// aProjectStepper.setProject(anOnyen);
		// // aProjectStepper.waitForClearance();
		// // runProject(anOnyen, aProject);
		//
		// }
		aProjectStepper.waitForClearance();

		resetIO();
		// if (System.in != origIn) {
		//
		// System.setIn(origIn);
		// }
		//
		//
		// if (System.out != origOut) {
		// System.out.close();
		// System.setOut(origOut);
		// }

	}

	void resetIO() {
		if (System.in != origIn) {

			System.setIn(origIn);
		}

		if (System.out != origOut) {
			System.out.close();
			System.setOut(origOut);
		}

	}

	protected DocumentDisplayer wordSourceCodeDisplayer = new AWordDocumentDisplayer();;

	public void displayOutput() {
		resetIO();
		for (String outputFileName : outputFiles) {

			System.out.println("Displaying output from:" + outputFileName);
			DocumentDisplayerRegistry.display(outputFileName);
			// String windowsName= Common.toWindowsFileName(outputFileName);

			// Common.toCanonicalFileName(aFileName);

			// wordSourceCodeDisplayer.displayFile(windowsName);
			// wordSourceCodeDisplayer.displayAllSources();
		}

	}

	boolean projectsMade;

	public SakaiProject getProject(String aName) {
		SakaiProject project = onyenToProject.get(aName);
		if (project == null) {
			StudentCodingAssignment aStudentAssignment = getStudentAssignment(aName);
			project = makeProject(aStudentAssignment);
			if (project != null) {
				onyenToProject.put(aStudentAssignment.getOnyen(), project);
			}
		}
		return project;
	}

	public StudentCodingAssignment getStudentAssignment(String anOnyen) {
		GenericStudentAssignmentDatabase<StudentCodingAssignment> aStudentAssignmentDatabase = getStudentAssignmentDatabase();

		Collection<StudentCodingAssignment> studentAssignments = aStudentAssignmentDatabase
				.getStudentAssignments();
		for (StudentCodingAssignment anAssignment : studentAssignments) {
			if (anAssignment.getOnyen().equals(anOnyen))
				return anAssignment;

		}
		return null;

	}

	public GenericStudentAssignmentDatabase<StudentCodingAssignment> getStudentAssignmentDatabase() {
		if (studentAssignmentDatabase == null) {
			studentAssignmentDatabase = new ASakaiStudentCodingAssignmentsDatabase(
					bulkFolder);

		}
		return studentAssignmentDatabase;

	}

	GenericStudentAssignmentDatabase<StudentCodingAssignment> studentAssignmentDatabase;

	public void maybeMakeProjects() {
		if (projectsMade)
			return;
		projectsMade = true;
		bulkFolder = new ASakaiBulkAssignmentFolder(bulkAssignmentsFolderName);
		String assignmentName = bulkFolder.getAssignmentName();
		String specificAssignmentDataFolderName = assignmentsDataFolderName
				+ "/" + assignmentName;

		assignmentDataFolder = new AnAssignmenDataFolder(
				specificAssignmentDataFolderName, bulkFolder.getSpreadsheet());

		if (!assignmentDataFolder.exists()) {
			System.out.println("Expecting assignment data folder:"
					+ specificAssignmentDataFolderName);
		}
		// GenericStudentAssignmentDatabase<StudentCodingAssignment>
		// studentAssignmentDatabase = new
		// ASakaiStudentCodingAssignmentsDatabase(bulkFolder);
		GenericStudentAssignmentDatabase<StudentCodingAssignment> aStudentAssignmentDatabase = getStudentAssignmentDatabase();

		// studentAssignmentDatabase = new
		// ASakaiStudentCodingAssignmentsDatabase(bulkFolder);

		System.out.println(aStudentAssignmentDatabase.getStudentIds());
		Collection<StudentCodingAssignment> studentAssignments = aStudentAssignmentDatabase
				.getStudentAssignments();
		// ClearanceManager clearanceManager = new ACodeDatababaseStepper();
		// OEFrame oeFrame = ObjectEditor.edit(clearanceManager);
		// oeFrame.setLocation(700, 500);
		// PrintStream origOut = System.out;
		// InputStream origIn = System.in;
		// Project lastProject = null;
		// StudentAssignment lastAssignment = null;
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

	public static void main(String[] args) {
		SakaiProjectDatabase projectDatabase = new ASakaiProjectDatabase(
				ASakaiBulkAssignmentFolder.DEFAULT_BULK_DOWNLOAD_FOLDER,
				"C:/Users/dewan/Downloads/GraderData");
		// projectDatabase.runProjectInteractively("mkcolema");
		projectDatabase.runProjectsInteractively();

	}

}
