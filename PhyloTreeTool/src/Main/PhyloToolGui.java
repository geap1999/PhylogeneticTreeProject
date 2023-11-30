package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Algorithms.NJ;
import Algorithms.UPGMA;

/**
 * The main GUI that lets the users choose their fasta files, checks if there are enough arguments + if the sequences are all different and creates the main GUI and calls
 * each method depending on what button is clicked.
 * 
 */

public class PhyloToolGui {
	public PhyloToolGui() {
		//select fasta files
		SelectFiles.chooseFilesAndReadContents();
		ArrayList<String> Sequences = SelectFiles.getSequences();
		if (Sequences.size() < 2) {
			throw new IllegalArgumentException("There must be more than one sequence.");
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
    public static boolean areSequencesDifferent(ArrayList<String> sequences) {
        int numSequences = sequences.size();

        for (int i = 0; i < numSequences - 1; i++) {
            for (int j = i + 1; j < numSequences; j++) {
                if (sequences.get(i).equals(sequences.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}