

package Controller.LearningCompose.TopToolbox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ChordInversionController
{
    public ToggleGroup chordInversionToggleGroup;
    @FXML
    public ToggleButton firstInversionBtn;
    @FXML
    public ToggleButton secondInversionBtn;
    @FXML
    public ToggleButton thirdInversionBtn;
    private TopToolboxController topToolboxController;
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    public void selectInversion(final ActionEvent actionEvent) {
        this.topToolboxController.setInversion(this.getSelectedChordInversion());
        this.topToolboxController.placeNote("");
    }
    
    public String getSelectedChordInversion() {
        final ToggleButton tog = (ToggleButton)this.chordInversionToggleGroup.getSelectedToggle();
        if (tog != null) {
            return tog.getText();
        }
        return "";
    }
    
    public void resetToggleBtn() {
        this.firstInversionBtn.setSelected(false);
        this.secondInversionBtn.setSelected(false);
        this.thirdInversionBtn.setSelected(false);
    }
    
    public void disableThirdInversionBtn(final Boolean disable) {
        if (disable) {
            this.thirdInversionBtn.setSelected(false);
        }
        this.thirdInversionBtn.setDisable((boolean)disable);
    }
}
