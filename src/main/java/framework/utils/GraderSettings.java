package framework.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class GraderSettings {

    private Map<String, String> settings;

    private GraderSettings() {
        settings = new HashMap<String, String>();
        try {
            List<String> lines = FileUtils.readLines(new File(".gradersettings"));
            for (String line : lines) {
                String[] parts = line.split("=");
                if (parts.length == 2 && !parts[1].isEmpty())
                    settings.put(parts[0], parts[1]);
            }
        } catch (Exception e) {
            System.out.println("Error reading settings file.");
        }
    }

    public String get(String key) {
        return settings.get(key);
    }

    public void set(String key, String value) {
        settings.put(key, value);
    }

    public boolean has(String key) {
        return settings.containsKey(key);
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(".gradersettings");
            String data = "";
            for (String key : settings.keySet())
                data += key + "=" + settings.get(key) + "\n";
            writer.write(data);
            writer.close();

            // Make sure the file is hidden
            if (GradingEnvironment.get().getOsName().equals("Windows"))
                Runtime.getRuntime().exec("attrib +H .gradersettings");
        } catch (IOException e) {
            System.out.println("Error saving settings.");
        }
    }


    // Singleton methods
    private static GraderSettings singleton = null;
    public static GraderSettings get() {
        if (singleton == null)
            singleton = new GraderSettings();
        return singleton;
    }
}
