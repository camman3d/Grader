package grader.feedback;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import grader.assignment.GradingFeature;
import grader.checkers.CheckResult;

public class AnAutoFeedbackManager extends AManualFeedbackManager implements AutoFeedback{

	@Override
	public void recordAutoGrade(GradingFeature aGradingFeature,
			CheckResult result) {
		
			try {
//				String feedbackFileName = feedbackFileName = feedbackFolder.getAbsoluteName() + "/" + FEEDBACK_FILE_PREFIX + featureName + FEEDBACK_FILE_SUFFIX ;

				
//				CheckResult result = featureChecker.check();
				FileOutputStream fileOutputStream = new FileOutputStream(aGradingFeature.getFeedbackFileName());
//				TeePrintStream outStream = new TeePrintStream(fileOutputStream);
				PrintStream outStream = new PrintStream(fileOutputStream);

				outStream.println("Score:" + result.getScore());
//				System.out.println("Score:" + result.getScore());

//				purseSetScore(result.getScore());
				for (String string:result.getLog()) {
					outStream.println(string);
					System.out.println(string);
				}
				outStream.close();
//				System.out.println("closing output");
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (aGradingFeature.getScore() != aGradingFeature.getMax())
			super.comment(aGradingFeature);
			
		}
		
	

}
