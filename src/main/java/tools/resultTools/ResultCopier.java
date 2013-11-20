package tools.resultTools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * This copies the .json files from the student folders in the Sakai bulk download folder to a local log folder.
 */
public class ResultCopier {
    public static void main(String[] args) {

        String path = "/Users/josh/Dropbox/Assignment 10";
        String logPath = "./log/Assignment10";

        File folder = new File(path);
        File logFolder = new File(logPath);
        logFolder.mkdirs();
        File[] studentFolders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File studentFolder : studentFolders) {
            // Look for a result .json file
            File resultFile = new File(studentFolder, "Feedback Attachment(s)/results.json");
            if (resultFile.exists()) {
                try {
                    FileUtils.copyFile(resultFile, new File(logFolder, studentFolder.getName() + ".json"));
                } catch (IOException e) {
                    System.out.println("Error copying file: " + studentFolder.getName());
                }
            } else
                System.out.println("No results.json for: " + studentFolder.getName());
        }

        System.out.println("Done");
    }
}
