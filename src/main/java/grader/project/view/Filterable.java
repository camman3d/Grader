package grader.project.view;

public interface Filterable<FilterType> {
	boolean filter(FilterType aFilter);
}
