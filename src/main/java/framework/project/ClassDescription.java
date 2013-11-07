package framework.project;

import com.github.antlrjavaparser.api.CompilationUnit;

import java.io.File;
import java.io.IOException;

/**
 * Based on {@link grader.project.ClassDescription}
 */
public interface ClassDescription {

    /**
     * @return The Java {@link java.lang.Class} that has been loaded
     */
    public Class<?> getJavaClass();

    /**
     * @return The source code file.
     */
    public File getSource();

    /**
     * @return The tags that the class has been annotated with
     */
    public String[] getTags();

    /**
     * @return The structure pattern that the class has been annotated with
     */
    public String getStructurePatternName();

    /**
     * @return The property names that the class has been annotated with
     */
    public String[] getPropertyNames();

    /**
     * @return The editable property that the class has been annotated with
     */
    public String[] getEditablePropertyNames();

    /**
     * @return The parsed code
     */
    public CompilationUnit parse() throws IOException;

}
