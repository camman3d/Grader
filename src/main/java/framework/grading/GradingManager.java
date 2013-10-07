package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.gui.GradingWindow;
import framework.gui.SettingsWindow;
import framework.logging.FeedbackJsonLogger;
import framework.logging.LocalJsonLogger;
import framework.logging.Logger;
import framework.logging.TextSummaryLogger;
import framework.navigation.BulkDownloadFolder;
import framework.navigation.NotValidDownloadFolderException;
import framework.navigation.SakaiBulkDownloadFolder;
import framework.navigation.StudentFolder;
import framework.project.Project;
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
public class GradingManager {

    private String projectName;
    private ProjectRequirements projectRequirements;
    private List<Logger> loggers;

    // Settings that affect what to grade
    private String downloadPath;
    private String start;
    private String end;

    public GradingManager(String projectName, ProjectRequirements projectRequirements) {
        this.projectName = projectName;
        this.projectRequirements = projectRequirements;
        loggers = new ArrayList<Logger>() {{
//            add(new LocalJsonLogger());
            add(new TextSummaryLogger());
        }};
    }

    public void run() {
        getGradingOptions();

        try {
            // Get the student folders, starting and ending with the specified onyens
            BulkDownloadFolder downloadFolder = new SakaiBulkDownloadFolder(downloadPath);
            List<StudentFolder> folders = downloadFolder.getStudentFolders(start, end);
//            loggers.add(new FeedbackJsonLogger(downloadFolder.getFolder()));

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

                // Log the results
                logResults(folder, featureResults, restrictionResults, comments);

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

    private void logResults(StudentFolder folder, List<CheckResult> featureResults,
                            List<CheckResult> restrictionResults, String comments) {

        // Log the results
        for (Logger logger : loggers)
            logger.save(projectName, folder.getUserId(), featureResults, restrictionResults, comments);
    }

}
