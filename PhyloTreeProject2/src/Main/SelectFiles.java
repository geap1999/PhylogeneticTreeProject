package Main;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SelectFiles {
	public static ArrayList<String> sequences;
    static void createAndShowGUI() {
	    JFrame frame = new JFrame("File Chooser Example");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    JButton openButton = new JButton("Open Files");
	    openButton.addActionListener(e -> {
	        chooseFilesAndReadContents();
	    });
	    frame.getContentPane().add(openButton);
	    frame.pack();
	    frame.setVisible(true);
 	}

    public static void chooseFilesAndReadContents() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            setSequences(new ArrayList<>());

            for (File file : selectedFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                	// Skip the first line
                    reader.readLine();
                    StringBuilder content = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    getSequences().add(content.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

	public static ArrayList<String> getSequences() {
		return sequences;
	}

	public static void setSequences(ArrayList<String> sequences) {
		SelectFiles.sequences = sequences;
	}
}
