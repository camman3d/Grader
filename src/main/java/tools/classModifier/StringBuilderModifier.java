package tools.classModifier;

import javassist.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 12/12/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class StringBuilderModifier extends ClassModifier<StringBuilder> {
    protected StringBuilderModifier() {
        super(StringBuilder.class);
    }

    @Override
    protected CtClass modifyClass(CtClass ctClass) throws NotFoundException, CannotCompileException {

        // Add a static field which counts the number of times "reverse" is called
        CtField field = new CtField(CtClass.intType, "reverseCount", ctClass);
        field.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        ctClass.addField(field);

        // Increment the counter when reverse is called
        CtMethod method = ctClass.getDeclaredMethod("reverse");
        String statementSource = "{StringBuilder.reverseCount++;}";

        method.insertBefore(statementSource);
        return ctClass;
    }
}
