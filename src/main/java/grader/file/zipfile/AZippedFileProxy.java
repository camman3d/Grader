package grader.file.zipfile;

import grader.file.AnAbstractFileProxy;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import util.misc.Common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

// zip system interface to common interface
public class AZippedFileProxy extends AnAbstractFileProxy implements FileProxy {
    ZipEntry zipEntry;
    ZipFile zipFile;
    String name, mixedCaseName;
    String absoluteName, mixedCaseAbsoluteName;

    //	RootFolderProxy rootFolderProxy;
    public AZippedFileProxy(RootFolderProxy aRootFolderProxy, ZipEntry aZipEntry, ZipFile aZipFile, String aRootLocalName) {
        super(aRootFolderProxy);
        this.zipEntry = aZipEntry;
        this.zipFile = aZipFile;
        mixedCaseAbsoluteName = Common.toCanonicalFileName(Common.toCanonicalFileName(zipFile.getName()) + "/" + zipEntry.getName());
        absoluteName = mixedCaseAbsoluteName.toLowerCase();
        if (aZipEntry == null) {
            System.out.println("null zip entry");
        }
        mixedCaseName = Common.toRelativeName(aRootLocalName, zipEntry.getName());
        if (mixedCaseName != null) // for MACOS, a null value is returned
            name = mixedCaseName.toLowerCase();
    }

    public String toString() {
        return zipFile.toString();
    }

    public boolean exists() {
        return zipFile != null && zipFile.size() > 0 && zipEntry != null;
    }

    @Override
    public String getAbsoluteName() {
        return absoluteName;
    }

    @Override
    public long getTime() {
        return zipEntry.getTime();
    }

    @Override
    public long getSize() {
        return zipEntry.getSize();
    }

    @Override
    public InputStream getInputStream() {
        try {
            return zipFile.getInputStream(zipEntry);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public String getLocalName() {
        return name;
    }

    @Override
    public String getMixedCaseAbsoluteName() {
        return mixedCaseAbsoluteName;
    }

    @Override
    public String getMixedCaseLocalName() {
        return mixedCaseName;
    }
}
