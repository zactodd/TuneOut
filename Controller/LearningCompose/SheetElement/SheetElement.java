// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.SheetElement;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.effect.Effect;
import Controller.LearningCompose.NoteNotSnapException;
import Controller.LearningCompose.NoteNotShownException;
import java.util.List;
import javafx.scene.input.MouseEvent;
import Controller.LearningCompose.SheetController;
import java.util.Iterator;
import java.util.HashSet;
import javafx.scene.paint.Color;
import Model.Play.Play;
import java.util.Set;
import Controller.LearningCompose.PercussionSheetInfo;
import Controller.LearningCompose.ElementDuration;
import javafx.scene.effect.DropShadow;
import Controller.LearningCompose.OuterExploreCompose;
import org.controlsfx.control.PopOver;
import javafx.scene.image.ImageView;
import java.util.Map;
import Model.Note.Note;
import javafx.scene.image.Image;
import Environment.GrammarParser;
import Environment.Environment;

public abstract class SheetElement
{
    protected Environment env;
    protected GrammarParser parser;
    protected Image sheetImage;
    protected Image cursorImage;
    protected String accidental;
    protected Note rootNote;
    protected Map<String, ImageView> elementImages;
    protected PopOver elementPopOver;
    protected String type;
    protected OuterExploreCompose outer;
    protected String inversion;
    protected String arrangement;
    protected Boolean selected;
    protected Double firstPosX;
    protected DropShadow shadowEffect;
    protected ElementDuration elementDuration;
    protected PercussionSheetInfo percussionInfo;
    protected Double placedX;
    private Set<Note> chord;
    protected Boolean allowMultiple;
    
    public SheetElement() {
        this.env = new Environment(new Play(Play.PlayType.REPLACE));
        this.parser = new GrammarParser(this.env);
        this.sheetImage = null;
        this.cursorImage = null;
        this.selected = false;
        this.shadowEffect = new DropShadow(20.0, Color.RED);
        this.chord = new HashSet<Note>();
        this.allowMultiple = false;
    }
    
    SheetElement findClickedSheetElement(final Note note) {
        SheetElement found = null;
        for (final SheetElement sheetElement : this.outer.getSheetElements()) {
            if (sheetElement.getPlacedX().equals(this.getPlacedX()) && (sheetElement.getRootNote().getBaseNoteWithOctave().equals(note.getBaseNoteWithOctave()) || sheetElement.getRootNote().getMidiNumber().equals(-1))) {
                found = sheetElement;
            }
        }
        return found;
    }
    
    void addMouseClickedHandler(final SheetController sheetController) {
        final ImageView firstImg = this.elementImages.get("note");
        if (firstImg != null) {
            final PopOver popOver = this.setPopOver(firstImg);
            firstImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getButton().equals((Object)MouseButton.SECONDARY)) {
                    sheetController.clearStave();
                }
                else {
                    popOver.show((Node)firstImg);
                    this.outer.setSelectedElement(this);
                    this.outer.syncTopToolbox(this);
                }
            });
        }
    }
    
    void placeNoteImage(final SheetController sheet, final Double firstLayoutX, final List<Note> notes) {
        String warningText = "The following notes are not shown:\n";
        Boolean isWarning = false;
        Double runningX = firstLayoutX;
        int counter = 1;
        for (final Note note : notes) {
            Map<String, ImageView> images = null;
            try {
                images = sheet.placeNote(note, runningX, this.sheetImage);
                for (final Map.Entry<String, ImageView> entry : images.entrySet()) {
                    if (counter == 1) {
                        this.elementImages.put(entry.getKey(), entry.getValue());
                    }
                    else {
                        this.elementImages.put(entry.getKey() + counter, entry.getValue());
                    }
                }
            }
            catch (NoteNotShownException e2) {
                isWarning = true;
                warningText = warningText + " " + note.getNoteWithOctave();
            }
            catch (NoteNotSnapException e) {
                e.printStackTrace();
            }
            runningX += SheetController.GAP_BETWEEN_NOTES;
            ++counter;
        }
        if (isWarning) {
            sheet.noteWarning.setText(warningText);
            sheet.noteWarning.setVisible(true);
        }
    }
    
    abstract PopOver setPopOver(final ImageView p0);
    
    public abstract void placeElements(final Double p0);
    
    public void moveElement() {
        Double xcoord = 290.0;
        if (this.elementImages != null) {
            for (final Map.Entry<String, ImageView> entry : this.elementImages.entrySet()) {
                if (entry.getKey().equals("note") || entry.getKey().equals("rest")) {
                    xcoord = entry.getValue().getLayoutX();
                }
                this.outer.getSheetController().stave.getChildren().remove((Object)entry.getValue());
            }
            this.placeElements(xcoord);
        }
    }
    
    public void setRootNote(final Note rootNote) {
        this.rootNote = rootNote;
    }
    
    public Note getRootNote() {
        return this.rootNote;
    }
    
    public void setAccidental(final String accidental) {
        this.accidental = accidental;
    }
    
    public String getAccidental() {
        return this.accidental;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    
    public Map<String, ImageView> getElementImages() {
        return this.elementImages;
    }
    
    public PopOver getPopUp() {
        return this.elementPopOver;
    }
    
    public void setInversion(final String inversion) {
        this.inversion = inversion;
    }
    
    public void setArrangement(final String arrangement) {
        this.arrangement = arrangement;
    }
    
    public void setSelected(final Boolean selected) {
        this.selected = selected;
    }
    
    public Boolean getSelected() {
        return this.selected;
    }
    
    public Double getFirstPosX() {
        return this.firstPosX;
    }
    
    public Image getCursorImage() {
        return this.cursorImage;
    }
    
    public Double getPlacedX() {
        return this.placedX;
    }
    
    public ElementDuration getElementDuration() {
        return this.elementDuration;
    }
    
    public void setElementDuration(final ElementDuration elementDuration) {
        this.elementDuration = elementDuration;
    }
    
    public Boolean getAllowMultiple() {
        return this.allowMultiple;
    }
    
    public PercussionSheetInfo getPercussionInfo() {
        return this.percussionInfo;
    }
    
    public void setPercussionInfo(final PercussionSheetInfo percussionInfo) {
        this.percussionInfo = percussionInfo;
    }
    
    public Set<Note> getChord() {
        return this.chord;
    }
    
    public void clearPopOver() {
        if (this.elementPopOver != null) {
            this.elementPopOver.hide();
        }
    }
    
    public void setSelectedBorder() {
        for (final Map.Entry<String, ImageView> entry : this.elementImages.entrySet()) {
            if (entry.getKey().equals("note") || entry.getKey().equals("rest")) {
                entry.getValue().setEffect((Effect)this.shadowEffect);
            }
        }
    }
    
    public void removeSelectedBorder() {
        if (this.elementImages != null) {
            for (final Map.Entry<String, ImageView> entry : this.elementImages.entrySet()) {
                if (entry.getKey().equals("note") || entry.getKey().equals("rest")) {
                    entry.getValue().setEffect((Effect)null);
                }
            }
        }
    }
}
