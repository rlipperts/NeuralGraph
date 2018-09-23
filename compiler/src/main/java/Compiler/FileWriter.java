package Compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class FileWriter {

    private ArrayList<String> lines;
    private char separator = '\n';

    public FileWriter() {
        lines = new ArrayList<>();
    }

    /**
     * Adds a line to the text which is to be written
     *
     * @param line
     */
    public void append(String line) {
        lines.add(line);
    }

    /**
     * Adds all lines to the text that is to be written
     *
     * @param addLines
     */
    public void appendAll(Collection<String> addLines) {
        lines.addAll(addLines);
    }

    /**
     * Sets the line separator; default is a newline character
     *
     * @param newSeparator separator that separates the lines in the written file
     */
    public void setSeparator(char newSeparator) {
        separator = newSeparator;
    }

    /**
     * Writes the built String to a pythonFile in given Direction
     *
     * @param destination path to save to
     */
    public void writeToFile(String destination) {

        BufferedWriter bw = null;

        try {

            File file = new File(destination);
            bw = new BufferedWriter(new java.io.FileWriter(file));

            for (String line : lines) {
                bw.write(line + separator);
            }
            System.out.println("File created in " + destination.toString());
        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            try {

                if (bw != null)
                    bw.close();
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }
}
