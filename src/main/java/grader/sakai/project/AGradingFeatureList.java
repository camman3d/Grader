package grader.sakai.project;

import grader.assignment.GradingFeature;
import util.models.AListenableVector;

public class AGradingFeatureList extends AListenableVector<GradingFeature> {
	
	@Override
	public void open(GradingFeature element) {
		element.comment();
	}

}
