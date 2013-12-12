package tools.classModifier.test;

/*
 * Run with the following VM option:
 * -Xbootclasspath/p:./modifiedClasses
 */
public class TestModifiedThread {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        System.out.println("runCount: " + Thread.class.getField("runCount").get(null));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world");
            }
        }).start();

        Thread.sleep(50);
        System.out.println("runCount: " + Thread.class.getField("runCount").get(null));
    }
}
