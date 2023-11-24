package BuildTree;

public class Sequence {
    private String FullSeq;
    private int Number;
    private double Length;

    public Sequence(String Seq, int num) {
        FullSeq = Seq;
        Number = num;
    }

    public void addLength(double length) {
        Length = length; 
    }

    public String getSeq() {
        return FullSeq;
    }

    public int getNumber() {
        return Number;
    }

    public double getLength() {
        return Length;
    }
    
    public String toString() {
    	if(Length != 0) {
    		return "Sequence" + String.valueOf(Number + 1) + " (" + String.format("%.2f", Length) + ")"; // Convert Number to a String
    	}
    	else {
    		return "Sequence" + String.valueOf(Number + 1);
    	}
    }
}
