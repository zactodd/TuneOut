// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.TopToolbox;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ScaleController
{
    public ToggleGroup scaleToggleGroup;
    public ToggleButton majorScaleBtn;
    public ToggleButton minorScaleBtn;
    public ToggleButton harmonicMinorScaleBtn;
    private TopToolboxController topToolboxController;
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    public void pressButton(final ActionEvent actionEvent) {
        this.topToolboxController.placeNote(this.getScaleType());
    }
    
    public String getScaleType() {
        final ToggleButton tog = (ToggleButton)this.scaleToggleGroup.getSelectedToggle();
        final String scaleType = "";
        if (tog != null) {
            return tog.getText();
        }
        return "";
    }
}
