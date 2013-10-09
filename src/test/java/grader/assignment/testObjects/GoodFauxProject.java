package grader.assignment.testObjects;

import com.thoughtworks.qdox.JavaDocBuilder;
import grader.project.ClassesManager;
import grader.project.MainClassFinder;
import grader.project.ProxyBasedClassesManager;
import grader.project.ProxyClassLoader;
import grader.project.file.RootCodeFolder;
import grader.project.source.ClassesTextManager;
import grader.project.view.ClassViewManager;
import grader.sakai.StudentAssignment;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class GoodFauxProject implements SakaiProject {
    @Override
    public StudentAssignment getStudentAssignment() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void displaySource(SakaiProjectDatabase aSakaiProjectDatabase) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ClassesManager getClassesManager() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setClassesManager(ProxyBasedClassesManager aClassesManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ClassViewManager getClassViewManager() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setClassViewManager(ClassViewManager aClassViewManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ClassesTextManager getClassesTextManager() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setClassesTextManager(ClassesTextManager aClassesTextManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setProjectFolder(String aProjectFolder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getProjectFolderName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getSourceProjectFolderName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getBinaryProjectFolderName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Class> getImplicitlyLoadedClasses() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RootCodeFolder getRootCodeFolder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ProxyClassLoader getClassLoader() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Thread run(String mainClassName, String[][] args, String[] anInputFiles, String[] anOutputFiles) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getOutputFolder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setOutputFolder(String outputFolder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void maybeMakeClassDescriptions() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setHasBeenRun(boolean newVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCanBeRun(boolean newVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean canBeRun() {
        return true;
    }

    @Override
    public boolean hasBeenRun() {
        return true;
    }

    @Override
    public String getOutputFileName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getSourceFileName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean runChecked() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean setRunParameters(String aMainClassName, String[][] anArgs, String[] anInputFiles, String[] anOutputFiles, MainClassFinder aMainClassFinder) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Thread runProject() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JavaDocBuilder getJavaDocBuilder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
