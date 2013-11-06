package grader.project.view;

import grader.project.ClassDescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.trace.Tracer;
// associates view names with factories
// sorts the views
public class ClassesViewRegistrar {
//	static Map<String, List<ClassDescription>> views = new HashMap();
	static Map<String, ViewableClassDescriptionFactory> factories = new HashMap();
	
	public static void register(String aViewName, ViewableClassDescriptionFactory aFactory) {		
		factories.put(aViewName, aFactory);		
	}
	public static Set<String> getViewNames() {
		return factories.keySet();
	}
	// returns sorted filtered class descriptions
	public static List<ViewableClassDescription<String>> createView(List<ClassDescription> classes, String aViewName) {
		ViewableClassDescriptionFactory factory = factories.get(aViewName);
		if (factory == null) {
			Tracer.error("Factory for view " + aViewName + " not registered.");
			return null;
		}
		List<ViewableClassDescription<String>> retVal = new ArrayList();
		for (ClassDescription aClassDescription:classes) {
			retVal.add(factory.createViewable(aClassDescription));			
		}
		Collections.sort(retVal);
		return retVal;
	}
	
	static {
		ClassesViewRegistrar.register(ViewNames.CLASS_NAME_VIEW, new AClassNameViewableFactory());
		ClassesViewRegistrar.register(ViewNames.UNSORTING_VIEW, new AClassNameViewableFactory());
	}

}
