package grader.project.view;

import grader.project.ClassesManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
// applies
public class AClassViewManager implements ClassViewManager<String> {
	ClassesManager classesManager;
	Map<String, List<ViewableClassDescription<String>>> views = new HashMap();
	public AClassViewManager (ClassesManager aClassesManager) {
		classesManager = aClassesManager;	
		initViews();
		
	}
	/* (non-Javadoc)
	 * @see grader.project.view.ClassViewManager#getView(java.lang.String)
	 */
	@Override
	public List<ViewableClassDescription<String>> getView(String aViewName) {
		return views.get(aViewName);		
	}
	
	public List<ViewableClassDescription<String>> getViewableClassDescriptions(String aViewName, String aFilter) {
		List<ViewableClassDescription<String>> view = getView(aViewName);
		List<ViewableClassDescription<String>> retVal = new ArrayList();
		for (ViewableClassDescription viewable:view) {
			if (viewable.filter(aFilter))
				retVal.add(viewable);
		}
		return retVal;
	}
	
	public List<ViewableClassDescription<String>> getViewableClassDescriptions() {
		return getViewableClassDescriptions(ViewNames.CLASS_NAME_VIEW, null);
	}
	
	
	void initViews() {
		Set<String> viewNames = ClassesViewRegistrar.getViewNames();
		for (String aViewName: viewNames) {
			List<ViewableClassDescription<String>> view = ClassesViewRegistrar.createView(classesManager.getClassDescriptions(), aViewName);
			views.put(aViewName, view);
		}		
	}

}
