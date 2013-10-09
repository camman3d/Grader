package grader.project;

import java.util.List;

public interface ProxyClassLoader {
	public  Class findClass(String aClassName) throws ClassNotFoundException;
	public Class loadClass(String aClassName) throws ClassNotFoundException;
	public List<Class> getClassesLoaded();



}
