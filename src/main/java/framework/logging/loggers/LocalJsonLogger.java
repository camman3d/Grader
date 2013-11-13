package framework.logging.loggers;

import framework.grading.testing.CheckResult;
import framework.logging.loggers.Logger;
import framework.logging.recorder.RecordingSession;
import framework.logging.serializers.SerializationUtils;
import framework.utils.GradingEnvironment;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This saves data to a local location
 */
public class LocalJsonLogger implements Logger {

    @Override
    public void save(RecordingSession recordingSession) {
        String text = SerializationUtils.getSerializer("json").serialize(recordingSession);

        // Maybe write this to a file
        File folder = new File("log/" + GradingEnvironment.get().getAssignmentName());
        try {
            FileUtils.writeStringToFile(new File(folder, recordingSession.getUserId() + ".json"), text);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
