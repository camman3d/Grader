package grader.sakai;

import java.util.Set;

import grader.file.FileProxy;

public interface BulkAssignmentFolder {
	public FileProxy getAssignmentFolder();

	Set<String> getStudentFolderNames();

	FileProxy getStudentFolder(String aName);

	FileProxy getSpreadsheet();
	public String getAssignmentName();
	public String getMixedCaseAssignmentName();


}
