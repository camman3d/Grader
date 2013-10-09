package grader.interactors;
// common interface for OE and other types
public interface Interactor<ModelType> {
	void interact(ModelType aModel);

}
