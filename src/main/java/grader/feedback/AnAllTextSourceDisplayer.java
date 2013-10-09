package grader.feedback;

import grader.documents.DocumentDisplayerRegistry;
import grader.sakai.project.SakaiProject;

public class AnAllTextSourceDisplayer implements SourceDisplayer {

    @Override
    public void displaySource(SakaiProject aProject) {
        DocumentDisplayerRegistry.display(aProject.getSourceFileName());

    }

}
