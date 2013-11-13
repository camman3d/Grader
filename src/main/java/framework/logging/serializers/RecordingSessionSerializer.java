package framework.logging.serializers;

import framework.logging.recorder.RecordingSession;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RecordingSessionSerializer {
    public String serialize(RecordingSession recordingSession);

}
