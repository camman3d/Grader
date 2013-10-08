package framework.project;

import org.apache.commons.io.FileUtils;
import scala.Option;
import framework.utils.DirectoryUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/3/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectClassesManager implements ClassesManager {

    private ClassLoader classLoader;
    private Set<ClassDescription> classDescriptions;

    public ProjectClassesManager(File buildFolder, File sourceFolder) throws IOException, ClassNotFoundException {

        // Create the Class Loader and load the classes
        classLoader = new URLClassLoader(new URL[]{buildFolder.toURI().toURL()});
        classDescriptions = new HashSet<ClassDescription>();
        loadClasses(sourceFolder);
    }

    /*
     * This loads all the classes based on the source code files.
     */
    private void loadClasses(File sourceFolder) throws ClassNotFoundException, IOException {
        Set<File> javaFiles = DirectoryUtils.getFiles(sourceFolder, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".java");
            }
        });
        for (File file : javaFiles) {
            String className = getClassName(file);
            try {
                Class c = classLoader.loadClass(className);
                classDescriptions.add(new BasicClassDescription(c, file));
            } catch (IncompatibleClassChangeError e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    /*
     * Given a file, this finds the canonical class name.
     */
    private String getClassName(File file) throws IOException {

        // Figure out the package
        List<String> lines = FileUtils.readLines(file, null);
        String packageName = "";
        for (String line : lines) {
            if (line.startsWith("package "))
                packageName = line.replace("package", "").replace(";", "").trim() + ".";
        }

        // Figure out the class name and combine it with the package
        String className = file.getName().replace(".java", "");
        return packageName + className;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public Option<ClassDescription> findByClassName(String className) {
        // First search the simple names
        for (ClassDescription description : classDescriptions) {
            if (description.getJavaClass().getSimpleName().equalsIgnoreCase(className))
                return Option.apply(description);
        }

        // Next search the canonical names
        for (ClassDescription description : classDescriptions) {
            if (description.getJavaClass().getCanonicalName().equalsIgnoreCase(className))
                return Option.apply(description);
        }
        return Option.empty();
    }

    @Override
    public Option<ClassDescription> findByTag(String tag) {
        for (ClassDescription description : classDescriptions) {
            for (String t : description.getTags()) {
                if (t.equalsIgnoreCase(tag))
                    return Option.apply(description);
            }
        }
        return Option.empty();
    }

    @Override
    public Set<ClassDescription> getClassDescriptions() {
        return classDescriptions;
    }
}
