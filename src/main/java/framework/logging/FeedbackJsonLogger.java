package framework.logging;

import framework.grading.testing.CheckResult;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This saves data to a local location
 */
public class FeedbackJsonLogger implements Logger {

    private File downloadFolder;

    public FeedbackJsonLogger(File downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    @Override
    public void save(String projectName, String userId, List<CheckResult> featureResults, List<CheckResult> restrictionResults, String comments) {
        File feedbackFolder = new File(downloadFolder, userId + "/Feedback Attachment(s)");

        // Write the json file
        ObjectMapper mapper = new ObjectMapper();
        JsonWritableResults results = new JsonWritableResults(userId, featureResults, restrictionResults, comments);
        try {
            mapper.writeValue(new File(feedbackFolder, "results.json"), results);
        } catch (IOException e) {
            System.out.println("Unable to write .json file");
        }
    }
}
