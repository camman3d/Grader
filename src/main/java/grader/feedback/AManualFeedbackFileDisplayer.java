package grader.feedback;

import grader.assignment.GradingFeature;
import grader.documents.DocumentDisplayerRegistry;

public class AManualFeedbackFileDisplayer implements ManualFeedback{

	@Override
	public void comment(GradingFeature aGradingFeature) {
		DocumentDisplayerRegistry.display(aGradingFeature.getFeedbackFileName());

		
	}

}
