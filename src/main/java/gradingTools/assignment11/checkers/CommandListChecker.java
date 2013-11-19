package gradingTools.assignment11.checkers;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.MethodDeclaration;
import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;
import grader.project.ClassDescription;
import tools.CompilationNavigation;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandListChecker extends AnAbstractFeatureChecker {
    @Override
    public CheckResult check() {
        CheckResult result = new ACheckResult();
        double passedTests = 0;

        // Get the class that is tagged as "Command List"
        Set<ClassDescription> descriptions = project.getClassesManager().tagToClassDescriptions("Command List");
        if (descriptions.isEmpty()) {
            result.setScore(0);
            result.getLog().add("No classed tagged \"Command List\"");
            return result;
        }
        ClassDescription description = new ArrayList<ClassDescription>(descriptions).get(0);

        // Check for the List of Vector
        Class<?> _class = description.getJavaClass();
        for (Field field : _class.getDeclaredFields()) {
            if (List.class.isAssignableFrom(field.getType()) || Vector.class.isAssignableFrom(field.getType())) {
                passedTests++;
                break;
            }
        }
        if (passedTests == 0)
            result.getLog().add("There is no List or Vector containing runnables.");

        // Check that it runs all of the commands
        try {
            ClassOrInterfaceDeclaration classDef = CompilationNavigation.getClassDef(description.getCompilationUnit());
            MethodDeclaration method = CompilationNavigation.getMethod(classDef, "run");
            String code = method.getBody().toString();
            if (code.contains(".run();"))
                passedTests++;
            else
                result.getLog().add("Command list should run other commands.");
        } catch (IOException e) {
            return null;
        }

        result.setScore((passedTests / 2.0) * feature.getMax());
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
