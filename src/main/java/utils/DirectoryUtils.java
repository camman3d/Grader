package utils;

import scala.Option;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/3/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryUtils {
    public static Option<File> find(File folder, FileFilter filter) {
        File[] files = folder.listFiles(filter);
        if (files.length == 0)
            return Option.empty();
        else
            return Option.apply(files[0]);
    }

    public static Option<File> locateFolder(File currentDir, final String folderName) {
        // Don't accept files (they don't make sense) or Mac meta folders
        if (!currentDir.isDirectory() || currentDir.getName().equals("__MACOSX"))
            return Option.empty();
        else {
            Option<File> folder = find(currentDir, new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().equals(folderName);
                }
            });
            if (folder.isDefined())
                return folder;
            else {
                for (File file : currentDir.listFiles()) {
                    Option<File> possible = locateFolder(file, folderName);
                    if (possible.isDefined())
                        return possible;
                }
                return Option.empty();
            }
        }
    }

    public static Set<File> getFiles(File dir, final FileFilter filter) {
        // Get files in this folder
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.isDirectory() && filter.accept(pathname);
            }
        });
        Set<File> allFiles = new HashSet<File>();
        allFiles.addAll(Arrays.asList(files));

        // Get files in sub directories
        File[] directories = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File directory : directories)
            allFiles.addAll(getFiles(directory, filter));

        return allFiles;
    }
}
