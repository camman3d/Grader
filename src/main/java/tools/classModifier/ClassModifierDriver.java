package tools.classModifier;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * This is the starting point for creating custom classes.
 */
public class ClassModifierDriver {

    private static ClassModifier[] classModifiers = new ClassModifier[]{
            new ThreadModifier(),
            new StringBuilderModifier()
//            new StringModifier()
    };

    public static void main(String[] args) {

        try {
            // Clear out old classes
            FileUtils.deleteDirectory(new File(ClassModifier.getDirectoryName()));

            // Create the modified the classes
            for (ClassModifier<?> classModifier : classModifiers) {
                try {
                    System.out.println("Modifying class: " + classModifier.get_class().getSimpleName());
                    classModifier.modify();
                    System.out.println("Successful.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
