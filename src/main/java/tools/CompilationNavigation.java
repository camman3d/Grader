package tools;

import com.github.antlrjavaparser.api.CompilationUnit;
import com.github.antlrjavaparser.api.TypeParameter;
import com.github.antlrjavaparser.api.body.*;
import framework.project.ClassDescription;
import framework.project.InnerClassDescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility to help with navigating the CompilationUnit that JavaParser returns.
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

    public static List<FieldDeclaration> getFields(ClassOrInterfaceDeclaration classDef) {
        List<FieldDeclaration> methods = new ArrayList<FieldDeclaration>();
        for (BodyDeclaration declaration : classDef.getMembers()) {
            if (declaration instanceof FieldDeclaration)
                methods.add((FieldDeclaration) declaration);
        }
        return methods;
    }

    public static List<ClassOrInterfaceDeclaration> getInnerClasses(ClassOrInterfaceDeclaration classDef) {
        List<ClassOrInterfaceDeclaration> classes = new ArrayList<ClassOrInterfaceDeclaration>();
        for (BodyDeclaration declaration : classDef.getMembers()) {
            if (declaration instanceof ClassOrInterfaceDeclaration)
                classes.add((ClassOrInterfaceDeclaration) declaration);
        }
        return classes;
    }

    /**
     * This looks for additional type parameters that are visible in the scope
     */
    public static List<TypeParameter> getTypeParameters(ClassDescription classDescription) {
        try {
            ClassOrInterfaceDeclaration classDef = classDescription.parse();
            List<TypeParameter> typeParameters = classDef.getTypeParameters();
            if (typeParameters == null)
                typeParameters = new ArrayList<TypeParameter>();

            if (classDescription instanceof InnerClassDescription) {
                ClassDescription parent = ((InnerClassDescription) classDescription).getParent();
                typeParameters.addAll(getTypeParameters(parent));
            }
            return typeParameters;
        } catch (IOException e) {
            System.err.println("Error getting type parameters from: " + classDescription.getJavaClass().getName());
        }
        return null;
    }

}
