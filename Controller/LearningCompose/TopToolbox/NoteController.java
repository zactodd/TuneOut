// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.TopToolbox;

import Model.Note.Note;
import javafx.event.ActionEvent;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class NoteController
{
    public ToggleGroup toggleGroupNotes;
    @FXML
    ToggleButton c;
    @FXML
    ToggleButton d;
    @FXML
    ToggleButton e;
    @FXML
    ToggleButton f;
    @FXML
    ToggleButton g;
    @FXML
    ToggleButton a;
    @FXML
    ToggleButton b;
    @FXML
    Button leftBtn;
    @FXML
    Button rightBtn;
    @FXML
    AnchorPane noteAnchorPane;
    private TopToolboxController topToolboxController;
    private List<ToggleButton> notes;
    private Integer currentOctave;
    private final Integer lowerBoundOctave;
    private final Integer upperBoundOctave;
    
    public NoteController() {
        this.notes = Arrays.asList(this.c, this.d, this.e, this.f, this.g, this.a, this.b);
        this.currentOctave = 4;
        this.lowerBoundOctave = 2;
        this.upperBoundOctave = 5;
    }
    
    public void setTopToolboxController(final TopToolboxController topToolboxController) {
        this.topToolboxController = topToolboxController;
    }
    
    private void updateNotesLabel() {
        this.notes = Arrays.asList(this.c, this.d, this.e, this.f, this.g, this.a, this.b);
        if (this.currentOctave == this.lowerBoundOctave) {
            this.rightBtn.setDisable(false);
            this.leftBtn.setDisable(true);
        }
        else if (this.currentOctave == this.upperBoundOctave) {
            this.leftBtn.setDisable(false);
            this.rightBtn.setDisable(true);
        }
        else {
            this.rightBtn.setDisable(false);
            this.leftBtn.setDisable(false);
        }
        for (final ToggleButton note : this.notes) {
            note.setText(note.getId().toUpperCase() + this.currentOctave);
            note.setSelected(false);
        }
    }
    
    @FXML
    protected void placeNote(final ActionEvent event) {
        final ToggleButton clickedBtn = (ToggleButton)event.getSource();
        clickedBtn.setSelected(true);
        this.topToolboxController.placeNote("");
        clickedBtn.requestFocus();
    }
    
    @FXML
    protected void moveToLowerOctave() {
        if (this.currentOctave - 1 > this.lowerBoundOctave) {
            --this.currentOctave;
            this.updateNotesLabel();
        }
        else {
            this.currentOctave = this.lowerBoundOctave;
            this.updateNotesLabel();
        }
    }
    
    @FXML
    protected void moveToHigherOctave() {
        if (this.currentOctave + 1 < this.upperBoundOctave) {
            ++this.currentOctave;
            this.updateNotesLabel();
        }
        else {
            this.currentOctave = this.upperBoundOctave;
            this.updateNotesLabel();
        }
    }
    
    public void unselectNote() {
        this.notes = Arrays.asList(this.c, this.d, this.e, this.f, this.g, this.a, this.b);
        for (final ToggleButton note : this.notes) {
            note.setSelected(false);
        }
    }
    
    protected void updateOctaveAndButton(final Note note) {
        this.currentOctave = note.getOctave();
        this.updateNotesLabel();
        this.notes = Arrays.asList(this.c, this.d, this.e, this.f, this.g, this.a, this.b);
        for (final ToggleButton noteBtn : this.notes) {
            String noteStr = note.getNoteName();
            noteStr = noteStr.substring(0, 1);
            if (noteBtn.getId().equals(noteStr.toLowerCase())) {
                noteBtn.setSelected(true);
            }
        }
    }
    
    public String getSelectedNote() {
        final ToggleButton tog = (ToggleButton)this.toggleGroupNotes.getSelectedToggle();
        if (tog != null) {
            return tog.getText();
        }
        return null;
    }
    
    protected void toggleDisableNotes(final Boolean disable) {
        for (final ToggleButton noteBtn : this.notes) {
            noteBtn.setDisable((boolean)disable);
        }
        if (disable) {
            this.leftBtn.setDisable(true);
            this.rightBtn.setDisable(true);
        }
        else if (this.currentOctave.equals(this.lowerBoundOctave)) {
            this.rightBtn.setDisable(false);
            this.leftBtn.setDisable(true);
        }
        else if (this.currentOctave.equals(this.upperBoundOctave)) {
            this.leftBtn.setDisable(false);
            this.rightBtn.setDisable(true);
        }
        else {
            this.rightBtn.setDisable(false);
            this.leftBtn.setDisable(false);
        }
    }
}
