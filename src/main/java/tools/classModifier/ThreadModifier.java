package tools.classModifier;

import javassist.*;

/**
 * This modifies the Thread class to count the number of times "run" is called.
 */
public class ThreadModifier extends ClassModifier<Thread> {

    protected ThreadModifier() {
        super(Thread.class);
    }

    @Override
    protected CtClass modifyClass(CtClass ctClass) throws NotFoundException, CannotCompileException {

        // Add a static field which counts the number of times "run" is called
        CtField field = new CtField(CtClass.intType, "runCount", ctClass);
        field.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        ctClass.addField(field);

        // So you can't modify start() without breaking everything. But you can modify run()
        CtMethod method = ctClass.getDeclaredMethod("run");
        String statementSource = "{Thread.runCount++;}";

        method.insertBefore(statementSource);
        return ctClass;
    }
}
