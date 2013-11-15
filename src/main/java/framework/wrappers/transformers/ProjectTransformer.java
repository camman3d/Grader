package framework.wrappers.transformers;

import framework.navigation.SakaiStudentFolder;
import framework.navigation.StudentFolder;
import framework.project.StandardProject;
import framework.utils.GraderSettings;
import grader.project.Project;
import grader.sakai.project.SakaiProject;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import tools.DirectoryUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

/**
 * This transforms a "grader" project into a "framework" project.
 * This class and {@link framework.wrappers.TestCaseWrapper} are needed for the
 * {@link framework.wrappers.ProjectStepperDisplayerWrapper} to work properly.
 */
public class ProjectTransformer extends StandardProject {

    private Project project;

    public ProjectTransformer(Project project, String name) throws FileNotFoundException {
        super(getDirectory(project), name);
        this.project = project;
    }

    public SakaiProject getProject() {
        return (SakaiProject) project;
    }

    /**
     * Given a grader project, this figures out what folder the project is in, extracting the zip file if necessary.
     *
     * @param project The grader project
     * @return The folder of the project
     * @throws FileNotFoundException
     */
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

    /**
     * Given a grader project, this figures out where the student folder is.
     *
     * @param project The grader project
     * @return The student folder
     */
    public static StudentFolder getStudentFolder(Project project) {
        File path = new File(project.getProjectFolderName());
        return new SakaiStudentFolder(path.getParentFile().getParentFile());
    }

    public static StudentFolder getStudentFolder(final String onyen) {
        File folder = DirectoryUtils.find(new File(GraderSettings.get().get("path")), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("(" + onyen + ")");
            }
        }).get();
        return new SakaiStudentFolder(folder);
    }
}
