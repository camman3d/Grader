package graders.assignment7.tools;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClassInheritanceChecker {

    public static boolean isSubclass(Class<?> query, Class<?> superclass) {
        return !query.equals(Object.class) || query.equals(superclass) || isSubclass(query.getSuperclass(), superclass);
    }

    public static boolean isSuperclass(Class<?> query, Class<?> subclass) {
        return isSubclass(subclass, query);
    }

}
