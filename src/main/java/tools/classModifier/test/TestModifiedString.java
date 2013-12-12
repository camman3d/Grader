package tools.classModifier.test;

import util.trace.TraceableLogFactory;

import java.util.List;

/*
 * Run with the following VM option:
 * -Xbootclasspath/p:./modifiedClasses
 */
public class TestModifiedString {
    public static void main(String[] args) throws Exception {

        List<Exception> log = TraceableLogFactory.getTraceableLog().getLog();

        String s = "HELLO WORLD";
        System.out.println(s);
        String[] newStrings = s.split("ffff");
        System.out.println(newStrings);
        System.out.println((5 * 3));
        System.out.println(String.class.getField("hiddenValue").getName());

        for (Exception e : log)
            System.out.println(e);
        System.out.println(log.size());
    }


}
