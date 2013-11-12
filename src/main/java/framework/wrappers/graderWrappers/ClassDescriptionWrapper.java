package framework.wrappers.graderWrappers;

import com.github.antlrjavaparser.api.CompilationUnit;
import framework.project.ClassDescription;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * This wrapper allows for {@link grader.project.ClassDescription} to be used in place of
 * {@link framework.project.ClassDescription}. Also, not every method will work, as the two classes are not identical.
 *
 * Available methods
 */
public class ClassDescriptionWrapper implements ClassDescription {

    private grader.project.ClassDescription classDescription;

    public ClassDescriptionWrapper(grader.project.ClassDescription classDescription) {
        this.classDescription = classDescription;
    }

    /**
     * @deprecated This doesn't translate. Instead use {@code getClassDescription().getClassProxy()}
     */
    @Override
    @Deprecated
    public Class<?> getJavaClass() {
        return null;
    }

    /**
     * @deprecated This doesn't translate. If you want the text use {@code getClassDescription().getText()}
     */
    @Override
    @Deprecated
    public File getSource() {
        return null;
    }

    @Override
    public String[] getTags() {
        return classDescription.getTags();
    }

    @Override
    public String getStructurePatternName() {
        return classDescription.getStructurePatternName();
    }

    @Override
    public String[] getPropertyNames() {
        return classDescription.getPropertyNames();
    }

    @Override
    public String[] getEditablePropertyNames() {
        return classDescription.getEditablePropertyNames();
    }

    @Override
    public CompilationUnit parse() throws IOException {
        return classDescription.getCompilationUnit();
    }

    /**
     * @deprecated This doesn't translate. Instead do {@code getClassDescription().getClassProxy()} and use reflection.
     */
    @Override
    @Deprecated
    public List<Method> getTaggedMethods(String tag) {
        return null;
    }

    public grader.project.ClassDescription getClassDescription() {
        return classDescription;
    }
}
