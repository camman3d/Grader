package grader.documents;


import grader.project.AClassesManager;
import grader.project.AProject;
import grader.project.ClassesManager;
import grader.project.Project;
import grader.project.source.AClassesTextManager;
import grader.project.source.ClassesTextManager;
import grader.project.view.AClassViewManager;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import util.misc.Common;

public class AGeneralDocumentDisplayer implements DocumentDisplayer {

	
	
	public void displayFile(String aFileName) {
		if (Desktop.isDesktopSupported()) {
		    try {
		        File aFile = new File(aFileName);
		        if (!aFile.exists()) {
		             BufferedWriter output = new BufferedWriter(new FileWriter(aFile));
		             output.close();
		        }
		        Desktop.getDesktop().open(aFile);
		    } catch (IOException ex) {
		        // no application registered for PDFs
		    	ex.printStackTrace();
		    }
		}
//		String[] command = {wordPath, aFileName};
//		Common.exec(command);
	}
	
	

	

}
