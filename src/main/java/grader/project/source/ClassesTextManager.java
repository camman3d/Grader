package grader.project.source;

import grader.project.view.ViewableClassDescription;

import java.util.Collection;

public interface ClassesTextManager {
    public static final String DEFAULT_SOURCES_FILE_PREFIX = "sources";

    public static final String DEFAULT_SOURCES_FILE_SUFFIX = ".doc";

    public static final String DEFAULT_SOURCES_FILE_NAME = DEFAULT_SOURCES_FILE_PREFIX + DEFAULT_SOURCES_FILE_SUFFIX;

    public static final String PROJECT_DIRECTORY = ".";

    public void writeAllSourcesText();

    public void initializeAllSourcesText();

    public StringBuffer toStringBuffer(
            Collection<ViewableClassDescription> sourceClasses);

    public int totalTextSize(Collection<ViewableClassDescription> aSourceClasses);

    public StringBuffer getAllSourcesText();

    public void setAllSourcesText(StringBuffer anAllSourcesText);

    void writeAllSourcesText(String aFileName);

}