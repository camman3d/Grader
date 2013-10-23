package joshTest.assignment7.tools;

import grader.project.ClassDescription;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/23/13
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class SuperclassFinder {

    public static ClassDescription findSuperclass(Set<ClassDescription> classDescriptions) throws Exception {
        // Look for the class that isn't extending anything
        for (ClassDescription description : classDescriptions) {
            if (!description.getClassProxy().isInterface() && description.getClassProxy().getSuperclass().getCanonicalName().equals("java.lang.Object")) {
                return description;
            }
        }
        throw new Exception("Cannot find superclass");
    }

    public static ClassDescription findClassThatExtends(Set<ClassDescription> classDescriptions, ClassDescription superclass) throws Exception {
        for (ClassDescription description : classDescriptions) {
            if (!description.getClassProxy().isInterface() && description.getClassProxy().getSuperclass().getCanonicalName().equals(superclass.getClassProxy().getCanonicalName())) {
                return description;
            }
        }
        throw new Exception("Cannot find class that extends " + superclass.getClassProxy().getCanonicalName());
    }

}
