package grader;


public class TracingClassLoader extends ClassLoader {
	 public TracingClassLoader(ClassLoader classLoader) {
	        super(classLoader);
	    }
	
//public TracingClassLoader(URL[] urls, ClassLoader parent) {
//		super(urls, parent);
//		// TODO Auto-generated constructor stub
//	}
//
//
//
//	public TracingClassLoader(URL[] urls) {
//		super(urls);
//		// TODO Auto-generated constructor stub
//	}

//	TracingClassLoader(URL[] urls, AccessControlContext acc) {
//		super(urls, acc);
//		// TODO Auto-generated constructor stub
//	}

//	TracingClassLoader(URL[] urls, ClassLoader parent, AccessControlContext acc) {
//		super(urls, parent, acc);
//		// TODO Auto-generated constructor stub
//	}

//	public TracingClassLoader(URL[] urls, ClassLoader parent,
//			URLStreamHandlerFactory factory) {
//		super(urls, parent, factory);
//		// TODO Auto-generated constructor stub
//	}

	public Class loadClass(String aFileName) throws ClassNotFoundException {
		System.out.println("Loading class" + aFileName);
		Class retVal =  super.loadClass(aFileName);
//		if (retVal == String.class)
//		return super.loadClass(aFileName);
		return retVal;
	}

}
