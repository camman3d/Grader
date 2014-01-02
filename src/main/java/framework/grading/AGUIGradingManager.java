package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.gui.GradingWindow;
import framework.gui.SettingsWindow;
import framework.logging.recorder.ConglomerateRecorder;
import framework.navigation.BulkDownloadFolder;
import framework.navigation.NotValidDownloadFolderException;
import framework.navigation.SakaiBulkDownloadFolder;
import framework.navigation.StudentFolder;
import framework.project.Project;
import org.joda.time.DateTime;
import scala.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the entire grading pipeline, which is:
 *  1. Getting Download location w/ onyen information
 *  2. Getting student folders and the associated java project. For each of those
 *      a. Auto grade the project
 *      b. Get manual grading and verification for the project
 *      c. Log the results
 *
 * Like ProjectStepper
 */
public class AGUIGradingManager implements GradingManager {

    private String projectName;
    private ProjectRequirements projectRequirements;
//    private List<Logger> loggers;

    // Settings that affect what to grade
    private String downloadPath;
    private String start;
    private String end;

    // Logger
//    private Logger logger;

    public AGUIGradingManager(String projectName, ProjectRequirements projectRequirements) {
        this.projectName = projectName;
        this.projectRequirements = projectRequirements;
//        loggers = new ArrayList<Logger>() {{
//            add(new LocalJsonLogger());
//            add(new LocalTextSummaryLogger());
//        }};
//        this.logger = logger;
    }

    @Override
    public void run() {
        getGradingOptions();

        try {
            // Get the student folders, starting and ending with the specified onyens
            BulkDownloadFolder downloadFolder = new SakaiBulkDownloadFolder(downloadPath);
            List<StudentFolder> folders = downloadFolder.getStudentFolders(start, end);
//            loggers.add(new FeedbackJsonLogger(downloadFolder.getFolder()));
//            loggers.add(new FeedbackTextSummaryLogger(downloadFolder.getFolder()));

            // Grade each one
            for (StudentFolder folder : folders) {

                Option<Project> project = folder.getProject(projectName);
                List<CheckResult> featureResults;
                List<CheckResult> restrictionResults;

                // If there is a  project then attempt to auto grade
                if (project.isDefined()) {

                    // Run all the checks/test cases
                    featureResults = projectRequirements.checkFeatures(project.get());
                    restrictionResults = projectRequirements.checkRestrictions(project.get());
                } else {

                    // Gracefully handle absence of project by not doing auto grading
                    featureResults = new ArrayList<CheckResult>();
                    restrictionResults = new ArrayList<CheckResult>();
                    for (Feature feature : projectRequirements.getFeatures())
                        featureResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, feature));
                    for (Restriction restriction : projectRequirements.getRestrictions())
                        restrictionResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, restriction));
                }

                // Do manual grading and verification
                GradingWindow window = GradingWindow.create(projectRequirements, folder, project, featureResults, restrictionResults);
                boolean continueGrading = window.awaitDone();
                String comments = window.getComments();

                // Figure out the late penalty
                Option<DateTime> timestamp = folder.getTimestamp();
                double gradePercentage = timestamp.isDefined() ? projectRequirements.checkDueDate(timestamp.get()) : 0;

                // Save the results
                ConglomerateRecorder.getInstance().newSession(folder.getOnyen());
                ConglomerateRecorder.getInstance().save(featureResults);
                ConglomerateRecorder.getInstance().save(restrictionResults);
                ConglomerateRecorder.getInstance().save(comments);
                ConglomerateRecorder.getInstance().save(gradePercentage);
                ConglomerateRecorder.getInstance().finish();

                if (!continueGrading)
                    System.exit(0);
            }
            System.out.println("Done!");
        } catch (NotValidDownloadFolderException e) {
            System.out.println("Not a valid Sakai download folder");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void getGradingOptions() {
        SettingsWindow window = SettingsWindow.create();
        window.awaitBegin();
        downloadPath = window.getDownloadPath();
        start = window.getStart();
        end = window.getEnd();
    }

//    private void logResults(StudentFolder folder, List<CheckResult> featureResults,
//                            List<CheckResult> restrictionResults, String comments, double gradePercentage) {
//
//        // Log the results
//        for (Logger logger : loggers)
//            logger.save(projectName, folder.getUserId(), featureResults, restrictionResults, comments, gradePercentage);
//    }

}
