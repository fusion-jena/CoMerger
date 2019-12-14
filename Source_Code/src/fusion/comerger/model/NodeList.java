package fusion.comerger.model;

import java.util.ArrayList;
import java.util.Iterator;


public class NodeList {

	 private ArrayList<Node> list = null;

	    public NodeList()
	    {
	        list = new ArrayList<Node>();
	    }

	    public NodeList(int capacity)
	    {
	        list = new ArrayList<Node>(capacity);
	    }

	    public int size()
	    {
	        return list.size();
	    }

	    public Node get(int i)
	    {
	        return list.get(i);
	    }

	    public void set(int index, Node node)
	    {
	        list.set(index, node);
	    }

	    public void add(Node node)
	    {
	        list.add(node);
	    }

	    public boolean contains(Node node)
	    {
	        return list.contains(node);
	    }

	    public int indexOf(Node node)
	    {
	        return list.indexOf(node);
	    }

	    public Node remove(int i)
	    {
	        return list.remove(i);
	    }

	    public boolean remove(Node node)
	    {
	        return list.remove(node);
	    }

	    public Iterator<Node> iterator()
	    {
	        return list.iterator();
	    }

	    public ArrayList<Node> getList()
	    {
	        return list;
	    }
}
