package framework.logging.serializers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SerializationUtils {

    private static Map<String, RecordingSessionSerializer> serializers = new HashMap<String, RecordingSessionSerializer>() {{
        put("text", new TextSerializer());
        put("json", new JsonSerializer());
    }};

    public static RecordingSessionSerializer getSerializer(String name) {
        return serializers.get(name);
    }

}
