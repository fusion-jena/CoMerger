
package fusion.comerger.general.visualization;

/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.gui.HierarchyWindows;

public class HierarchyCalculation {
	public static JTree tree;
    	public static void ShowTreeCalculating(OntModel ont){
    		    
    		    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Thing");
    		    createNodes(top, ont);

    		    tree = new JTree(top);
    		    
//    		    HierarchyFrame.hierScrollPane.setViewportView(tree);
    		    HierarchyWindows.hierScrollPane.setViewportView(tree);

    		  //Responding to Node Selection

    		  //Where the tree is initialized:
    		      tree.getSelectionModel().setSelectionMode
    		              (TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);



    		}

    

private static void createNodes(DefaultMutableTreeNode top, OntModel ont) {
    DefaultMutableTreeNode parent = null;
    DefaultMutableTreeNode child = null;

    List<OntClass> list1=new ArrayList<OntClass>();
//	list1=BuildModel.OntModel.listNamedClasses().toList();// listClasses().toList();
	list1=ont.listNamedClasses().toList();// listClasses().toList();
	for(int i=0;i<list1.size();i++)
	   {
	    	OntClass cls= list1.get(i);   
	    	String s;
	    	if(cls.isClass()){
	    		if (cls.getLabel(null) == null){
	    			s = cls.getLocalName();
	    		} else{
	    			s = cls.getLabel(null);
	    		}
	    	
//	    		parent = new DefaultMutableTreeNode(cls.getLocalName().toString());
	    		parent = new DefaultMutableTreeNode(s);
	    		top.add(parent);
//	    	ExtendedIterator it=cls.listSubClasses();
	    	ExtendedIterator<OntClass> it=cls.listSubClasses();
	    	while(it.hasNext())
	    	{
		    	OntClass chi= it.next();
	    		if(chi.isClass()){
		    		if (chi.getLabel(null) == null){
		    			s = chi.getLocalName();
		    		} else{
		    			s = chi.getLabel(null);
		    		}
	    		}
	    		
//	    		child= new DefaultMutableTreeNode(it.next().getLocalName().toString());
	    		child= new DefaultMutableTreeNode(s);
	    	    parent.add(child);
//	    		it.next();
	    	}
	    
	    	}	
	    }
}

//Responding to Node Selection
/*
public void valueChanged(TreeSelectionEvent e) {
//Returns the last path element of the selection.
//This method is useful only when the selection model allows a single selection.
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                       tree.getLastSelectedPathComponent();

    if (node == null)
    //Nothing is selected.     
    return;

    Object nodeInfo = node.getUserObject();
    if (node.isLeaf()) {
        BookInfo book = (BookInfo)nodeInfo;
        displayURL(book.bookURL);
    } else {
        displayURL(helpURL); 
    }
}
*/

}