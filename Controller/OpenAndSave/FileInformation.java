// 
// Decompiled by Procyon v0.5.36
// 

package Controller.OpenAndSave;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import Model.Settings.StyleMap;
import javafx.scene.control.Alert;
import java.util.List;
import javafx.stage.Window;
import java.io.File;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

public class FileInformation
{
    private static FileChooser fileChooser;
    private static FileChooser fileMXLChooser;
    public static Stage stage;
    private static File prevDirectory;
    private static final String TXT_EXTENSION_DESCRIPTION = "Text Files";
    private static final String TXT_EXTENSION = "*.txt";
    private static final String TXT = ".txt";
    
    public static File openFile(final String title) {
        FileInformation.fileChooser.setTitle(title);
        if (FileInformation.prevDirectory != null && FileInformation.prevDirectory.exists()) {
            FileInformation.fileChooser.setInitialDirectory(FileInformation.prevDirectory);
        }
        if (FileInformation.fileChooser.getExtensionFilters().size() == 0) {
            FileInformation.fileChooser.getExtensionFilters().addAll((Object[])new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("Text Files", new String[] { "*.txt" }) });
        }
        final File file = getFileChooser().showOpenDialog((Window)FileInformation.stage);
        if (file != null) {
            FileInformation.prevDirectory = file.getParentFile();
            return file;
        }
        return null;
    }
    
    public static File openMXLFile(final String title) {
        FileInformation.fileMXLChooser.setTitle(title);
        if (FileInformation.prevDirectory != null && FileInformation.prevDirectory.exists()) {
            FileInformation.fileMXLChooser.setInitialDirectory(FileInformation.prevDirectory);
        }
        if (FileInformation.fileMXLChooser.getExtensionFilters().size() == 0) {
            FileInformation.fileMXLChooser.getExtensionFilters().addAll((Object[])new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("Music XML files", new String[] { "*.mxl" }) });
        }
        final File file = FileInformation.fileMXLChooser.showOpenDialog((Window)FileInformation.stage);
        if (file != null) {
            FileInformation.prevDirectory = file.getParentFile();
            return file;
        }
        return null;
    }
    
    public static List<File> getMultipleFiles(final String title, final Stage stage) {
        FileInformation.fileChooser.setTitle(title);
        if (FileInformation.prevDirectory != null && FileInformation.prevDirectory.exists()) {
            FileInformation.fileChooser.setInitialDirectory(FileInformation.prevDirectory);
        }
        if (FileInformation.fileChooser.getExtensionFilters().size() == 0) {
            FileInformation.fileChooser.getExtensionFilters().addAll((Object[])new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("Text Files", new String[] { "*.txt" }) });
        }
        final List<File> file = (List<File>)getFileChooser().showOpenMultipleDialog((Window)stage);
        if (file != null) {
            FileInformation.prevDirectory = file.get(0).getParentFile();
            return file;
        }
        return null;
    }
    
    public static File saveFile(final String title) {
        (FileInformation.fileChooser = new FileChooser()).setTitle(title);
        final Boolean isSaved = true;
        if (FileInformation.prevDirectory != null && FileInformation.prevDirectory.exists()) {
            FileInformation.fileChooser.setInitialDirectory(FileInformation.prevDirectory);
        }
        final FileChooser.ExtensionFilter exFilter = new FileChooser.ExtensionFilter("Text Files", new String[] { "*.txt" });
        if (FileInformation.fileChooser.getExtensionFilters().size() == 0) {
            FileInformation.fileChooser.getExtensionFilters().add((Object)exFilter);
        }
        FileInformation.fileChooser.setInitialFileName("*.txt");
        final File file = getFileChooser().showSaveDialog((Window)FileInformation.stage);
        if (checkFileValid(file)) {
            FileInformation.prevDirectory = file.getParentFile();
            return file;
        }
        return null;
    }
    
    private static void applyStyle(final Alert alert) {
        final DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add((Object)StyleMap.getCurrentStyle());
    }
    
    private static boolean checkFileValid(final File file) {
        if (file == null) {
            return false;
        }
        if (!file.getName().endsWith(".txt")) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            applyStyle(alert);
            alert.setTitle("File Not Saved");
            alert.setHeaderText("Format Error");
            alert.setContentText("You must save it as a \".txt\" file.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    public static void showNotValidError() {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        applyStyle(alert);
        alert.setTitle("File Error");
        alert.setHeaderText("Invalid File");
        alert.setContentText("The file you've chosen is not valid.");
        alert.showAndWait();
    }
    
    public static void showValidMessage(final String projectName) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        applyStyle(alert);
        alert.setTitle("File Information");
        alert.setHeaderText("Opened File");
        alert.setContentText("Successfully opened project " + projectName);
        alert.showAndWait();
    }
    
    public static String showUnsavedChangesError(final String filename) {
        final Alert alert = new Alert(Alert.AlertType.WARNING);
        applyStyle(alert);
        alert.setTitle("TuneOut");
        alert.setHeaderText("Do you want to save changes to " + filename + "?");
        final ButtonType yesButton = new ButtonType("Save");
        final ButtonType noButton = new ButtonType("Don't Save");
        final ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll((Object[])new ButtonType[] { yesButton, noButton, cancelButton });
        return alert.showAndWait().get().getText();
    }
    
    public static String showSaveTestDialog() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        applyStyle(alert);
        alert.setTitle("Save Result?");
        alert.setHeaderText("Do you want to save your results to persistent file?");
        final ButtonType yesButton = new ButtonType("Save");
        final ButtonType cancelButton = new ButtonType("Don't Save", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll((Object[])new ButtonType[] { yesButton, cancelButton });
        return alert.showAndWait().get().getText();
    }
    
    private static FileChooser getFileChooser() {
        return FileInformation.fileChooser;
    }
    
    public static File getPrevDirectory() {
        return FileInformation.prevDirectory;
    }
    
    public static void setPrevDirectory(final File prevDirectory) {
        FileInformation.prevDirectory = prevDirectory;
    }
    
    static {
        FileInformation.fileChooser = new FileChooser();
        FileInformation.fileMXLChooser = new FileChooser();
    }
}
