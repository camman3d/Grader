package joshTest;

import grader.sakai.project.ProjectStepper;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 12:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoshProjectStepper implements ProjectStepper {



    @Override
    public boolean setProject(SakaiProject newVal) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void output() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sources() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getScore() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setScore(double newVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void proceed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void waitForClearance() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SakaiProjectDatabase getProjectDatabase() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setOnyen(String anOnyen) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean setProject(String anOnyen) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAutoRun() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAutoRun(boolean newVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void autoRun() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasMoreSteps() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setHasMoreSteps(boolean newVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SakaiProject getProject() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
