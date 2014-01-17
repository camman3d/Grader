package tools;

import framework.utils.GradingSettings;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/16/14
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileEditing {

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

}
