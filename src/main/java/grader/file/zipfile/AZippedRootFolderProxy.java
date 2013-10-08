package grader.file.zipfile;

import grader.file.AnAbstractRootFolderProxy;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import util.misc.Common;
import util.trace.Tracer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AZippedRootFolderProxy extends AnAbstractRootFolderProxy implements RootFolderProxy {
	ZipFile zipFile;
	String rootLocalName;
	String absoluteName;

	public AZippedRootFolderProxy(String aZipFileName) {
		super();
		try {
			this.zipFile = new ZipFile(aZipFileName);
		} catch (IOException e) {
			System.out.println(aZipFileName + ":" + e);
			return;
//			e.printStackTrace();
		}
		initRootName();
		initEntries();
		
	}
	
	public boolean exists() {
		return zipFile != null && zipFile.size() > 0;
	}

	@Override
	public String getAbsoluteName() {
//		return zipFile.getName();
		return absoluteName;

	}

	@Override
	public String getLocalName() {
		return rootLocalName;
	}
	public static String rootLocalName (String anElementName) {
		int firstSlashIndex = anElementName.indexOf("/");
		if (firstSlashIndex < 0)
			return anElementName;
		else
			return anElementName.substring(0, firstSlashIndex);
		
	}
	
	void initRootName() {
		absoluteName = Common.toCanonicalFileName(zipFile.getName());
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
		if (enumeration.hasMoreElements()) {
			ZipEntry nextEntry = enumeration.nextElement();
			if (rootLocalName == null) {
				rootLocalName = rootLocalName(nextEntry.getName());
				Tracer.info(this, "Local name:" + rootLocalName + " of zip file" + zipFile.getName());
			}
		}
		
	}
	
	void initEntries() {
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry nextEntry = enumeration.nextElement();
//			if (rootName == null) {
//				rootName = rootName(nextEntry.getName());
//			}
			add(new AZippedFileProxy(this, nextEntry, zipFile, rootLocalName));
		}
		initChildrenRootData();

	}
	

	public static void main (String[] args) {
	  final String ZIPPED_FOLDER = "D:/dewan_backup/Java/AmandaKaramFinalUpdated.zip";

		RootFolderProxy rootFolder = new AZippedRootFolderProxy(ZIPPED_FOLDER);
		List<FileProxy> elements = rootFolder.getFileEntries();
		for (FileProxy aFileProxy:elements) {
			System.out.println(aFileProxy.getAbsoluteName());
			System.out.println(aFileProxy.getLocalName());

		}		
	}


	

}
