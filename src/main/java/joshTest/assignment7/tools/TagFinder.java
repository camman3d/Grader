package joshTest.assignment7.tools;

import grader.project.ClassDescription;
import grader.project.Project;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/23/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagFinder {

    public static Set<ClassDescription> find(Project project, String tag) {
        Set<ClassDescription> tagged = new HashSet<ClassDescription>();
        for (ClassDescription description : project.getClassesManager().getClassDescriptions()) {
            String[] tags = description.getTags();
            for (String t : tags) {
                if (tag.equalsIgnoreCase(t))
                    tagged.add(description);
            }
        }
        return tagged;
    }

}
