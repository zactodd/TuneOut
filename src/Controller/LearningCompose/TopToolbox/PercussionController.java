

package Controller.LearningCompose.TopToolbox;

import Controller.LearningCompose.PercussionCategory;
import javafx.event.ActionEvent;
import Controller.LearningCompose.PercussionSheetInfoMap;
import Controller.LearningCompose.PercussionSheetInfo;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class PercussionController
{
    public ToggleGroup percussionToggleGroup;
    public ToggleButton crotchetUpBtn;
    public ToggleButton crotchetDownBtn;
    public ToggleButton crossUpBtn;
    public ToggleButton crossDownBtn;
    public ToggleButton diamondBtn;
    public ToggleButton triangleSideBtn;
    public ToggleButton triangleMiddleBtn;
    public ToggleButton strikeBtn;
    public ToggleButton restBtn;
    public HBox secondRowToggles;
    TopToolboxController topToolboxController;
    private ToggleGroup toggleGroup;
    
    public PercussionController() {
        this.toggleGroup = new ToggleGroup();
    }
    
    public void initialize() {
        final ToggleButton toggleButtonIcon = (ToggleButton)this.percussionToggleGroup.getToggles().get(0);
        this.toggleGroup.selectToggle((Toggle)toggleButtonIcon);
        this.setUpSecondRowToggles(this.getCategory(toggleButtonIcon));
        final ToggleButton toggleButtonInstrument = (ToggleButton)this.toggleGroup.getToggles().get(0);
        this.toggleGroup.selectToggle((Toggle)toggleButtonInstrument);
        this.addToggleGroupListener();
        ((Toggle)this.percussionToggleGroup.getToggles().get(0)).setSelected(true);
        this.percussionToggleGroup.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue ov, final Toggle oldToggle, final Toggle newToggle) {
                if (newToggle == null) {
                    oldToggle.setSelected(true);
                }
            }
        });
    }
    
    private void addToggleGroupListener() {
        this.toggleGroup.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue ov, final Toggle oldToggle, final Toggle newToggle) {
                if (newToggle != null) {
                    final ToggleButton toggleBtn = (ToggleButton)newToggle;
                    final String btnText = toggleBtn.getText();
                    PercussionController.this.topToolboxController.outerExploreController.setPercussionInfo(PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.get(btnText));
                    if (PercussionController.this.topToolboxController.outerExploreController.getSelectedElement() != null) {
                        PercussionController.this.topToolboxController.placeNote("");
                    }
                }
                else {
                    oldToggle.setSelected(true);
                }
            }
        });
    }
    
    public void pressButton(final ActionEvent actionEvent) {
        final ToggleButton selectedtoggleBtn = (ToggleButton)this.percussionToggleGroup.getSelectedToggle();
        this.setUpSecondRowToggles(this.getCategory(selectedtoggleBtn));
    }
    
    private PercussionCategory getCategory(final ToggleButton selectedtoggleBtn) {
        PercussionCategory category = null;
        if (selectedtoggleBtn.equals(this.crotchetUpBtn)) {
            category = PercussionCategory.CROTCHET_UP;
        }
        else if (selectedtoggleBtn.equals(this.crotchetDownBtn)) {
            category = PercussionCategory.CROTCHET_DOWN;
        }
        else if (selectedtoggleBtn.equals(this.crossUpBtn)) {
            category = PercussionCategory.CROSS_UP;
        }
        else if (selectedtoggleBtn.equals(this.crossDownBtn)) {
            category = PercussionCategory.CROSS_DOWN;
        }
        else if (selectedtoggleBtn.equals(this.diamondBtn)) {
            category = PercussionCategory.DIAMOND;
        }
        else if (selectedtoggleBtn.equals(this.triangleSideBtn)) {
            category = PercussionCategory.TRIANGLE_SIDE;
        }
        else if (selectedtoggleBtn.equals(this.triangleMiddleBtn)) {
            category = PercussionCategory.TRIANGLE_MIDDLE;
        }
        else if (selectedtoggleBtn.equals(this.strikeBtn)) {
            category = PercussionCategory.STRIKE;
        }
        else if (selectedtoggleBtn.equals(this.restBtn)) {
            category = PercussionCategory.REST;
        }
        return category;
    }
    
    private ToggleButton getButton(final PercussionCategory category) {
        ToggleButton selectedtoggleBtn = null;
        if (category.equals(PercussionCategory.CROTCHET_UP)) {
            selectedtoggleBtn = this.crotchetUpBtn;
        }
        else if (category.equals(PercussionCategory.CROTCHET_DOWN)) {
            selectedtoggleBtn = this.crotchetDownBtn;
        }
        else if (category.equals(PercussionCategory.CROSS_UP)) {
            selectedtoggleBtn = this.crossUpBtn;
        }
        else if (category.equals(PercussionCategory.CROSS_DOWN)) {
            selectedtoggleBtn = this.crossDownBtn;
        }
        else if (category.equals(PercussionCategory.DIAMOND)) {
            selectedtoggleBtn = this.diamondBtn;
        }
        else if (category.equals(PercussionCategory.TRIANGLE_SIDE)) {
            selectedtoggleBtn = this.triangleSideBtn;
        }
        else if (category.equals(PercussionCategory.TRIANGLE_MIDDLE)) {
            selectedtoggleBtn = this.triangleMiddleBtn;
        }
        else if (category.equals(PercussionCategory.STRIKE)) {
            selectedtoggleBtn = this.strikeBtn;
        }
        else if (category.equals(PercussionCategory.REST)) {
            selectedtoggleBtn = this.restBtn;
        }
        return selectedtoggleBtn;
    }
    
    private void setUpSecondRowToggles(final PercussionCategory category) {
        this.secondRowToggles.getChildren().clear();
        this.toggleGroup = new ToggleGroup();
        for (final PercussionSheetInfo percussionInfo : PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.values()) {
            if (percussionInfo.getCategory().equals(category)) {
                final ToggleButton percussionToggle = new ToggleButton();
                percussionToggle.setId("exploreBtn");
                percussionToggle.setText(percussionInfo.getPercussion().getInstrument());
                percussionToggle.setToggleGroup(this.toggleGroup);
                this.secondRowToggles.getChildren().add((Object)percussionToggle);
            }
        }
        final ToggleButton toggleButtonToSelect = (ToggleButton)this.toggleGroup.getToggles().get(0);
        this.toggleGroup.selectToggle((Toggle)toggleButtonToSelect);
        if (this.topToolboxController != null) {
            this.topToolboxController.outerExploreController.setPercussionInfo(PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.get(toggleButtonToSelect.getText()));
        }
        this.addToggleGroupListener();
    }
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    public PercussionSheetInfo getPercussionInfo() {
        final ToggleButton selectedToggleButton = (ToggleButton)this.toggleGroup.getSelectedToggle();
        if (selectedToggleButton != null) {
            final String selectedBtnText = selectedToggleButton.getText();
            return PercussionSheetInfoMap.PERCUSSION_SHEET_INFO.get(selectedBtnText);
        }
        return null;
    }
    
    public void updatePercussion(final PercussionSheetInfo percussionInfo) {
        this.setUpSecondRowToggles(percussionInfo.getCategory());
        this.getButton(percussionInfo.getCategory()).setSelected(true);
        ToggleButton toggleToSelect = null;
        for (final Toggle toggle : this.toggleGroup.getToggles()) {
            final ToggleButton toggleBtn = (ToggleButton)toggle;
            if (toggleBtn.getText().equals(percussionInfo.getPercussion().getInstrument())) {
                toggleToSelect = toggleBtn;
            }
        }
        if (toggleToSelect != null) {
            this.toggleGroup.selectToggle((Toggle)toggleToSelect);
        }
    }
}
