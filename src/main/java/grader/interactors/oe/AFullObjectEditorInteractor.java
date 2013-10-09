package grader.interactors.oe;

import grader.interactors.Interactor;
import bus.uigen.ObjectEditor;

public class AFullObjectEditorInteractor implements Interactor {

	@Override
	public void interact(Object aModel) {
		ObjectEditor.edit(aModel);
		
	}

}
