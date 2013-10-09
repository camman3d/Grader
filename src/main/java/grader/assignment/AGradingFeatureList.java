package grader.assignment;

import util.models.AListenableVector;
import util.annotations.Visible;

public class AGradingFeatureList extends AListenableVector<GradingFeature> implements GradingFeatureList{
	
	@Override
	public void open(GradingFeature element) {
		element.comment();
	}
	@Visible(false)
	public boolean isAllGraded() {
//		boolean retVal = true;
		for (GradingFeature gradingFeature:this) {
			// check if the assignment is graded or its companion manual feature is, if not return false
			if (! (gradingFeature.isGraded()))
					return false;
//			if (! (gradingFeature.isGraded() ||  
//					(gradingFeature.isAutoGradable() && gradingFeature.getLinkedFeature().isGraded())))
//				return false;
			
		}
		return true;
	}

}
