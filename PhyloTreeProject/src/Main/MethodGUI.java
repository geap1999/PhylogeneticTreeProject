package Main;

import javax.swing.*;
import java.awt.*;

/**
 * GUi for UPGMA of NJ. Both appear when their designated button is clicked on the main GUI
 */

public class MethodGUI extends JFrame {
    public MethodGUI(String Tree, String method) {
    	JTextArea textArea = new JTextArea(Tree);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 24)); // Use a monospaced font for proper alignment
        JFrame frame;
        if(method.equals("UPGMA")) {
        	frame = new JFrame("UPGMA phylogenic tree");
        }
        else {
        	frame = new JFrame("NJ phylogenic tree");
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(textArea));
        frame.setSize(1000, 900);
        frame.setVisible(true);
    }
}