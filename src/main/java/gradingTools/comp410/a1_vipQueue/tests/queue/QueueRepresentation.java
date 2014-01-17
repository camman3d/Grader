package gradingTools.comp410.a1_vipQueue.tests.queue;

import framework.grading.testing.NotAutomatableException;
import tools.classFinder2.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/14/14
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueueRepresentation {

    private Class<?> _class;
    private Object instantiation;
    private Method enqueueMethod;
    private Method dequeueMethod;
    private Method peekMethod;
    private Method isEmptyMethod;
    private Method isFullMethod;

    public QueueRepresentation(Class<?> _class, boolean autoGrade) throws NotAutomatableException, NoSuchMethodException {
        this._class = _class;

        // Find the methods
        MethodFinder methodFinder = MethodFinder.get(_class);
        try {
            enqueueMethod = methodFinder.find("enqueue", autoGrade, true, void.class, int.class).get();
        } catch (NoSuchElementException e) {
            enqueueMethod = null;
        }
        try {
            dequeueMethod = methodFinder.find("dequeue", autoGrade, true, int.class).get();
        } catch (NoSuchElementException e) {
            dequeueMethod = null;
        }
        try {
            peekMethod = methodFinder.find("peek", autoGrade, true, int.class).get();
        } catch (NoSuchElementException e) {
            peekMethod = null;
        }
        try {
            isEmptyMethod = methodFinder.find("isEmpty", autoGrade, true, boolean.class).get();
        } catch (NoSuchElementException e) {
            isEmptyMethod = null;
        }
        try {
            isFullMethod = methodFinder.find("isFull", autoGrade, true, boolean.class).get();
        } catch (NoSuchElementException e) {
            isFullMethod = null;
        }

    }

    /**
     * This attempts to create the queue by trying to find a valid constructor then instantiating it.
     */
    public void instantiate() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            instantiation = _class.getConstructor(Object.class).newInstance(10);
        } catch (NoSuchMethodException e) {
            try {
                instantiation = _class.getConstructor(int.class).newInstance(10);
            } catch (NoSuchMethodException e1) {
                try {
                    instantiation = _class.getConstructor(Integer.class).newInstance(10);
                } catch (NoSuchMethodException e2) {
                    try {
                        instantiation = _class.newInstance();
                    } catch (Exception e3) {
                        throw new InstantiationException();
                    }
                }
            }
        }
    }

    public Object getInstantiation() {
        return instantiation;
    }

    public void setInstantiation(Object obj) {
        instantiation = obj;
    }

    public void enqueue(int element) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (enqueueMethod == null)
            throw new NoSuchMethodException();
        enqueueMethod.invoke(instantiation, element);
    }

    public Object dequeue() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (dequeueMethod == null)
            throw new NoSuchMethodException();
        return dequeueMethod.invoke(instantiation);
    }

    public Object peek() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (peekMethod == null)
            throw new NoSuchMethodException();
        return peekMethod.invoke(instantiation);
    }

    public boolean isEmpty() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (isEmptyMethod == null)
            throw new NoSuchMethodException();
        return (Boolean) isEmptyMethod.invoke(instantiation);
    }

    public boolean isFull() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (isFullMethod == null)
            throw new NoSuchMethodException();
        return (Boolean) isFullMethod.invoke(instantiation);
    }

    public Field getArrayField() throws NoSuchFieldException {
        // Find the array field
        Field[] fields = _class.getDeclaredFields();
        Field arrayField = null;
        for (Field field : fields)
            if (field.getType().isArray())
                arrayField = field;
        if (arrayField == null)
            throw new NoSuchFieldException();
        return arrayField;
    }

}
