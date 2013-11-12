package framework.wrappers.graderWrappers;

import framework.project.ClassDescription;
import framework.project.ClassesManager;
import scala.Option;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This wrapper allows for a {@link grader.project.ClassesManager} to be used where a
 * {@link framework.project.ClassesManager} is used.
 *
 * This isn't a perfect fit so there may be some things that don't work just right.
 */
public class ClassesManagerWrapper implements ClassesManager {

    private grader.project.ClassesManager classesManager;

    public ClassesManagerWrapper(grader.project.ClassesManager classesManager) {
        this.classesManager = classesManager;
    }

    /**
     * @deprecated This doesn't translate.
     */
    @Override
    @Deprecated
    public ClassLoader getClassLoader() {
        return null;
    }

    /**
     * Finds and returns a {@link ClassDescriptionWrapper} wrapped in an {@link Option}
     * @param className The name of the class to find
     * @return The class description
     */
    @Override
    public Option<ClassDescription> findByClassName(String className) {
        grader.project.ClassDescription description = classesManager.classNameToClassDescription(className);
        if (description == null)
            return Option.empty();
        else {
            ClassDescription wrappedDescription = new ClassDescriptionWrapper(description);
            return Option.apply(wrappedDescription);
        }
    }

    @Override
    public Set<ClassDescription> findByTag(String tag) {
        Set<grader.project.ClassDescription> descriptions = classesManager.tagToClassDescriptions(tag);
        Set<ClassDescription> wrappedDescriptions = new HashSet<ClassDescription>();
        for (grader.project.ClassDescription d : descriptions)
            wrappedDescriptions.add(new ClassDescriptionWrapper(d));
        return wrappedDescriptions;
    }

    @Override
    public Set<ClassDescription> getClassDescriptions() {
        List<grader.project.ClassDescription> descriptions = classesManager.getClassDescriptions();
        Set<ClassDescription> wrappedDescriptions = new HashSet<ClassDescription>();
        for (grader.project.ClassDescription d : descriptions)
            wrappedDescriptions.add(new ClassDescriptionWrapper(d));
        return wrappedDescriptions;
    }
}
