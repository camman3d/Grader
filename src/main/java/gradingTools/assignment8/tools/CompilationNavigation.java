package gradingTools.assignment8.tools;

import com.github.antlrjavaparser.api.CompilationUnit;
import com.github.antlrjavaparser.api.body.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/29/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class CompilationNavigation {

    public static ClassOrInterfaceDeclaration getClassDef(CompilationUnit compilation) {
        for (TypeDeclaration declaration : compilation.getTypes()) {
            if (declaration instanceof ClassOrInterfaceDeclaration)
                return (ClassOrInterfaceDeclaration) declaration;
        }
        return null;
    }

    public static List<ConstructorDeclaration> getConstructors(ClassOrInterfaceDeclaration classDef) {
        List<ConstructorDeclaration> constructors = new ArrayList<ConstructorDeclaration>();
        for (BodyDeclaration declaration : classDef.getMembers()) {
            if (declaration instanceof ConstructorDeclaration)
                constructors.add((ConstructorDeclaration) declaration);
        }
        return constructors;
    }

    public static List<MethodDeclaration> getMethods(ClassOrInterfaceDeclaration classDef) {
        List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
        for (BodyDeclaration declaration : classDef.getMembers()) {
            if (declaration instanceof MethodDeclaration)
                methods.add((MethodDeclaration) declaration);
        }
        return methods;
    }

    public static List<MethodDeclaration> getMethods(ClassOrInterfaceDeclaration classDef, String name) {
        List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
        for (MethodDeclaration method : getMethods(classDef)) {
            if (method.getName().equals(name))
                methods.add(method);
        }
        return methods;
    }

    public static MethodDeclaration getMethod(ClassOrInterfaceDeclaration classDef, String name) {
        for (MethodDeclaration method : getMethods(classDef)) {
            if (method.getName().equals(name))
                return method;
        }
        return null;
    }


}
