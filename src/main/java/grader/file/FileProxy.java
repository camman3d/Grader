package grader.file;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

// unite file system and zip file interface
public interface FileProxy extends RootFolderProxy {
    String getAbsoluteName();

    long getTime();

    long getSize();

    InputStream getInputStream();

    OutputStream getOutputStream();

    Set<String> getEntryNames();

    void initRootData();

    Set<String> getChildrenNames();

    public FileProxy getParentFolder();

}
