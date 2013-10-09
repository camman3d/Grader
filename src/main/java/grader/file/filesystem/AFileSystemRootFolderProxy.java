package grader.file.filesystem;

import grader.file.AnAbstractRootFolderProxy;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import grader.file.zipfile.AZippedRootFolderProxy;
import grader.project.AClassDescription;
import grader.project.ClassDescription;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;

import util.misc.Common;

public class AFileSystemRootFolderProxy extends AnAbstractRootFolderProxy
		implements RootFolderProxy {
	protected File rootFolder;
	String rootName;
	String localName;
//	String mixedCaseLocalName;
//	String mixedCaseRootName;
	

	public AFileSystemRootFolderProxy(String aRootFolderName) {
		super();
		rootFolder = new File(aRootFolderName);
		if (!rootFolder.exists()) {
			System.out.println("File:" + aRootFolderName + "  does not exist");
			rootFolder = null;
			System.exit(-1);
			return;
		}
		
//		rootName = Common.toCanonicalFileName(aRootFolderName);
		rootName = aRootFolderName;

		localName = Common.toCanonicalFileName(rootFolder.getName());
//		mixedCaseRootName = localName;
		initEntries(rootFolder);
	}

	void initEntries(File aFolder) {
		File[] files = aFolder.listFiles();
		String rootName =  Common.toCanonicalFileName(rootFolder.getAbsolutePath());
//		mixedCaseRootName = rootName;
		for (File aFile : files) {
			add(new AFileSystemFileProxy(this, aFile, rootName));
			if (aFile.isDirectory()) {
				initEntries(aFile);
			}
		}
		initChildrenRootData();
	}
	
	public static void main (String[] args) {
		  final String FOLDER = "D:/dewan_backup/Java/AmandaKaramFinalUpdated/Final";

			RootFolderProxy rootFolder = new AFileSystemRootFolderProxy(FOLDER);
			List<FileProxy> elements = rootFolder.getFileEntries();
			for (FileProxy aFileProxy:elements) {
				System.out.println(aFileProxy.getAbsoluteName());
				System.out.println(aFileProxy.getLocalName());

			}		
		}

	@Override
	public boolean exists() {
		return rootFolder != null && rootFolder.exists();
	}

	@Override
	public String getMixedCaseAbsoluteName() {
		// TODO Auto-generated method stub
		return rootName;
	}

	@Override
	public String getMixedCaseLocalName() {
		// TODO Auto-generated method stub
		return localName;
	}
	@Override
	public String getAbsoluteName() {
//		return rootFolder.getAbsolutePath();
		return rootName;

	}
	@Override
	public String getLocalName() {
		
//		return rootFolder.getName();
		return localName;
	}

}
