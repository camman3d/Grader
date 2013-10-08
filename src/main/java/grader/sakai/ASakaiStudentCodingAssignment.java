package grader.sakai;

import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import grader.file.zipfile.AZippedRootFolderProxy;
import grader.project.Project;

import java.util.Set;

public class ASakaiStudentCodingAssignment extends ASakaiStudentAssignment implements StudentCodingAssignment{
	FileProxy rubrick;
	RootFolderProxy projectFolder;
	Project project;
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project newVal) {
		this.project = newVal;
	}

	public static final String RUBRICK_SUBSTRING = "rubric";

	public ASakaiStudentCodingAssignment(String aFolderName, FileProxy aFileProxy) {
		super(aFolderName, aFileProxy);
		if (isSubmitted())
		findRubrickAndProject();
		
	}
	
	void findRubrickAndProject() {
		Set<String> childrenNames = submissionFolder.getChildrenNames();
		for (String childName:childrenNames) {
			FileProxy childProxy = submissionFolder.getFileEntry(childName);
			if (childName.toLowerCase().indexOf(RUBRICK_SUBSTRING) > -1) {
				rubrick = childProxy;		
			} else if (childProxy.isDirectory()) {
				projectFolder = childProxy;
			} else if (childProxy.getAbsoluteName().endsWith(".zip")) {
				projectFolder = new AZippedRootFolderProxy(childProxy.getAbsoluteName());
			}
				
				
		}
		
	}

	public FileProxy getRubrick() {
		return rubrick;
	}

	public RootFolderProxy getProjectFolder() {
		return projectFolder;
	}
	
	

}
