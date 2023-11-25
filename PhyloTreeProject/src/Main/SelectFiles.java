package Main;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SelectFiles {
	private static ArrayList<String> sequences;
	private static String[] sampleNames;
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
            sampleNames = new String[selectedFiles.length];
            int i = 0;
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
                    sampleNames[i] = extractFileNameWithoutExtension(file.getName());
                    i++;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private static String extractFileNameWithoutExtension(String fileNameWithExtension) {
        int dotIndex = fileNameWithExtension.lastIndexOf('.');
        if (dotIndex != -1) {
            return fileNameWithExtension.substring(0, dotIndex);
        } else {
            return fileNameWithExtension;
        }
    }

	public static ArrayList<String> getSequences() {
		return sequences;
	}

	public static void setSequences(ArrayList<String> sequences) {
		SelectFiles.sequences = sequences;
	}
	
	public static String[] getSampleNames() {
		return sampleNames;
	}

	public static void setSampleNames(String[] SN) {
		SelectFiles.sampleNames = SN;
	}
}
