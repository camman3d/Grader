package gradingTools.comp410.a1_vipQueue.tests;

import com.github.antlrjavaparser.api.TypeParameter;
import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.FieldDeclaration;
import com.github.antlrjavaparser.api.body.MethodDeclaration;
import com.github.antlrjavaparser.api.body.Parameter;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.InnerClassDescription;
import framework.project.Project;
import scala.Option;
import tools.CompilationNavigation;
import tools.classFinder2.ClassFinder;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/14/14
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsesGenericsTestCase extends BasicTestCase {

    private String className;

    public UsesGenericsTestCase(String className) {
        super(className + " uses generics test case");
        this.className = className;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Find the "Queue" class
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByName(className, autoGrade);
        if (classDescription.isEmpty())
            return fail("No " + className.toLowerCase() + " class");

        try {
            // Get the parsed code and pull out the type parameters
            ClassOrInterfaceDeclaration classDef = classDescription.get().parse();
            List<TypeParameter> typeParameters = CompilationNavigation.getTypeParameters(classDescription.get());

            // There should be one type parameter
            if (typeParameters == null) {
                return fail("The " + className + " didn't use generics.");
            }
            if (typeParameters.size() > 1)
                return fail("There should only be one generic type in the " + className + " class.");

            // There should be at least one field of the type, one method that returns the type, or one method that uses
            // the type as an argument.

            // Look at methods. Count the number of times the type parameter is used in parameters or return type
            TypeParameter type = typeParameters.get(0);
            int typeCount = 0;
            List<MethodDeclaration> methods = CompilationNavigation.getMethods(classDef);
            if (methods != null)
                for (MethodDeclaration method : methods) {
                    if (method.getType().toString().equals(type.toString()))
                        typeCount++;
                    if (method.getParameters() != null)
                        for (Parameter parameter : method.getParameters())
                            if (parameter.getType().toString().equals(type.toString()))
                                typeCount++;
            }

            // Look at fields. Count the number of fields which use the type parameter
            List<FieldDeclaration> fields = CompilationNavigation.getFields(classDef);
            if (fields != null)
                for (FieldDeclaration field : fields)
                    if (field.getType().toString().equals(type.toString()))
                        typeCount++;

            if (typeCount > 0)
                return pass();
            return fail("The generic type was never used");

        } catch (IOException e) {
            throw new NotGradableException();
        }

    }
}
