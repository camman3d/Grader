package tools.classModifier.test;

/*
 * Run with the following VM option:
 * -Xbootclasspath/p:./modifiedClasses
 */
public class TestModifiedStringBuilder {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String str = "Hello world";
        System.out.println(str);
        System.out.println("reverseCount: " + StringBuilder.class.getField("reverseCount").get(null));

        str = new StringBuilder(str).reverse().toString();
        System.out.println(str);
        System.out.println("reverseCount: " + StringBuilder.class.getField("reverseCount").get(null));
    }
}
