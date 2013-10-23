package joshTest;

import grader.assignment.GradingFeature;
import grader.feedback.ManualFeedback;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class JoshManualFeedback implements ManualFeedback {
    @Override
    public void comment(GradingFeature aGradingFeature) {
        String comments = JOptionPane.showInputDialog(null, "Please enter a comment:", "Comments", JOptionPane.QUESTION_MESSAGE);
        if (comments != null) {
            File feedbackFile = new File(aGradingFeature.getFeedbackFileName());
            try {
                FileUtils.writeStringToFile(feedbackFile, comments);
            } catch (IOException e) {
                System.out.println("Unable to write feedback to file.");
            }
        }
    }
}
