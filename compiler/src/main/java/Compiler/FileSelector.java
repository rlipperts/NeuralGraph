package Compiler;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.io.File;

public class FileSelector {

    public File chooseOpeningFile(String WindowTitle, Window window, File initialDirectory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(WindowTitle);
        fileChooser.setInitialDirectory(initialDirectory);
        /*
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new ExtensionFilter("All Files", "*.*"));
                */
        return fileChooser.showOpenDialog(window);
    }

    public File chooseSavingFile(String WindowTitle, Window window, File initialDirectory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(WindowTitle);
        fileChooser.setInitialDirectory(initialDirectory);
        return fileChooser.showSaveDialog(window);
    }

}
