

package Controller.LearningCompose.TopToolbox;

import java.util.Collection;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import Model.Note.Chord.ChordMap;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;

public class ChordController
{
    public ToggleGroup chordToggleGroup;
    public HBox chordNamesDynamicAboveHBox;
    public HBox chordNamesDynamicBelowHBox;
    public Button leftBtn;
    public Button rightBtn;
    private TopToolboxController topToolboxController;
    private final Integer MIN_CHORD_LENGTH;
    private final Integer MAX_CHORD_LENGTH;
    private Integer currentChordLength;
    private static final Integer MAX_ABOVE_CHILDREN;
    private String currentChordType;
    
    public ChordController() {
        this.chordToggleGroup = new ToggleGroup();
        this.MIN_CHORD_LENGTH = 3;
        this.MAX_CHORD_LENGTH = 4;
        this.currentChordLength = this.MIN_CHORD_LENGTH;
    }
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    public void selectChordType(final String chordType) {
        if (ChordMap.checkChordTypeExists(chordType, 4)) {
            this.topToolboxController.disableThirdInversionBtn(false);
        }
        else if (ChordMap.checkChordTypeExists(chordType, 3)) {
            this.topToolboxController.disableThirdInversionBtn(true);
        }
        this.topToolboxController.placeNote(chordType);
    }
    
    public void initialize() {
        this.updateChordNames();
        final ToggleButton toggleButton = (ToggleButton)this.chordToggleGroup.getToggles().get(0);
        this.chordToggleGroup.selectToggle((Toggle)toggleButton);
    }
    
    public String getChordType() {
        final ToggleButton tog = (ToggleButton)this.chordToggleGroup.getSelectedToggle();
        final String chordType = "";
        if (tog != null) {
            this.currentChordType = tog.getText();
            return tog.getText();
        }
        return "";
    }
    
    private void updateChordNames() {
        final Collection<String> chordMap = ChordMap.getAllChordTypes(this.currentChordLength);
        this.chordNamesDynamicAboveHBox.getChildren().clear();
        this.chordNamesDynamicBelowHBox.getChildren().clear();
        for (final String chord : chordMap) {
            final ToggleButton chordToggleBtn = new ToggleButton(Character.toUpperCase(chord.charAt(0)) + chord.substring(1));
            chordToggleBtn.setToggleGroup(this.chordToggleGroup);
            chordToggleBtn.setId("exploreBtn");
            chordToggleBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
                public void handle(final ActionEvent event) {
                    ChordController.this.selectChordType(ChordController.this.getChordType());
                }
            });
            if (this.chordNamesDynamicAboveHBox.getChildren().size() < ChordController.MAX_ABOVE_CHILDREN) {
                this.chordNamesDynamicAboveHBox.getChildren().add((Object)chordToggleBtn);
            }
            else {
                this.chordNamesDynamicBelowHBox.getChildren().add((Object)chordToggleBtn);
            }
            if (this.currentChordType != null && chordToggleBtn.getText().equals(this.currentChordType)) {
                chordToggleBtn.setSelected(true);
            }
        }
        this.chordNamesDynamicAboveHBox.setAlignment(Pos.CENTER);
        this.chordNamesDynamicBelowHBox.setAlignment(Pos.CENTER);
    }
    
    public void moveToLowerChord() {
        if (this.currentChordLength > this.MIN_CHORD_LENGTH) {
            --this.currentChordLength;
            this.updateChordNames();
            this.leftBtn.setDisable(true);
            this.rightBtn.setDisable(false);
        }
        else {
            this.leftBtn.setDisable(false);
        }
    }
    
    public void moveToHigherChord() {
        if (this.currentChordLength < this.MAX_CHORD_LENGTH) {
            ++this.currentChordLength;
            this.updateChordNames();
            this.rightBtn.setDisable(true);
            this.leftBtn.setDisable(false);
        }
        else {
            this.rightBtn.setDisable(false);
        }
    }
    
    public void resetToggleBtn() {
        final ToggleButton toggleButton = (ToggleButton)this.chordToggleGroup.getToggles().get(0);
        this.chordToggleGroup.selectToggle((Toggle)toggleButton);
        this.currentChordType = toggleButton.getText();
    }
    
    static {
        MAX_ABOVE_CHILDREN = 4;
    }
}
