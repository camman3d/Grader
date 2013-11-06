package grader.project.file;

import grader.file.FileProxy;
import grader.file.RootFolderProxy;

import java.util.List;
import java.util.Set;

public interface RootCodeFolder {
    FileProxy sourceFile(String aClassName);

    FileProxy binaryFile(String aClassName);

    String getAbsoluteName();

    String getLocalName();

    List<FileProxy> getFileEntries();

    FileProxy getFileEntry(String name);

    public String getSourceProjectFolderName();

    public String getMixedCaseSourceProjectFolderName();

    public String getBinaryProjectFolderName();

    Set<String> getEntryNames();

    RootFolderProxy getRootFolder();

    String getProjectFolderName();

    public boolean hasSource();

    public boolean hasBinary();

    public boolean hasSeparateSourceBinary();

    public boolean hasValidBinaryFolder();


}
