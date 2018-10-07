package Compiler;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.io.File;

public class FileSelector {

    public File chooseOpeningFile(String WindowTitle, Window window, File initialDirectory, String extensionFilterName, String filetype) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(WindowTitle);
        fileChooser.setInitialDirectory(initialDirectory);

        fileChooser.getExtensionFilters().add(new ExtensionFilter(extensionFilterName, filetype));

        return fileChooser.showOpenDialog(window);
    }

    public File chooseSavingFile(String WindowTitle, Window window, File initialDirectory, String extensionFilterName, String filetype) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(WindowTitle);
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().add(new ExtensionFilter(extensionFilterName, filetype));
        return fileChooser.showSaveDialog(window);
    }

}
