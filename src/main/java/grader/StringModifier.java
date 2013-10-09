package grader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;

public class StringModifier {
	public static void main(String[] args) {
		modifyAndWriteStringClass(String.class);
		
//		try {
//		ClassPool pool = ClassPool.getDefault();
//		CtClass cc = pool.get("java.lang.String");
//		CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
//		f.setModifiers(Modifier.PUBLIC);
//		cc.addField(f);
//		CtMethod m = cc.getDeclaredMethod("split");
//		m.insertBefore("{ System.out.println($1);}");		
//		cc.writeFile("./bin");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static CtClass modifyStringClass(Class c) {
		return modifyStringClass(c.getName());
	}
	public static CtClass modifyStringClass(String className) {
		try {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("java.lang.String");
		CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
		f.setModifiers(Modifier.PUBLIC);
		cc.addField(f);
		CtMethod m = cc.getDeclaredMethod("split");
//		String statementSource = "{util.trace.MethodInvoked.newCase(\"String\", \"split\", \"String split\", new Object[]{$1, $2}, this);}";
//		String statementSource = "{util.trace.MethodInvoked.newCase(\"String\", \"split\", \"String split\", new Object[]{$1}, this);}";
		String statementSource = "{util.trace.ImageFileMissing.newCase(\"String split\", new Object[]{$1});}";

//		String statementSource = "{util.trace.MethodInvoked.newCase(\"String\", \"split\", \"String split\", this);}";

//		m.insertBefore("{ System.out.println($1);}");	
		m.insertBefore(statementSource);
		return cc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void modifyAndWriteStringClass(Class aClass) {
		try {
		CtClass cc = modifyStringClass(aClass);
		cc.writeFile("./bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static Class modifyAndReturnStringClass(Class aClass) {
		try {
		CtClass cc = modifyStringClass(aClass);
		return cc.toClass();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
