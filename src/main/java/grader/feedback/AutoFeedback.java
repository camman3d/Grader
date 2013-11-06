package grader.feedback;

import grader.assignment.GradingFeature;
import grader.checkers.CheckResult;

public interface AutoFeedback {
    void recordAutoGrade(GradingFeature aGradingFature, CheckResult result);

}
