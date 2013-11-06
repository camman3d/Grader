package grader.sakai;

import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import grader.project.Project;

public interface StudentCodingAssignment extends StudentAssignment {

    public FileProxy getRubrick();

    public RootFolderProxy getProjectFolder();

    public Project getProject();

    public void setProject(Project newVal);


}
