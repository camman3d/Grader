package tools.classFinder;

import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class RootTagFinder {

    private Project project;

    public RootTagFinder(Project project) {
        this.project = project;
    }

    private Option<ClassDescription> find(String tag, boolean isInterface) {
        try {
            Set<ClassDescription> classes = project.getClassesManager().get().findByTag(tag);
            for (ClassDescription description : classes) {
                Class<?> _class = description.getJavaClass();
                Class<?> superclass = _class.getSuperclass();
                if (_class.isInterface() == isInterface && superclass.equals(Object.class))
                    return Option.apply(description);
            }
            return Option.empty();
        } catch (Exception e) {
            return Option.empty();
        }
    }

    public Option<ClassDescription> findClass(String tag) {
        return find(tag, false);
    }

    public Option<ClassDescription> findInterface(String tag) {
        return find(tag, true);
    }
}
