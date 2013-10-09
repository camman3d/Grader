package grader.file.zipfile;

import grader.file.AnAbstractRootFolderProxy;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import util.misc.Common;
import util.trace.Tracer;

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
//			e.printStackTrace();
		}
//		if (zipFile.getName().indexOf("erichman") != -1) {
//			System.out.println("found student erichman");
//		}
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
		while (enumeration.hasMoreElements()) {
			 
			ZipEntry nextEntry = enumeration.nextElement();
			String name = nextEntry.getName();
			if (name.indexOf(".") >= 0 && name.indexOf('/') == -1) 
				continue; // we got a file at the top level
			
			
//			if (rootLocalName == null) {
				rootLocalName = rootLocalName(name);
				Tracer.info(this, "Local name:" + rootLocalName + " of zip file" + zipFile.getName());
				return;
//			}
		}
		
	}
	
	void initEntries() {
//		if (zipFile.getName().indexOf("erichman") != -1) {
//			System.out.println("found student erichman");
//		}
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry nextEntry = enumeration.nextElement();
			if (nextEntry.getName().indexOf(MACOS) >= 0) 
				continue; // mac added stuff
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

	@Override
	public String getMixedCaseAbsoluteName() {
		// TODO Auto-generated method stub
		return rootLocalName;
	}

	@Override
	public String getMixedCaseLocalName() {
		// TODO Auto-generated method stub
		return absoluteName;
	}


	

}
