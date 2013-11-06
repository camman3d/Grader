package tools.classModifier;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/5/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ClassModifier<T> {

    private static String directoryName = "./modifiedClasses";
    private Class<T> _class;

    protected ClassModifier(Class<T> _class) {
        this._class = _class;
    }

    public void modify() throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass cc = classPool.get(_class.getCanonicalName());
        cc = modifyClass(cc);
        saveModifiedClass(cc);
    }

    protected abstract CtClass modifyClass(CtClass ctClass) throws NotFoundException, CannotCompileException;

    public final void saveModifiedClass(CtClass ctClass) throws CannotCompileException, IOException {
        ctClass.writeFile(directoryName);
    }

    public static String getDirectoryName() {
        return directoryName;
    }

    public static void setDirectoryName(String _directoryName) {
        directoryName = _directoryName;
    }

    public Class<T> get_class() {
        return _class;
    }

    public void set_class(Class<T> _class) {
        this._class = _class;
    }
}
