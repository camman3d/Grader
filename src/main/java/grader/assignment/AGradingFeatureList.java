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
		for (GradingFeature gradingFeature:this) {
			if (! (gradingFeature.isGraded()))
					return false;
		}
		return true;
	}

}
