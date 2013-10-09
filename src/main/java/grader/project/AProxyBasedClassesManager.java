package grader.project;

import grader.file.FileProxy;
import util.misc.Common;

import java.util.List;

// this makes the class descriptions providing the text to each class
// It uses the file proxy
public class AProxyBasedClassesManager extends AClassesManager implements ProxyBasedClassesManager {

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#makeSourceCodeDescriptions(java.lang.String, boolean)
     */
    // ignoring second parameter
    @Override
    public void makeClassDescriptions(String aProjectDirectory, boolean aSeparateSrcBin) {
        Project project = new AProject(aProjectDirectory);
        makeClassDescriptions(project);
    }

    public void makeClassDescriptions(Project aProject) {
        List<FileProxy> entries = aProject.getRootCodeFolder().getFileEntries();
        String projectPath = aProject.getRootCodeFolder().getAbsoluteName();
        ProxyClassLoader classLooder = null;
        if (aProject.canBeRun() && aProject.hasBeenRun()) {
            classLooder = aProject.getClassLoader();
        }
        makeClassDescriptions(aProject.getSourceProjectFolderName(), entries, classLooder, aProject);
    }

    /* (non-Javadoc)
     * @see grader.project.ClassesManager#makeClassDescriptions(java.io.File, java.io.File)
     */
    void makeClassDescriptions(String srcFolderName, List<FileProxy> aFiles, ProxyClassLoader aClassLoder, Project aProject) {
        for (FileProxy aFile : aFiles) {
            String locaName = aFile.getMixedCaseLocalName();

            if (locaName != null && locaName.endsWith(SOURCE_FILE_SUFFIX)) {
                String relativeName = Common.toRelativeName(srcFolderName, aFile.getMixedCaseAbsoluteName());
                String className = Common.projectRelativeNameToClassName(relativeName);
                StringBuffer text = Common.toText(aFile.getInputStream());
                ClassDescription classDescription = new AClassDescription(className, text, aFile.getTime(), aClassLoder, aProject, aFile);
                put(className, classDescription);
            }
        }
    }
}
