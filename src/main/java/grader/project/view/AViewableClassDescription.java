package grader.project.view;

import grader.project.ClassDescription;
/*
 * The toString() method, implemented by its classes will be used to sort the class source
 * using the Collections sort method
 */
public abstract class AViewableClassDescription<FilterType> implements ViewableClassDescription<FilterType> {
	ClassDescription classDescription;

	public AViewableClassDescription(ClassDescription aClassDescription) {
		this.classDescription = aClassDescription;
	}

	public ClassDescription getClassDescription() {
		return classDescription;
	}

	public void setClassDescription(ClassDescription classDescription) {
		this.classDescription = classDescription;
	}	

}
