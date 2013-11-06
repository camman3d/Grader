package tools.classModifier;

import javassist.*;

public class StringModifier extends ClassModifier<String> {
    protected StringModifier() {
        super(String.class);
    }

    @Override
    protected CtClass modifyClass(CtClass ctClass) throws NotFoundException, CannotCompileException {

        // Create a public hiddenValue int field
        CtField field = new CtField(CtClass.intType, "hiddenValue", ctClass);
        field.setModifiers(Modifier.PUBLIC);
        ctClass.addField(field);

        // Have the split method emit a MethodInvoked tracer event
        CtMethod method = ctClass.getDeclaredMethod("split");
        String statementSource = "{util.trace.MethodInvoked.newCase(\"String\", \"split\", \"void split(String)\", new Object[]{$1}, this);}";
        method.insertBefore(statementSource);
        return ctClass;
    }

}
