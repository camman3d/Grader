package tools;

import framework.utils.GradingEnvironment;
import framework.utils.GradingSettings;

import java.io.File;
import java.io.IOException;

/**
 * This class allows you to edit files or open then in the file browser
 */
public class FileViewer {

    /**
     * Edits a directory or file in the text editor
     *
     * @param file The directory or file
     */
    public static void edit(File file) {
        try {
            String editor = GradingSettings.get().getEditor();
            new ProcessBuilder(editor, file.getAbsolutePath()).start();
        } catch (IOException e) {
            System.out.println("Can't edit file/folder");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Opens a directory in the file browser
     * @param file The directory
     */
    public static void open(File file) {
        try {
            new ProcessBuilder(GradingEnvironment.get().getBrowser(), file.getAbsolutePath()).start();
        } catch (IOException e) {
            System.out.println("Can't open file");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
