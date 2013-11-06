package grader.project.view;

import grader.project.ClassDescription;

public class AClassNameViewableFactory implements ViewableClassDescriptionFactory<String>{
	// chooses a class viewer
	@Override
	public ViewableClassDescription createViewable(ClassDescription aClassDescription) {
		return new AClassNameViewable(aClassDescription);
	}

}
