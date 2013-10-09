package grader.documents;

import util.misc.Common;

import java.util.HashMap;
import java.util.Map;

public class DocumentDisplayerRegistry {
    public static Map<String, DocumentDisplayer> suffixToDisplayer = new HashMap();
    public static DocumentDisplayer defaultDisplayer = new AGeneralDocumentDisplayer();
    final static DocumentDisplayer wordDisplayer = new AWordDocumentDisplayer();


    public static DocumentDisplayer getDefaultDisplayer() {
        return defaultDisplayer;
    }

    public static void setDefaultDisplayer(DocumentDisplayer defaultOpener) {
        DocumentDisplayerRegistry.defaultDisplayer = defaultOpener;
    }

    public static void clear() {
        suffixToDisplayer.clear();
    }

    public static void registerDocumentDisplayer(String aSuffix, DocumentDisplayer aDisplayer) {
        suffixToDisplayer.put(aSuffix, aDisplayer);
    }

    public static void unRegisterDocumentDisplayer(String aSuffix, DocumentDisplayer aDisplayer) {
        suffixToDisplayer.remove(aSuffix);
    }

    public static DocumentDisplayer getDocumentDisplayer(String aSuffix) {
        return suffixToDisplayer.get(aSuffix);
    }

    public static void display(String aFileName) {
        String aSuffix = Common.getFileSuffix(aFileName);

        DocumentDisplayer displayer = getDocumentDisplayer(aSuffix);
        if (displayer == null) {
            displayer = defaultDisplayer;
        }

        displayer.displayFile(aFileName);
    }

    public static void useWordIfPossible() {
        registerDocumentDisplayer("txt", wordDisplayer);

    }

}
