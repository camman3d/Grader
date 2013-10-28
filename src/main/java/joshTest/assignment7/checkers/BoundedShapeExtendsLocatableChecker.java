package joshTest.assignment7.checkers;

//import framework.grading.testing.BasicTestCase;
//import framework.grading.testing.NotAutomatableException;
//import framework.grading.testing.NotGradableException;
//import framework.grading.testing.TestCaseResult;
//import framework.project.ClassDescription;
//import framework.project.Project;

import grader.checkers.ACheckResult;
import grader.checkers.CheckResult;
import grader.project.ClassDescription;
import joshTest.wrappers.ErrorHandlingFeatureChecker;
import joshTest.assignment7.tools.SuperclassFinder;
import joshTest.assignment7.tools.TagFinder;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoundedShapeExtendsLocatableChecker extends ErrorHandlingFeatureChecker {


//    @Override
//    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
//        // Make sure we can get the class description
//        if (project.getClassesManager().isEmpty())
//            throw new NotGradableException();
//
//        // Get the bounded shape "root" class
//        Option<ClassDescription> boundedShapeDescription = new RootTagFinder(project).findClass("Bounded Shape");
//        if (boundedShapeDescription.isEmpty()) {
//            if (autoGrade)
//                throw new NotAutomatableException();
//            boundedShapeDescription = ManualClassFinder.find(project, "Bounded Shape");
//        }
//
//        // Get the locatable "root" class
//        Option<ClassDescription> locatableDescription = new RootTagFinder(project).findClass("Locatable");
//        if (locatableDescription.isEmpty()) {
//            if (autoGrade)
//                throw new NotAutomatableException();
//            locatableDescription = ManualClassFinder.find(project, "Locatable");
//        }
//
//        Class<?> boundedClass = boundedShapeDescription.get().getJavaClass();
//        Class<?> locatableClass = locatableDescription.get().getJavaClass();
//
//        if (ClassInheritanceChecker.isSubclass(boundedClass, locatableClass))
//            return pass();
//        else
//            return fail("Bounded shape should extend Locatable");
//    }

    @Override
    protected CheckResult doCheck() throws Exception {
        CheckResult result = new ACheckResult();

        // Get the class description for locatable
//            Set<ClassDescription> classDescriptions = project.getClassesManager().tagToClassDescriptions("Locatable");
        Set<ClassDescription> classDescriptions = TagFinder.find(project, "Locatable");
        if (classDescriptions == null || classDescriptions.isEmpty())
            return null;
        ClassDescription locatable = SuperclassFinder.findSuperclass(classDescriptions);

        // Get the class descriptions for all bounded shapes
        classDescriptions = TagFinder.find(project, "Bounded Shape");
        try {
            SuperclassFinder.findClassThatExtends(classDescriptions, locatable);
            result.setScore(feature.getMax());
        } catch (Exception e) {
            result.setScore(0);
            result.getLog().add("Unable to find a class tagged with \"Bounded Shape\" that extends a class tagged with \"Locatable\"");
        }
        return result;
    }

}
