package grader.project.view;

import grader.project.ClassDescription;
// an object that applies a fllter to the class name view
public class AClassNameViewable extends AViewableClassDescription<String> {
	public AClassNameViewable(ClassDescription aClassDescription) {
		super(aClassDescription);
	}
	@Override
	public int compareTo(ViewableClassDescription other) {
//		return classDescription.getClassProxy().getName().compareTo(other.getClassDescription().getClassProxy().getName());
		return classDescription.getClassName().compareTo(other.getClassDescription().getClassName());

	}
	@Override
	public boolean filter(String aRegExp) {
		if (aRegExp == null)
			return true;
		return classDescription.getClassProxy().getName().matches(aRegExp);
	}
//	@Override
//	public String getViewName() {
//		return ViewNames.CLASS_NAME_VIEW;
//	}	

}
