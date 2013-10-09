package grader.project;

import grader.file.FileProxy;
import grader.project.file.RootCodeFolder;
import util.misc.Common;

import java.util.List;

public class AMainClassFinder implements MainClassFinder {
    public static final String DEFAULT_MAIN_PACKAGE_NAME = "main";

    public Class mainClass(RootCodeFolder aRootCodeFolder, ProxyClassLoader aProxyClassLoader, String expectedName) {
        String binaryFolderName = aRootCodeFolder.getBinaryProjectFolderName();
        String mainFolderName = binaryFolderName + "/" + DEFAULT_MAIN_PACKAGE_NAME;
        List<FileProxy> binaryChildren = aRootCodeFolder.getRootFolder().getChildrenOf(
                mainFolderName);
        if (binaryChildren.size() == 1) {
            String mainClassAbsoluteFileName = binaryChildren.get(0).getMixedCaseAbsoluteName();
            String classFileName = Common.absoluteNameToLocalName(mainClassAbsoluteFileName);
            int dotIndex = classFileName.indexOf('.');
            String className = classFileName.substring(0, dotIndex);

            String mainClassFileName = DEFAULT_MAIN_PACKAGE_NAME + "." + className;
            try {
                return aProxyClassLoader.loadClass(mainClassFileName);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }

        return null;
    }

}
