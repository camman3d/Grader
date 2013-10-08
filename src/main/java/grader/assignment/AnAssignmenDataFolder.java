package grader.assignment;

import grader.file.FileProxy;
import grader.file.FileProxyUtils;
import grader.file.filesystem.AFileSystemFileProxy;
import grader.file.filesystem.AFileSystemRootFolderProxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class AnAssignmenDataFolder extends AFileSystemRootFolderProxy implements AssignmentDataFolder{
	
	public static final String DEFAULT_ID_FILE_NAME = "onyens.txt";
	public static final String DEFAULT_INPUT_FOLDER_NAME = "input";
	public static final String DEFAULT_FEATURE_GRADE_FILE_NAME = "FeatureGrades.csv";
	String idFileName = DEFAULT_ID_FILE_NAME;
	String inputFolderName = DEFAULT_INPUT_FOLDER_NAME;
	String featureGradeFileName = DEFAULT_FEATURE_GRADE_FILE_NAME;
	String idText;
	Set<String> inputFiles;
	List<String> studentIDs;
	 FileProxy finalGradeFile, featureGradeFile;
	
	

	public AnAssignmenDataFolder(String aRootFolderName, FileProxy aFinalGradeFile) {
		super(aRootFolderName);
		finalGradeFile = aFinalGradeFile;
		if (rootFolder != null )
		initGraderData();
	}
	
	void initGraderData() {
	
		FileProxy inputFolder = this.getFileEntryFromLocalName(inputFolderName);
		if (inputFolder != null)
		inputFiles = inputFolder.getChildrenNames();
		FileProxy idFileProxy = getFileEntryFromLocalName(idFileName);
		featureGradeFile = getFileEntryFromLocalName(featureGradeFileName);
		if (finalGradeFile != null && (featureGradeFile == null || !featureGradeFile.exists())) {
			String fullFeatureGradeFileName = rootFolder.getAbsolutePath()+"/" + featureGradeFileName;
			File featureFile = new File(fullFeatureGradeFileName);
			File aFinalGradeFile = new File(finalGradeFile.getAbsoluteName() );
			 
			try {
				Files.copy(aFinalGradeFile.toPath(), featureFile.toPath(), REPLACE_EXISTING );
				featureGradeFile = new AFileSystemFileProxy(this, new File(fullFeatureGradeFileName), this.getAbsoluteName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		studentIDs = FileProxyUtils.toList(idFileProxy);
		
	}
	public Set<String> getInputFiles() {
		return inputFiles;
	}

	public List<String> getStudentIDs() {
		return studentIDs;
	}
	
//	public FileProxy getFeatureGradeFileProxy() {
//		return featureGradeFile;
//	}

	@Override
	public FileProxy getFeatureGradeFile() {
		// TODO Auto-generated method stub
		return featureGradeFile;
	}


}
