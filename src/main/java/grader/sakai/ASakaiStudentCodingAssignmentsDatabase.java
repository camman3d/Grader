package grader.sakai;

import grader.assignment.AnAssignmenDataFolder;
import grader.assignment.AssignmentDataFolder;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import grader.project.AProject;
import grader.project.Project;
import grader.sakai.project.AProjectStepper;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;

import util.misc.AClearanceManager;
import util.misc.ClearanceManager;
import util.misc.Common;
import util.misc.ThreadSupport;

public class ASakaiStudentCodingAssignmentsDatabase extends AnAbstractSakaiStudentAssignmentsDatabase<StudentCodingAssignment> {
	public static final String  DEFAULT_DATA_FOLDER_NAME = "C:/Users/dewan/Downloads/GraderData/Assignment11";

	Map<String,Project> onyenToProject = new HashMap();

	public ASakaiStudentCodingAssignmentsDatabase(BulkAssignmentFolder aBulkAssignmentFolder) {
		super(aBulkAssignmentFolder);
	}

    public void makeProjects(){
	}

	@Override
	protected StudentCodingAssignment createAssignment(String aStudentDescription, String aFolderName) {
		return new ASakaiStudentCodingAssignment(aStudentDescription, bulkAssignmentFolder.getStudentFolder(aFolderName));
	}
}
