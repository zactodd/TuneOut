

package Controller.LearningCompose.SheetElement;

import javafx.event.ActionEvent;
import java.util.Iterator;
import javafx.scene.input.MouseButton;
import Controller.LearningCompose.Coord;
import Controller.LearningCompose.SheetController;
import javafx.scene.input.MouseEvent;
import Controller.LearningCompose.NoteNotSnapException;
import Model.CommandMessages;
import Controller.LearningCompose.NoteNotShownException;
import java.util.Map;
import java.util.HashMap;
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

public class SheetSignatureNote extends SheetElement
{
    public SheetSignatureNote(final OuterExploreCompose outer) {
        this.sheetImage = new Image("View/LearningCompose/graphic/Crotchet.png");
        this.cursorImage = new Image("View/LearningCompose/graphic/Cursors/cursor_note.png");
        this.outer = outer;
        this.firstPosX = 300.0;
        this.env.setOuterTemplateController(App.getOuterTemplate());
        this.allowMultiple = true;
    }
    
    String getBodyText() {
        final String noteWithOctave = this.rootNote.getNoteWithOctave();
        final StringBuilder allText = new StringBuilder();
        allText.append("Key: ").append(this.outer.getSignature().type);
        return allText.toString();
    }
    
    @Override
    PopOver setPopOver(final ImageView imageView) {
        final Note popoverNote = this.rootNote;
        (this.elementPopOver = new PopOver()).setDetachable(true);
        this.elementPopOver.setAnimated(true);
        this.elementPopOver.setConsumeAutoHidingEvents(false);
        this.elementPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        this.elementPopOver.setHeaderAlwaysVisible(true);
        this.elementPopOver.setTitle(String.format("Note: %1$s %2$s", popoverNote.getNoteWithOctave(), this.accidental.equals("n") ? "natural" : ""));
        final Label label = new Label();
        label.setId("keyLabel");
        label.setMaxWidth(150.0);
        label.setWrapText(true);
        label.setText(this.getBodyText());
        final Button openKeySigTutor = new Button();
        openKeySigTutor.setOnAction(event -> {
            this.parser.executeCommand("keySignatureTutor()");
            if (!this.elementPopOver.isDetached()) {
                this.elementPopOver.hide();
            }
        });
        openKeySigTutor.setText("Key Signature Tutor");
        final Button playNote = new Button();
        playNote.setId("play");
        playNote.setOnMouseClicked(event -> this.parser.executeCommand(String.format("play(%s)", popoverNote.getNoteWithOctave())));
        final Image play = new Image("View/LearningCompose/graphic/play_small.png");
        playNote.setGraphic((Node)new ImageView(play));
        final HBox horizBox = new HBox();
        horizBox.setSpacing(10.0);
        horizBox.getChildren().add((Object)playNote);
        horizBox.getChildren().add((Object)openKeySigTutor);
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
        Boolean isValid = false;
        this.elementImages = new HashMap<String, ImageView>();
        final SheetController sheet = this.outer.getSheetController();
        final String[] typeArr = this.outer.getSignature().type.split(" ");
        final String keyNote = typeArr[0];
        final String keyScale = typeArr[1];
        sheet.noteWarning.setVisible(false);
        if (this.rootNote != null) {
            try {
                this.placedX = firstLayoutX;
                this.elementImages.putAll(sheet.placeNote(this.rootNote, firstLayoutX, this.sheetImage));
                isValid = true;
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
            if (isValid) {
                if (this.accidental.equals("n")) {
                    this.elementImages = this.adjustForNatural(this.elementImages);
                }
                else {
                    this.rootNote = sheet.adjustNoteForKey(this.rootNote, keyNote, keyScale);
                }
                final ImageView noteImg = this.elementImages.get("note");
                final PopOver notePopOver = this.setPopOver(noteImg);
                noteImg.setPickOnBounds(true);
                final Map<String, ImageView> newImages = this.elementImages;
                noteImg.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getButton().equals((Object)MouseButton.SECONDARY)) {
                        for (final Map.Entry<String, ImageView> img : newImages.entrySet()) {
                            if (!img.getKey().startsWith("key")) {
                                sheet.stave.getChildren().remove((Object)img.getValue());
                            }
                            this.outer.getSheetElements().remove(this);
                        }
                    }
                    else {
                        notePopOver.show((Node)noteImg);
                        this.outer.setSelectedElement(this);
                        this.outer.syncTopToolbox(this);
                    }
                });
            }
        }
    }
    
    private Map<String, ImageView> adjustForNatural(final Map<String, ImageView> elementImages) {
        final SheetController sheet = this.outer.getSheetController();
        final Coord coord = new Coord(elementImages.get("note").getLayoutX(), elementImages.get("note").getLayoutY());
        if (elementImages.containsKey("accidental")) {
            sheet.stave.getChildren().remove((Object)elementImages.get("accidental"));
            elementImages.remove("accidental");
        }
        final ImageView natualAccidental = sheet.insertAccidental("n", coord);
        elementImages.put("accidental", natualAccidental);
        this.rootNote = this.rootNote.getBaseNote();
        return elementImages;
    }
}
