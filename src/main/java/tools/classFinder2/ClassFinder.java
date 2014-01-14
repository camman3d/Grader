package tools.classFinder2;

import framework.grading.testing.NotAutomatableException;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import javax.swing.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/8/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClassFinder {
    private static Map<Project, ClassFinder> projectCache = new HashMap<Project, ClassFinder>();

    public static ClassFinder get(Project project) {
        if (!projectCache.containsKey(project))
            projectCache.put(project, new ClassFinder(project));
        return projectCache.get(project);
    }

    private Project project;
    private Map<String, Option<ClassDescription>> classCache = new HashMap<String, Option<ClassDescription>>();

    private ClassFinder(Project project) {
        this.project = project;
    }

    /**
     * Finds the first class with the given tag. If no class is found then it asks the grader which one it is.
     * @param tag The tag to search for
     * @return The class description wrapped in an {@link Option} in case none was found
     */
    public Option<ClassDescription> findByTag(String tag, boolean autoGrade) throws NotAutomatableException {
        // First check the cache
        if (classCache.containsKey(tag))
            return classCache.get(tag);

        // We haven't search for that yet. Let's look starting with the tags
        Set<ClassDescription> descriptions = project.getClassesManager().get().findByTag(tag);
        Option<ClassDescription> classDescription;
        if (descriptions.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ask(tag);
        } else
            classDescription = Option.apply(new ArrayList<ClassDescription>(descriptions).get(0));
        classCache.put(tag, classDescription);
        return classDescription;
    }

    /**
     * Finds the first class with the given tag. If no class is found then it asks the grader which one it is.
     * @param name
     * @return The class description wrapped in an {@link Option} in case none was found
     */
    public Option<ClassDescription> findByName(String name, boolean autoGrade) throws NotAutomatableException {
        // First check the cache
        if (classCache.containsKey(name))
            return classCache.get(name);

        project.getClassesManager().get().findByClassName(name);
        Option<ClassDescription> classDescription = project.getClassesManager().get().findByClassName(name);
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ask(name);
        }
        classCache.put(name, classDescription);
        return classDescription;
    }

    private Option<ClassDescription> ask(String description) {
        List<ClassDescription> classDescriptions = new ArrayList<ClassDescription>(project.getClassesManager().get().getClassDescriptions());
        Collections.sort(classDescriptions, new Comparator<ClassDescription>() {
            @Override
            public int compare(ClassDescription o1, ClassDescription o2) {
                return o1.getJavaClass().getCanonicalName().compareTo(o2.getJavaClass().getCanonicalName());
            }
        });

        ClassDescription classDescription = (ClassDescription) JOptionPane.showInputDialog(null,
                "Which class is: \"" + description + "\"? (Click Cancel if doesn't exist)",
                "Class finder", JOptionPane.QUESTION_MESSAGE, null,
                classDescriptions.toArray(),
                null
        );
        if (classDescription == null)
            return Option.empty();
        return Option.apply(classDescription);
    }
}
