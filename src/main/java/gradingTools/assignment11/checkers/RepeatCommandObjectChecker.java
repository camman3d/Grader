package gradingTools.assignment11.checkers;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.MethodDeclaration;
import com.github.antlrjavaparser.api.stmt.Statement;
import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;
import grader.project.ClassDescription;
import tools.CompilationNavigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RepeatCommandObjectChecker extends AnAbstractFeatureChecker {

    @Override
    public CheckResult check() {
        CheckResult result = new ACheckResult();

        // Get the class that is tagged as "Command List"
        Set<ClassDescription> descriptions = project.getClassesManager().tagToClassDescriptions("Repeat");
        if (descriptions.isEmpty()) {
            result.setScore(0);
            result.getLog().add("No classed tagged \"Repeat\"");
            return result;
        }
        ClassDescription description = new ArrayList<ClassDescription>(descriptions).get(0);

        // Check that it loops and runs the command within the loop
        try {
            ClassOrInterfaceDeclaration classDef = CompilationNavigation.getClassDef(description.getCompilationUnit());
            MethodDeclaration method = CompilationNavigation.getMethod(classDef, "run");
            String code = method.getBody().toString();

            // Look for a loop
            double passes = 1;
            if (code.contains("for") || code.contains("while"))
                passes++;
            else
                result.getLog().add("Couldn't find a loop.");

            // Look for run
            if (code.contains(".run();"))
                passes++;
            else
                result.getLog().add("Couldn't find a run invocation.");
            result.setScore(feature.getMax() * (passes / 3.0));
            return result;
        } catch (IOException e) {
            return null;
        }
    }
}
