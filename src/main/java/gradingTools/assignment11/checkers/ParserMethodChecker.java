package gradingTools.assignment11.checkers;

import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;
import grader.project.ClassDescription;
import util.annotations.Tags;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParserMethodChecker extends AnAbstractFeatureChecker {

    @Override
    public CheckResult check() {
        CheckResult result = new ACheckResult();

        // Get the command interpreter
        Set<ClassDescription> descriptions = project.getClassesManager().tagToClassDescriptions("Command Interpreter");
        if (descriptions.isEmpty()) {
            result.setScore(0);
            result.getLog().add("Couldn't find the command interpreter class.");
            return result;
        }
        ClassDescription commandInterpreter = new ArrayList<ClassDescription>(descriptions).get(0);

        Set<ClassDescription> parserDescriptions = project.getClassesManager().tagToClassDescriptions("Parser");
        parserDescriptions.add(commandInterpreter);

        double passes = 0;

        for (ClassDescription description : parserDescriptions) {
            Class<?> _class = description.getJavaClass();
            String[] tags = new String[] {
                    "command parser", "say parser", "move parser", "approach parser", "pass parser", "fail parser",
                    "command list parser", "repeat parser"
            };
            for (String tag : tags ) {
                // Get the method with the given tag
                Method targetMethod = null;
                for (Method method : _class.getDeclaredMethods()) {
                    Tags methodTags = method.getAnnotation(Tags.class);
                    if (methodTags != null && methodTags.value()[0].equalsIgnoreCase(tag))
                        targetMethod = method;
                }
                if (targetMethod == null)
                    result.getLog().add("No method tagged: " + tag);
                else {
                    passes++;

                    // Check the return type
                    if (Runnable.class.isAssignableFrom(targetMethod.getReturnType()))
                        passes++;
                    else
                        result.getLog().add("The " + tag + " method should return some sort of Runnable.");
                }
            }
        }

        result.setScore((passes / 16.0) * feature.getMax());
        return result;
    }
}
