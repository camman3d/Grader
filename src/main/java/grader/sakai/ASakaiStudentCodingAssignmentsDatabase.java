package grader.sakai;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;
import grader.assignment.AnAssignmenDataFolder;
import grader.assignment.AssignmentDataFolder;
import grader.file.RootFolderProxy;
import grader.project.AProject;
import grader.project.Project;
import grader.sakai.project.AProjectStepper;
import util.misc.ClearanceManager;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class ASakaiStudentCodingAssignmentsDatabase extends AnAbstractSakaiStudentAssignmentsDatabase<StudentCodingAssignment> {
//	BulkAssignmentFolder bulkAssignmentFolder;
//	Map<String, StudentAssignment> nameToStudentAssignment = new HashMap();
	public static final String  DEFAULT_DATA_FOLDER_NAME = "C:/Users/dewan/Downloads/GraderData/Assignment11";
//	String assignmentsDataFolderName;
//	AssignmentDataFolder assignmentsDataFolder;
	Map<String,Project> onyenToProject = new HashMap();
	public ASakaiStudentCodingAssignmentsDatabase(BulkAssignmentFolder aBulkAssignmentFolder) {
		super(aBulkAssignmentFolder);
//		assignmentsDataFolderName = anAssignmentDataFolderName;
//		assignmentsDataFolder = new AnAssignmenDataFolder(anAssignmentDataFolderName);
//		bulkAssignmentFolder = aBulkAssignmentFolder;
//		makeTable();
	}
	public void makeProjects(){
		
	}
//	void makeTable() {
//		Set<String> studentFolderNames = bulkAssignmentFolder.getStudentFolderNames();
//		for (String aFolderName:studentFolderNames) {
//			
//			String studentId = Common.shortFileName(aFolderName);
//			System.out.println(aFolderName);
//			
//			StudentAssignment studentAssignment = new ASakaiStudentAssignment(bulkAssignmentFolder.getStudentFolder(aFolderName));
//			nameToStudentAssignment.put(studentId, studentAssignment);
//		}
//	}
//	
//	public Set<String> getStudentIds() {
//		return nameToStudentAssignment.keySet();
//		
//	}
//	
//	public Collection<StudentAssignment> getStudentAssignments() {
//		return nameToStudentAssignment.values();
//	}
//	public StudentAssignment getStudentAssignment(String aStudentId) {
//		return nameToStudentAssignment.get(aStudentId);
//	}
	@Override
	protected StudentCodingAssignment createAssignment(String aStudentDescription, String aFolderName) {
		// TODO Auto-generated method stub
		return new ASakaiStudentCodingAssignment(aStudentDescription, bulkAssignmentFolder.getStudentFolder(aFolderName));
	}
	
	
	public static void main (String[] args) {
		
	

//		BulkAssignmentFolder bulkFolder = new ASakaiBulkAssignmentFolder(ASakaiBulkAssignmentFolder.DEFAULT_BULK_DOWNLOAD_FOLDER, ASakaiBulkAssignmentFolder.DEFAULT_ASSIGNMENT_NAME);
		BulkAssignmentFolder bulkFolder = new ASakaiBulkAssignmentFolder(ASakaiBulkAssignmentFolder.DEFAULT_BULK_DOWNLOAD_FOLDER);
	    AssignmentDataFolder assignmentDataFolder = new AnAssignmenDataFolder("C:/Users/dewan/Downloads/GraderData/Assignment11", bulkFolder.getSpreadsheet());
	    if (!assignmentDataFolder.exists()) {
	    	System.out.println("Expecting assignment data folder:" + "C:/Users/dewan/Downloads/GraderData/Assignment11");
	    }
		GenericStudentAssignmentDatabase<StudentCodingAssignment> studentAssignmentDatabase = new ASakaiStudentCodingAssignmentsDatabase(bulkFolder);
		System.out.println(studentAssignmentDatabase.getStudentIds());
		Collection<StudentCodingAssignment> studentAssignments = studentAssignmentDatabase.getStudentAssignments();
		ClearanceManager clearanceManager = new AProjectStepper();
		OEFrame oeFrame = ObjectEditor.edit(clearanceManager);
		oeFrame.setLocation(700, 500);
		 PrintStream origOut = System.out;
		    InputStream origIn = System.in;
		  Project lastProject = null;
		  StudentAssignment lastAssignment = null;
		  for (StudentCodingAssignment anAssignment:studentAssignments) {
			RootFolderProxy projectFolder = anAssignment.getProjectFolder();
			if (!assignmentDataFolder.getStudentIDs().contains(anAssignment.getOnyen()))
					continue;
			if (projectFolder == null) {
				System.out.println("Np project folder found for:" + anAssignment.getOnyen() + " " + anAssignment.getStudentName());
				continue;
			}
//			List<OEFrame> oldList = new ArrayList( uiFrameList.getList());

			if (!anAssignment.isSubmitted())
				break;
			List<OEFrame> newList = new ArrayList( uiFrameList.getList());
//			for (OEFrame frame:newList) {
//				if (oldList.contains(frame))
//					continue;
//				frame.dispose(); // will this work
//				
			Project aProject;
		try {
			 aProject = new AProject(anAssignment);
		} catch (Exception e) {
			e.printStackTrace();
			continue;
		}

			if (aProject.getRootCodeFolder().hasSeparateSourceBinary() && !aProject.getRootCodeFolder().hasBinary() ) {
				continue;
			}
			clearanceManager.waitForClearance();
			if (lastProject != null ) {
//				System.out.println("Ran:" + lastAssignment.getOnyen() + " " + lastAssignment.getStudentName());

				lastProject.maybeMakeClassDescriptions();
			}
			if (lastProject != null && !lastProject.hasBeenRun()) {
				System.out.println("Could not run:" + lastAssignment.getOnyen() + " " + lastAssignment.getStudentName());
			}
			 if (System.in != origIn)
			
			    System.setIn(origIn);
			    

			 if (System.out != origOut) {
				 System.out.close();
			   System.setOut(origOut);
			 }
			 

//			Project aProject = new AProject(anAssignment.getProjectFolder(),null);

//			String[] strings = {"move 10 100"};
			String[][] strings = {{}};
			String[] input = {null};
			
			
			String outputFileName = aProject.getOutputFolder() + "/" + "output.txt";
			String[] output = {outputFileName};
			System.out.println("Trying to run:" + anAssignment.getOnyen() + " " + anAssignment.getStudentName());

			Thread thread = aProject.run("main.Assignment11", strings, input, output);
			lastProject = aProject;
			lastAssignment = anAssignment;
			// need to pick up my last project's windows, how long to sleep? 2 minute?
//			ThreadSupport.sleep(ACodeDatababaseStepper.UI_LOAD_TIME);
//			thread.stop();
		
			
		}
		  oeFrame.dispose();
	}
	

	
	

}
