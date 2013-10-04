package framework.project;

import utils.DirectoryUtils;
import scala.Option;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A "standard" project. That is, an IDE-based java project.
 */
public class StandardProject implements Project {

    private File directory;
    private Option<ProjectClassesManager> classesManager;

    /**
     * Basic constructor
     *
     * @param directory The location of the project
     * @param name      The name of the project, such as "Assignment1"
     * @throws FileNotFoundException
     */
    public StandardProject(File directory, String name) throws FileNotFoundException {
        // Find the folder. We could be there or it could be in a different folder
        Option<File> src = DirectoryUtils.locateFolder(directory, "src");
        if (src.isEmpty())
            throw new FileNotFoundException("No src folder");
        this.directory = src.get().getParentFile();

        try {
            File sourceFolder = new File(this.directory, "src");
            File buildFolder = getBuildFolder("main." + name);
            classesManager = Option.apply(new ProjectClassesManager(buildFolder, sourceFolder));
        } catch (Exception e) {
            classesManager = Option.empty();
        }
    }

    /**
     * This figures out where the build folder is, taking into account variations due to IDE
     *
     * @param executeClass The name of the class that has the main method, such as "main.Assignment1"
     * @return The build folder
     * @throws FileNotFoundException
     */
    private File getBuildFolder(String executeClass) throws FileNotFoundException {
        Option<File> out = DirectoryUtils.locateFolder(directory, "out");
        Option<File> bin = DirectoryUtils.locateFolder(directory, "bin");

        // If there is no 'out' or 'bin' folder then give up
        if (out.isEmpty() && bin.isEmpty())
            throw new FileNotFoundException();
        else {
            // There can be more folders under it, so look around some more
            // But first check the class name to see what we are looking for
            File dir = null;
            if (out.isDefined())
                dir = out.get();
            if (bin.isDefined())
                dir = bin.get();

            if (executeClass.contains(".")) {
                Option<File> packageDir = DirectoryUtils.locateFolder(dir, executeClass.split("\\.")[0]);
                if (packageDir.isDefined())
                    return packageDir.get().getParentFile();
                else
                    return dir;
            } else
                return dir;
        }
    }

    @Override
    public void start() {
        // TODO: Start the project
    }

    @Override
    public void launch() {
        // TODO: Launch the project
    }

    @Override
    public Option<ProjectClassesManager> getClassesManager() {
        return classesManager;
    }
}
