package framework.execution.reflectionObjects;

import framework.project.ClassDescription;
import framework.project.ClassesManager;
import scala.Option;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ManualClassesManager implements ClassesManager {

    private Set<ClassDescription> classDescriptions;

    public ManualClassesManager(Class<?> ... classes) {
        classDescriptions = new HashSet<ClassDescription>();
        for (Class<?> _class : classes)
            classDescriptions.add(new ManualClassDescription(_class));
    }

    @Override
    public ClassLoader getClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    @Override
    public Option<ClassDescription> findByClassName(String className) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Option<ClassDescription> findByTag(String tag) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<ClassDescription> getClassDescriptions() {
        return classDescriptions;
    }
}
