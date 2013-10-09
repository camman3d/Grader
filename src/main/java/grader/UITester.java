package grader;

import javax.swing.JFrame;

import bus.uigen.ObjectEditor;
import bus.uigen.attributes.AttributeNames;
import bus.uigen.widgets.awt.AWTContainer;

public class UITester {
public static void main (String[] args) {

	
		
//		ObjectEditor.setMethodAttribute(AFeature.class, "correct", AttributeNames.SHOW_BUTTON, true);
		AGradeSheet gradeSheet = new AGradeSheet();
		ObjectEditor.edit(gradeSheet);
		JFrame customFrame = new JFrame();
		ObjectEditor.editInMainContainer(gradeSheet,  AWTContainer.virtualContainer(customFrame.getContentPane()));
		customFrame.setSize(500, 400);
		customFrame.setVisible(true);
}

}
