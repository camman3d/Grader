package grader.project;

import util.misc.Common;

import java.io.File;
import java.util.*;

// this makes the class descriptions providing the text to each class
// It does not seem to use the proxy
public class AClassesManager implements ClassesManager {
    Map<String, ClassDescription> classNameToDescription = new HashMap();
    Map<String, Set<ClassDescription>> tagToDescription = new HashMap();

    List<ClassDescription> classDescriptions = new ArrayList();
    final int ESTIMATED_SOURCES_LENGTH = 20;
    final String SOURCE_FILE_SUFFIX = ".java";

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#getClassNameToDescription()
     */
    @Override
    public Map<String, ClassDescription> getClassNameToDescription() {
        return classNameToDescription;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#setClassNameToDescription(java.util.Map)
     */
    @Override
    public void setClassNameToDescription(
            Map<String, ClassDescription> classNameToDescription) {
        classNameToDescription = classNameToDescription;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#getClassDescriptions()
     */
    @Override
    public List<ClassDescription> getClassDescriptions() {
        return classDescriptions;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#setClassDescriptions(java.util.List)
     */
    @Override
    public void setClassDescriptions(
            List<ClassDescription> classDescriptions) {
        classDescriptions = classDescriptions;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#put(java.lang.String, grader.project.ClassDescription)
     */
    @Override
    public void put(String aClassName, ClassDescription aClass) {
        classNameToDescription.put(aClassName, aClass);
        classDescriptions.add(aClass);
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#put(java.lang.String[], grader.project.ClassDescription)
     */
    @Override
    public void put(String[] aTags, ClassDescription aClass) {
        for (String tag : aTags) {
            putTag(tag, aClass);
        }
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#tagToClassDescriptions(java.lang.String)
     */
    @Override
    public Set<ClassDescription> tagToClassDescriptions(String aTag) {
        return tagToDescription.get(aTag);
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#tagsToClassDescriptions(java.lang.String[])
     */
    @Override
    public Set<ClassDescription> tagsToClassDescriptions(String[] aTagList) {
        Set<ClassDescription> retVal = new HashSet();
        for (String tag : aTagList) {
            retVal.addAll(tagToClassDescriptions(tag));
        }
        return retVal;
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#putTag(java.lang.String, grader.project.ClassDescription)
     */
    @Override
    public void putTag(String aTag, ClassDescription aClass) {
        Set<ClassDescription> classes = tagToDescription.get(aTag);
        if (classes == null) {
            classes = new HashSet();

            // Added by Josh. The classes object is never added to the tagToDescription map
            tagToDescription.put(aTag, classes);
        }
        classes.add(aClass);


    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#classNameToClassDescription(java.lang.String)
     */
    @Override
    public ClassDescription classNameToClassDescription(String aClassName) {
        return classNameToDescription.get(aClassName);
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#getTags(grader.project.ClassDescription)
     */
    @Override
    public String[] getTags(ClassDescription aClassDescription) {
        return aClassDescription.getTags();
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#makeSourceCodeDescriptions(java.lang.String, boolean)
     */
    // this is fle stuff, should not be her
    @Override
    public void makeClassDescriptions(String aProjectDirectory, boolean aSeparateSrcBin) {
        String anActualProjectDirectory = aProjectDirectory;
        if (aSeparateSrcBin)
            anActualProjectDirectory += "/src";
        List<ClassDescription> sources = new ArrayList(ESTIMATED_SOURCES_LENGTH);
        File projectFoider = new File(anActualProjectDirectory);
        makeClassDescriptions(projectFoider, projectFoider);
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#makeClassDescriptions(java.io.File, java.io.File)
     */
    void makeClassDescriptions(File aFolder, File aProjectFolder) {
        String[] fileNames = aFolder.list();
        File[] files = aFolder.listFiles();
        for (File aFile : files) {
            if (aFile.getName().endsWith(SOURCE_FILE_SUFFIX)) {
                String relativeName = Common.toRelativeName(aProjectFolder.getAbsolutePath(), aFile.getAbsolutePath());
                String className = Common.projectRelativeNameToClassName(relativeName);
                StringBuffer text = Common.toText(aFile.getAbsolutePath());
                ClassDescription classDescription = new AClassDescription(className, text, aFile.lastModified(), null, null, null);
                put(className, classDescription);

                // Added by Josh: The tag to class description map is never added to. This is doing just that.
                put(classDescription.getTags(), classDescription);
            }
        }

        for (File aFile : files) {
            if (aFile.isDirectory()) {
                makeClassDescriptions(aFile, aProjectFolder);
            }
        }
    }
}
