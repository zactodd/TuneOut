// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.SheetElement;

import javafx.scene.input.MouseButton;
import java.util.Map;
import java.util.Iterator;
import Controller.LearningCompose.SheetController;
import javafx.scene.input.MouseEvent;
import Controller.LearningCompose.NoteNotSnapException;
import Model.CommandMessages;
import Controller.LearningCompose.NoteNotShownException;
import Model.Note.Note;
import Controller.LearningCompose.ElementDuration;
import org.controlsfx.control.PopOver;
import javafx.scene.image.ImageView;
import seng302.App;
import javafx.scene.image.Image;
import Controller.LearningCompose.OuterExploreCompose;

public class SheetComposeNoteRest extends SheetElement
{
    public SheetComposeNoteRest(final OuterExploreCompose outer) {
        this.sheetImage = new Image("View/LearningCompose/graphic/Crotchet.png");
        this.cursorImage = new Image("View/LearningCompose/graphic/Cursors/cursor_note.png");
        this.outer = outer;
        this.firstPosX = 250.0;
        this.env.setOuterTemplateController(App.getOuterTemplate());
    }
    
    @Override
    PopOver setPopOver(final ImageView imageView) {
        return null;
    }
    
    @Override
    public void placeElements(final Double firstLayoutX) {
        this.placedX = firstLayoutX;
        final SheetController sheet = this.outer.getSheetController();
        sheet.noteWarning.setVisible(false);
        if (this.rootNote != null) {
            ImageView elementImg = null;
            try {
                if (this.elementDuration.getElementType().equals(ElementDuration.ElementType.REST)) {
                    final Boolean treble = this.rootNote.getMidiNumber() > 58;
                    this.rootNote = new Note("R", -1, -1, true);
                    Boolean valid = true;
                    if (!sheet.getMoveNoteMode() && !sheet.getIsKeyboardInputMode()) {
                        Double snappedCoord = sheet.snapToXCoord(firstLayoutX);
                        if (snappedCoord == null) {
                            snappedCoord = firstLayoutX;
                        }
                        for (final SheetElement sheetElement : this.outer.getSheetElements()) {
                            if (sheetElement.getPlacedX().equals(snappedCoord)) {
                                valid = false;
                            }
                        }
                    }
                    if (valid) {
                        this.elementImages = sheet.placeRest(firstLayoutX, treble, this.elementDuration.getElementImage(), this.elementDuration.getOffsetY());
                        elementImg = this.elementImages.get("rest");
                        this.placedX = this.elementImages.get("rest").getLayoutX();
                    }
                    else {
                        this.outer.getSheetElements().remove(this);
                    }
                }
                else {
                    Boolean valid2 = true;
                    if (!sheet.getMoveNoteMode() && !sheet.getIsKeyboardInputMode()) {
                        Double snappedCoord2 = sheet.snapToXCoord(firstLayoutX);
                        if (snappedCoord2 == null) {
                            snappedCoord2 = firstLayoutX;
                        }
                        for (final SheetElement sheetElement2 : this.outer.getSheetElements()) {
                            if (sheetElement2.getPlacedX().equals(snappedCoord2) && sheetElement2.getRootNote().getNoteWithOctave().equals(this.rootNote.getNoteWithOctave())) {
                                valid2 = false;
                            }
                        }
                    }
                    if (valid2) {
                        this.elementImages = sheet.placeNote(this.rootNote, firstLayoutX, this.elementDuration.getElementImage());
                        this.placedX = this.elementImages.get("note").getLayoutX();
                        elementImg = this.elementImages.get("note");
                    }
                    else {
                        this.outer.getSheetElements().remove(this);
                    }
                }
            }
            catch (NoteNotShownException e) {
                sheet.noteWarning.setText("Some notes or rests could not be shown");
                sheet.noteWarning.setVisible(true);
                this.outer.getSheetElements().remove(this);
            }
            catch (NoteNotSnapException e2) {
                sheet.noteWarning.setText(CommandMessages.getMessage("CANNOT_SNAP_NOTE"));
                sheet.noteWarning.setVisible(true);
                this.outer.getSheetElements().remove(this);
            }
            if (elementImg != null) {
                elementImg.setPickOnBounds(true);
                if (this.elementDuration.getDotted()) {
                    this.elementImages.put("dot", sheet.placeDot(elementImg));
                }
                final Map<String, ImageView> newImages = this.elementImages;
                elementImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getButton().equals((Object)MouseButton.SECONDARY)) {
                        final Note note = sheet.getNoteNameFromStave(sheet.getMouseCoord(), false);
                        if (note != null) {
                            final SheetElement clickedSheetElement = this.findClickedSheetElement(note);
                            if (clickedSheetElement != null) {
                                for (final ImageView h : clickedSheetElement.getElementImages().values()) {
                                    sheet.stave.getChildren().remove((Object)h);
                                }
                                this.outer.getSheetElements().remove(clickedSheetElement);
                                sheet.getPositionXTracker().removeXCoord(clickedSheetElement.getPlacedX());
                            }
                        }
                        else {
                            for (final ImageView img : newImages.values()) {
                                sheet.stave.getChildren().remove((Object)img);
                            }
                            this.outer.getSheetElements().remove(this);
                            sheet.getPositionXTracker().removeXCoord(this.getPlacedX());
                        }
                    }
                    else if (event.getButton().equals((Object)MouseButton.PRIMARY)) {
                        final SheetElement newElement = new SheetComposeNoteRest(this.outer);
                        final Note note2 = sheet.getNoteNameFromStave(sheet.getMouseCoord(), false);
                        if (note2 != null) {
                            newElement.setRootNote(note2);
                            newElement.setAccidental(this.outer.getTopToolboxController().getAccidental());
                            final Double clickedX = sheet.getMouseCoord().getX() - 20.0;
                            Double snapCoord = sheet.snapToXCoord(clickedX);
                            if (snapCoord == null) {
                                snapCoord = clickedX;
                            }
                            newElement.setElementDuration(this.outer.getTopToolboxController().getDuration());
                            final SheetElement clickedSheetElement2 = this.findClickedSheetElement(note2);
                            if (clickedSheetElement2 != null && clickedSheetElement2.getElementImages() != null) {
                                this.outer.setSelectedElement(clickedSheetElement2);
                                this.outer.syncTopToolbox(this.outer.getSelectedElement());
                            }
                            else if (clickedSheetElement2 != null && clickedSheetElement2.getElementImages() == null) {
                                this.outer.getSheetElements().remove(clickedSheetElement2);
                            }
                            else {
                                newElement.placeElements(this.getPlacedX());
                                this.outer.getSheetElements().add(newElement);
                            }
                        }
                    }
                });
            }
        }
    }
}
