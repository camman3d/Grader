package grader.demo.feedback;

import grader.assignment.GradingFeature;
import grader.documents.DocumentDisplayerRegistry;
import grader.feedback.AManualFeedbackManager;
import grader.feedback.ManualFeedback;

public class ATracingManualFeedbackFileDisplayer extends AManualFeedbackManager implements ManualFeedback{

	@Override
	public void comment(GradingFeature aGradingFeature) {
		super.comment(aGradingFeature);
		System.out.println("manual comment:" + aGradingFeature);
		
	}

}
