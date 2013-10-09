package grader.file;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.misc.Common;

public abstract class AnAbstractFileProxy extends AnAbstractProxy implements FileProxy{
	protected RootFolderProxy rootFolderProxy;
	List<FileProxy> fileEntries;
//	Set<String> descendentNames;
	Set<FileProxy> childrenProxies;
	boolean isFolder;
//	Set<String>  childrenNames = new HashSet();
	public AnAbstractFileProxy(RootFolderProxy aRootFolderProxy) {
		rootFolderProxy = aRootFolderProxy;	
//		entryNames = rootFolderProxy.getChildrenEntryNames(this);
//		fileEntries = new ArrayList();
//		for (String entryName: entryNames) {
//			fileEntries.add(rootFolderProxy.getFileEntry(entryName));
//		}
		
		
	}
	
	@Override
	public boolean isDirectory() {
		return getChildrenNames().size() > 0;
	}
	
	public FileProxy getParentFolder() {
		String parentName = Common.getParentFileName(getAbsoluteName());
		return this.getFileEntry(parentName);
	}
	
	
	
	
	public void initRootData() {
		String myName = getAbsoluteName();
		int myDepth = Common.numOccurences(myName, '/');
		descendentNames = rootFolderProxy.getDescendentEntryNames(this);
		isFolder = descendentNames.size() > 0;
		fileEntries = new ArrayList();
		for (String entryName: descendentNames) {
			int childDepth = Common.numOccurences(entryName, '/');

			if (childDepth == myDepth + 1) {
				childrenNames.add(entryName);
			
			
//			String entryRelativeName = Common.toRelativeName(myName, entryName);
			
		   }
			fileEntries.add(rootFolderProxy.getFileEntry(entryName));
		}

			
		
	}
	
//	public boolean isDirectory() {
//		return isFolder;
//	}
	
	public List<FileProxy> getFileEntries() {
		return fileEntries;
	}
	public FileProxy getFileEntry(String name) {
		return rootFolderProxy.getFileEntry(name);
	}
	public FileProxy getFileEntryFromLocalName(String name) {
		return rootFolderProxy.getFileEntry(getAbsoluteName()+ "/" + name);
	}
	public Set<String> getEntryNames() {
		return descendentNames;
	}
//	public Set<String> getChildrenNames() {
//		return childrenNames;
//	}
	public Set<String> getDescendentEntryNames(FileProxy aParent) {
		return rootFolderProxy.getDescendentEntryNames(aParent);
	}
	@Override
	public List<FileProxy> getChildrenOf(String aParentName) {
		return rootFolderProxy.getChildrenOf(aParentName);
	}
	
	
	

}
