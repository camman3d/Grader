package grader.project.view;

import grader.project.ClassDescription;

public interface ViewableClassDescription<FilterType> extends /*Viewable<ClassDescription, String>,*/ Filterable<String>, Comparable<ViewableClassDescription> {
	public ClassDescription getClassDescription() ;
	public void setClassDescription(ClassDescription classDescription);
	public boolean filter(String aRegExp);

}
