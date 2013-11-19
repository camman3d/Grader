
//non Java doc reg exp: (?s)/\*[^*](?:(?!\*/).)*\(non-javadoc\)(?:(?!\*/).)*\*/

package grader.project;

import java.io.IOException;
import java.io.InputStreamReader;

import com.github.antlrjavaparser.JavaParser;
import com.github.antlrjavaparser.api.CompilationUnit;
import grader.file.FileProxy;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.Tags;
import util.javac.SourceClass;
import util.javac.SourceClassManager;
import util.misc.Common;
import util.trace.Tracer;
import bus.uigen.reflect.ClassProxy;
import bus.uigen.reflect.local.AClassProxy;
// gets the source from AClassesManager
// converts class name to class object
public class AClassDescription  implements ClassDescription {
	
	ClassProxy classProxy;
	Class javaClass;
	StringBuffer text;
	long sourceTime;
	String packageName;
	String className;
	JavaClass qdoxClass;
	JavaSource javaSource;
	Project project;
	FileProxy fileProxy;
	SourceClass javacSourceClass;

    private CompilationUnit compilationUnit;
	
	
	public AClassDescription( String aClassName, StringBuffer aText, long aSourceTime, ProxyClassLoader aClassLoader, Project aProject, FileProxy aFileProxy) {
//		text = Common.toText(aClassName);
		fileProxy = aFileProxy;
		project = aProject;
		text = aText;
		sourceTime = aSourceTime;
		if (aClassLoader != null)
		try {
//			javaClass = Class.forName(aClassName);
			javaClass = aClassLoader.loadClass(aClassName);
			if (javaClass == null) {
				Tracer.error("Missing class file for:" + aClassName);

			} else {


//			javaClass = Class.forName(aClassName);
//			javaClass = aClassLoader.findClass(aClassName);
			classProxy = AClassProxy.classProxy(javaClass);
			}
			
		} catch (Exception e) {
			Tracer.error("Missing class file for:" + aClassName);
			e.printStackTrace();
		}	
		className = aClassName;
		packageName = Common.classNameToPackageName(aClassName);
//		qdoxClass = getQdoxClass();
	}
	public String toString() {
		if (classProxy != null)
		return classProxy.toString();
		else return className;
	}
	public String getClassName() {
		return className;
	}
	public ClassProxy getClassProxy() {
		return classProxy;
	}
	public void setClassProxy(ClassProxy classProxy) {
		this.classProxy = classProxy;
	}
	public StringBuffer getText() {
		return text;
	}
	public void setText(StringBuffer text) {
		this.text = text;
	}	
	public long getSourceTime() {
		return sourceTime;
	}
	public void setSourceTime(long newVal) {
		this.sourceTime = newVal;
	}
	public String getPackageName() {
		return packageName;
	}
	
	
	@Override
	public  String[] getTags() {
		if (classProxy == null)
			return new String[]{};
		Tags tags = classProxy.getAnnotation(Tags.class);
		return tags == null?new String[]{}:tags.value();			
	}
	
	@Override
	public  String getStructurePatternName() {
		if (classProxy == null)
			return null;
		StructurePattern structurePattern = classProxy.getAnnotation(StructurePattern.class);
		return  structurePattern == null?null:structurePattern.value();		
	}
	
	@Override
	public  String[] getPropertyNames() {
		if (classProxy == null)
			return new String[]{};
		PropertyNames propertyNames = classProxy.getAnnotation(PropertyNames.class);
		return propertyNames == null?new String[]{}:propertyNames.value();			
	}
	
	@Override
	public  String[] getEditablePropertyNames() {
		if (classProxy == null)
			return new String[]{};
		EditablePropertyNames editablePropertyNames = classProxy.getAnnotation(EditablePropertyNames.class);
		return editablePropertyNames == null?new String[]{}:editablePropertyNames.value();			
	}
	@Override
	public JavaClass getQdoxClass() {
		if (qdoxClass == null) {
			initializeQdoxData();
//			FileProxy fileProxy = project.
		}
		return qdoxClass;
	}
	
	@Override
	public JavaSource getQdoxSource() {
		if (javaSource == null) {
			initializeQdoxData();
//			FileProxy fileProxy = project.
		}
		return javaSource;
	}
	
	public void initializeQdoxData() {
		JavaDocBuilder builder = project.getJavaDocBuilder();
		javaSource = builder.addSource( new InputStreamReader(fileProxy.getInputStream()));
		qdoxClass = builder.getClassByName(className);
	}

	@Override
	public SourceClass getJavacSourceClass() {
		if (javacSourceClass == null) {
			javacSourceClass = SourceClassManager.getInstance().getOrCreateClassInfo(className);

		}
		return javacSourceClass;
	}

    @Override
    public Class<?> getJavaClass() {
        return javaClass;
    }

//	public String getComment() {
//		return getQdoxClass().getComment();
//	}

    @Override
    public CompilationUnit getCompilationUnit() throws IOException {
        if (compilationUnit == null)
            compilationUnit = JavaParser.parse(fileProxy.getInputStream());
        return compilationUnit;
    }
	

}
