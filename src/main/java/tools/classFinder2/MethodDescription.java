package tools.classFinder2;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/23/14
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class MethodDescription {

    private String name;
    private Class<?> returnType;
    private Class<?>[] parameters;

    public MethodDescription(String name, Class<?> returnType, Class<?> ... parameters) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<?>[] getParameters() {
        return parameters;
    }
}
