package wrappers.grader.sakai.project;

import framework.project.Project;
import wrappers.grader.file.SimplifiedFileProxy;
import grader.sakai.ASakaiStudentCodingAssignment;
import grader.sakai.StudentCodingAssignment;
import grader.sakai.project.ASakaiProject;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class SakaiProjectWrapper extends ASakaiProject {
    public SakaiProjectWrapper(Project project) {
        super(getCodingAssignment(project), ".java", ".txt");

        // Set it as run so we can do stuff without actually running it
        setCanBeRun(true);
        setHasBeenRun(true);
    }

    private static StudentCodingAssignment getCodingAssignment(Project project) {
        File folder = project.getSourceFolder();
        while (!folder.getName().matches("^[^,]+, [^\\(]+\\([^\\)]+\\)$"))
            folder = folder.getParentFile();
        return new ASakaiStudentCodingAssignment(folder.getName(), new SimplifiedFileProxy(folder));
    }
}
