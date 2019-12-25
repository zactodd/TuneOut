// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.SheetElement;

import javafx.scene.input.MouseButton;
import java.util.Map;
import java.util.Iterator;
import Controller.LearningCompose.SheetController;
import Controller.LearningCompose.NoteNotSnapException;
import Controller.LearningCompose.NoteNotShownException;
import javafx.scene.input.MouseEvent;
import Controller.LearningCompose.PercussionCategory;
import org.controlsfx.control.PopOver;
import javafx.scene.image.ImageView;
import seng302.App;
import javafx.scene.image.Image;
import Controller.LearningCompose.OuterExploreCompose;

public class SheetComposePercussion extends SheetElement
{
    public SheetComposePercussion(final OuterExploreCompose outer) {
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
        this.rootNote = this.percussionInfo.getPositionOnStave();
        this.placedX = firstLayoutX;
        final SheetController sheet = this.outer.getSheetController();
        sheet.noteWarning.setVisible(false);
        ImageView elementImg = null;
        try {
            if (this.rootNote.getMidiNumber().equals(-1)) {
                Boolean valid = true;
                if (!sheet.getMoveNoteMode() && !sheet.getIsKeyboardInputMode()) {
                    Double snappedCoord = sheet.snapToXCoord(firstLayoutX);
                    if (snappedCoord == null) {
                        snappedCoord = firstLayoutX;
                    }
                    this.placedX = snappedCoord;
                    for (final SheetElement sheetElement : this.outer.getSheetElements()) {
                        if (!sheetElement.equals(this) && sheetElement.getPlacedX().equals(snappedCoord) && sheetElement.getRootNote().getMidiNumber().equals(-1)) {
                            valid = false;
                            break;
                        }
                    }
                }
                if (valid) {
                    this.elementImages = sheet.placeRest(firstLayoutX, true, this.percussionInfo.getElementImage(), -40.0);
                    elementImg = this.elementImages.get("rest");
                }
            }
            else {
                Boolean valid = true;
                if (!sheet.getMoveNoteMode() && !sheet.getIsKeyboardInputMode()) {
                    Double snappedCoord = sheet.snapToXCoord(firstLayoutX);
                    if (snappedCoord == null) {
                        snappedCoord = firstLayoutX;
                    }
                    this.placedX = snappedCoord;
                    for (final SheetElement sheetElement : this.outer.getSheetElements()) {
                        if (!sheetElement.equals(this) && sheetElement.getPlacedX().equals(snappedCoord) && sheetElement.getPercussionInfo().getPercussion().equals(this.percussionInfo.getPercussion())) {
                            valid = false;
                            break;
                        }
                    }
                }
                if (valid) {
                    this.elementImages = sheet.placeNote(this.rootNote, firstLayoutX, this.percussionInfo.getElementImage());
                    elementImg = this.elementImages.get("note");
                    if (this.percussionInfo.getCategory() != null && this.percussionInfo.getCategory().equals(PercussionCategory.TRIANGLE_MIDDLE)) {
                        sheet.rotateImage(this.elementImages.get("note"), this.elementImages.get("note").getImage(), true);
                    }
                }
                else {
                    this.outer.getSheetElements().remove(this);
                }
            }
            if (elementImg != null) {
                elementImg.setPickOnBounds(true);
                final Map<String, ImageView> newImages = this.elementImages;
                elementImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getButton().equals((Object)MouseButton.SECONDARY)) {
                        for (final ImageView img : newImages.values()) {
                            sheet.stave.getChildren().remove((Object)img);
                        }
                        this.outer.getSheetElements().removeIf(element -> element.equals(this));
                        sheet.getPositionXTracker().removeXCoord(this.getPlacedX());
                    }
                    else {
                        this.outer.setSelectedElement(this);
                        this.outer.syncTopToolbox(this);
                    }
                });
            }
        }
        catch (NoteNotShownException e2) {
            sheet.noteWarning.setText("Some percussion symbols could not be shown");
            sheet.noteWarning.setVisible(true);
            this.outer.getSheetElements().remove(this);
        }
        catch (NoteNotSnapException e) {
            e.printStackTrace();
        }
    }
}
