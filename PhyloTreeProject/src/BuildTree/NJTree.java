package BuildTree;

/**
 * See NJ javadoc
 */

public class NJTree {
	private TreeNode mainNode = new TreeNode();
	private TreeNode[] Nodes;
	private Sequence[] Sequences;
	
	public NJTree(Sequence[] list){
		for(Sequence seq : list) {
			mainNode.addSequence(seq);
		}
		Sequences = list;
		Nodes = new TreeNode[Sequences.length];
	}
	
	//merge sequences and remove unmerged sequences
	public void merge(Sequence S1, Sequence S2, int index1) {
		TreeNode node = new TreeNode();
		node.addSequence(S1);
		node.addSequence(S2);
		mainNode.addChildNode(node);
		Nodes[index1] = node;
		mainNode.removeSeq(S1);
		mainNode.removeSeq(S2);
	}
	
	//merge node with sequence branch and remove unmerged node and sequence branch
	public void merge(TreeNode node, Sequence S1, int index1, double length) {
		TreeNode NewNode = new TreeNode();
		NewNode.addSequence(S1);
		NewNode.addChildNode(node);
		node.setLength(length);
		Nodes[index1]=NewNode;
		mainNode.addChildNode(NewNode);
		mainNode.removeSeq(S1);
		mainNode.removeNode(node);
	}
	
	//merge nodes and remove unmerged nodes
	public void merge(TreeNode node1, TreeNode node2, int index1) { 
		TreeNode NewNode = new TreeNode();
		NewNode.addChildNode(node1);
		NewNode.addChildNode(node2);
		Nodes[index1]=NewNode;
		mainNode.addChildNode(NewNode);
		mainNode.removeNode(node1);
		mainNode.removeNode(node2);
	}
	
	public TreeNode[] getNodes() {
		return Nodes;
	}
	
	public Sequence[] getSequences() {
		return Sequences;
	}
	
	public TreeNode getMainNode() {
		return mainNode;
	}
	
	@Override
	public String toString() {
		return mainNode.toString();
	}
}
