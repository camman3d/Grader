package grader.demo.feedback;

import grader.documents.DocumentDisplayerRegistry;
import grader.feedback.AnAllTextSourceDisplayer;
import grader.feedback.SourceDisplayer;
import grader.sakai.project.SakaiProject;

public class ATracingAllTextSourceDisplayer extends AnAllTextSourceDisplayer implements SourceDisplayer {

	@Override
	public void displaySource(SakaiProject aProject) {
		 super.displaySource(aProject);
		 System.out.println("Showing source from folder:" + aProject.getRootCodeFolder().getSourceProjectFolderName());

	}

}
