package grader.file.filesystem;

import grader.file.AnAbstractRootFolderProxy;
import grader.file.RootFolderProxy;
import util.misc.Common;

import java.io.File;

public class AFileSystemRootFolderProxy extends AnAbstractRootFolderProxy
        implements RootFolderProxy {
    protected File rootFolder;
    String rootName;
    String localName;

    public AFileSystemRootFolderProxy(String aRootFolderName) {
        super();
        rootFolder = new File(aRootFolderName);
        if (!rootFolder.exists()) {
            System.out.println("File:" + aRootFolderName + "  does not exist");
            rootFolder = null;
            System.exit(-1);
            return;
        }

        rootName = aRootFolderName;
        localName = Common.toCanonicalFileName(rootFolder.getName());
        initEntries(rootFolder);
    }

    void initEntries(File aFolder) {
        File[] files = aFolder.listFiles();
        String rootName = Common.toCanonicalFileName(rootFolder.getAbsolutePath());
        for (File aFile : files) {
            add(new AFileSystemFileProxy(this, aFile, rootName));
            if (aFile.isDirectory()) {
                initEntries(aFile);
            }
        }
        initChildrenRootData();
    }

    @Override
    public boolean exists() {
        return rootFolder != null && rootFolder.exists();
    }

    @Override
    public String getMixedCaseAbsoluteName() {
        return rootName;
    }

    @Override
    public String getMixedCaseLocalName() {
        return localName;
    }

    @Override
    public String getAbsoluteName() {
        return rootName;
    }

    @Override
    public String getLocalName() {
        return localName;
    }

}
