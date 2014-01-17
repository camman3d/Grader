package tools;

import org.apache.commons.io.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * Tools when working with code.
 */
public class CodeTools {

    /**
     * Given code in a string, this removes all comments.
     */
    public static String removeComments(String code) {
        return code.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");
    }

    /**
     * This generates the canonical name of the java class given a source file.
     *
     * @param source The java source
     * @return The canonical name
     */
    public static String generateCanonicalName(File source) throws IOException {
        String contents = removeComments(FileUtils.readFileToString(source));
        return generateCanonicalName(source, contents);
    }

    /**
     * This generates the canonical name of the java class given a source file and the contents.
     *
     * @param source The java source
     * @return The canonical name
     */
    public static String generateCanonicalName(File source, String code) {
        String className = source.getName().replace(".java", "");
        String[] lines = code.split("\n");
        String packageName = "";
        for (String line : lines)
            if (line.startsWith("package ")) {
                packageName = line.replace("package", "").replace(";", "").trim();
                break;
            }
        if (packageName.isEmpty())
            return className;
        return packageName + "." + className;
    }

    /**
     * This attempts to locate the class with the main method.
     *
     * @param sourceFolder The folder containing the source code
     * @return The canonical name of the class containing the main method
     * @throws FileNotFoundException If no class has a main method
     */
    public static String findMain(File sourceFolder) throws FileNotFoundException {
        Set<File> sources = DirectoryUtils.getFiles(sourceFolder, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".java");
            }
        });
        try {
            for (File source : sources) {
                String contents = removeComments(FileUtils.readFileToString(source));
                if (contents.contains("public static void main")) {
                    return generateCanonicalName(source, contents);
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        throw new FileNotFoundException();
    }

    /**
     * This compiles all the java sources in a folder. If the sources are located at X/src, then the .class files will
     * be generated at X/out
     * @param sourceFolder The source folder
     * @return The out folder containing the class files
     * @throws IOException
     * @throws CantCompileException
     */
    public static File compile(File sourceFolder) throws IOException, CantCompileException {

        System.out.println("Compiling code in folder: " + sourceFolder.getPath());

        // Prepare the compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        // Prepare the sources
        Set<File> sources = DirectoryUtils.getFiles(sourceFolder, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".java");
            }
        });
        Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjectsFromFiles(sources);

        // Now compile
        boolean successful = compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
        fileManager.close();
        if (!successful)
            throw new CantCompileException();

        // The .class files were placed in the source folder. Let's clean it up
        File parent = sourceFolder.getParentFile();
        File outFolder = new File(parent, "out");

        // First, copy the source folder to the out folder
        outFolder.delete();
        FileUtils.copyDirectory(sourceFolder, outFolder);

        // Delete all .class files from the source folder
        Set<File> classFiles = DirectoryUtils.getFiles(sourceFolder, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class");
            }
        });
        for (File classFile : classFiles)
            classFile.delete();

        // Delete all .java files from the out folder
        Set<File> javaFiles = DirectoryUtils.getFiles(outFolder, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".java");
            }
        });
        for (File javaFile : javaFiles)
            javaFile.delete();

        // Return the out folder
        return outFolder;
    }
}
