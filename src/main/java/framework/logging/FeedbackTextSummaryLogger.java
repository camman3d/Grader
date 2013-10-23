package framework.logging;

import framework.grading.testing.CheckResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 7:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackTextSummaryLogger implements Logger {

    private File downloadFolder;

    public FeedbackTextSummaryLogger(File downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    @Override
    public void save(String projectName, String userId, List<CheckResult> featureResults, List<CheckResult> restrictionResults, String comments) {
        String log = LocalTextSummaryLogger.getLog(userId, featureResults, restrictionResults, comments);

        // Maybe write this to a file
        File feedbackFolder = new File(downloadFolder, userId + "/Feedback Attachment(s)");
        try {
            FileWriter writer = new FileWriter(new File(feedbackFolder, "feedback.txt"));
            writer.write(log);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

//        System.out.println(log);
    }
}
