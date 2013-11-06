package framework.wrappers;

import framework.grading.ProjectRequirements;
import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.gui.GradingWindow;
import framework.navigation.StudentFolder;
import framework.utils.GradingEnvironment;
import grader.assignment.GradingFeature;
import grader.project.Project;
import grader.sakai.project.ProjectStepper;
import grader.sakai.project.ProjectStepperDisplayer;
import org.joda.time.DateTime;
import scala.Option;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/31/13
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectStepperDisplayerWrapper implements ProjectStepperDisplayer, PropertyChangeListener {

    private ProjectRequirements requirements;
    private ProjectStepper projectStepper;
    private Project project = null;

    @Override
    public Object display(ProjectStepper aProjectStepper) {
        projectStepper = aProjectStepper;
        aProjectStepper.addPropertyChangeListener(this);
        return null;
    }

    private void initRequirements() {
        // If the database happens to be a ProjectDatabaseWrapper, then pull out the requirements, otherwise create new
        if (requirements == null) {
            if (projectStepper.getProjectDatabase() instanceof ProjectDatabaseWrapper)
                requirements = ((ProjectDatabaseWrapper) projectStepper.getProjectDatabase()).getProjectRequirements();
            else {
                requirements = new ProjectRequirements();
                for (GradingFeature feature : projectStepper.getProjectDatabase().getGradingFeatures()) {
                    requirements.addFeature(feature.getFeature(), feature.getMax(), feature.isExtraCredit(), new TestCaseWrapper(feature));
                }
            }
        }
    }

    /**
     * Look for event changes and, when they happen, check the project. If it changed then display a new window
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (project != projectStepper.getProject()) {
            project = projectStepper.getProject();

            // Make sure that the project requirements are all ready
            initRequirements();
            StudentFolder studentFolder = ProjectWrapper.getStudentFolder(project);
            List<CheckResult> featureResults;
            List<CheckResult> restrictionResults;
            GradingWindow window;

            try {

                // Attempt to get the student's project and check it
                framework.project.Project wrappedProject = new ProjectWrapper(this.project, GradingEnvironment.get().getAssignmentName());
                featureResults = requirements.checkFeatures(wrappedProject);
                restrictionResults = requirements.checkRestrictions(wrappedProject);
                window = GradingWindow.create(requirements, studentFolder, Option.apply(wrappedProject), featureResults, restrictionResults);
            } catch (FileNotFoundException e) {

                // We were unable to load the project for some reason, so start with all zeros
                featureResults = new ArrayList<CheckResult>();
                restrictionResults = new ArrayList<CheckResult>();
                for (Feature feature : requirements.getFeatures())
                    featureResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, feature));
                for (Restriction restriction : requirements.getRestrictions())
                    restrictionResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, restriction));
                Option<framework.project.Project> wrappedProject = Option.empty();
                window = GradingWindow.create(requirements, studentFolder, wrappedProject, featureResults, restrictionResults);
            }

            // Do stuff when the grader is done
            boolean continueGrading = window.awaitDone();

            // Figure out the late penalty
            Option<DateTime> timestamp = studentFolder.getTimestamp();
            double gradePercentage = timestamp.isDefined() ? requirements.checkDueDate(timestamp.get()) : 0;

            // TODO: Save the results and comments
            String comments = window.getComments();

            if (continueGrading)
                projectStepper.next();
            else
                System.exit(0);
        }
    }

}
