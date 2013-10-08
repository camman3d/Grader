package grader.file;

import java.util.*;

public  abstract class AnAbstractRootFolderProxy extends AnAbstractProxy implements RootFolderProxy {
	protected Map<String, FileProxy> nameToFileProxy = new HashMap();
	protected List<FileProxy> entries = new ArrayList();
//	Set<String> descendentNames;
//	Set<String> childEntryNames;

	protected void add(FileProxy aFileProxy) {
		entries.add(aFileProxy);
		nameToFileProxy.put(aFileProxy.getAbsoluteName(), aFileProxy);
	}
	@Override
	public List<FileProxy> getFileEntries() {
		return entries;
	}
	@Override
	public Set<String> getEntryNames() {
		return nameToFileProxy.keySet();
	}
	
	@Override
	public Set<String> getDescendentEntryNames(FileProxy aParent) {
//		if (descendentEntryNames == null) {
		
		String parentName = aParent.getAbsoluteName();
		Set<String> allChildren = getEntryNames();
		Set<String> retVal = new HashSet();
		for (String name:allChildren) {
			if (name.startsWith(parentName))
				retVal.add(name);
		}
		return retVal;		 
		
	}
	
	public FileProxy getFileEntryFromLocalName(String name) {
		return getFileEntry(getAbsoluteName()+ "/" + name);
	}
	
	public boolean isDirectory() {
		return true;
	}
	
	protected void initChildrenRootData() {
		for (FileProxy entry:entries) {
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
		return nameToFileProxy.get(name);
	}
	
	
	

}
