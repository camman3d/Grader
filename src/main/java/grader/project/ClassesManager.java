package grader.project;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ClassesManager {

	public Map<String, ClassDescription> getClassNameToDescription();

	public void setClassNameToDescription(
            Map<String, ClassDescription> classNameToDescription);

	public List<ClassDescription> getClassDescriptions();

	public void setClassDescriptions(List<ClassDescription> classDescriptions);

	public void put(String aClassName, ClassDescription aClass);

	public void put(String[] aTags, ClassDescription aClass);

	public Set<ClassDescription> tagToClassDescriptions(String aTag);

	public Set<ClassDescription> tagsToClassDescriptions(String[] aTagList);

	public void putTag(String aTag, ClassDescription aClass);

	public ClassDescription classNameToClassDescription(String aClassName);

	public String[] getTags(ClassDescription aClassDescription);

	public void makeClassDescriptions(String aProjectDirectory,
                                      boolean aSeparateSrcBin);

//	public void makeClassDescriptions(File aFolder, File aProjectFolder);

}