// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.TopToolbox;

import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.scene.Scene;
import Model.Note.Melody.Melody;
import Model.Settings.StyleMap;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import java.util.Iterator;
import javafx.scene.control.MenuItem;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckMenuItem;
import Model.Note.Melody.MelodyMap;
import Model.CommandMessages;
import Controller.OuterTemplateController;
import Controller.LearningCompose.OuterComposeController;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Menu;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class TopShowEditController
{
    private final String defaultMelody = "default melody";
    public ComboBox<String> melodyCombo;
    public Button melodySaveBtn;
    public Button melodyNewBtn;
    public Button melodyDeleteBtn;
    public Menu openMelodyMenu;
    public Menu deleteMelodyMenu;
    public MenuButton melodyMenuButton;
    private OuterComposeController outerComposeController;
    private OuterTemplateController outerTemplateController;
    private final String cannotDeleteDefaultMelody;
    private final String cannotDeleteSelectedMelody;
    private String selectedMelody;
    
    public TopShowEditController() {
        this.cannotDeleteDefaultMelody = CommandMessages.getMessage("CANNOT_DELETE_DEFAULT_MELODY");
        this.cannotDeleteSelectedMelody = CommandMessages.getMessage("CANNOT_DELETE_SELECTED_MELODY");
    }
    
    public void setUpMelodiesMenuList() {
        this.openMelody(this.selectedMelody = "default melody");
        this.updateMenus();
    }
    
    private void updateOpenMelodyMenu() {
        this.openMelodyMenu.getItems().clear();
        for (final String melodyName : MelodyMap.getMelodiesMap().keySet()) {
            final CheckMenuItem menuItem = new CheckMenuItem(melodyName);
            menuItem.setOnAction(event -> {
                this.selectedMelody = menuItem.getText();
                this.updateSelectedMelody();
                this.openMelody(menuItem.getText());
            });
            this.openMelodyMenu.getItems().add((Object)menuItem);
        }
        this.updateSelectedMelodyOnList((ObservableList<MenuItem>)this.openMelodyMenu.getItems());
    }
    
    private void updateDeleteMelodyMenu() {
        this.deleteMelodyMenu.getItems().clear();
        for (final String melodyName : MelodyMap.getMelodiesMap().keySet()) {
            final CheckMenuItem menuItem = new CheckMenuItem(melodyName);
            menuItem.setOnAction(event -> this.deleteMelody(menuItem.getText()));
            this.deleteMelodyMenu.getItems().add((Object)menuItem);
        }
        this.updateSelectedMelodyOnList((ObservableList<MenuItem>)this.deleteMelodyMenu.getItems());
    }
    
    private void updateSelectedMelodyOnList(final ObservableList<MenuItem> listOfMelodyNames) {
        for (final MenuItem eachMenuItem : listOfMelodyNames) {
            final CheckMenuItem eachCheckMenuItem = (CheckMenuItem)eachMenuItem;
            if (eachCheckMenuItem.getText().equals(this.selectedMelody)) {
                eachCheckMenuItem.setSelected(true);
                this.melodyMenuButton.setText(this.selectedMelody);
            }
            else {
                eachCheckMenuItem.setSelected(false);
            }
        }
    }
    
    private void updateSelectedMelody() {
        this.melodyMenuButton.setText(this.selectedMelody);
        this.updateSelectedMelodyOnList((ObservableList<MenuItem>)this.openMelodyMenu.getItems());
        this.updateSelectedMelodyOnList((ObservableList<MenuItem>)this.deleteMelodyMenu.getItems());
    }
    
    public void setOuterController(final OuterComposeController outerComposeController) {
        this.outerComposeController = outerComposeController;
    }
    
    public void setOuterTemplateController(final OuterTemplateController outerTemplateController) {
        this.outerTemplateController = outerTemplateController;
    }
    
    public void saveMelody(final ActionEvent actionEvent) {
        final Melody melody = this.outerComposeController.convertSheetToMelody(this.getSelectedMelody());
        MelodyMap.addMelody(melody);
        final Alert afteralert = new Alert(Alert.AlertType.INFORMATION);
        afteralert.setTitle("Melody Saved");
        afteralert.setHeaderText((String)null);
        afteralert.setContentText("Successfully saved the melody");
        final Scene scene = afteralert.getDialogPane().getScene();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        afteralert.showAndWait();
        if (this.outerTemplateController != null) {
            this.outerTemplateController.updateAppTitle();
        }
    }
    
    private String getSelectedMelody() {
        for (final MenuItem melodyMenuItem : this.openMelodyMenu.getItems()) {
            final CheckMenuItem melodyCheckMenuItem = (CheckMenuItem)melodyMenuItem;
            if (melodyCheckMenuItem.isSelected()) {
                return melodyCheckMenuItem.getText().toLowerCase();
            }
        }
        return "";
    }
    
    public void newMelody() {
        final TextInputDialog textInput = new TextInputDialog("");
        textInput.setTitle("New Melody");
        textInput.setHeaderText("Melody Name");
        textInput.setContentText("Please enter a melody name:");
        final Optional<String> result = (Optional<String>)textInput.showAndWait();
        if (result.isPresent()) {
            if (MelodyMap.isMelodyExist(result.get())) {
                this.selectedMelody = result.get().toLowerCase();
                this.updateMenus();
                this.openMelody(this.selectedMelody);
            }
            else {
                this.openMelodyMenu.getItems().add((Object)new CheckMenuItem((String)result.get()));
                MelodyMap.addMelody(new Melody(result.get()));
                this.selectedMelody = result.get();
                this.updateMenus();
                this.outerComposeController.clearStave();
            }
        }
    }
    
    public void updateMenus() {
        this.updateOpenMelodyMenu();
        this.updateDeleteMelodyMenu();
    }
    
    private void showConfirmDeleteAlert(final String melodyName) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete melody");
        alert.setContentText("Are you sure you want to delete the melody '" + melodyName + "'?" + "\nOnce deleted, you could no longer use the melody");
        Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        final Optional<ButtonType> result = (Optional<ButtonType>)alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (MelodyMap.getMelodiesMap().containsKey(melodyName)) {
                MelodyMap.removeMelody(MelodyMap.getMelody(melodyName));
                this.selectedMelody = "default melody";
            }
            final Alert afteralert = new Alert(Alert.AlertType.INFORMATION);
            afteralert.setTitle("Melody Deleted");
            afteralert.setHeaderText((String)null);
            afteralert.setContentText("Successfully deleted the melody '" + melodyName + "'");
            scene = afteralert.getDialogPane().getScene();
            scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
            afteralert.showAndWait();
        }
    }
    
    public void deleteMelody(final String melodyName) {
        final String selectedMelodyName = this.getSelectedMelody();
        if (MelodyMap.getDefaultMelodies().containsKey(melodyName)) {
            this.showError(this.cannotDeleteDefaultMelody);
            this.updateSelectedMelody();
        }
        else if (selectedMelodyName.equals(melodyName)) {
            this.showError(this.cannotDeleteSelectedMelody);
            this.updateSelectedMelody();
        }
        else {
            this.showConfirmDeleteAlert(melodyName);
            this.updateMenus();
            this.openMelody("default melody");
        }
    }
    
    private void openMelody(final String melodyName) {
        this.outerComposeController.placeMelodyOnSheet(MelodyMap.getMelody(melodyName));
    }
    
    void showError(final String errorText) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        final Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300.0, 200.0);
        alert.setTitle("Error");
        alert.setContentText(errorText);
        alert.showAndWait();
    }
}
