package grader.project.view;

import grader.project.ClassDescription;

public class AnUnsortingViewable extends AViewableClassDescription<String> {
	public AnUnsortingViewable(ClassDescription aClassDescription) {
		super(aClassDescription);
	}
	@Override
	public int compareTo(ViewableClassDescription other) {
		return -1;
	}
	@Override
	public boolean filter(String aRegExp) {
		return true;
	}
//	@Override
//	public String getViewName() {
//		return ViewNames.CLASS_NAME_VIEW;
//	}	

}
