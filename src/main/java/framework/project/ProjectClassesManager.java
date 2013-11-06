package framework.project;

import org.apache.commons.io.FileUtils;
import scala.Option;
import tools.DirectoryUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @see ClassesManager
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

    /**
     * This loads all the classes based on the source code files.
     * @param sourceFolder The folder containing the source code
     * @throws ClassNotFoundException
     * @throws IOException
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
            } catch (Error e) {
                throw new IOException(e.getMessage());
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    /**
     * Given a file, this finds the canonical class name.
     * @param file The Java file
     * @return The canonical class name.
     * @throws IOException
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

    /**
     * Looks for a class description given a class name (simple or canonical)
     * @param className The name of the class to find
     * @return The class description wrapped in a {@link Option} in case none was found.
     */
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

    /**
     * Looks for all class descriptions with a particular tag
     * @param tag The tag to search for
     * @return The set of matching class descriptions
     */
    @Override
    public Set<ClassDescription> findByTag(String tag) {
        Set<ClassDescription> classes = new HashSet<ClassDescription>();
        for (ClassDescription description : classDescriptions) {
            for (String t : description.getTags()) {
                if (t.equalsIgnoreCase(tag))
                    classes.add(description);
            }
        }
        return classes;
    }

    /**
     * @return All class descriptions
     */
    @Override
    public Set<ClassDescription> getClassDescriptions() {
        return classDescriptions;
    }
}
