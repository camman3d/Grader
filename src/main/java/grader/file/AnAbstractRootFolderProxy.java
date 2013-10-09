package grader.file;

import util.misc.Common;

import java.util.*;

public abstract class AnAbstractRootFolderProxy extends AnAbstractProxy implements RootFolderProxy {
    protected Map<String, FileProxy> nameToFileProxy = new HashMap();
    protected List<FileProxy> entries = new ArrayList();

    protected void add(FileProxy aFileProxy) {
        entries.add(aFileProxy);
        nameToFileProxy.put(aFileProxy.getAbsoluteName().toLowerCase(), aFileProxy);
    }

    @Override
    public List<FileProxy> getFileEntries() {
        return entries;
    }

    @Override
    public Set<String> getEntryNames() {
        return nameToFileProxy.keySet();
    }

    public List<FileProxy> getChildrenOf(String aParentName) {
        String myName = aParentName.toLowerCase();
        int parentDepth = Common.numMiddleOccurences(myName, '/');

        List<FileProxy> retVal = new ArrayList();
        for (FileProxy entry : entries) {
            String childName = entry.getAbsoluteName();
            if (!childName.startsWith(myName)) continue;
            int childDepth = Common.numMiddleOccurences(childName, '/');

            if (childDepth == parentDepth + 1) {
                retVal.add(entry);
            }
        }
        return retVal;

    }

    @Override
    public Set<String> getDescendentEntryNames(FileProxy aParent) {
        String parentName = aParent.getAbsoluteName();
        Set<String> allChildren = getEntryNames();
        Set<String> retVal = new HashSet();
        for (String name : allChildren) {
            if (name.startsWith(parentName))
                retVal.add(name);
        }
        return retVal;
    }

    public FileProxy getFileEntryFromLocalName(String name) {
        return getFileEntry(getAbsoluteName() + "/" + name);
    }

    public boolean isDirectory() {
        return true;
    }

    protected void initChildrenRootData() {
        for (FileProxy entry : entries) {
            entry.initRootData();
            String entryName = entry.getLocalName();
            if (entryName == null) continue;
            int index = entryName.indexOf('/');
            if (index == -1)
                childrenNames.add(entry.getAbsoluteName());
        }
    }

    @Override
    public FileProxy getFileEntry(String name) {
        return nameToFileProxy.get(name.toLowerCase());
    }


}
