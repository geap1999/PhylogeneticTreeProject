package Algorithms;

import BuildTree.NJTree;
import java.util.ArrayList;

public class NJ extends Method {
	private String FinishedTree;
	
    public NJ(String[] sequences) {
    	super(sequences);
    	int maxNodes = SequenceList.length - 2; //to not create any more nodes by the end of the algorithm
    	//create tree
    	NJTree tree = new NJTree(SequenceList);
    	//get cluster size for each clusters, to know if lone branch or cluster
    	double[] clusterSize = new double[DistanceMatrix.size()];
	    for(int i = 0; i<clusterSize.length; i++) {
	    	clusterSize[i] = 1;
	    }
	    double numSeq = DistanceMatrix.size();
	    double[] Sums = new double[DistanceMatrix.size()];
	    //loop and update of the distance matrix and tree
    	while(!isMatrixFinished(DistanceMatrix)) { 
    		for (int i = 0; i<DistanceMatrix.size(); i++) {
        		double SumOfSeq = 0;
        		for (int j = 0; j<DistanceMatrix.get(i).size(); j++) {
        			SumOfSeq = SumOfSeq + DistanceMatrix.get(i).get(j);
        		}
        		Sums[i] = SumOfSeq;
        	}
		    ArrayList<ArrayList<Double>> Qmatrix = new ArrayList<>();
	        for (ArrayList<Double> row : DistanceMatrix) {
	            ArrayList<Double> newRow = new ArrayList<>(row); 
	            Qmatrix.add(newRow); 
	        }
	        Qmatrix = calcQmatrix(numSeq, Qmatrix, Sums);
		    int[] PairToMerge = smallestMismatchPair(Qmatrix);
	    	int index1 = PairToMerge[0];
	    	int index2 = PairToMerge[1];
	    	double[] lengths = branchLengths(numSeq, DistanceMatrix, index1, index2, Sums, clusterSize);
	    	DistanceMatrix = distanceMatrixUpdate(DistanceMatrix, index1, index2);
	    	MergeClusters(clusterSize, index1, index2, lengths, tree, maxNodes);
	    	clusterSize[index1] = clusterSize[index1] + clusterSize[index2];
    	}
    	tree.getMainNode().setLength(0.0);
    	FinishedTree = tree.getMainNode().toString();
    }	      
    
    //make Qmatrix 
    public static ArrayList<ArrayList<Double>> calcQmatrix(double numSeq, ArrayList<ArrayList<Double>> Qmatrix, double[] Sums){
    	for (int i = 0; i<Qmatrix.size(); i++) {
    		for (int j = 0; j<Qmatrix.get(i).size(); j++) {
    			if(Qmatrix.get(i).get(j) == 0.0){
    				continue;
    			}
    			else{
    				double newDistance = (numSeq - 2) * Qmatrix.get(i).get(j) - Sums[i] - Sums[j];
    				Qmatrix.get(i).set(j, newDistance);
    			}
    		}
    	}
    	return Qmatrix;
    }
    
    //clusters lengths calculus
    public double[] branchLengths(double numSeq, ArrayList<ArrayList<Double>> Matrix, int cluster1, int cluster2, double[] Sums, double[] clusterSize){
    	double[] lengths = null;
    	double lengthC1 = (Matrix.get(cluster1).get(cluster2)/2) + (Sums[cluster1] - Sums[cluster2])/(2*(numSeq - 2));
    	double lengthC2 = Matrix.get(cluster1).get(cluster2) - lengthC1;
		lengths = new double[]{lengthC1, lengthC2};
    	return lengths;
    }
    
    //distance matrix update
    public static ArrayList<ArrayList<Double>> distanceMatrixUpdate(ArrayList<ArrayList<Double>> Matrix, int cluster1, int cluster2) {
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
	    		double DistanceMean = (Matrix.get(cluster1).get(i) + Matrix.get(cluster2).get(i) - Matrix.get(cluster1).get(cluster2))/ 2.0;
	    		if(Matrix.get(cluster1).get(i) == 0.0 && Matrix.get(cluster2).get(i) == 0.0) {
	    			DistanceMean = 0.0;
	    		}
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
    public void MergeClusters(double[] clusterSize, int cluster1, int cluster2, double[] branchLengths, NJTree tree, int maxNodes) {
    	if(clusterSize[cluster1] == 1.0 && clusterSize[cluster2] == 1.0) {
    		if(tree.getMainNode().getNumberOfNodes() < maxNodes) {
    			tree.merge(SequenceList[cluster1], SequenceList[cluster2], cluster1);
    			SequenceList[cluster1].addLength(branchLengths[0]);
    			SequenceList[cluster2].addLength(branchLengths[1]);
    		}
		}
		else if(clusterSize[cluster1] > 1.0 && clusterSize[cluster2] == 1.0) {
			if(tree.getMainNode().getNumberOfNodes() < maxNodes) {
				tree.merge(tree.getNodes()[cluster1], SequenceList[cluster2], cluster1, branchLengths[0]);
			}		
			else {
				if(tree.getMainNode().getLength() == 0.0) {
					tree.getMainNode().setLength(branchLengths[0]);
				}
			}
			SequenceList[cluster2].addLength(branchLengths[1]);
		}
		else if(clusterSize[cluster1] == 1.0 && clusterSize[cluster2] > 1.0) {
			if(tree.getMainNode().getNumberOfNodes() < maxNodes) {
				tree.merge(tree.getNodes()[cluster2], SequenceList[cluster1], cluster1, branchLengths[1]);
			}
			else {
				if(tree.getMainNode().getLength() == 0.0) {
					tree.getMainNode().setLength(branchLengths[1]);
				}
			}
			SequenceList[cluster1].addLength(branchLengths[0]);
		}
		else {
			if(tree.getMainNode().getNumberOfNodes() < maxNodes) {
				tree.merge(tree.getNodes()[cluster1], tree.getNodes()[cluster2], cluster1);
			}
			else {
				tree.getNodes()[cluster1].setLength(branchLengths[0]);
				tree.getNodes()[cluster2].setLength(branchLengths[1]);
			}
		}
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

