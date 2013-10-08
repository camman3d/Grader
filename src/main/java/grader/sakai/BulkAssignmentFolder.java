package grader.sakai;

import grader.file.FileProxy;

import java.util.Set;

public interface BulkAssignmentFolder {
	public FileProxy getAssignmentFolder();

	Set<String> getStudentFolderNames();

	FileProxy getStudentFolder(String aName);

	FileProxy getSpreadsheet();
	public String getAssignmentName();

}
