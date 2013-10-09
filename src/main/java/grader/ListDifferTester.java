package grader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.misc.Common;
import bus.uigen.ObjectEditor;
import bus.uigen.diff.ToChangeMapVisitor;
import bus.uigen.jung.ALogicalStructureDisplayer;
import bus.uigen.oadapters.ObjectAdapter;
import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferFactory;
import de.danielbechler.diff.node.Node;
import de.danielbechler.diff.path.PropertyPath;
import de.danielbechler.diff.visitor.PrintingVisitor;
import de.danielbechler.diff.visitor.ToMapPrintingVisitor;
import de.danielbechler.merge.ObjectMerger;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;

public class ListDifferTester {
	public static void main (String[] args) {
		String s1 = "hello";
		String s2 =  "hell";
		String[] strings1 = {"hello", "world1", "world2", "sansar"};
//		String[] strings2 = {"hello", "duniya", "sansar"};
		String[] strings2 = {"hello", "sansar", "comos1", "cosmos2"};

		List<String> listWorking = Common.arrayToArrayList(strings1);
		List<String> listBase = Common.arrayToArrayList(strings2);
		
		List parentListWorking = new ArrayList();
		List parentListBase = new ArrayList();
		parentListWorking.add(listWorking);
		parentListBase.add(listBase);
		
		Map aMapWorking = new LinkedHashMap();
		Map aMapBase = new LinkedHashMap();
		aMapWorking.put("key1", "value1");
		aMapWorking.put("key2", "value2");
		aMapWorking.put("key3", "value3");
		aMapWorking.put("key4", listWorking);
		aMapBase.put("key1", "value3");
		aMapBase.put("key2", "value2" );
		aMapBase.put("key4", listBase);
		aMapBase.put("key5", "value5");
		
		
				
		
		ObjectDiffer objectDiffer = ObjectDifferFactory.getInstance();
		ObjectMerger objectMerger = new ObjectMerger();
//		final Node root = objectDiffer.compare(s1, s2);	
//		 ToChangeMapVisitor changeMapVisitor0 = new ToChangeMapVisitor(s1, s2);
//			root.visit(changeMapVisitor0);
//
//		 Map changeMap0 = changeMapVisitor0.getChangeMap();
//
//		 Collection changes0 = changeMap0.values();
//		 Object change0 = changes0.iterator().next();
//		 ObjectEditor.edit(change0);

//		root.visit(new PrintingVisitor(s1, s2));
//		Node root2 = objectDiffer.compare(strings1, strings2);		
//		root2.visit(new PrintingVisitor(strings1, strings2))		;
		Node root3 = objectDiffer.compare(listWorking, listBase);	
		root3.visit(new PrintingVisitor(listWorking, listBase));
		List mergedList = (List) Common.deepCopy(listBase);
		objectMerger.merge(listWorking, listBase, mergedList);
		System.out.println(mergedList);
		List parentListMerged = (List) Common.deepCopy(parentListBase);
		System.out.println(parentListMerged);
		objectMerger.merge(parentListWorking, parentListBase, parentListMerged);
		System.out.println(parentListMerged);
		
//		root3.visit(new CollectionToListPathVisitor(list1, list2));
		
		Node rootParentList = objectDiffer.compare(parentListWorking, parentListBase);	
		rootParentList.visit(new PrintingVisitor(parentListWorking, parentListBase));
//		rootParentList.visit(new CollectionToListPathVisitor(parentListWorking, parentListBase));
		
		
		
		
//		Node root4 = objectDiffer.compare(aMap1, aMap2);	
//		root4.visit(new PrintingVisitor(aMap1, aMap2));
//		
				
		ObjectAdapter adapter1 = ObjectEditor.toObjectAdapter(aMapWorking);
		ObjectAdapter adapter2 = ObjectEditor.toObjectAdapter(aMapBase);
		Object canonical1 = adapter1.toCanonicalForm();
		Object canonical2 = adapter2.toCanonicalForm();
		Node root5 = objectDiffer.compare(canonical1, canonical2);	

//		Node root5 = objectDiffer.compare(adapter1, adapter2);	
//		root5.visit(new PrintingVisitor(list1Adapter, list2Adapter));
//		ToMapPrintingVisitor mapPrintingVisitor = new ToMapPrintingVisitor(adapter1, adapter2);
		ToMapPrintingVisitor mapPrintingVisitor = new ToMapPrintingVisitor(canonical1, canonical2);
		PrintingVisitor printingVisitor =  new PrintingVisitor(canonical1, canonical2);
		root5.visit(printingVisitor);

		 root5.visit(mapPrintingVisitor);
		 ToChangeMapVisitor changeMapVisitor = new ToChangeMapVisitor(adapter1, adapter2);
		 root5.visit(changeMapVisitor);
		 Map changeMap = changeMapVisitor.getChangeMap();
//		 Map changeMap = mapPrintingVisitor.getMessages();
		 ObjectEditor.ignoreComponentsOfClass(PropertyPath.class);
		 ObjectEditor.ignoreOperationsOfClass(PropertyPath.class);
		 Collection changes = changeMap.values();
//		 Object change = changes.iterator().next();
//		 ObjectEditor.edit(change0);
		 
//		 ObjectEditor.edit(changeMap);
		
		 	JFrame frame = new JFrame();
		 	JPanel panel = new JPanel();
			
//			ALogicalStructureDisplayer.createLogicalStructureDisplay(aMapWorking, frame, panel);
//			ALogicalStructureDisplayer.createLogicalStructureDisplay(aMapBase, frame, panel);
			ALogicalStructureDisplayer.createLogicalStructureDisplay(new Object[] {aMapBase, aMapBase}, frame, panel);

			frame.pack();
			frame.setVisible (true);
			
			
			
//			ALogicalStructureDisplayer.createLogicalStructureDisplay(changeMap, new JFrame());
//			ObjectEditor.edit(aMap1);
//			Object root = new ACompositeExampleWithBackLink();
		
//		ObjectEditor.edit(aMap1);
		
//		ObjectEditor.edit(list1);
			Patch patch = DiffUtils.diff(listWorking, listBase);
			for (Delta delta: patch.getDeltas()) {
                System.out.println(delta);
        }
			try {
				List newVal = patch.applyTo(listWorking);
				System.out.println(newVal);
			} catch (PatchFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}

}
