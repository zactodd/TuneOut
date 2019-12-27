

package Controller.LearningCompose.TopToolbox;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class AccidentalController
{
    @FXML
    ToggleButton sharpBtn;
    @FXML
    ToggleButton flatBtn;
    @FXML
    ToggleButton naturalBtn;
    private TopToolboxController topToolboxController;
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    @FXML
    public void selectItem() {
        this.topToolboxController.placeNote("");
    }
    
    String getSelectedAccidental() {
        if (this.flatBtn.isSelected()) {
            return "b";
        }
        if (this.sharpBtn.isSelected()) {
            return "#";
        }
        if (this.naturalBtn.isSelected()) {
            return "n";
        }
        return " ";
    }
    
    protected void toggleDisableAccidentals(final Boolean disable) {
        this.sharpBtn.setDisable((boolean)disable);
        this.flatBtn.setDisable((boolean)disable);
        this.naturalBtn.setDisable((boolean)disable);
    }
}
