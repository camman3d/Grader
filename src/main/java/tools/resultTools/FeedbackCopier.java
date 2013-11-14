package tools.resultTools;

import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/13/13
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackCopier {
    public static void main(String[] args) throws IOException {

        File fileFolder = new File("/Users/josh/Downloads/log 2/Assignment9");
        File sakaiFolder = new File("/Users/josh/Dropbox/Assignment 9");

        for (File file : fileFolder.listFiles()) {
            if (!file.isDirectory() && file.getName().endsWith(".txt")) {
                String userId = file.getName().replace(".txt", "");
                File dest = new File(sakaiFolder, userId + "/Feedback Attachment(s)/feedback.txt");
                System.out.println(file.getAbsolutePath() + " -> " + dest.getAbsolutePath());
                FileUtils.copyFile(file, dest);
            }
        }

        System.out.println("Done");

    }
}
