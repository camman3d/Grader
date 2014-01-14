package framework.grading.manifests;

import framework.gui.SettingsWindow;
import org.apache.commons.io.FileUtils;
import scala.Option;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/14/14
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class GradingManifestLoader {

    /**
     * This looks for the onyens file. If it exist then it won't ask for the onyens in the settings window.
     *
     * @return
     */
    public static GradingManifest load() {
        Option<List<String>> onyens = lookForOnyensFile();
        SettingsWindow window = SettingsWindow.create(onyens);
        window.awaitBegin();
        return window.getGradingManifest();
    }

    private static Option<List<String>> lookForOnyensFile() {
        File onyensFile = new File(".onyens");
        if (onyensFile.exists()) {
            try {
                List<String> lines = FileUtils.readLines(onyensFile);
                return Option.apply(lines);
            } catch (IOException e) {
                return Option.empty();
            }
        }
        return Option.empty();
    }
}
