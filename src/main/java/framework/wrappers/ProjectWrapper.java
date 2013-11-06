package framework.wrappers;

import framework.navigation.SakaiStudentFolder;
import framework.navigation.StudentFolder;
import framework.project.StandardProject;
import grader.project.Project;
import grader.sakai.project.SakaiProject;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * This wraps a "grader" project in a "framework" project.
 * This class and {@link TestCaseWrapper} are needed for the {@link ProjectStepperDisplayerWrapper} to work.
 */
public class ProjectWrapper extends StandardProject {

    private Project project;

    public ProjectWrapper(Project project, String name) throws FileNotFoundException {
        super(getDirectory(project), name);
        this.project = project;
    }

    public SakaiProject getProject() {
        return (SakaiProject) project;
    }

    private static File getDirectory(Project project) throws FileNotFoundException {

        // Can be a path or a directory
        File path = new File(project.getProjectFolderName());
        if (path.isFile()) {
            if (path.getName().endsWith(".zip")) {
                // A zip file, so unzip
                File dir = new File(path.getParentFile(), path.getName().replace(".zip", ""));
                dir.mkdir();

                try {
                    ZipFile zip = new ZipFile(path);
                    zip.extractAll(dir.getAbsolutePath());
                    return dir;
                } catch (ZipException e) {
                    throw new FileNotFoundException();
                }
            } else
                throw new FileNotFoundException();
        } else
            return path;
    }

    public static StudentFolder getStudentFolder(Project project) {
        File path = new File(project.getProjectFolderName());
        return new SakaiStudentFolder(path.getParentFile().getParentFile());
    }
}
