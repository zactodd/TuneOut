

package Controller.LearningCompose.Toolbox;

import Controller.LearningCompose.SheetElement.SheetElement;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.beans.value.ChangeListener;
import Controller.LearningCompose.TopToolbox.TopToolboxController;
import Controller.LearningCompose.OuterExploreCompose;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public abstract class Toolbox
{
    @FXML
    public AnchorPane toolbox;
    @FXML
    public ToggleGroup toggleToolbox;
    protected OuterExploreCompose outerExploreController;
    protected TopToolboxController topToolboxController;
    
    public void setOuterExploreController(final OuterExploreCompose outerExplorController) {
        this.outerExploreController = outerExplorController;
    }
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    public void initialize() {
        this.toggleToolbox.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue ov, final Toggle oldToggle, final Toggle newToggle) {
                if (newToggle == null) {
                    oldToggle.setSelected(true);
                }
            }
        });
    }
    
    public String getSelectedToolboxElement() {
        final ToggleButton toggleBtn = (ToggleButton)this.toggleToolbox.getSelectedToggle();
        if (toggleBtn != null) {
            return toggleBtn.getText();
        }
        return "";
    }
    
    public Boolean toolboxIsSelected() {
        final ToggleButton toggleBtn = (ToggleButton)this.toggleToolbox.getSelectedToggle();
        return toggleBtn != null;
    }
    
    protected void clearFromToolbox() {
        this.outerExploreController.getSheetController().clearStave();
        this.topToolboxController.hideOptions();
        this.toolbox.setCursor(Cursor.DEFAULT);
        this.outerExploreController.getSheetController().clearBtn.setDisable(true);
        this.outerExploreController.setType("");
        this.outerExploreController.setInversion("");
        this.outerExploreController.setArrangement("Simultaneous");
        if (this.outerExploreController.getSignature() != null) {
            final SheetElement signature = this.outerExploreController.getSignature();
            if (signature.getElementImages() != null && !signature.getElementImages().isEmpty()) {
                for (final ImageView image : signature.getElementImages().values()) {
                    this.outerExploreController.getSheetController().stave.getChildren().remove((Object)image);
                }
            }
            this.outerExploreController.setSignature(null);
        }
        this.topToolboxController.enableNaturalButton(false);
    }
}
