package framework.logging;

import framework.grading.testing.CheckResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 7:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class LocalTextSummaryLogger implements Logger {


    @Override
    public void save(String projectName, String userId, List<CheckResult> featureResults, List<CheckResult> restrictionResults, String comments) {
        String log = getLog(userId, featureResults, restrictionResults, comments);


        // Maybe write this to a file
        File folder = new File("log/" + projectName);
        try {
            FileWriter writer = new FileWriter(new File(folder, userId + ".txt"));
            writer.write(log);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String getLog(String userId, List<CheckResult> featureResults, List<CheckResult> restrictionResults, String comments) {
        String log = "";
        double awarded = 0;
        double deducted = 0;

        log += "Grading result for: " + userId + "\n\n";

        log += "Grading features...\n";
        log += "----------------------------------\n";
        for (CheckResult result : featureResults) {
            awarded += result.getScore();
            log += String.format(result.getTarget().getSummary(), result.getScore()) + "\n";
        }
        log += "----------------------------------\n";
        log += "  Points Awarded: " + awarded + "\n\n";

        if (!restrictionResults.isEmpty()) {
            log += "Grading restrictions...\n";
            log += "----------------------------------\n";
            for (CheckResult result : restrictionResults) {
                deducted += result.getScore();
                log += String.format(result.getTarget().getSummary(), result.getScore()) + "\n";
            }
            log += "----------------------------------\n";
            log += "  Points Deducted: " + deducted + "\n\n";
        }

        log += "  Total Score: " + (awarded + deducted) + "\n";

        log += "\nNotes from grading features:\n";
        log += "----------------------------------\n";
        for (CheckResult result : featureResults) {
            String note = result.getSummary();
            if (!note.isEmpty())
                log += note + "\n";
        }

        log += "\nNotes from grading restrictions:\n";
        log += "----------------------------------\n";
        for (CheckResult result : restrictionResults) {
            String note = result.getSummary();
            if (!note.isEmpty())
                log += note + "\n";
        }

        log += "\nTA Comments:\n";
        log += "----------------------------------\n";
        log += comments;
        return log;
    }
}
