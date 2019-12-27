

package Controller.LearningCompose.TopToolbox;

import javafx.scene.control.Toggle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ChordArrangementController
{
    public ToggleGroup chordArrangementToggleGroup;
    @FXML
    public ToggleButton chordSimultaneousBtn;
    @FXML
    public ToggleButton chordArpeggioBtn;
    private TopToolboxController topToolboxController;
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    public void pressButton(final ActionEvent actionEvent) {
        final ToggleButton clickedBtn = (ToggleButton)actionEvent.getSource();
        clickedBtn.setSelected(true);
        this.topToolboxController.setArrangement(this.getSelectedChordArrangement());
        this.topToolboxController.placeNote("");
    }
    
    public String getSelectedChordArrangement() {
        final ToggleButton tog = (ToggleButton)this.chordArrangementToggleGroup.getSelectedToggle();
        if (tog != null) {
            return tog.getText();
        }
        return "";
    }
    
    public void resetToggleBtn() {
        final ToggleButton toggleButton = (ToggleButton)this.chordArrangementToggleGroup.getToggles().get(0);
        this.chordArrangementToggleGroup.selectToggle((Toggle)toggleButton);
    }
}
