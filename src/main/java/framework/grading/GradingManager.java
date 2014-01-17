package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.gui.GradingWindow;
import framework.logging.recorder.ConglomerateRecorder;
import framework.navigation.BulkDownloadFolder;
import framework.navigation.NotValidDownloadFolderException;
import framework.navigation.SakaiBulkDownloadFolder;
import framework.navigation.StudentFolder;
import framework.project.Project;
import framework.utils.GradingManifest;
import org.joda.time.DateTime;
import scala.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the entire grading pipeline, which is:
 * 1. Getting Download location w/ onyen information
 * 2. Getting student folders and the associated java project. For each of those
 * a. Auto grade the project
 * b. Get manual grading and verification for the project
 * c. Log the results
 * <p/>
 * Like ProjectStepper
 */
public class GradingManager {

    private ProjectRequirements projectRequirements;

    public GradingManager(ProjectRequirements projectRequirements) {
        this.projectRequirements = projectRequirements;
    }

    public void run() {

        try {
            // Get the student folders
            String path = GradingManifest.getActiveManifest().getDownloadPath();
            List<String> onyens = GradingManifest.getActiveManifest().getOnyens();
            BulkDownloadFolder downloadFolder = new SakaiBulkDownloadFolder(path);
            List<StudentFolder> folders = downloadFolder.getStudentFolders(onyens);

            // Grade each one
            for (StudentFolder folder : folders) {

                Option<Project> project = folder.getProject(GradingManifest.getActiveManifest().getProjectName());
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

//    private void getGradingOptions() {
//        SettingsWindow window = SettingsWindow.create();
//        window.awaitBegin();
//        downloadPath = window.getDownloadPath();
//        start = window.getStart();
//        end = window.getEnd();
//    }

//    private void logResults(StudentFolder folder, List<CheckResult> featureResults,
//                            List<CheckResult> restrictionResults, String comments, double gradePercentage) {
//
//        // Log the results
//        for (Logger logger : loggers)
//            logger.save(projectName, folder.getUserId(), featureResults, restrictionResults, comments, gradePercentage);
//    }

}
