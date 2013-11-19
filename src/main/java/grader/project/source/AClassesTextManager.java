package grader.project.source;


import grader.project.AClassesManager;
import grader.project.AProject;
import grader.project.ClassesManager;
import grader.project.Project;
import grader.project.view.AClassViewManager;
import grader.project.view.ClassViewManager;
import grader.project.view.ViewableClassDescription;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class AClassesTextManager implements ClassesTextManager {
    public static final String SOURCE_SEPARATOR = "\n";
    Map<String, StringBuffer> views = new HashMap();
    StringBuffer allSourcesText;
    ClassViewManager classesManager;

    public AClassesTextManager(ClassViewManager aClassesManager) {
        classesManager = aClassesManager;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesSourceManager#writeAllSourcesText()
     */
    @Override
    public void writeAllSourcesText(String aFileName) {
        try {
            PrintWriter out = new PrintWriter(aFileName);
            String allText = getAllSourcesText().toString();
            out.print(allText);
            out.close();
        } catch (Exception e) {
//            e.printStackTrace(); // Commented out by Josh
        }
    }

    @Override
    public void writeAllSourcesText() {
        writeAllSourcesText(DEFAULT_SOURCES_FILE_NAME);
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesSourceManager#initializeAllSourcesText()
     */
    @Override
    public void initializeAllSourcesText() {
        Collection<ViewableClassDescription> filteredClasses = classesManager.getViewableClassDescriptions();
        allSourcesText = toStringBuffer(filteredClasses);
    }

    @Override
    public StringBuffer toStringBuffer(Collection<ViewableClassDescription> sourceClasses) {
        int totalTextSize = totalTextSize(sourceClasses) + sourceClasses.size() * SOURCE_SEPARATOR.length();
        StringBuffer retVal = new StringBuffer(totalTextSize);
        for (ViewableClassDescription viewable : sourceClasses) {
            retVal.append(viewable.getClassDescription().getText());
            retVal.append(SOURCE_SEPARATOR);
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesSourceManager#totalTextSize(java.util.Collection)
     */
    public int totalTextSize(Collection<ViewableClassDescription> aSourceClasses) {
        int retVal = 0;
        for (ViewableClassDescription viewable : aSourceClasses) {
            retVal += viewable.getClassDescription().getText().length();
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesSourceManager#getAllSourcesText()
     */
    @Override
    public StringBuffer getAllSourcesText() {
        if (allSourcesText == null)
            initializeAllSourcesText();
        return allSourcesText;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesSourceManager#setAllSourcesText(java.lang.StringBuffer)
     */
    @Override
    public void setAllSourcesText(StringBuffer anAllSourcesText) {
        allSourcesText = allSourcesText;
    }

}
