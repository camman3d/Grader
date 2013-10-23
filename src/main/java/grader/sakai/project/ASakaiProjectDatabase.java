package grader.sakai.project;

import grader.assignment.AGradingFeature;
import grader.assignment.AGradingFeatureList;
import grader.assignment.AnAssignmenDataFolder;
import grader.assignment.AssignmentDataFolder;
import grader.assignment.GradingFeature;
import grader.assignment.GradingFeatureList;
import grader.documents.AWordDocumentDisplayer;
import grader.documents.DocumentDisplayer;
import grader.documents.DocumentDisplayerRegistry;
import grader.feedback.AManualFeedbackManager;
import grader.feedback.AScoreFeedbackFileWriter;
import grader.feedback.AnAllTextSourceDisplayer;
import grader.feedback.AnAutoFeedbackManager;
import grader.feedback.AutoFeedback;
import grader.feedback.ManualFeedback;
import grader.feedback.ScoreFeedback;
import grader.feedback.SourceDisplayer;
import grader.file.RootFolderProxy;
import grader.project.AMainClassFinder;
import grader.project.AProject;
import grader.project.MainClassFinder;
import grader.project.Project;
import grader.project.source.ClassesTextManager;
import grader.sakai.ASakaiBulkAssignmentFolder;
import grader.sakai.ASakaiStudentCodingAssignment;
import grader.sakai.ASakaiStudentCodingAssignmentsDatabase;
import grader.sakai.AnAbstractSakaiStudentAssignmentsDatabase;
import grader.sakai.BulkAssignmentFolder;
import grader.sakai.GenericStudentAssignmentDatabase;
import grader.sakai.StudentAssignment;
import grader.sakai.StudentCodingAssignment;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FeatureGradeRecorderSelector;
import grader.spreadsheet.FinalGradeRecorder;
import grader.spreadsheet.FinalGradeRecorderSelector;
import grader.spreadsheet.TotalScoreRecorderSelector;
import grader.spreadsheet.csv.ASakaiCSVFeatureGradeManager;
import grader.spreadsheet.csv.ASakaiCSVFinalGradeManager;
import grader.spreadsheet.xlsx.ASakaiSpreadsheetGradeRecorder;

import java.awt.Window;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.misc.Common;
import util.models.AListenableVector;
import util.trace.Tracer;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;

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
	GradingFeatureList gradingFeatures = new AGradingFeatureList();
	String sourceSuffix = ClassesTextManager.DEFAULT_SOURCES_FILE_SUFFIX;
	String outputSuffix = AProject.DEFAULT_OUTPUT_FILE_SUFFIX;
	ScoreFeedback scoreFeedback;
	AutoFeedback autoFeedback;
	ManualFeedback manualFeedback;
	SourceDisplayer sourceDisplayer;
	 MainClassFinder mainClassFinder;


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
		featureGradeRecorder = createFeatureGradeRecorder();
		gradeRecorder = createFinalGradeRecorder();
