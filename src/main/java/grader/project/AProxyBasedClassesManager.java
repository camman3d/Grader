package grader.project;

import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import grader.file.filesystem.AFileSystemRootFolderProxy;
import grader.project.file.java.AJavaRootCodeFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.annotations.Tags;
import util.misc.Common;
// this makes the class descriptions providing the text to each class
// It uses the file proxy
public class AProxyBasedClassesManager extends AClassesManager implements ProxyBasedClassesManager {
//	 Map<String, ClassDescription> classNameToDescription = new HashMap();
//	 Map<String, Set<ClassDescription>> tagToDescription = new HashMap();
//
//	 List<ClassDescription> classDescriptions = new ArrayList();
//	 final int ESTIMATED_SOURCES_LENGTH = 20;
//	 final String SOURCE_FILE_SUFFIX = ".java";
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#getClassNameToDescription()
//	 */
//	@Override
//	public  Map<String, ClassDescription> getClassNameToDescription() {
//		return classNameToDescription;
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#setClassNameToDescription(java.util.Map)
//	 */
//	@Override
//	public  void setClassNameToDescription(
//			Map<String, ClassDescription> classNameToDescription) {
//		classNameToDescription = classNameToDescription;
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#getClassDescriptions()
//	 */
//	@Override
//	public  List<ClassDescription> getClassDescriptions() {
//		return classDescriptions;
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#setClassDescriptions(java.util.List)
//	 */
//	@Override
//	public  void setClassDescriptions(
//			List<ClassDescription> classDescriptions) {
//		classDescriptions = classDescriptions;
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#put(java.lang.String, grader.project.ClassDescription)
//	 */
//	@Override
//	public  void put (String aClassName, ClassDescription aClass) {
//		classNameToDescription.put(aClassName, aClass);
//		classDescriptions.add(aClass);
//	}
	/* (non-Javadoc)
	 * @see grader.project.ClassesManager#put(java.lang.String[], grader.project.ClassDescription)
	 */
//	@Override
//	public  void put (String[] aTags, ClassDescription aClass) {
//		for (String tag:aTags) {
//			putTag(tag, aClass);
//		}		
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#tagToClassDescriptions(java.lang.String)
//	 */
//	@Override
//	public   Set<ClassDescription> tagToClassDescriptions(String aTag) {
//		return tagToDescription.get(aTag);
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#tagsToClassDescriptions(java.lang.String[])
//	 */
//	@Override
//	public   Set<ClassDescription> tagsToClassDescriptions(String[] aTagList) {
//		Set<ClassDescription> retVal = new HashSet();
//		for (String tag:aTagList) {
//			retVal.addAll(tagToClassDescriptions(tag));
//		}
//		return retVal;		
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#putTag(java.lang.String, grader.project.ClassDescription)
//	 */
//	@Override
//	public  void putTag (String aTag, ClassDescription aClass) {
//		Set<ClassDescription> classes = tagToDescription.get(aTag);
//		if (classes == null)
//			classes = new HashSet();
//		classes.add(aClass);	
//		
//	}
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#classNameToClassDescription(java.lang.String)
//	 */
//	@Override
//	public  ClassDescription classNameToClassDescription (String aClassName) {
//		return classNameToDescription.get(aClassName);
//	}
//	
//	/* (non-Javadoc)
//	 * @see grader.project.ClassesManager#getTags(grader.project.ClassDescription)
//	 */
//	@Override
//	public  String[] getTags(ClassDescription aClassDescription) {
//		Tags tags = aClassDescription.getClassProxy().getAnnotation(Tags.class);
//		return tags == null?new String[]{}:tags.value();			
//	}

	/* (non-Javadoc)
	 * @see grader.project.ClassesManager#makeSourceCodeDescriptions(java.lang.String, boolean)
	 */
	
	// ignoring second parameter
	@Override
	public  void makeClassDescriptions(String aProjectDirectory, boolean aSeparateSrcBin) {
		
		Project project = new AProject(aProjectDirectory);
//		String anActualProjectDirectory = aProjectDirectory;
//
//		RootFolderProxy rootProxy = new AFileSystemRootFolderProxy(aProjectDirectory);
//		RootCodeFolder rootCodeFolder = new AJavaRootCodeFolder(rootProxy, aSeparateSrcBin);
//		List<FileProxy> entries = rootCodeFolder.getEntries();
//		String projectPath = rootCodeFolder.getAbsoluteName();
//		if (aSeparateSrcBin) {
//			projectPath += "\\src";
//		}
		makeClassDescriptions(project);

	}
	
	public  void makeClassDescriptions(Project aProject) {
		
		List<FileProxy> entries = aProject.getRootCodeFolder().getFileEntries();
		String projectPath = aProject.getRootCodeFolder().getAbsoluteName();
//		if (aSeparateSrcBin) {
//			projectPath += "\\src";
//		}
		ProxyClassLoader classLooder = null;
		if (aProject.canBeRun() && aProject.hasBeenRun()) {
			classLooder = aProject.getClassLoader();
		}
		
		makeClassDescriptions(aProject.getSourceProjectFolderName(), entries, classLooder, aProject);
//		}

	}
	
	/* (non-Javadoc)
	 * @see grader.project.ClassesManager#makeClassDescriptions(java.io.File, java.io.File)
	 */
	
	  void makeClassDescriptions(String srcFolderName, List<FileProxy> aFiles, ProxyClassLoader aClassLoder, Project aProject) {
//		String[] fileNames = aFolder.list();
//		File[] files = aFolder.listFiles();
		for (FileProxy aFile:aFiles) {
			String locaName = aFile.getMixedCaseLocalName();
			
			if (locaName != null && locaName.endsWith(SOURCE_FILE_SUFFIX)) {
				String relativeName = Common.toRelativeName(srcFolderName, aFile.getMixedCaseAbsoluteName());
				String className = Common.projectRelativeNameToClassName(relativeName);
				StringBuffer text = Common.toText(aFile.getInputStream());
//				StringBuffer text = Common.toText(aFile.getAbsoluteName());

				ClassDescription classDescription = new AClassDescription(className, text, aFile.getTime(), aClassLoder, aProject, aFile);
				
				put(className, classDescription);		
			}
	    }

//		
//		for (File aFile:files) {
//				if (aFile.isDirectory()) {
//					makeClassDescriptions(aFile, aProjectFolder);
//			    }
//		}

	}
//	public  void makeClassDescriptions(RootCodeFolder aFolder, File aProjectFolder) {
//		String[] fileNames = aFolder.list();
//		File[] files = aFolder.listFiles();
//		for (File aFile:files) {
//			if (aFile.getName().endsWith(SOURCE_FILE_SUFFIX)) {
//				String relativeName = Common.toRelativeName(aProjectFolder.getAbsolutePath(), aFile.getAbsolutePath());
//				String className = Common.projectRelativeNameToClassName(relativeName);
//				StringBuffer text = Common.toText(aFile.getAbsolutePath());
//				ClassDescription classDescription = new AClassDescription(className, text, aFile.lastModified());
//				put(className, classDescription);		
//			}
//	    }
//
//		
//		for (File aFile:files) {
//				if (aFile.isDirectory()) {
//					makeClassDescriptions(aFile, aProjectFolder);
//			    }
//		}
//
//	}

}
