package framework.utils;

import framework.gui.SettingsWindow;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This is used to set, get, save, and load settings related to the grader.
 * This is a singleton, so you'll have to do the following {@code GradingSettings.get()} before invoking any method.
 * It looks for the file named "config/settings.properties" in the current working directory for the saved settings.
 *
 * Settings are things that the user sets.
 */
public class GradingSettings {

    private static final Map<String, String[]> editors = new HashMap<String, String[]>() {{
        put("Mac", new String[] {
                "/Applications/Sublime Text 2.app/Contents/SharedSupport/bin/subl",
                "/usr/local/bin/edit"
        });
        put("Windows", new String[] {
                "C:\\Program Files\\Sublime Text 2\\sublime_text.exe",
                "C:\\Program Files (x86)\\Sublime Text 2\\sublime_text.exe",
                "C:\\Program Files\\Notepad++\\notepad++.exe",
                "C:\\Program Files (x86)\\Notepad++\\notepad++.exe",
                "notepad"
        });
    }};

    private PropertiesConfiguration configuration;
    private String username;
    private String editor;

    private GradingSettings() {
        try {
            configuration = new PropertiesConfiguration(new File("config/settings.properties"));
            username = configuration.getString("username", "");
            editor = configuration.getString("editor", "");
        } catch (ConfigurationException e) {
            System.err.println("Error loading settings");
        }
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean hasUsername() {
        return !username.isEmpty();
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public boolean hasEditor() {
        return !editor.isEmpty();
    }

    public void save() {
        try {
            if (hasUsername())
                configuration.clearProperty("username");
            if (hasEditor())
                configuration.clearProperty("editor");
            configuration.addProperty("username", username);
            configuration.addProperty("editor", editor);
            configuration.save();
        } catch (ConfigurationException e) {
            System.err.println("GradingSettings: Error saving configuration");
        }
    }


    // Singleton methods
    private static GradingSettings singleton = null;
    public static GradingSettings get() {
        if (singleton == null) {
            singleton = new GradingSettings();

            if (singleton.username.isEmpty() || singleton.editor.isEmpty()) {
                // Attempt to locate a text editor if there is none
                if (singleton.editor.isEmpty())
                    for (String editor : editors.get(GradingEnvironment.get().getOsName()))
                        if (new File(editor).exists()) {
                            singleton.editor = editor;
                            break;
                        }

                // Show the window
                SettingsWindow.create().awaitBegin();
            }

        }
        return singleton;
    }
}
