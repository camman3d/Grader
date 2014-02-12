package framework.project;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import tools.CompilationNavigation;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/29/14
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class InnerClassDescription extends BasicClassDescription {

    private ClassDescription parent;

    public InnerClassDescription(Class<?> javaClass, ClassDescription parent) {
        super(javaClass, parent.getSource());
        this.parent = parent;
    }

    public ClassDescription getParent() {
        return parent;
    }

    @Override
    public ClassOrInterfaceDeclaration parse() throws IOException {
        // Get the parent class
        ClassOrInterfaceDeclaration parentDef = parent.parse();
        List<ClassOrInterfaceDeclaration> innerClasses = CompilationNavigation.getInnerClasses(parentDef);

        for (ClassOrInterfaceDeclaration c : innerClasses) {
            if (c.getName().equals(getJavaClass().getSimpleName()))
                return c;
        }
        return null;
    }
}
