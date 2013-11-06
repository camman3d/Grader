package grader.project;

import grader.file.FileProxy;
import grader.project.file.RootCodeFolder;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AProxyProjectClassLoader extends ClassLoader implements ProxyClassLoader {
    public static final String CLASS_MAIN = "main.finalAssignment";

    RootCodeFolder rootCodeFolder;
    String projectFolderName, binaryFolderName;
    List<Class> classesLoaded = new ArrayList();

    public AProxyProjectClassLoader(RootCodeFolder aRootCodeFolder) {
        init(aRootCodeFolder);
    }

    void init(RootCodeFolder aRootCodeFolder) {
        rootCodeFolder = aRootCodeFolder;
        binaryFolderName = rootCodeFolder.getBinaryProjectFolderName();
        projectFolderName = rootCodeFolder.getProjectFolderName();
    }

    public InputStream getResourceAsStream(String name) {
        InputStream retVal = super.getResourceAsStream(name);
        if (retVal != null)
            return retVal;
        String aFullFileName = projectFolderName + "/" + name;
        FileProxy fileProxy = rootCodeFolder.getRootFolder().getFileEntry(aFullFileName);
        InputStream inputStream = fileProxy.getInputStream();
        return inputStream;
    }

    public Class findClass(String aClassName) throws ClassNotFoundException {
        try {
            byte classBytes[];
            String aFileName = aClassName.replaceAll("\\.", "/") + ".class";
            String aFullFileName = binaryFolderName + "/" + aFileName;
            FileProxy ClassFile = rootCodeFolder.getFileEntry(aFullFileName);
            InputStream inputStream = ClassFile.getInputStream();

            classBytes = new byte[inputStream.available()];
            inputStream.read(classBytes);
            Class classObject = defineClass(aClassName, classBytes, 0, classBytes.length);
            classesLoaded.add(classObject);
            return classObject;
        } catch (Exception e) {
            return null;
        }
    }

    public Class loadClass(String aClassName) throws ClassNotFoundException {
        return super.loadClass(aClassName);
    }

    public static void run(Project aProject, String mainClassName) {
        try {
            ClassLoader classLoader = new AProxyProjectClassLoader(aProject.getRootCodeFolder());
            Class mainClass = classLoader.loadClass(mainClassName);
            Method mainMethod = mainClass.getMethod("main", String[].class);
            String[] strings = {"move 10 100"};
            Object[] myArgs = {strings};
            mainMethod.invoke(mainClass, myArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Class> getClassesLoaded() {
        return classesLoaded;
    }
}
