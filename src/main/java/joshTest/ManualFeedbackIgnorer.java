package joshTest;

import grader.assignment.GradingFeature;
import grader.feedback.ManualFeedback;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/23/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManualFeedbackIgnorer implements ManualFeedback {
    @Override
    public void comment(GradingFeature aGradingFeature) {
        // Do nothing
    }
}
