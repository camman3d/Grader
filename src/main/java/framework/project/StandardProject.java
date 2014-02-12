package framework.project;

import framework.execution.*;
import org.apache.commons.io.FileUtils;
import scala.Option;
import tools.CantCompileException;
import tools.CodeTools;
import tools.DirectoryUtils;
import util.trace.TraceableLog;
import util.trace.TraceableLogFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * A "standard" project. That is, an IDE-based java project.
 */
public class StandardProject implements Project {

    private File directory;
    private File sourceFolder;
    private Option<ClassesManager> classesManager;
    private TraceableLog traceableLog;

    /**
     * Basic constructor
     *
     * @param directory The location of the project
     * @param name      The name of the project, such as "Assignment1"
     * @throws FileNotFoundException
     */
    public StandardProject(File directory, String name) throws FileNotFoundException {
        // Find the src folder. Do this by searching recursively for a .java file, then look at it's package
        Set<File> sourceFiles = DirectoryUtils.getFiles(directory, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".java");
            }
        });
        if (sourceFiles.isEmpty())
            throw new FileNotFoundException("No src code");
        File file = new ArrayList<File>(sourceFiles).get(0);
        try {

            // Navigate up N times, where N is the number of nested packages
            String canonicalName = CodeTools.generateCanonicalName(file);
            sourceFolder = file;
            int packages = canonicalName.split("\\.").length;
            for (int i = 0; i < packages; i++) {
                File parent = sourceFolder.getParentFile();
                if (sourceFolder.equals(directory)) {

                    // Invalid package. Make a non-compilable project
                    this.directory = directory;
                    classesManager = Option.empty();
                    return;
                }
                sourceFolder = parent;
            }

        } catch (IOException e) {
            throw new FileNotFoundException("Error while attempting to locate package");
        }
        this.directory = sourceFolder.getParentFile();

        // Now let's try to find the build folder
        try {
//            File sourceFolder = new File(this.directory, "src");
            File buildFolder = getBuildFolder("main." + name);
            classesManager = Option.apply((ClassesManager) new ProjectClassesManager(buildFolder, sourceFolder));
        } catch (Exception e) {

            // Try to compile the code and create the project classes manager again.
            try {
                File buildFolder = CodeTools.compile(sourceFolder);
                classesManager = Option.apply((ClassesManager) new ProjectClassesManager(buildFolder, sourceFolder));
            } catch (Exception e2) {

                // Well, we can't compile. So something is really messed up
                classesManager = Option.empty();
            }
        }

        // Create the traceable log
        traceableLog = TraceableLogFactory.getTraceableLog();

    }

    /**
     * This figures out where the build folder is, taking into account variations due to IDE
     *
     * @param preferredClass The name of the class that has the main method, such as "main.Assignment1"
     * @return The build folder
     * @throws FileNotFoundException
     */
    public File getBuildFolder(String preferredClass) throws NotCompiledException, FileNotFoundException {
        Option<File> out = DirectoryUtils.locateFolder(directory, "out");
        Option<File> bin = DirectoryUtils.locateFolder(directory, "bin");

        // If there is no 'out' or 'bin' folder then give up
        if (out.isEmpty() && bin.isEmpty())
            throw new NotCompiledException();
        else {
            // There can be more folders under it, so look around some more
            // But first check the class name to see what we are looking for
            File dir = null;
            if (out.isDefined())
                dir = out.get();
            if (bin.isDefined())
                dir = bin.get();

            if (preferredClass.contains(".")) {
                Option<File> packageDir = DirectoryUtils.locateFolder(dir, preferredClass.split("\\.")[0]);
                if (packageDir.isDefined())
                    return packageDir.get().getParentFile();
                else {

                    // There wasn't a main.{Project Name} class, so look for the main method in the source then pull out
                    // that class name as the preferred class, and re-run this method.
                    File srcFolder = new File(this.directory, "src");
                    String mainClass = CodeTools.findMain(sourceFolder);
                    if (mainClass.equals(preferredClass))
                        // If we get here then it means that the byte code was missing, so blow up
                        throw new FileNotFoundException();
                    return getBuildFolder(mainClass);
                }
            } else
                return dir;
        }
    }

    @Override
    public TraceableLog getTraceableLog() {
        return traceableLog;
    }

    @Override
    public RunningProject start(String input) throws NotRunnableException {
        return new ReflectionRunner(this).run(input);
    }

    @Override
    public RunningProject launch(String input) throws NotRunnableException {
        return new ProcessRunner(this).run(input);
    }

    @Override
    public RunningProject start(String input, int timeout) throws NotRunnableException {
        return new ReflectionRunner(this).run(input, timeout);
    }

    @Override
    public RunningProject launch(String input, int timeout) throws NotRunnableException {
        return new ProcessRunner(this).run(input, timeout);
    }

    @Override
    public RunningProject launchInteractive() throws NotRunnableException {
        return new InteractiveConsoleProcessRunner(this).run("");
    }

    @Override
    public Option<ClassesManager> getClassesManager() {
        return classesManager;
    }

    @Override
    public File getSourceFolder() {
        return sourceFolder;
    }
}
