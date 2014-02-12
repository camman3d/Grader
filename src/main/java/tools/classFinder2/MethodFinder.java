package tools.classFinder2;

import framework.grading.testing.NotAutomatableException;
import scala.Option;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/14/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodFinder {

    private static Map<Class<?>, MethodFinder> classCache = new HashMap<Class<?>, MethodFinder>();

    public static MethodFinder get(Class<?> _class) {
        if (!classCache.containsKey(_class))
            classCache.put(_class, new MethodFinder(_class));
        return classCache.get(_class);
    }

    private Class<?> _class;
    private Map<String, Option<Method>> methodCache = new HashMap<String, Option<Method>>();

    private MethodFinder(Class<?> _class) {
        this._class = _class;
    }

    public MethodFinder(Class<?> _class, Map<String, Option<Method>> methodCache) {
        this._class = _class;
        this.methodCache = methodCache;
    }

    // This is if you want to prompt for needed methods beforehand. You can
    public static void setClassCache(Map<Class<?>, MethodFinder> cache) {
        classCache = cache;
    }

    /**
     * Returns the method that has been designated with "tag", or asks if there is none.
     *
     * @param name The tag to search for
     * @return The method wrapped in an {@link Option} in case none was found
     */
    public Option<Method> find(String name, boolean autoGrade, boolean lenient, Class<?> returnType, Class<?>... parameters) throws NotAutomatableException {
        // First check the cache
        if (methodCache.containsKey(name))
            return methodCache.get(name);

        // Next look for the signature
        for (Method method : _class.getMethods()) {

            // Check the name and return type
            if (method.getName().equalsIgnoreCase(name) && typesEquals(returnType, method.getReturnType(), lenient)) {

                // Now compare the parameter types
                Class<?>[] methodParamTypes = method.getParameterTypes();
                if (methodParamTypes.length == parameters.length) {
                    boolean matches = true;
                    for (int i = 0; i < parameters.length; i++) {
                        if (!typesEquals(methodParamTypes[i], parameters[i], lenient))
                            matches = false;
                    }
                    if (matches) {
                        methodCache.put(name, Option.apply(method));
                        return Option.apply(method);
                    }
                }
            }
        }

        // Finally, resort to asking if we're not automated right now
        if (autoGrade)
            throw new NotAutomatableException();
        Option<Method> method = ask(name);
        methodCache.put(name, method);
        return method;
    }

    public Option<Method> find(MethodDescription description, boolean autoGrade, boolean lenient) throws NotAutomatableException {
        return find(description.getName(), autoGrade, lenient, description.getReturnType(), description.getParameters());
    }

    private boolean typesEquals(Class<?> type1, Class<?> type2, Boolean lenient) {
        // Change primitives to abstractions for consistency
        if (type1 == int.class) type1 = Integer.class; if (type2 == int.class) type2 = Integer.class;
        if (type1 == double.class) type1 = Double.class; if (type2 == double.class) type2 = Double.class;
        if (type1 == boolean.class) type1 = Boolean.class; if (type2 == boolean.class) type2 = Boolean.class;
        if (type1 == byte.class) type1 = Byte.class; if (type2 == byte.class) type2 = Byte.class;
        if (type1 == short.class) type1 = Short.class; if (type2 == short.class) type2 = Short.class;
        if (type1 == long.class) type1 = Long.class; if (type2 == long.class) type2 = Long.class;
        if (type1 == float.class) type1 = Float.class; if (type2 == float.class) type2 = Float.class;
        if (type1 == char.class) type1 = Character.class; if (type2 == char.class) type2 = Character.class;

        // If we are lenient then we accept if type1 instanceof type2 and vice versa
        if (lenient)
            return type2.isAssignableFrom(type1) || type1.isAssignableFrom(type2);
        else
            return type1 == type2;
    }

    private Option<Method> ask(String description) {
        List<Method> methods = Arrays.asList(_class.getMethods());
        Collections.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Method method = (Method) JOptionPane.showInputDialog(null,
                "Which method is: \"" + description + "\"? (Click Cancel if doesn't exist)",
                "Method finder", JOptionPane.QUESTION_MESSAGE, null,
                methods.toArray(),
                null
        );
        if (method == null)
            return Option.empty();
        return Option.apply(method);
    }

}
