package framework.logging.loggers;

import framework.grading.testing.CheckResult;
import framework.logging.loggers.Logger;
import framework.logging.recorder.RecordingSession;
import framework.logging.serializers.SerializationUtils;
import framework.utils.GradingEnvironment;
import framework.utils.GradingManifest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Saves a text summary to a local file.
 */
public class LocalTextSummaryLogger implements Logger {
    @Override
    public void save(RecordingSession recordingSession) {
        String text = SerializationUtils.getSerializer("text").serialize(recordingSession);

        // Maybe write this to a file
        File folder = new File("log/" + GradingManifest.getActiveManifest().getProjectName());
        try {
            FileUtils.writeStringToFile(new File(folder, recordingSession.getUserId() + ".txt"), text);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
