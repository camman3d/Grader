package grader.project.file;

import java.util.List;
import java.util.Set;

import grader.file.FileProxy;
import grader.file.RootFolderProxy;

public interface RootCodeFolder {
	FileProxy sourceFile(String aClassName);
	FileProxy binaryFile(String aClassName);
	String getAbsoluteName();
	String getLocalName();
//	FileProxy getSourceFolderProxy();
//	FileProxy getBinaryFolderProxy();
	List<FileProxy> getFileEntries();
	FileProxy getFileEntry(String name);
	public String getSourceProjectFolderName() ;
	public String getBinaryProjectFolderName() ;
	Set<String> getEntryNames();
	RootFolderProxy getRootFolder();
	String getProjectFolderName();
	public boolean hasSource() ;
	public boolean hasBinary() ;
	public boolean hasSeparateSourceBinary() ;
	public boolean hasValidBinaryFolder();



	

}
