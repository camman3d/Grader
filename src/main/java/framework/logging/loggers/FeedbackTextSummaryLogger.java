package framework.logging.loggers;

import framework.logging.recorder.RecordingSession;
import framework.logging.serializers.SerializationUtils;
import framework.utils.GraderSettings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Saves the text summary to the student's feedback folder
 */
public class FeedbackTextSummaryLogger implements Logger {

    @Override
    public void save(RecordingSession recordingSession) {
        String text = SerializationUtils.getSerializer("text").serialize(recordingSession);

        // Maybe write this to a file
        File file = new File(GraderSettings.get().get("path") + "/" + recordingSession.getUserId() + "/Feedback Attachment(s)/feedback.txt");
        try {
            FileUtils.writeStringToFile(file, text);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
