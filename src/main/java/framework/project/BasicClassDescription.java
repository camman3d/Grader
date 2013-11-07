package framework.project;

import com.github.antlrjavaparser.JavaParser;
import com.github.antlrjavaparser.api.CompilationUnit;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.Tags;

import java.io.File;
import java.io.IOException;

/**
 * @see ClassDescription
 */
public class BasicClassDescription implements ClassDescription {

    private Class<?> javaClass;
    private File source;
    private CompilationUnit compilationUnit;

    public BasicClassDescription(Class<?> javaClass, File source) {
        this.javaClass = javaClass;
        this.source = source;
    }

    @Override
    public Class getJavaClass() {
        return javaClass;
    }

    @Override
    public File getSource() {
        return source;
    }

    @Override
    public String[] getTags() {
        try {
            return javaClass.getAnnotation(Tags.class).value();
        } catch (Exception e) {
            return new String[]{};
        }
    }

    @Override
    public String getStructurePatternName() {
        try {
            return javaClass.getAnnotation(StructurePattern.class).value();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String[] getPropertyNames() {
        try {
            return javaClass.getAnnotation(PropertyNames.class).value();
        } catch (Exception e) {
            return new String[]{};
        }
    }

    @Override
    public String[] getEditablePropertyNames() {
        try {
            return javaClass.getAnnotation(EditablePropertyNames.class).value();
        } catch (Exception e) {
            return new String[]{};
        }
    }

    @Override
    public CompilationUnit parse() throws IOException {
        if (compilationUnit == null)
            compilationUnit = JavaParser.parse(source);
        return compilationUnit;
    }

    @Override
    public String toString() {
        return javaClass.getCanonicalName();
    }
}
