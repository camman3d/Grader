package grader.file.filesystem;

import grader.file.AnAbstractRootFolderProxy;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import util.misc.Common;

import java.io.File;
import java.util.List;

public class AFileSystemRootFolderProxy extends AnAbstractRootFolderProxy
		implements RootFolderProxy {
	protected File rootFolder;
	String rootName;
	String localName;

	public AFileSystemRootFolderProxy(String aRootFolderName) {
		super();
		rootFolder = new File(aRootFolderName);
		if (!rootFolder.exists()) {
			System.out.println("File:" + aRootFolderName + "  does not exist");
			rootFolder = null;
			return;
		}
		
//		rootName = Common.toCanonicalFileName(aRootFolderName);
		rootName = aRootFolderName;

		localName = Common.toCanonicalFileName(rootFolder.getName());
		initEntries(rootFolder);
	}

	void initEntries(File aFolder) {
		File[] files = aFolder.listFiles();
		String rootName =  Common.toCanonicalFileName(rootFolder.getAbsolutePath());
		for (File aFile : files) {
			add(new AFileSystemFileProxy(this, aFile, rootName));
			if (aFile.isDirectory()) {
				initEntries(aFile);
			}
		}
		initChildrenRootData();
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

}
