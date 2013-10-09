package grader.file.filesystem;

import grader.file.AnAbstractFileProxy;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import util.misc.Common;
// file system translated to common interface for zip file and file system
public class AFileSystemFileProxy extends AnAbstractFileProxy implements FileProxy {
	File file;
//	File rootFolder;
	String localName;
	String absoluteName;
	String mixedCaseLocalName;
	String mixedCaseAbsoluteName;
	public AFileSystemFileProxy(RootFolderProxy aRootFolderProxy, File aFile, String aRootFolderName) {
		super (aRootFolderProxy);
		file = aFile;
		mixedCaseAbsoluteName = Common.toCanonicalFileName(file.getAbsolutePath());
		absoluteName =  mixedCaseAbsoluteName.toLowerCase();

		mixedCaseLocalName = Common.toRelativeName(aRootFolderName, getMixedCaseAbsoluteName());
//		rootFolder = aRootFolder;
		localName = mixedCaseLocalName.toLowerCase();
//		init();
	}
	public AFileSystemFileProxy(File aFile) {
		super(null);
		file = aFile;
		absoluteName =  Common.toCanonicalFileName(file.getAbsolutePath()).toLowerCase();
//		rootFolder = aRootFolder;
//		init();
	}
	
	public String toString() {
		return file.toString();
	}
	
	@Override
	public String getAbsoluteName() {
//		return file.getAbsolutePath();
		return absoluteName;
	}
	@Override
	public String getMixedCaseAbsoluteName() {
//		return file.getAbsolutePath();
		return mixedCaseAbsoluteName;
	}
	@Override
	public String getMixedCaseLocalName() {
//		return file.getAbsolutePath();
		return mixedCaseLocalName;
	}
	@Override
	public long getTime() {
		return file.lastModified();
	}
	@Override
	public long getSize() {
		return file.length();
	}
	
	@Override
	public InputStream getInputStream() {
		try {
			return new FileInputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public OutputStream getOutputStream() {
		try {
			return new FileOutputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public String getLocalName() {
		return localName;
	}
	@Override
	public boolean exists() {
		return file.exists();
	}
	
	
	

}
