package grader.sakai;

import grader.file.FileProxy;
import grader.file.RootFolderFactory;
import grader.file.RootFolderProxy;
import grader.file.zipfile.AZippedRootFolderProxy;
import grader.project.Project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ASakaiBulkAssignmentFolder implements BulkAssignmentFolder {
public static String DEFAULT_BULK_DOWNLOAD_FOLDER = "C:/Users/dewan/Downloads/bulk_download";
public static String DEFAULT_ASSIGNMENT_NAME= "Assignment 11";
public static String GRADES_SPREADSHEET_NAME = "grades.csv";

String bulkDownloadDirectory;
String assignmentName;
RootFolderProxy rootBulkDownloadFolder;
boolean isZippedRootFolder;
FileProxy assignmentFolder;
Set<String> studentFolderNames;
Set<Project> studentFolders;
//String sumissionFolderName;
FileProxy submissionFolder;
FileProxy gradeSpreadsheet;

public ASakaiBulkAssignmentFolder(String aBulkDownloadFolder, String anAssignmentName) {
	bulkDownloadDirectory = aBulkDownloadFolder;
	assignmentName = anAssignmentName;
	initializeAssignmentData();
//	rootBulkDownloadFolder = RootFolderFactory.createRootFolder(bulkDownloadDirectory);
//	isZippedRootFolder = rootBulkDownloadFolder instanceof AZippedRootFolderProxy;
//	
	
}
public ASakaiBulkAssignmentFolder(String aBulkDownloadFolder) {
	bulkDownloadDirectory = aBulkDownloadFolder;
//	initializeAssignmentData();
//	assignmentName = assignmentFolder.getLocalName();
//	rootBulkDownloadFolder = RootFolderFactory.createRootFolder(bulkDownloadDirectory);
//	isZippedRootFolder = rootBulkDownloadFolder instanceof AZippedRootFolderProxy;
//	
	
}

void initializeAssignmentData() {
	rootBulkDownloadFolder = RootFolderFactory.createRootFolder(bulkDownloadDirectory);
	isZippedRootFolder = rootBulkDownloadFolder instanceof AZippedRootFolderProxy;
	setAssignmentFolder();
	setGradeSpreadsheet();
	setStudentFolderNames();
	
}

public String getAssignmentName() {
	return assignmentName;
}

 FileProxy extractAssignmentFolder() {
	 Set<String> childrenNames = rootBulkDownloadFolder.getChildrenNames();
	 for (String childName:childrenNames) {
		 FileProxy fileProxy = rootBulkDownloadFolder.getFileEntry(childName);
		 if (fileProxy.isDirectory()) return fileProxy;
	 }
	return rootBulkDownloadFolder.getFileEntry(rootBulkDownloadFolder.getAbsoluteName() + "/" + assignmentName);
	
}
 FileProxy extractGradeSpreadsheet() {
	return rootBulkDownloadFolder.getFileEntry(assignmentFolder.getAbsoluteName() + "/" + GRADES_SPREADSHEET_NAME);
	
}

public FileProxy getAssignmentFolder() {
	return assignmentFolder;
}

void setAssignmentFolder() {
	assignmentFolder = extractAssignmentFolder();
}

 Set<String> extractStudentFolderNames() {
	 Set<String> retVal = new HashSet(
	 assignmentFolder.getChildrenNames());
	 List<String> fileChildren = new ArrayList();
	 for (String childName:retVal) {
		 FileProxy fileProxy = rootBulkDownloadFolder.getFileEntry(childName);
		 if (!fileProxy.isDirectory()) {
			fileChildren.add(childName);
		 }


	 }
	 for (String fileChild:fileChildren) {
		 retVal.remove(fileChild);
	 }
//	 retVal.remove(gradeSpreadsheet.getAbsoluteName());
	 return retVal;
	 
}

 void setStudentFolderNames() {
	 studentFolderNames = extractStudentFolderNames();
}
 
 void setGradeSpreadsheet() {
	 gradeSpreadsheet = extractGradeSpreadsheet();
}
 @Override
 public FileProxy getSpreadsheet() {
	 return gradeSpreadsheet;
 }
 @Override
 public Set<String> getStudentFolderNames() {
	 return studentFolderNames;
 }
 
 @Override 
 public FileProxy getStudentFolder(String aName) {
	 return rootBulkDownloadFolder.getFileEntry(aName);
 }
 
// void setProjects() {
//	 Set<Project> studentProjects = new HashSet();
//	 for (String studentName:studentFolderNames) {
//		 FileProxy studentFolder = rootBulkDownloadFolder.getFileEntry(studentName);
//		 Project project = new AProject(studentFolder, null, true);
//	 }
// }
 
public static void main (String[] args) {
	BulkAssignmentFolder assignment = new ASakaiBulkAssignmentFolder(DEFAULT_BULK_DOWNLOAD_FOLDER, DEFAULT_ASSIGNMENT_NAME);
	System.out.println(assignment.getAssignmentFolder());
	System.out.println(assignment.getStudentFolderNames());
	
}



}
