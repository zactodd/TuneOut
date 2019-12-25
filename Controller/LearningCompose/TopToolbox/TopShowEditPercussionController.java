// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.TopToolbox;

import javafx.scene.control.ButtonType;
import java.util.Optional;
import Model.Percussion.PercussionLoop;
import javafx.scene.control.TextInputDialog;
import javafx.scene.Scene;
import Model.Settings.StyleMap;
import javafx.scene.control.Alert;
import java.util.Iterator;
import javafx.scene.control.MenuItem;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckMenuItem;
import Model.Percussion.PercussionLoopMap;
import javafx.event.ActionEvent;
import java.util.Collection;
import java.util.ArrayList;
import Model.CommandMessages;
import Controller.OuterTemplateController;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Menu;
import javafx.scene.control.Button;
import Controller.LearningCompose.OuterComposePercussionController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class TopShowEditPercussionController
{
    @FXML
    private ComboBox<Integer> numberLoopsCombo;
    private OuterComposePercussionController outerController;
    @FXML
    private ComboBox<String> percussionCombo;
    @FXML
    private Button percussionSaveBtn;
    @FXML
    private Button percussionNewBtn;
    @FXML
    private Button percussionDeleteBtn;
    @FXML
    private Menu openPercussionMenu;
    @FXML
    private Menu deletePercussionMenu;
    @FXML
    private MenuButton percussionMenuButton;
    private OuterTemplateController outerTemplateController;
    private final String cannotDeleteDefaultPercussion;
    private final String cannotDeleteSelectedPercussion;
    private String selectedPercussion;
    private static final Integer MAX_LOOPS;
    private final String defaultPercussionLoop = "default percussion loop";
    
    public TopShowEditPercussionController() {
        this.cannotDeleteDefaultPercussion = CommandMessages.getMessage("CANNOT_DELETE_DEFAULT_PERCUSSION");
        this.cannotDeleteSelectedPercussion = CommandMessages.getMessage("CANNOT_DELETE_SELECTED_PERCUSSION");
    }
    
    public void initialize() {
        final ArrayList<Integer> loops = new ArrayList<Integer>();
        for (int x = 1; x <= TopShowEditPercussionController.MAX_LOOPS; ++x) {
            loops.add(x);
        }
        this.numberLoopsCombo.getItems().setAll((Collection)loops);
        this.numberLoopsCombo.getSelectionModel().select(0);
        this.updateMenus();
    }
    
    public void setOuterTemplateController(final OuterTemplateController outerTemplateController) {
        this.outerTemplateController = outerTemplateController;
    }
    
    public void setUp(final OuterComposePercussionController outerController) {
        this.outerController = outerController;
        this.openPercussion(this.selectedPercussion = "default percussion loop");
        this.updateMenus();
    }
    
    public void comboChange(final ActionEvent actionEvent) {
        this.outerController.setNumberLoops((Integer)this.numberLoopsCombo.getSelectionModel().getSelectedItem());
    }
    
    private void updateOpenPercussionMenu() {
        this.openPercussionMenu.getItems().clear();
        for (final String percussionLoop : PercussionLoopMap.getPercussionLoopMap().keySet()) {
            final CheckMenuItem menuItem = new CheckMenuItem(percussionLoop);
            menuItem.setOnAction(event -> {
                this.selectedPercussion = menuItem.getText();
                this.updateSelectedPercussion();
                this.openPercussion(menuItem.getText());
            });
            this.openPercussionMenu.getItems().add((Object)menuItem);
        }
        this.updateSelectedPercussionToList((ObservableList<MenuItem>)this.openPercussionMenu.getItems());
    }
    
    private void updateDeletePercussionMenu() {
        this.deletePercussionMenu.getItems().clear();
        for (final String percussionLoop : PercussionLoopMap.getPercussionLoopMap().keySet()) {
            final CheckMenuItem menuItem = new CheckMenuItem(percussionLoop);
            menuItem.setOnAction(event -> this.deletePercussionLoop(menuItem.getText()));
            this.deletePercussionMenu.getItems().add((Object)menuItem);
        }
        this.updateSelectedPercussionToList((ObservableList<MenuItem>)this.deletePercussionMenu.getItems());
    }
    
    private void updateSelectedPercussionToList(final ObservableList<MenuItem> listOfPercussionNames) {
        for (final MenuItem eachMenuItem : listOfPercussionNames) {
            final CheckMenuItem eachCheckMenuItem = (CheckMenuItem)eachMenuItem;
            if (eachCheckMenuItem.getText().equals(this.selectedPercussion)) {
                eachCheckMenuItem.setSelected(true);
                this.percussionMenuButton.setText(this.selectedPercussion);
            }
            else {
                eachCheckMenuItem.setSelected(false);
            }
        }
        this.percussionMenuButton.setText(this.selectedPercussion);
    }
    
    private void updateSelectedPercussion() {
        this.percussionMenuButton.setText(this.selectedPercussion);
        this.updateSelectedPercussionToList((ObservableList<MenuItem>)this.openPercussionMenu.getItems());
        this.updateSelectedPercussionToList((ObservableList<MenuItem>)this.deletePercussionMenu.getItems());
    }
    
    public void savePercussionLoop(final ActionEvent actionEvent) {
        PercussionLoopMap.add(this.selectedPercussion, this.outerController.getPercussionLoopFromSheet());
        final Alert afteralert = new Alert(Alert.AlertType.INFORMATION);
        afteralert.setTitle("Percussion Loop Saved");
        afteralert.setHeaderText((String)null);
        Scene scene = afteralert.getDialogPane().getScene();
        afteralert.setContentText("Successfully saved the percussion loop");
        scene = afteralert.getDialogPane().getScene();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        afteralert.showAndWait();
        this.outerTemplateController.updateAppTitle();
    }
    
    private String getSelectedPercussion() {
        for (final MenuItem menuItem : this.openPercussionMenu.getItems()) {
            final CheckMenuItem checkMenuItem = (CheckMenuItem)menuItem;
            if (checkMenuItem.isSelected()) {
                return checkMenuItem.getText();
            }
        }
        return "";
    }
    
    public void newPercussion() {
        final TextInputDialog textInput = new TextInputDialog("");
        textInput.setTitle("New Percussion");
        textInput.setHeaderText("Percussion Name");
        textInput.setContentText("Please enter a percussion loop name:");
        final Optional<String> result = (Optional<String>)textInput.showAndWait();
        if (result.isPresent()) {
            if (PercussionLoopMap.isPercussionLoopExist(result.get())) {
                this.selectedPercussion = result.get().toLowerCase();
                this.updateMenus();
                this.openPercussion(this.selectedPercussion);
            }
            else {
                this.openPercussionMenu.getItems().add((Object)new CheckMenuItem((String)result.get()));
                PercussionLoopMap.add(result.get(), new PercussionLoop());
                this.selectedPercussion = result.get();
                this.updateMenus();
                this.outerController.clearStave();
            }
        }
    }
    
    public void updateMenus() {
        this.updateOpenPercussionMenu();
        this.updateDeletePercussionMenu();
    }
    
    private void showConfirmDeleteAlert(final String percussionloop) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Percussion loop");
        alert.setContentText("Are you sure you want to delete the percussion loop '" + percussionloop + "'?" + "\nOnce deleted, you could no longer use the percussion loop");
        Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        final Optional<ButtonType> result = (Optional<ButtonType>)alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (PercussionLoopMap.getPercussionLoopMap().containsKey(percussionloop)) {
                PercussionLoopMap.removePercussionLoop(percussionloop);
                this.selectedPercussion = "default percussion loop";
            }
            final Alert afteralert = new Alert(Alert.AlertType.INFORMATION);
            afteralert.setTitle("Percussion loop Deleted");
            afteralert.setHeaderText((String)null);
            afteralert.setContentText("Successfully deleted the percussion '" + percussionloop + "'");
            scene = afteralert.getDialogPane().getScene();
            scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
            afteralert.showAndWait();
        }
    }
    
    public void deletePercussionLoop(final String percussionLoop) {
        final String selectPercussion = this.getSelectedPercussion();
        if (PercussionLoopMap.getDefaultPercussionLoopMap().containsKey(percussionLoop)) {
            this.showError(this.cannotDeleteDefaultPercussion);
            this.updateSelectedPercussion();
        }
        else if (selectPercussion.equals(percussionLoop)) {
            this.showError(this.cannotDeleteSelectedPercussion);
            this.updateSelectedPercussion();
        }
        else {
            this.showConfirmDeleteAlert(percussionLoop);
            this.updateMenus();
            this.openPercussion("default percussion loop");
        }
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
    
    private void openPercussion(final String percussionLoop) {
        this.outerController.placeLoopOnSheet(PercussionLoopMap.getPercussionLoop(percussionLoop));
    }
    
    static {
        MAX_LOOPS = 10;
    }
}
