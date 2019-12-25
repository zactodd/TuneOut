// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.TopToolbox;

import java.util.Iterator;
import javafx.event.ActionEvent;
import Controller.LearningCompose.ElementDurationMap;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.beans.value.ChangeListener;
import Controller.LearningCompose.ElementDuration;
import java.util.Map;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class NoteRestDurationController
{
    public ToggleGroup durationToggleGroup;
    public ToggleButton semiQuaverBtn;
    public ToggleButton quaverBtn;
    public ToggleButton crotchetBtn;
    public ToggleButton minimBtn;
    public ToggleButton semibreveBtn;
    public ToggleButton breveBtn;
    public ToggleButton semiQuaverRestBtn;
    public ToggleButton quaverRestBtn;
    public ToggleButton crotchetRestBtn;
    public ToggleButton minimRestBtn;
    public ToggleButton semibreveRestBtn;
    public ToggleButton breveRestBtn;
    private TopToolboxController topToolboxController;
    private Boolean dotsEnabled;
    private Map<ToggleButton, ElementDuration> buttons;
    
    public NoteRestDurationController() {
        this.dotsEnabled = false;
    }
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    public void initialize() {
        this.durationToggleGroup.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue ov, final Toggle oldToggle, final Toggle newToggle) {
                if (newToggle == null) {
                    oldToggle.setSelected(true);
                }
            }
        });
    }
    
    public void setUpMapButtons() {
        ElementDurationMap.ELEMENT_DURATIONS.get("semiquaverNote").setToggleButton(this.semiQuaverBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("semiquaverDottedNote").setToggleButton(this.semiQuaverBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("quaverNote").setToggleButton(this.quaverBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("quaverDottedNote").setToggleButton(this.quaverBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("crotchetNote").setToggleButton(this.crotchetBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("crotchetDottedNote").setToggleButton(this.crotchetBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("minimNote").setToggleButton(this.minimBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("minimDottedNote").setToggleButton(this.minimBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("semibreveNote").setToggleButton(this.semibreveBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("semibreveDottedNote").setToggleButton(this.semibreveBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("breveNote").setToggleButton(this.breveBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("breveDottedNote").setToggleButton(this.breveBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("semiquaverRest").setToggleButton(this.semiQuaverRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("semiquaverDottedRest").setToggleButton(this.semiQuaverRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("quaverRest").setToggleButton(this.quaverRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("quaverDottedRest").setToggleButton(this.quaverRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("crotchetRest").setToggleButton(this.crotchetRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("crotchetDottedRest").setToggleButton(this.crotchetRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("minimRest").setToggleButton(this.minimRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("minimDottedRest").setToggleButton(this.minimRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("semibreveRest").setToggleButton(this.semibreveRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("semibreveDottedRest").setToggleButton(this.semibreveRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("breveRest").setToggleButton(this.breveRestBtn);
        ElementDurationMap.ELEMENT_DURATIONS.get("breveDottedRest").setToggleButton(this.breveRestBtn);
    }
    
    public void pressButton(final ActionEvent actionEvent) {
        final ToggleButton toggleBtn = (ToggleButton)actionEvent.getSource();
        for (final ElementDuration duration : ElementDurationMap.ELEMENT_DURATIONS.values()) {
            if (duration.getToggleButton().equals(toggleBtn) && toggleBtn.getText().equals(".") == duration.getDotted()) {
                this.topToolboxController.outerExploreController.setElementDuration(duration);
                if (duration.getElementType().equals(ElementDuration.ElementType.REST)) {
                    this.topToolboxController.toggleDisableNotesAccidentals(true);
                }
                else {
                    this.topToolboxController.toggleDisableNotesAccidentals(false);
                }
            }
        }
        this.topToolboxController.placeNote("");
    }
    
    protected void toggleDots() {
        this.dotsEnabled = !this.dotsEnabled;
        for (final Toggle toggle : this.durationToggleGroup.getToggles()) {
            final ToggleButton toggleBtn = (ToggleButton)toggle;
            if (this.dotsEnabled) {
                toggleBtn.setText(".");
            }
            else {
                toggleBtn.setText("");
            }
        }
        this.topToolboxController.placeNote("");
    }
    
    protected void addDots(final Boolean add) {
        for (final Toggle toggle : this.durationToggleGroup.getToggles()) {
            final ToggleButton toggleBtn = (ToggleButton)toggle;
            if (add) {
                toggleBtn.setText(".");
                this.dotsEnabled = true;
            }
            else {
                toggleBtn.setText("");
                this.dotsEnabled = false;
            }
        }
    }
    
    ElementDuration getElementDuration() {
        final ToggleButton selectedToggleButton = (ToggleButton)this.durationToggleGroup.getSelectedToggle();
        final Map<String, ElementDuration> bob = ElementDurationMap.ELEMENT_DURATIONS;
        for (final ElementDuration duration : ElementDurationMap.ELEMENT_DURATIONS.values()) {
            if (duration.getToggleButton().equals(selectedToggleButton) && selectedToggleButton.getText().equals(".") == duration.getDotted()) {
                return duration;
            }
        }
        return null;
    }
    
    protected void updateDuration(final ElementDuration elementDuration) {
        final ToggleButton toggleBtn = elementDuration.getToggleButton();
        toggleBtn.setSelected(true);
        if (elementDuration.getDotted()) {
            this.addDots(true);
        }
        else {
            this.addDots(false);
        }
        if (elementDuration.getElementType().equals(ElementDuration.ElementType.REST)) {
            this.topToolboxController.toggleDisableNotesAccidentals(true);
        }
        else {
            this.topToolboxController.toggleDisableNotesAccidentals(false);
        }
    }
}
