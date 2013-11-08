package framework.project;

import framework.execution.NotRunnableException;
import framework.execution.RunningProject;
import org.joda.time.DateTime;
import scala.Option;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Based on {@link grader.project.Project}
 */
public interface Project {

    /**
     * Attempts to start the project in the same process
     */
    public RunningProject start(String input) throws NotRunnableException;

    /**
     * Attempts to launch the project in a new process
     */
    public RunningProject launch(String input) throws NotRunnableException;

    /**
     * Attempts to start the project in the same process
     */
    public RunningProject start(String input, int timeout) throws NotRunnableException;

    /**
     * Attempts to launch the project in a new process
     */
    public RunningProject launch(String input, int timeout) throws NotRunnableException;


    public RunningProject launchInteractive() throws NotRunnableException;

    /**
     * @return The {@link ClassesManager} for this project. This can be used to look at the source code.
     */
    public Option<ClassesManager> getClassesManager();

    /**
     * @return The source code folder
     */
    public File getSourceFolder();

    /**
     * @return The bin/out/target folder
     */
    public File getBuildFolder(String preferredClass) throws FileNotFoundException;
}