//		totalScoreRecorder = createTotalScoreRecorder();
		totalScoreRecorder = createTotalScoreRecorder();
		autoFeedback = createAutoFeedback();
		manualFeedback = createManualFeedback();
		scoreFeedback = createScoreFeedback();
		sourceDisplayer = createSourceDisplayer();
		mainClassFinder = createMainClassFinder();
		projectStepperDisplayer = createProjectStepperDisplayer();
		navigationListCreator = createNavigationListCreator();
		

		
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
//		return FinalGradeRecorderSelector.createFinalGradeRecorder(this);
		return featureGradeRecorder;

	}

	protected FeatureGradeRecorder createFeatureGradeRecorder() {
		return FeatureGradeRecorderSelector.createFeatureGradeRecorder(this);
	}

	protected FinalGradeRecorder createTotalScoreRecorder() {
		return featureGradeRecorder;
//		return TotalScoreRecorderSelector.createFinalGradeRecorder(this);
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
						"Override" + aGradingFeature.getFeature(), aGradingFeature.getMax(), aGradingFeature.isExtraCredit());
				manualFeature.setProjectDatabase(this);
				gradingFeatures.add(manualFeature);
				aGradingFeature.setLinkedFeature(manualFeature);
				manualFeature.setLinkedFeature(aGradingFeature);
				
				
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

	String assignmentName, mixedCaseAssignmentName;

	protected String getAssignmentName() {
		if (assignmentName == null)
			assignmentName = this.getBulkAssignmentFolder().getAssignmentName()
					.replaceAll("\\s", "");
		return assignmentName;
	}
	protected String getMixedCaseAssignmentName() {
		if (mixedCaseAssignmentName == null)
			mixedCaseAssignmentName = this.getBulkAssignmentFolder().getMixedCaseAssignmentName()
					.replaceAll("\\s", "");
		return mixedCaseAssignmentName;
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
		return new String[] {};
	}

	String[] outputFiles;
	String outputFileName;
	String[] inputFiles;
	String[][] args;

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
		// SakaiProject aProject = getProject(anOnyen);
		if (aProject != null) {

			// String[] strings = {};

			// outputFileName = //aProject.getOutputFileName();
			// aProject.getOutputFolder() + "/" +
			// AProject.DEFAULT_OUTPUT_FILE_NAME;
//			System.out.println("Trying to run:" + anOnyen + " "
//					+ aProject.getStudentAssignment().getStudentName());
			System.out.println("Trying to run:" + anOnyen );
			// String assignmentName =
			// this.getBulkAssignmentFolder().getAssignmentName().replaceAll("\\s","");
			String assignmentName = getMixedCaseAssignmentName();
			
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
			outputFileName = aProject.getOutputFileName();
		
			String[][] strings = getArgs(inputFiles);

			aProject.setRunParameters(mainClassName, strings, inputFiles,
					outputFiles, mainClassFinder);
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

//			resetRunningProject(aProject);
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
	
	ProjectStepperDisplayer projectStepperDisplayer;
	
	public ProjectStepperDisplayer getProjectStepperDisplayer() {
		return projectStepperDisplayer;
	}

	public void setProjectStepperDisplayer(
			ProjectStepperDisplayer projectStepperDisplayer) {
		this.projectStepperDisplayer = projectStepperDisplayer;
	}

	protected ProjectStepperDisplayer createProjectStepperDisplayer() {
		return new AnOEProjectStepperDisplayer();
	}

	public Object displayProjectStepper(ProjectStepper aProjectStepper) {
		return projectStepperDisplayer.display(aProjectStepper);

//		OEFrame oeFrame = ObjectEditor.edit(aProjectStepper);
//		oeFrame.setLocation(700, 500);
//		oeFrame.setSize(500, 700);
//		return oeFrame;
	}

	public ProjectStepper createProjectStepper() {
		return new AProjectStepper();
	}

	public void runProjectInteractively(String anOnyen) {
		runProjectInteractively(anOnyen, createAndDisplayProjectStepper());

	}
	NavigationListCreator navigationListCreator;
	
	public NavigationListCreator getNavigationListCreator() {
		return navigationListCreator;
	}

	public void setNavigationListCreator(NavigationListCreator navigationListCreator) {
		this.navigationListCreator = navigationListCreator;
	}

	NavigationListCreator createNavigationListCreator() {
		return new AnUnsortedNavigationListCreator();
	}
	
	public List<String> getOnyenNavigationList(SakaiProjectDatabase aSakaiProjectDatabase) {
//		Set<String> onyens = new HashSet(aSakaiProjectDatabase.getOnyens());
//		return new ArrayList(aSakaiProjectDatabase.getOnyens());
		return getNavigationListCreator().getOnyenNavigationList(aSakaiProjectDatabase);
		
		
	}
	
	
	
	public List<String> getOnyenNavigationList() {
//		Set<String> onyens = new HashSet(aSakaiProjectDatabase.getOnyens());
		return getOnyenNavigationList(this);
		
		
	}

	public void runProjectsInteractively() {
		ProjectStepper aProjectStepper = createAndDisplayProjectStepper();
		maybeMakeProjects();
//		aProjectStepper.setProjectDatabase(this);
//		Set<String> onyens = new HashSet(onyenToProject.keySet());
//		aProjectStepper.setHasMoreSteps(true);
		List<String> onyens = getOnyenNavigationList(this);

		for (String anOnyen : onyens) {
			runProjectInteractively(anOnyen, aProjectStepper);
		}
		aProjectStepper.setHasMoreSteps(false);

	}
	public void nonBlockingRunProjectsInteractively() {
		maybeMakeProjects();
		ProjectStepper aProjectStepper = createAndDisplayProjectStepper();
		aProjectStepper.configureNavigationList();
		aProjectStepper.runProjectsInteractively();
//		aProjectStepper.setProjectDatabase(this);
//		Set<String> onyens = new HashSet(onyenToProject.keySet());
//		aProjectStepper.setHasMoreSteps(true);
//		List<String> onyens = getOnyenNavigationList(this);
//
//		for (String anOnyen : onyens) {
//			runProjectInteractively(anOnyen, aProjectStepper);
//		}
//		aProjectStepper.setHasMoreSteps(false);

	}
	@Override
	public void startProjectStepper() {
		maybeMakeProjects();
		ProjectStepper aProjectStepper = createAndDisplayProjectStepper();
		aProjectStepper.configureNavigationList();
	}
	
	String navigationFilter  = "";

	public String getNavigationFilter() {
		return navigationFilter;
	}

	public void setNavigationFilter(String navigationFilter) {
		this.navigationFilter = navigationFilter;
	}

	PrintStream origOut;
	InputStream origIn;

	// SakaiProject nextProject;
	// String onyen;
	// ProjectStepper projectStepper;
	List<OEFrame>  oldList;
	Window[] oldWindows;
	public void recordWindows() {
		oldList = new ArrayList( uiFrameList.getList());
		oldWindows =	Window.getWindows();
	}
	public void clearWindows() {
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
	}
	
	public void initIO() {
		origOut = System.out;
		origIn = System.in;
	}
	

	public void runProjectInteractively(String anOnyen,
			ProjectStepper aProjectStepper) {
		SakaiProject aProject = getProject(anOnyen);

//		origOut = System.out;
//		origIn = System.in;
		initIO();
		
//		if (aProjectStepper.isAutoRun()) {
//			runProject(anOnyen, aProject);
//		}

		
		recordWindows();
		if (aProjectStepper.isAutoRun()) {
			runProject(anOnyen, aProject);
		}
		
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

	protected DocumentDisplayer wordSourceCodeDisplayer = new AWordDocumentDisplayer();;

	public void displayOutput() {
		resetIO();
		if (outputFiles.length == 0) {
			DocumentDisplayerRegistry.display(outputFileName);
			return;
		}
		for (String anOutputFileName : outputFiles) {

			System.out.println("Displaying output from:" + anOutputFileName);
			DocumentDisplayerRegistry.display(anOutputFileName);
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
			if (aStudentAssignment ==  null) {
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
//		projectDatabase.runProjectsInteractively();
		projectDatabase.nonBlockingRunProjectsInteractively();


	}


    // Added by Josh
    public void setManualFeedback(ManualFeedback manualFeedback) {
        this.manualFeedback = manualFeedback;
    }

}
