package framework.execution.reflectionObjects;

import com.github.antlrjavaparser.api.CompilationUnit;
import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import framework.project.ClassDescription;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ManualClassDescription implements ClassDescription {

    private Class<?> _class;

    public ManualClassDescription(Class<?> _class) {
        this._class = _class;
    }

    @Override
    public Class<?> getJavaClass() {
        return _class;
    }

    @Override
    public File getSource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getTags() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getStructurePatternName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getPropertyNames() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getEditablePropertyNames() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ClassOrInterfaceDeclaration parse() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Method> getTaggedMethods(String tag) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
