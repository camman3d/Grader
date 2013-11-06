package grader.sakai.project;

import java.util.ArrayList;
import java.util.List;

public class AnUnsortedNavigationListCreator implements NavigationListCreator {

	@Override
	public List<String> getOnyenNavigationList(
			SakaiProjectDatabase aSakaiProjectDatabase) {
		 return new ArrayList(aSakaiProjectDatabase.getOnyens());
	}

}
