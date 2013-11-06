package tools.classModifier;

import javassist.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/5/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
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
