package framework.wrappers;

import framework.grading.ProjectRequirements;
import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.gui.GradingWindow;
import framework.logging.recorder.ConglomerateRecorder;
import framework.navigation.StudentFolder;
import framework.utils.GradingEnvironment;
import framework.wrappers.transformers.ProjectTransformer;
import grader.assignment.GradingFeature;
import grader.assignment.GradingFeatureList;
import grader.project.Project;
import grader.sakai.project.ProjectStepper;
import grader.sakai.project.ProjectStepperDisplayer;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import org.joda.time.DateTime;
import scala.Option;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This wraps the "framework" GUI in a {@link ProjectStepperDisplayer}
 */
public class ProjectStepperDisplayerWrapper implements ProjectStepperDisplayer, PropertyChangeListener {

    private ProjectRequirements requirements;
    private ProjectStepper projectStepper;
    private SakaiProject project = null;
    private SakaiProjectDatabase projectDatabase;

    @Override
    public Object display(ProjectStepper aProjectStepper) {
        projectStepper = aProjectStepper;
        projectStepper.configureNavigationList();
        projectDatabase = projectStepper.getProjectDatabase();
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

        // Go off the project event
        if (evt.getPropertyName().equalsIgnoreCase("Project")) {

            // If the new value is null then the next submission didn't have a project.
            if (evt.getNewValue() != null) {
                project = projectStepper.getProject();

                // Make sure that the project requirements are all ready
                initRequirements();
                StudentFolder studentFolder = ProjectTransformer.getStudentFolder(project);
                List<CheckResult> featureResults;
                List<CheckResult> restrictionResults;
                GradingWindow window;

                try {

                    // Attempt to get the student's project and check it
                    framework.project.Project wrappedProject = new ProjectTransformer(this.project, GradingEnvironment.get().getAssignmentName());
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
                ConglomerateRecorder.getInstance().newSession(project.getStudentAssignment().getOnyen());

                // Figure out the late penalty
                Option<DateTime> timestamp = studentFolder.getTimestamp();
                double gradePercentage = timestamp.isDefined() ? requirements.checkDueDate(timestamp.get()) : 0;
                ConglomerateRecorder.getInstance().save(gradePercentage);

                // Save the comments
                String comments = window.getComments();
                ConglomerateRecorder.getInstance().save(comments);

                // Save the results (grades + notes)
                GradingFeatureList features = projectDatabase.getGradingFeatures();
                String studentName = project.getStudentAssignment().getStudentName();
                String onyen = project.getStudentAssignment().getOnyen();
//                double total = 0;
                for (int i = 0; i < features.size(); i++) {
                    // Save the score for the feature
                    double score = (i < featureResults.size()) ? featureResults.get(i).getScore() : restrictionResults.get(i - featureResults.size()).getScore();

                    // Save the comments. We save them in the ConglomerateRecorder so that, if it is being used as the
                    // manual feedback, they will be pulled in.
                    ConglomerateRecorder.getInstance().setFeatureComments(featureResults.get(i).getNotes());
                    ConglomerateRecorder.getInstance().setFeatureResults(featureResults.get(i).getResults());
                    features.get(i).setScore(score);

                    // Save the score
                    projectDatabase.getFeatureGradeRecorder().setGrade(studentName, onyen, features.get(i).getFeature(), score);
//                    total += score;
                }

                // Finish up
//                projectDatabase.getTotalScoreRecorder().setGrade(studentName, onyen, total);
                ConglomerateRecorder.getInstance().finish();

                if (continueGrading)
                    projectStepper.next();
                else
                    System.exit(0);
            }
        }
    }

}
