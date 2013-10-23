package grader.sakai.project;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;

public class AnOEProjectStepperDisplayer implements ProjectStepperDisplayer<OEFrame> {
	public OEFrame display(ProjectStepper aProjectStepper) {
		OEFrame oeFrame = ObjectEditor.edit(aProjectStepper);
		oeFrame.setLocation(700, 500);
		oeFrame.setSize(500, 700);
		return oeFrame;
	}

}
