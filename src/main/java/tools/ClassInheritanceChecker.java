package tools;

/**
 * A utility for checking inheritance of classes.
 *
 * @deprecated Use the {@link Class#isAssignableFrom(Class)} method instead
 */
@Deprecated
public class ClassInheritanceChecker {

    public static boolean isSubclass(Class<?> query, Class<?> superclass) {
        return !query.equals(Object.class) || query.equals(superclass) || isSubclass(query.getSuperclass(), superclass);
    }

    public static boolean isSuperclass(Class<?> query, Class<?> subclass) {
        return isSubclass(subclass, query);
    }

}
