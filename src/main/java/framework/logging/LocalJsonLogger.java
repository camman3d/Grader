package framework.logging;

import framework.grading.testing.CheckResult;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This saves data to a local location
 */
public class LocalJsonLogger implements Logger {

    @Override
    public void save(String projectName, String userId, List<CheckResult> featureResults, List<CheckResult> restrictionResults, String comments) {
        // Make sure the folder exists
        File folder = new File("log/" + projectName);
        folder.mkdirs();

        // Write the json file
        ObjectMapper mapper = new ObjectMapper();
        JsonWritableResults results = new JsonWritableResults(userId, featureResults, restrictionResults, comments);
        try {
            mapper.writeValue(new File(folder, userId + ".json"), results);
        } catch (IOException e) {
            System.out.println("Unable to write .json file");
        }
    }
}
