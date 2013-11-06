package grader.project.view;

import grader.project.ClassDescription;

public class AnUnsortingViewableFactory implements ViewableClassDescriptionFactory<String>{

	@Override
	public ViewableClassDescription createViewable(ClassDescription aClassDescription) {
		return new AnUnsortingViewable(aClassDescription);
	}

}
