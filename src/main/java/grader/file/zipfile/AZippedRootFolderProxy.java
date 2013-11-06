package grader.file.zipfile;

import grader.file.AnAbstractRootFolderProxy;
import grader.file.RootFolderProxy;
import util.misc.Common;
import util.trace.Tracer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AZippedRootFolderProxy extends AnAbstractRootFolderProxy implements RootFolderProxy {
    ZipFile zipFile;
    String rootLocalName;
    String absoluteName;
    public static final String MACOS = "_MACOS";

    public AZippedRootFolderProxy(String aZipFileName) {
        super();
        try {
            this.zipFile = new ZipFile(aZipFileName);
        } catch (IOException e) {
            System.out.println(aZipFileName + ":" + e);
            return;
        }
        initRootName();
        initEntries();
    }

    public boolean exists() {
        return zipFile != null && zipFile.size() > 0;
    }

    @Override
    public String getAbsoluteName() {
        return absoluteName;
    }

    @Override
    public String getLocalName() {
        return rootLocalName;
    }

    public static String rootLocalName(String anElementName) {
        int firstSlashIndex = anElementName.indexOf("/");
        if (firstSlashIndex < 0)
            return anElementName;
        else
            return anElementName.substring(0, firstSlashIndex);
    }

    void initRootName() {
        absoluteName = Common.toCanonicalFileName(zipFile.getName());
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {

            ZipEntry nextEntry = enumeration.nextElement();
            String name = nextEntry.getName();
            if (name.indexOf(".") >= 0 && name.indexOf('/') == -1)
                continue; // we got a file at the top level
            rootLocalName = rootLocalName(name);
            Tracer.info(this, "Local name:" + rootLocalName + " of zip file" + zipFile.getName());
            return;
        }

    }

    void initEntries() {
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {
            ZipEntry nextEntry = enumeration.nextElement();
            if (nextEntry.getName().indexOf(MACOS) >= 0)
                continue; // mac added stuff
            add(new AZippedFileProxy(this, nextEntry, zipFile, rootLocalName));
        }
        initChildrenRootData();
    }

    @Override
    public String getMixedCaseAbsoluteName() {
        return rootLocalName;
    }

    @Override
    public String getMixedCaseLocalName() {
        return absoluteName;
    }
}
