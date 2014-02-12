package tools.classFinder2;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/30/14
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class FieldFinder {

    /**
     * This looks for a field in _class which is of fieldClass type. It also checks interfaces and super classes.
     *
     * @param _class     The class whose field we are searching for
     * @param fieldClass The type of the field that we are looking for
     */
    public static Field find(Class<?> _class, Class<?> fieldClass) {
        if (fieldClass == null || fieldClass == Object.class)
            return null;

        // First look in the declared fields
        for (Field f : _class.getDeclaredFields())
            if (f.getType() == fieldClass)
                return f;

        // Now check for interface assignments
        Class<?>[] interfaces = fieldClass.getInterfaces();
        if (interfaces != null) {
            for (Class<?> _interface : interfaces) {
                Field field = find(_class, _interface);
                if (field != null)
                    return field;
            }
        }

        // Also check the superclass
        return find(_class, fieldClass.getSuperclass());
    }

    /**
     * This looks for all fields in _class which is of fieldClass type. It also checks interfaces and super classes.
     *
     * @param _class     The class whose field we are searching for
     * @param fieldClass The type of the fields that we are looking for
     */
    public static Set<Field> findAll(Class<?> _class, Class<?> fieldClass) {
        if (fieldClass == null || fieldClass == Object.class)
            return new HashSet<Field>();

        Set<Field> fields = new HashSet<Field>();

        // First look in the declared fields
        for (Field f : _class.getDeclaredFields())
            if (f.getType() == fieldClass)
                fields.add(f);

        // Now check for interface assignments
        Class<?>[] interfaces = fieldClass.getInterfaces();
        if (interfaces != null) {
            for (Class<?> _interface : interfaces) {
                fields.addAll(findAll(_class, _interface));
            }
        }

        // Also check the superclass
        fields.addAll(findAll(_class, fieldClass.getSuperclass()));
        return fields;
    }

}
