package grader.project.source;


import grader.project.AClassesManager;
import grader.project.AProject;
import grader.project.ClassDescription;
import grader.project.ClassesManager;
import grader.project.Project;
import grader.project.view.AClassViewManager;
import grader.project.view.ClassViewManager;
import grader.project.view.ViewableClassDescription;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AClassesTextManager implements ClassesTextManager  {
//	public static  String SOURCE_FILE_NAME = "sources.docx";	
	public static  final String SOURCE_SEPARATOR = "\n";
	Map<String, StringBuffer> views = new HashMap();

	StringBuffer allSourcesText;
//	ClassesManager classesManager;
	ClassViewManager classesManager;

	 
//	 public AClassesTextManager(ClassesManager aClassesManager) {
//		 classesManager = aClassesManager;
//	 }
	 public AClassesTextManager(ClassViewManager aClassesManager) {
		 classesManager = aClassesManager;
	 }

	/* (non-Javadoc)
	 * @see grader.project.ClassesSourceManager#writeAllSourcesText()
	 */
//	 @Override
//	public  void writeAllSourcesTextByTime() {
//		 
//	 }
	@Override
	public  void writeAllSourcesText(String aFileName) {
		try {
			PrintWriter out = new PrintWriter(aFileName);
			String allText = getAllSourcesText().toString();
			out.print(allText);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public  void writeAllSourcesText(){
		writeAllSourcesText(DEFAULT_SOURCES_FILE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see grader.project.ClassesSourceManager#initializeAllSourcesText()
	 */
	@Override
	public  void initializeAllSourcesText() {
		// why is this assignment allowed? because it is first assignment
//		Collection<ClassDescription> sourceClasses = classesManager.getClassDescriptions();
		Collection<ViewableClassDescription> filteredClasses = classesManager.getViewableClassDescriptions();
//		allSourcesText = toStringBuffer(sourceClasses);
		allSourcesText = toStringBuffer(filteredClasses);

		
		
		
///	int totalTextSize = ClassSourceManager.totalTextSize(sourceClasses) + sourceClasses.size()*SOURCE_SEPARATOR.length();
//		ClassSourceManager.allSourcesText = new StringBuffer(totalTextSize);
//		for (SourceAndObjectClassDescription classDescription:sourceClasses) {
//			ClassSourceManager.allSourcesText.append(classDescription.getText());
//			ClassSourceManager.allSourcesText.append(SOURCE_SEPARATOR);
//		}		
	}
	/* (non-Javadoc)
	 * @see grader.project.ClassesSourceManager#toStringBuffer(java.util.Collection)
	 */
//	@Override
//	public  StringBuffer toStringBuffer(Collection<ClassDescription> sourceClasses) {
//		int totalTextSize = totalTextSize(sourceClasses) + sourceClasses.size()*SOURCE_SEPARATOR.length();
//		StringBuffer retVal  = new StringBuffer(totalTextSize);
//		for (ClassDescription classDescription:sourceClasses) {
//			retVal.append(classDescription.getText());
//			retVal.append(SOURCE_SEPARATOR);
//		}	
//		return retVal;
//	}
	 
		@Override
		public  StringBuffer toStringBuffer(Collection<ViewableClassDescription> sourceClasses) {
			int totalTextSize = totalTextSize(sourceClasses) + sourceClasses.size()*SOURCE_SEPARATOR.length();
			StringBuffer retVal  = new StringBuffer(totalTextSize);
			for (ViewableClassDescription viewable:sourceClasses) {
				retVal.append(viewable.getClassDescription().getText());
				retVal.append(SOURCE_SEPARATOR);
			}	
			return retVal;
		}
	/* (non-Javadoc)
	 * @see grader.project.ClassesSourceManager#totalTextSize(java.util.Collection)
	 */
	
	public  int totalTextSize(Collection<ViewableClassDescription> aSourceClasses ) {
		int retVal = 0;
		for (ViewableClassDescription viewable:aSourceClasses) {
			retVal += viewable.getClassDescription().getText().length();
		}
		return retVal;
	}
	/* (non-Javadoc)
	 * @see grader.project.ClassesSourceManager#getAllSourcesText()
	 */
	@Override
	public  StringBuffer getAllSourcesText() {
		if (allSourcesText == null)
			initializeAllSourcesText();
		return allSourcesText;
	}
	/* (non-Javadoc)
	 * @see grader.project.ClassesSourceManager#setAllSourcesText(java.lang.StringBuffer)
	 */
	@Override
	public  void setAllSourcesText(StringBuffer anAllSourcesText) {
		allSourcesText = allSourcesText;
	}
	
	public static  void main (String[] args) {
		Project project = new AProject(".", ".",  false);
		ClassesManager classesManager = new AClassesManager();
		ClassViewManager classesViewManager = new AClassViewManager(classesManager);
		classesManager.makeClassDescriptions(project.getProjectFolderName(), true);
//		ClassesTextManager classesSourceManager = new AClassesTextManager(classesManager);
		ClassesTextManager classesSourceManager = new AClassesTextManager(classesViewManager);

		classesSourceManager.initializeAllSourcesText();
		classesSourceManager.writeAllSourcesText();		
	}
	

}
