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
	String name;
	String absoluteName;
//	RootFolderProxy rootFolderProxy;
	public AZippedFileProxy(RootFolderProxy aRootFolderProxy, ZipEntry aZipEntry, ZipFile aZipFile, String aRootLocalName) {
		super (aRootFolderProxy);
		this.zipEntry = aZipEntry;
		this.zipFile = aZipFile;
		absoluteName = Common.toCanonicalFileName(Common.toCanonicalFileName(zipFile.getName()) + "/" + zipEntry.getName());
		name = Common.toRelativeName(aRootLocalName, zipEntry.getName());
//		init();

	}
	
	public String toString() {
		return zipFile.toString();
	}
	public boolean exists() {
		return zipFile != null && zipFile.size() > 0 && zipEntry != null;
	}
	@Override
	public String getAbsoluteName() {
//		return zipFile.getName() + "/" + zipEntry.getName();
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
		// TODO Auto-generated method stub
		return name;
	}
	
	
	

}
