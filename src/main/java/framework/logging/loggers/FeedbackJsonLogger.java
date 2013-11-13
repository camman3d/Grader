package framework.logging.loggers;

import framework.logging.recorder.RecordingSession;
import framework.logging.serializers.SerializationUtils;
import framework.utils.GraderSettings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * This saves data to a local location
 */
public class FeedbackJsonLogger implements Logger {
    @Override
    public void save(RecordingSession recordingSession) {
        String text = SerializationUtils.getSerializer("json").serialize(recordingSession);

        // Maybe write this to a file
        File file = new File(GraderSettings.get().get("path") + "/" + recordingSession.getUserId() + "/Feedback Attachments(s)/results.json");
        try {
            FileUtils.writeStringToFile(file, text);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
