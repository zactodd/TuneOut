// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.SheetElement;

import javafx.event.ActionEvent;
import java.util.Iterator;
import javafx.scene.input.MouseButton;
import java.util.Map;
import Controller.LearningCompose.SheetController;
import javafx.scene.input.MouseEvent;
import Controller.LearningCompose.NoteNotSnapException;
import Model.CommandMessages;
import Controller.LearningCompose.NoteNotShownException;
import Model.Note.Note;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;
import javafx.scene.image.ImageView;
import seng302.App;
import javafx.scene.image.Image;
import Controller.LearningCompose.OuterExploreCompose;

public class SheetNote extends SheetElement
{
    public SheetNote(final OuterExploreCompose outer) {
        this.sheetImage = new Image("View/LearningCompose/graphic/Crotchet.png");
        this.cursorImage = new Image("View/LearningCompose/graphic/Cursors/cursor_note.png");
        this.outer = outer;
        this.firstPosX = 250.0;
        this.env.setOuterTemplateController(App.getOuterTemplate());
        this.allowMultiple = true;
    }
    
    String getBodyText() {
        final String noteWithOctave = this.rootNote.getNoteWithOctave();
        final StringBuilder allText = new StringBuilder();
        this.parser.executeCommand(String.format("midi(%s)", noteWithOctave));
        allText.append("Midi: ").append(this.env.getResponse());
        this.parser.executeCommand(String.format("enharmonicHigh(%s)", noteWithOctave));
        allText.append("\nEnharmonic High: ").append(this.env.getResponse());
        this.parser.executeCommand(String.format("enharmonicLow(%s)", noteWithOctave));
        allText.append("\nEnharmonic Low: ").append(this.env.getResponse());
        return allText.toString();
    }
    
    public PopOver setPopOver(final ImageView imageView) {
        final Note popoverNote = this.rootNote;
        (this.elementPopOver = new PopOver()).setDetachable(true);
        this.elementPopOver.setAnimated(true);
        this.elementPopOver.setConsumeAutoHidingEvents(false);
        this.elementPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        this.elementPopOver.setHeaderAlwaysVisible(true);
        this.elementPopOver.setTitle(String.format("Note: %s", popoverNote.getNoteWithOctave()));
        final Label label = new Label();
        label.setMaxWidth(150.0);
        label.setWrapText(true);
        label.setText(this.getBodyText());
        final Button openPitchTutor = new Button();
        openPitchTutor.setOnAction(event -> {
            this.parser.executeCommand("pitchTutor()");
            if (!this.elementPopOver.isDetached()) {
                this.elementPopOver.hide();
            }
        });
        openPitchTutor.setText("Pitch Tutor");
        final Button playNote = new Button();
        playNote.setId("play");
        playNote.setOnMouseClicked(event -> this.parser.executeCommand(String.format("play(%s)", popoverNote.getNoteWithOctave())));
        final Image play = new Image("View/LearningCompose/graphic/play_small.png");
        playNote.setGraphic((Node)new ImageView(play));
        final HBox horizBox = new HBox();
        horizBox.setSpacing(10.0);
        horizBox.getChildren().add((Object)playNote);
        horizBox.getChildren().add((Object)openPitchTutor);
        final VBox verticalBox = new VBox();
        verticalBox.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        verticalBox.setSpacing(10.0);
        verticalBox.getChildren().add((Object)label);
        verticalBox.getChildren().add((Object)horizBox);
        final Pane pane = new Pane();
        pane.getChildren().add((Object)verticalBox);
        this.elementPopOver.setContentNode((Node)pane);
        this.elementPopOver.show((Node)imageView);
        return this.elementPopOver;
    }
    
    @Override
    public void placeElements(final Double firstLayoutX) {
        this.placedX = firstLayoutX;
        Boolean isNotePlaced = false;
        final SheetController sheet = this.outer.getSheetController();
        sheet.noteWarning.setVisible(false);
        this.placedX = firstLayoutX;
        if (this.rootNote != null) {
            try {
                this.elementImages = sheet.placeNote(this.rootNote, firstLayoutX, this.sheetImage);
                isNotePlaced = true;
                this.outer.setSelectedElement(this);
            }
            catch (NoteNotShownException e) {
                sheet.noteWarning.setText("The note could not be shown");
                sheet.noteWarning.setVisible(true);
            }
            catch (NoteNotSnapException e2) {
                if (sheet.getComposeMode()) {
                    sheet.noteWarning.setText(CommandMessages.getMessage("CANNOT_SNAP_NOTE"));
                }
                else {
                    sheet.noteWarning.setText(CommandMessages.getMessage("CAN_ONLY_PLACE_ONE_NOTE"));
                }
                sheet.noteWarning.setVisible(true);
            }
            if (isNotePlaced) {
                final ImageView noteImg = this.elementImages.get("note");
                final PopOver notePopOver = this.setPopOver(noteImg);
                noteImg.setPickOnBounds(true);
                final Map<String, ImageView> newImages = this.elementImages;
                noteImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getButton().equals((Object)MouseButton.SECONDARY)) {
                        for (final ImageView img : newImages.values()) {
                            sheet.stave.getChildren().remove((Object)img);
                        }
                        sheet.setIsCheckingMode(true);
                        Double snappedCoord = sheet.snapToXCoord(this.getPlacedX());
                        sheet.setIsCheckingMode(false);
                        if (snappedCoord == null) {
                            snappedCoord = this.getPlacedX();
                        }
                        this.outer.getSheetElements().remove(this);
                        sheet.getPositionXTracker().removeXCoord(snappedCoord);
                    }
                    else {
                        final Note note = sheet.getNoteNameFromStave(sheet.getMouseCoord(), false);
                        if (note != null) {
                            final SheetElement clickedSheetElement = this.findClickedSheetElement(note);
                            if (clickedSheetElement != null) {
                                notePopOver.show((Node)noteImg);
                                this.outer.setSelectedElement(this);
                                this.outer.syncTopToolbox(this);
                            }
                            else {
                                sheet.noteWarning.setText(CommandMessages.getMessage("CAN_ONLY_PLACE_ONE_NOTE"));
                                sheet.noteWarning.setVisible(true);
                            }
                            this.outer.syncTopToolbox(this);
                        }
                    }
                });
            }
        }
    }
}
