package project;

import scala.Option;

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
}
