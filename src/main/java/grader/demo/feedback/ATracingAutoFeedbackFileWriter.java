package grader.demo.feedback;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import grader.assignment.GradingFeature;
import grader.checkers.CheckResult;
import grader.feedback.AnAutoFeedbackManager;
import grader.feedback.AutoFeedback;

public class ATracingAutoFeedbackFileWriter extends AnAutoFeedbackManager implements AutoFeedback{

	@Override
	public void recordAutoGrade(GradingFeature aGradingFature,
			CheckResult result) {
		super.recordAutoGrade(aGradingFature, result);
		System.out.println("recording grade for:" + aGradingFature + " checker" + result);
		
	}
		
	

}
