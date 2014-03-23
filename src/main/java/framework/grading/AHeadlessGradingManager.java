package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.logging.recorder.ConglomerateRecorder;
import framework.navigation.BulkDownloadFolder;
import framework.navigation.NotValidDownloadFolderException;
import framework.navigation.SakaiBulkDownloadFolder;
import framework.navigation.StudentFolder;
import framework.project.Project;
import framework.utils.GraderSettings;
import framework.utils.GradingEnvironment;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.joda.time.DateTime;
import scala.Option;

/**
 * Created by Andrew on 1/1/14.
 */
public class AHeadlessGradingManager implements GradingManager {

    private String configFile;

    private String projectName;
    private ProjectRequirements projectRequirements;
//    private List<Logger> loggers;

    // Settings that affect what to grade
    private String downloadPath;
    private String start;
    private String end;

    public AHeadlessGradingManager(String projectName, ProjectRequirements projectRequirements) {
        this(projectName, projectRequirements, "config.properties");
    }

    public AHeadlessGradingManager(String projectName, ProjectRequirements projectRequirements, String configFile) {
        this.projectName = projectName;
        this.projectRequirements = projectRequirements;
        this.configFile = configFile;
    }

    @Override
    public void run() {
        try {
            getGradingOptions();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        try {
            // Get the student folders, starting and ending with the specified onyens
            System.out.println(downloadPath);
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
                    for (Feature feature : projectRequirements.getFeatures()) {
                        featureResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, feature));
                    }
                    for (Restriction restriction : projectRequirements.getRestrictions()) {
                        restrictionResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, restriction));
                    }
                }

                // Do manual grading and verification
                //GradingWindow window = GradingWindow.create(projectRequirements, folder, project, featureResults, restrictionResults);
                //boolean continueGrading = window.awaitDone();
                //String comments = window.getComments();

                // Figure out the late penalty
                Option<DateTime> timestamp = folder.getTimestamp();
                double gradePercentage = timestamp.isDefined() ? projectRequirements.checkDueDate(timestamp.get()) : 0;

                // Save the results
                ConglomerateRecorder.getInstance().newSession(folder.getOnyen());
                ConglomerateRecorder.getInstance().save(featureResults);
                ConglomerateRecorder.getInstance().save(restrictionResults);
                //ConglomerateRecorder.getInstance().save(comments);
                ConglomerateRecorder.getInstance().save(gradePercentage);
                ConglomerateRecorder.getInstance().finish();

                //if (!continueGrading)
                //    System.exit(0);
            }
            System.out.println("Done!");
        } catch (NotValidDownloadFolderException e) {
            System.out.println("Not a valid Sakai download folder");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void getGradingOptions() throws ConfigurationException {
        // Load the config file
        PropertiesConfiguration configuration = new PropertiesConfiguration(configFile);

        String configPath = configuration.getString("grader.headless.path", null);
        if (configPath != null) {
            GraderSettings.get().set("path", configPath);
        }

        String configStart = configuration.getString("grader.headless.start", null);
        if (configStart != null) {
            GraderSettings.get().set("start", configStart);
        }

        String configEnd = configuration.getString("grader.headless.end", null);
        if (configEnd != null) {
            GraderSettings.get().set("end", configEnd);
        }

        if (GraderSettings.get().has("editor")) {
            String editor = GraderSettings.get().get("editor");
            GradingEnvironment.get().setEditor(editor);
        }
        if (GraderSettings.get().has("path")) {
            downloadPath = GraderSettings.get().get("path");
        }
        if (GraderSettings.get().has("start")) {
            start = GraderSettings.get().get("start");
        }
        if (GraderSettings.get().has("end")) {
            end = GraderSettings.get().get("end");
        }
    }

//    private void logResults(StudentFolder folder, List<CheckResult> featureResults,
//                            List<CheckResult> restrictionResults, String comments, double gradePercentage) {
//
//        // Log the results
//        for (Logger logger : loggers)
//            logger.save(projectName, folder.getUserId(), featureResults, restrictionResults, comments, gradePercentage);
//    }

}
