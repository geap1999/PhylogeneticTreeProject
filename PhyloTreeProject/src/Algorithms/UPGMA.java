package Algorithms;

import BuildTree.TreeNode;
import java.util.ArrayList;

public class UPGMA extends Method {
	private static TreeNode[] Clusters;
	private String FinishedTree;
	
    public UPGMA(String[] sequences) {
    	super(sequences);
    	Clusters = new TreeNode[NumSeq]; //allows the same merges than the distance matrix with nodes and sequence branches
	    double[] clusterSize = new double[DistanceMatrix.size()];
	    for(int i = 0; i<clusterSize.length; i++) {
	    	clusterSize[i] = 1;
	    }
	  //loop and update of the distance matrix and tree
	    while(!isMatrixFinished(DistanceMatrix)) {
	    	int[] PairToMerge = smallestMismatchPair(DistanceMatrix);
	    	int index1 = PairToMerge[0];
	    	int index2 = PairToMerge[1];
	    	double NodeLength = returnTotalNodeLength(index1, index2, DistanceMatrix);
	    	DistanceMatrix = distanceMatrixUpdate(DistanceMatrix, index1, index2, clusterSize);
	    	MergeClusters(NodeLength, clusterSize, index1, index2);
	    	clusterSize[index1] = clusterSize[index1] + clusterSize[index2];
	    }
	    TreeNode CompleteTree = Clusters[findIndexOfClusterMaxTaxa(Clusters)];
	    ArrayList<Double> trueLengths = new ArrayList<>();
    	getTrueNodeLength(CompleteTree, trueLengths);
	    setTrueNodesLength(CompleteTree, trueLengths);
	    CompleteTree.setLength(0.0); //set root node to 0.0
	    //replace first node name with "root"
		String Tree = CompleteTree.toString();
	    String[] lines = Tree.split("\n");
	    lines[0] = lines[0].replaceFirst("^Node", "Root");
	    FinishedTree = String.join("\n", lines);
    }	      
    
    //get the total length of a node
    public double returnTotalNodeLength(int cluster1, int cluster2, ArrayList<ArrayList<Double>> Matrix) {
    	return Matrix.get(cluster1).get(cluster2)/2;
    }
    
    //update distance matrix
    public static ArrayList<ArrayList<Double>> distanceMatrixUpdate(ArrayList<ArrayList<Double>> Matrix, int cluster1, int cluster2, double[] clusterSize) {
        ArrayList<ArrayList<Double>> NewDistanceMatrix = new ArrayList<>();
        for (ArrayList<Double> row : Matrix) {
            ArrayList<Double> newRow = new ArrayList<>(row); 
            NewDistanceMatrix.add(newRow); 
        }
        for(int i = 0; i<NewDistanceMatrix.size(); i++) {
        	for(int j = 0; j<NewDistanceMatrix.get(i).size(); j++) {
        		if(j==cluster2) {
        			NewDistanceMatrix.get(i).set(j, 0.0);
        		}
        		if(i == cluster1 || i==cluster2) {
        			NewDistanceMatrix.get(i).set(j, 0.0);
        		}
        	}
        }
        for(int i = 0; i<Matrix.size(); i++) {
        	if(i != cluster1 && i != cluster2) {
	    		double DistanceMean = (Matrix.get(i).get(cluster1) * clusterSize[cluster1] + Matrix.get(i).get(cluster2) * clusterSize[cluster2]) / (clusterSize[cluster1] + clusterSize[cluster2]);
	    		NewDistanceMatrix.get(cluster1).set(i, DistanceMean);
        	}
        }
        for(int i = 0; i<NewDistanceMatrix.size(); i++) {
        	if(i != cluster1) {
        		NewDistanceMatrix.get(i).set(cluster1, NewDistanceMatrix.get(cluster1).get(i));
        	}
        }
		return NewDistanceMatrix;
    }
    
    //merge clusters with different situations, cluster size = 1 corresponds a sequence branch, otherwise it is a cluster
    public void MergeClusters(double Length, double[] clusterSize, int cluster1, int cluster2) {
		TreeNode NewNode = new TreeNode();
		NewNode.setLength(Length);
		if(clusterSize[cluster1] == 1.0 && clusterSize[cluster2] == 1.0) {
			NewNode.addSequence(SequenceList[cluster1]);
			SequenceList[cluster1].addLength(Length);
			NewNode.addSequence(SequenceList[cluster2]);
			SequenceList[cluster2].addLength(Length);
			Clusters[cluster1] = NewNode;
		}
		else if(clusterSize[cluster1] > 1.0 && clusterSize[cluster2] == 1.0) {
			NewNode.addSequence(SequenceList[cluster2]);
			SequenceList[cluster2].addLength(Length);
			NewNode.addChildNode(Clusters[cluster1]);
			Clusters[cluster1] = NewNode;
		}
		else if(clusterSize[cluster1] == 1.0 && clusterSize[cluster2] > 1.0) {
			NewNode.addSequence(SequenceList[cluster1]);
			SequenceList[cluster1].addLength(Length);
			NewNode.addChildNode(Clusters[cluster2]);
			Clusters[cluster2] = NewNode;
		}
		else {
			NewNode.addChildNode(Clusters[cluster1]);
			NewNode.addChildNode(Clusters[cluster2]);
			Clusters[cluster1] = NewNode;
		}
	}
    
    //get the node branch lengths 
    public void getTrueNodeLength(TreeNode tree, ArrayList<Double> trueLengths) {
    	for(int i = 0; i<tree.getChildNodes().size(); i++) {
    		double trueLength = tree.getLength() - tree.getChildNodes().get(i).getLength();
    		trueLengths.add(trueLength);
    		getTrueNodeLength(tree.getChildNodes().get(i), trueLengths);
    	}
    }
    
    //change the node lengths with the branch lengths and not total length
    public void setTrueNodesLength(TreeNode tree, ArrayList<Double> trueLengths) {
    	for(TreeNode node : tree.getChildNodes()) {
    		node.setLength(trueLengths.get(0));
    		trueLengths.remove(0);
    		setTrueNodesLength(node, trueLengths);
    	}
    	
    }
    
    //find the completed tree
    public static int findIndexOfClusterMaxTaxa(TreeNode[]  Clusters) {
        int maxNodeCount = -1;
        int maxNodeClusterIndex = -1;

        // Iterate through the clusters to find the one with the most nodes
        for (int i = 0; i < Clusters.length; i++) {
        	if(Clusters[i] != null) {
	            double nodeCount = Clusters[i].getNumberOfNodes();
	
	            if (nodeCount > maxNodeCount) {
	                maxNodeCount = (int) nodeCount;
	                maxNodeClusterIndex = i;
	            }
        	}
        }
        return maxNodeClusterIndex;
    }
    
    public static boolean isMatrixFinished(ArrayList<ArrayList<Double>> matrix) {
        for (ArrayList<Double> row : matrix) {
            for (Double value : row) {
                if (value != 0.0) {
                    return false; 
                }
            }
        }
        return true; 
    }
    
    @Override
    public String toString() {
    	return FinishedTree;
    }
}