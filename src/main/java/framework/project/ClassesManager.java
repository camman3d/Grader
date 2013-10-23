package framework.project;

import scala.Option;

import java.util.Set;

/**
 * Like ClassesManager
 */
public interface ClassesManager {

    /**
     * @return The {@link ClassLoader} created by this manager.
     */
    public ClassLoader getClassLoader();

    /**
     * Attempts to find a class description based on the name of the class
     *
     * @param className The name of the class to find
     * @return The {@link ClassDescription} wrapped in an {@link scala.Option} in case none is found.
     */
    public Option<ClassDescription> findByClassName(String className);

    /**
     * Attempts to find a class description based on a tag
     *
     * @param tag The tag to search for
     * @return The {@link ClassDescription} wrapped in an {@link scala.Option} in case none is found.
     */
    public Set<ClassDescription> findByTag(String tag);

    /**
     * @return All the {@link ClassDescription}
     */
    public Set<ClassDescription> getClassDescriptions();

}
