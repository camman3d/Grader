package gradingTools.comp410.a1_vipQueue.tests.vipQueue;

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
public class VipQueueRepresentation {

    private Class<?> _class;
    private Object instantiation;
    private Method enqueueMethod;
    private Method vipEnqueueMethod;
    private Method dequeueMethod;
    private Method peekMethod;
    private Method isEmptyMethod;
    private Method isFullMethod;

    public VipQueueRepresentation(Class<?> _class, boolean autoGrade) throws NotAutomatableException, NoSuchMethodException {
        this._class = _class;

        // Find the methods
        MethodFinder methodFinder = MethodFinder.get(_class);
        try {
            enqueueMethod = methodFinder.find("enqueue", autoGrade, true, void.class, int.class).get();
            vipEnqueueMethod = methodFinder.find("vipEnqueue", autoGrade, true, void.class, int.class).get();
            dequeueMethod = methodFinder.find("dequeue", autoGrade, true, int.class).get();
            peekMethod = methodFinder.find("peek", autoGrade, true, int.class).get();
            isEmptyMethod = methodFinder.find("isEmpty", autoGrade, true, boolean.class).get();
            isFullMethod = methodFinder.find("isFull", autoGrade, true, boolean.class).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchMethodException();
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

    public void enqueue(int element) throws InvocationTargetException, IllegalAccessException {
        enqueueMethod.invoke(instantiation, element);
    }

    public void vipEnqueue(int element) throws InvocationTargetException, IllegalAccessException {
        vipEnqueueMethod.invoke(instantiation, element);
    }

    public Object dequeue() throws InvocationTargetException, IllegalAccessException {
        return dequeueMethod.invoke(instantiation);
    }

    public Object peek() throws InvocationTargetException, IllegalAccessException {
        return peekMethod.invoke(instantiation);
    }

    public boolean isEmpty() throws InvocationTargetException, IllegalAccessException {
        return (Boolean) isEmptyMethod.invoke(instantiation);
    }

    public boolean isFull() throws InvocationTargetException, IllegalAccessException {
        return (Boolean) isFullMethod.invoke(instantiation);
    }

}
