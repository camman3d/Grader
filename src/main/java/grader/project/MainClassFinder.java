package grader.project;

import grader.project.file.RootCodeFolder;

public interface MainClassFinder {
	public Class mainClass(RootCodeFolder aRootCodeFolder, ProxyClassLoader aProxyClassLoader, String expectedName);


}
