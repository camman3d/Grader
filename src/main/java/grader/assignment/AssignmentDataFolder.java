package grader.assignment;

import grader.file.FileProxy;
import grader.file.RootFolderProxy;

import java.util.List;
import java.util.Set;

public interface AssignmentDataFolder extends RootFolderProxy{
	public Set<String> getInputFiles() ;

	public List<String> getStudentIDs() ;
	public FileProxy getFeatureGradeFile();



}
