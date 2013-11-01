package gradingTools.assignment7.tools;

import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManualClassFinder {

    private static Map<Project, CachedProjectClassFinder> cache = new HashMap<Project, CachedProjectClassFinder>();

    public static Option<ClassDescription> find(Project project, String tag) {
        // Get the project cache
        if (!cache.containsKey(project))
            cache.put(project, new CachedProjectClassFinder(project));
        return cache.get(project).get(tag);
    }

}
