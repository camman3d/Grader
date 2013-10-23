package graders.assignment7.tools;

import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CachedProjectClassFinder {

    private Project project;
    private Map<String, Option<ClassDescription>> cache = new HashMap<String, Option<ClassDescription>>();

    public CachedProjectClassFinder(Project project) {
        this.project = project;
    }

    public Option<ClassDescription> get(String tag) {
        if (!cache.containsKey(tag)) {
            ClassDescription description = (ClassDescription) JOptionPane.showInputDialog(null,
                    "Which class is: \"" + tag + "\"? (Click Cancel if doesn't exist)",
                    "Class finder", JOptionPane.QUESTION_MESSAGE, null,
                    project.getClassesManager().get().getClassDescriptions().toArray(),
                    null
            );
            if (description == null)
                cache.put(tag, Option.<ClassDescription>empty());
            else
                cache.put(tag, Option.apply(description));
        }
        return cache.get(tag);
    }
}
