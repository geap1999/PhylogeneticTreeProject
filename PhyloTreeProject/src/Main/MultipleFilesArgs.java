/*package Main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class MultipleFilesArgs {
	public static String[] readFileContents(String... filePaths) {
		String[] fileContents = new String[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            String filePath = filePaths[i];
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean firstLineSkipped = false;
                while ((line = reader.readLine()) != null) {
                    if (!firstLineSkipped) {
                        firstLineSkipped = true;
                        continue; // Skip the first line
                    }
                    content.append(line).append("\n");
                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            fileContents[i] = content.toString();
        }
        return fileContents;
	}
}*/