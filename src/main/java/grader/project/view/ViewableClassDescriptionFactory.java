package grader.project.view;

import grader.project.ClassDescription;

public interface ViewableClassDescriptionFactory<FilterType> {
	 ViewableClassDescription createViewable(ClassDescription aClassDescription);

}
