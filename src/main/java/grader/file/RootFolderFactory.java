package grader.file;

import grader.file.filesystem.AFileSystemRootFolderProxy;
import grader.file.zipfile.AZippedRootFolderProxy;
import grader.project.AProject;

public class RootFolderFactory {
    public static RootFolderProxy createRootFolder(String aFolder) {
        boolean isZipperFolder = aFolder.endsWith(AProject.ZIP_SUFFIX);
        if (isZipperFolder) {
            return new AZippedRootFolderProxy(aFolder);
        } else {
            return new AFileSystemRootFolderProxy(aFolder);
        }
    }

}
