package framework.wrappers.graderWrappers;

import framework.execution.NotRunnableException;
import framework.execution.RunningProject;
import framework.project.ClassesManager;
import framework.project.Project;
import scala.Option;
import util.trace.TraceableLog;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectWrapper implements Project {

    private grader.project.Project project;

    public ProjectWrapper(grader.project.Project project) {
        this.project = project;
    }

    @Override
    public RunningProject start(String input) throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RunningProject launch(String input) throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RunningProject start(String input, int timeout) throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RunningProject launch(String input, int timeout) throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RunningProject launchInteractive() throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Option<ClassesManager> getClassesManager() {
        ClassesManager manager = new ClassesManagerWrapper(project.getClassesManager());
        return Option.apply(manager);
    }

    @Override
    public File getSourceFolder() {
        return new File(project.getRootCodeFolder().getAbsoluteName(), "src");
    }

    @Override
    public File getBuildFolder(String preferredClass) throws FileNotFoundException {
        return new File(project.getBinaryProjectFolderName());
    }

    @Override
    public TraceableLog getTraceableLog() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
