package grader.file;

import java.util.HashSet;
import java.util.Set;

public class AnAbstractProxy {
    Set<String> descendentNames;
    Set<String> childrenNames = new HashSet();

    public Set<String> getChildrenNames() {
        return childrenNames;
    }

}
