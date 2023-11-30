package BuildTree;

import java.util.ArrayList;
import java.util.Iterator;

public class TreeNode {
	private int NumberOfNodes = 0;
	private ArrayList<TreeNode> ChildNodes;
	private ArrayList<Sequence> Sequences;
	private double Length;
	
	public TreeNode() {
		NumberOfNodes++;
		ChildNodes = new ArrayList<>();
		Sequences = new ArrayList<>();
	}
	
	public void addChildNode(TreeNode Node) {
		ChildNodes.add(Node);
		NumberOfNodes += Node.getNumberOfNodes(); 
	}
	
	public void addSequence(Sequence Seq) {
		Sequences.add(Seq);
	}
	
	public void setLength(double length) {
		Length = length;
	}
	
	public TreeNode getChildNode(int index) {
		return ChildNodes.get(index);
	}
	
	public ArrayList<TreeNode> getChildNodes() {
		return ChildNodes;
	}
	
	public Sequence getSequence(int index) {
		return Sequences.get(index);
	}
	
	public double getLength() {
		return Length;
	}
	
	public int getNumberOfNodes() {
		return NumberOfNodes;
	}
	
	//remove specific sequence from node
	public void removeSeq(Sequence Seq) {
	    Iterator<Sequence> iterator = Sequences.iterator();
	    while (iterator.hasNext()) {
	        Sequence sequence = iterator.next();
	        if (sequence == Seq) {
	            iterator.remove();
	        }
	    }
	}
	
	//remove specific childnode from node
	public void removeNode(TreeNode N) {
	    Iterator<TreeNode> iterator = ChildNodes.iterator();
	    while (iterator.hasNext()) {
	        TreeNode Node = iterator.next();
	        if (Node == N) {
	            iterator.remove();
	        }
	    }
	    NumberOfNodes -= N.getNumberOfNodes();
	}
	
	@Override
	public String toString() {
		StringBuilder tree = new StringBuilder();
		tree.append("Node" + " " + "(" + String.format("%.2f", Length) + ")\n"); //keep only 2 decimals for length
		
		for (Sequence sequence : Sequences) {
		    tree.append("    ").append(sequence).append("\n");
		}
		
		for (TreeNode child : ChildNodes) {
		    String childString = child.toString().replaceAll("(?m)^", "    "); //keep indentation for every childnodes
			tree.append(childString);
		}
		return tree.toString();
	}
}