package project;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.Tags;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/3/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicClassDescription implements ClassDescription {

    private Class<?> javaClass;
    private File source;

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
}
