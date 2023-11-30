package BuildTree;

public class Sequence {
    private String FullSeq;
    private String Name;
    private double Length;

    public Sequence(String Seq, String name) {
        FullSeq = Seq;
        Name = name;
    }

    public void addLength(double length) {
        Length = length; 
    }

    public String getSeq() {
        return FullSeq;
    }

    public String getNamer() {
        return Name;
    }

    public double getLength() {
        return Length;
    }
    
    public String toString() {
    	if(Length != 0) {
    		return Name + " (" + String.format("%.2f", Length) + ")"; // Convert Number to a String
    	}
    	else {
    		return Name;
    	}
    }
}
