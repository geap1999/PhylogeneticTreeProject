package Algorithms;

import BuildTree.Sequence;
import java.util.ArrayList;

/**
 * Class that contains variables and methods for both UPGMA and NJ
 */

public class Method {
    protected int NumSeq;
    protected Sequence[] SequenceList;
    protected ArrayList<ArrayList<Double>> DistanceMatrix;

    public Method(String[] sequences) {
        NumSeq = sequences.length;
        DistanceMatrix = new ArrayList<>();
        SequenceList = new Sequence[NumSeq];
        for (int i = 0; i < NumSeq; i++) {
        	//create branch for each sequence
            SequenceList[i] = new Sequence(sequences[i], i);
            //create the distance matrix
        	DistanceMatrix.add(new ArrayList<Double>());
        }
	    // initialize the distance matrix
	    for (int i = 0; i < NumSeq; i++) {
	        for (int j = 0; j < NumSeq; j++) {
	        	String sequence1 = sequences[i].replaceAll("[\n]", ""); //take out newline caracters
	        	String sequence2 = sequences[j].replaceAll("[\n]", "");
	        	DistanceMatrix.get(i).add(countMismatch(sequence1, sequence2, i, j));
	        	}
	        if(DistanceMatrix.get(i).isEmpty()) {
	        	DistanceMatrix.remove(i);
	        }
	    }
	    for (int i = 0; i < DistanceMatrix.size(); i++) {
	        System.out.print(DistanceMatrix.get(i));
		    System.out.println();	    
	    }
	    System.out.println();
    }
    
    // Compare characters and count mismatches
    public double countMismatch(String seq1, String seq2, int index1, int index2) {
    	if (seq1.length() != seq2.length()) {
    		System.out.println("Sequence index " + index1 + " : ");
    		System.out.println(seq1);
    		System.out.println(seq1.length());
    		System.out.println("Sequence index " + index2 + " : ");
    		System.out.println(seq2);
    		System.out.println(seq2.length());
            throw new IllegalArgumentException("Sequences must have the same length.");
        }
    	double mismatch = 0;
    	for (int i = 0; i < seq1.length(); i++) {
            char AbaseSeq1 = seq1.charAt(i);
            char AbaseSeq2 = seq2.charAt(i);
            if (AbaseSeq1 != AbaseSeq2) {
                mismatch++;
            }
        }
    	return mismatch;
    }
    
    //finds the smallest value in a matrix aside from 0.0
    public int[] smallestMismatchPair(ArrayList<ArrayList<Double>> matrix) {
    	// Initialize with a large value
        double SmallestNonZero = Double.MAX_VALUE; 
        int[] SmallestPair = null;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = i; j < matrix.get(i).size(); j++) { 
            	double currentValue = matrix.get(i).get(j);
                if (currentValue < SmallestNonZero && currentValue !=0) {
                    // Found a new smallest value, update smallestNonZero and the smallestPair
                    SmallestNonZero = currentValue;
                    SmallestPair = new int[]{i, j};
                } else if (currentValue == SmallestNonZero) {
                    // Found a pair with equal value, continue to the next pair
                    continue;
                }
            }
        }      
        return SmallestPair;
    }
}