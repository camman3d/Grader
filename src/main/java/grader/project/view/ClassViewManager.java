package grader.project.view;

import java.util.List;

public interface ClassViewManager<FilterType> {

	public List<ViewableClassDescription<FilterType>> getView(String aClassName);
	public List<ViewableClassDescription<FilterType>> getViewableClassDescriptions();
	public List<ViewableClassDescription<FilterType>> getViewableClassDescriptions(String aViewName, String aFilter);



}