package joshTest;

import grader.sakai.project.AProjectStepper;
import grader.sakai.project.ProjectStepper;
import grader.sakai.project.ProjectStepperDisplayer;
import joshTest.gui.JoshStepperForm;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoshProjectStepperDisplayer implements ProjectStepperDisplayer {
    @Override
    public Object display(ProjectStepper aProjectStepper) {
        // Show the window
        JoshStepperForm.create(aProjectStepper);
        return null;
    }
}
