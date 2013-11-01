package framework.wrappers;

import framework.project.StandardProject;
import grader.project.Project;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/23/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectWrapper extends StandardProject {



    public ProjectWrapper(Project project, String name) throws FileNotFoundException {
        super(getDirectory(project), name);
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
}
