package grader.project.file.java;

import java.util.List;
import java.util.Set;

import util.misc.Common;
import grader.file.FileProxy;
import grader.file.RootFolderProxy;
import grader.project.file.RootCodeFolder;
//a root folder containing source and binary directories
public class AJavaRootCodeFolder implements RootCodeFolder {
	public static final String SOURCE = "src";
	public static final String BINARY = "bin";
	public static final String BINARY_2 = "out";
	public static final String SOURCE_FILE_SUFFIX = ".java";
	public static final String BINARY_FILE_SUFFIX = ".class";

	
	String sourceFolderName = SOURCE;
	String binaryFolderName = BINARY;
	RootFolderProxy root;
	String projectFolder; 
	FileProxy sourceFolder, binaryFolder;
	
	boolean separateSourceBinary = true;
	boolean hasSource;
	boolean hasBinary;
	boolean hasSourceFile;
	boolean hasBinaryFile;
	

	public AJavaRootCodeFolder(RootFolderProxy aRoot) {
		root = aRoot;
		setSeparateSourcBinary();
		sourceFolderName = getEntryWithSuffix(aRoot, SOURCE);
		binaryFolderName = getEntryWithSuffix(aRoot, BINARY);
		if (binaryFolderName == null)
			binaryFolderName = getEntryWithSuffix(aRoot, BINARY_2);

	
		
		if (sourceFolderName == null || binaryFolderName == null) {
		sourceFolderName = aRoot.getAbsoluteName();
		binaryFolderName = sourceFolderName;
		// this should go
		if (separateSourceBinary) {
			sourceFolderName +=  "/" + SOURCE;
			binaryFolderName += "/" + BINARY;
			
		}
		}
		
		if (separateSourceBinary) {
			projectFolder = Common.getParentFileName(binaryFolderName);
			sourceFolder = root.getFileEntry(sourceFolderName);
			binaryFolder = root.getFileEntry(binaryFolderName);
		} else {
			projectFolder = binaryFolderName;
		}
		
		
		
	}
	public boolean hasValidBinaryFolder() {
		return hasBinaryFile;
	}
	void setSeparateSourcBinary() {
		Set<String> names = root.getEntryNames();
		String srcPattern = SOURCE + "/";
		String binPattern = BINARY + "/";
		for (String name:names ) {
			if (!hasSource && name.indexOf(srcPattern) != -1) {
				hasSource = true;
			}
			if (!hasBinary && name.indexOf(binPattern) != -1) {
				hasBinary = true;
			}
			if (!hasSourceFile && name.indexOf(SOURCE_FILE_SUFFIX) != -1) {
				hasSourceFile = true;
			}
			if (!hasBinaryFile && name.indexOf(BINARY_FILE_SUFFIX) != -1) {
				hasBinaryFile = true;
			}
			if (hasSource && hasBinary && hasSourceFile && hasBinaryFile)
				break;
		}
		separateSourceBinary = hasSource || hasBinary;
		
	}
	public static String getEntryWithSuffix (RootFolderProxy aRoot, String suffix) {
		Set<String> nameSet = aRoot.getEntryNames();
		for (String name:nameSet) {
			int index = name.indexOf(suffix);
			if (index < 0)
				continue;
			return name.substring(0, index + suffix.length());
//			if (name.endsWith(suffix))
//				return name;
		}
		return null;
	}
	public AJavaRootCodeFolder(RootFolderProxy aRoot, String aSourceFolder, String aBinaryFolder) {
		root = aRoot;
		sourceFolderName = aSourceFolder;
		binaryFolderName = aBinaryFolder;
		
	}
	
	@Override
	public FileProxy sourceFile(String aClassName) {
		String sourceFileName = Common.classNameToSourceFileName(aClassName);
		if (separateSourceBinary) {
			sourceFileName = SOURCE + "/" + sourceFileName;
		}
		return root.getFileEntry(sourceFileName);
	}
	@Override
	public RootFolderProxy getRootFolder() {
		return root;
	}
	@Override
	public String getProjectFolderName() {
		return projectFolder;
	}
	@Override
	public FileProxy binaryFile(String aClassName) {
		String binaryFileName = Common.classNameToBinaryFileName(aClassName);
		if (separateSourceBinary) {
			binaryFileName += BINARY + "/" + binaryFileName;
		}
		return root.getFileEntry(binaryFileName);
	}
	@Override
	public String getAbsoluteName() {
		// TODO Auto-generated method stub
		return root.getAbsoluteName();
	}
	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return root.getAbsoluteName();
	}
	@Override
	public List<FileProxy> getFileEntries() {
		// TODO Auto-generated method stub
		return root.getFileEntries();
	}
	@Override
	public FileProxy getFileEntry(String name) {
		// TODO Auto-generated method stub
		return root.getFileEntry(name);
	}
	@Override
	public String getSourceProjectFolderName() {
		return sourceFolderName;
	}
	@Override
	public String getBinaryProjectFolderName() {
		return binaryFolderName;
	}
	@Override
	public Set<String> getEntryNames() {
		return root.getEntryNames();
	}
	public boolean hasSource() {
		return hasSource;
	}
	
	public boolean hasBinary() {
		return hasBinary;
	}
	public boolean hasSeparateSourceBinary() {
		return separateSourceBinary;
	}

}
