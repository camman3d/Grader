package framework.project;

import scala.Option;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Like Project
 */
public interface Project<T extends ClassesManager> {

    /**
     * Attempts to start the project in the same process
     */
    public void start();

    /**
     * Attempts to launch the project in a new process
     */
    public void launch();

    /**
     * @return The {@link ClassesManager} for this project. This can be used to look at the source code.
     */
    public Option<T> getClassesManager();

    /**
     * @return The source code folder
     */
    public File getSourceFolder();

    /**
     * @return The bin/out/target folder
     */
    public File getBuildFolder(String preferredClass) throws FileNotFoundException;
}
