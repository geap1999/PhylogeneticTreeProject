package Main;
import Algorithms.UPGMA;
import Algorithms.NJ;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The main program that checks if there are enough arguments, if the sequences are all different, that creates the main GUI and calls
 * each method depending on what button is clicked.
 * 
 */

public class Project {
	public static void main(String[] args) {
		String[] Sequences = MultipleFilesArgs.readFileContents(args);
		//see if enough arguments
		if (Sequences.length < 2) {
            System.out.println("There needs to be more than one sequence.");
            return;
        }
        if(!areSequencesDifferent(Sequences)) {
        	throw new IllegalArgumentException("Sequences must all be different");
        }
        //create main GUI
        JFrame PhyloGui = new JFrame("Phylogenic tree");
        PhyloGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PhyloGui.setSize(1500, 800);
        JLabel Text = new JLabel("<html><body><br><br><br>Hello, which algorithm would you like to use ?<br><br><br><br></body></html>");
        Text.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = new Font("Times New Roman", Font.BOLD, 28); 
        Text.setFont(font);
        // Create buttons
        JButton button1 = new JButton("UPGMA");
        JButton button2 = new JButton("NJ");
        Dimension buttonSize = new Dimension(200, 100); 
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        Font buttonFont = new Font("Times New Roman", Font.BOLD, 26);
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        JPanel CreateButtons = new JPanel();
        CreateButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
        CreateButtons.add(button1);
        CreateButtons.add(button2);
        // Button action UPGMA and NJ
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {           	
            	UPGMA upgma = new UPGMA(Sequences);
            	MethodGUI upgmaWindow = new MethodGUI(upgma.toString(), "UPGMA");
            	upgmaWindow.setVisible(true);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	NJ nj = new NJ(Sequences);
            	MethodGUI njWindow = new MethodGUI(nj.toString(), "NJ");
            	njWindow.setVisible(true);
            }
        });
        // Rearrange text & buttons
        PhyloGui.add(Text, BorderLayout.NORTH);
        PhyloGui.add(CreateButtons, BorderLayout.CENTER);
        // Make GUI appear
        PhyloGui.setVisible(true);
    }
	//see if sequences are all different 
    public static boolean areSequencesDifferent(String[] sequences) {
        int numSequences = sequences.length;

        for (int i = 0; i < numSequences - 1; i++) {
            for (int j = i + 1; j < numSequences; j++) {
                if (sequences[i].equals(sequences[j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
