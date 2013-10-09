package grader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferFactory;
import de.danielbechler.diff.node.Node;
import de.danielbechler.diff.visitor.PrintingVisitor;

public class ObjectDifferTester {
	public static void main (String[] args) {		
		String[] stringsWorking = {"b1", "w1", "w2", "b4"};
		String[] stringsBase = {"b1", "b2", "b3", "b4"};
		List<String> listWorking = Arrays.asList(stringsWorking);
		List<String> listBase = Arrays.asList(stringsBase);			
		List nestedListWorking = new ArrayList();
		List nestedListBase = new ArrayList();
		nestedListWorking.add(listWorking);
		nestedListBase.add(listBase);		
		ObjectDiffer objectDiffer = ObjectDifferFactory.getInstance();		
		Node listRoot = objectDiffer.compare(listWorking, listBase);	
		listRoot.visit(new PrintingVisitor(listWorking, listBase));		
		Node nestedListRoot = objectDiffer.compare(nestedListWorking, nestedListBase);	
		nestedListRoot.visit(new PrintingVisitor(nestedListWorking, nestedListBase));			
	}

}
