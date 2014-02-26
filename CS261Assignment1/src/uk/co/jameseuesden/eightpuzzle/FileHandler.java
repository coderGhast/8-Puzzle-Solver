package uk.co.jameseuesden.eightpuzzle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * <h2>FileHandler</h2>
 * <p>
 * Reads in Files based on passed String filenames and
 * returns the data from within the file.
 * </p>
 * 
 * @author James Euesden - jee22@aber.ac.uk
 * @version 1.1
 */
public class FileHandler {

    private BufferedReader buffReader;
    private StringBuffer sb;

    
    public File transmogrify(String fileName){
        return new File(fileName);
    }
    
    /**
     * <p>
     * Allows the creating of a new File object based on a file's name, assuming
     * it's location is local to the application source files.
     * </p>
     * 
     * @param fileName
     *            Takes the file name of the File to be made
     * @return returns the reference to the File.
     */
    public File newFile(String fileName) {
        return new File(fileName);
    }
    
    /**
     * <p>
     * Extracts the content of a file, returning
     * if as a String.
     * </p>
     * 
     * @param file
     * @return content of the file as a String
     */
    public String readFile(File file) {
        // Make a new null String to represent the elements of the 8 puzzle
        String grid = null;
        try {
            // New FileReader and BufferedReader to read the file contents.
            buffReader = new BufferedReader(new FileReader(file));
            sb = new StringBuffer();
            for(int i=0; i<5; i++){
                // Read the lines of the file.
                sb.append(buffReader.readLine());
            }
            // Safely close the file reader.
            buffReader.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(sb != null){
            // If the file is not null, remove any Commas (,).
            grid = removeCommas(sb.toString());
        }
        
        return grid;
    }
    
    /**
     * <p>Replace commas with null character
     * </p>
     * @param input String passed
     * @return String without commas.
     */
    public String removeCommas(String input){
        return input.replace(",","");
    }

}