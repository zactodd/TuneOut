// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.SheetElement;

import Model.CommandMessages;
import javafx.event.ActionEvent;
import java.util.Iterator;
import Controller.LearningCompose.SheetController;
import java.util.Arrays;
import Model.Note.Note;
import Model.Note.NoteMap;
import Model.Note.Intervals.IntervalMap;
import java.util.HashMap;
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

public class SheetInterval extends SheetElement
{
    private static final String NO_ENHARMONIC_EQUIVALENT;
    private static final double FIRST_POS_X_INTERVAL = 200.0;
    
    public SheetInterval(final OuterExploreCompose outer) {
        this.sheetImage = new Image("View/LearningCompose/graphic/Crotchet.png");
        this.cursorImage = new Image("View/LearningCompose/graphic/Cursors/cursor_interval.png");
        this.outer = outer;
        this.firstPosX = 200.0;
        if (this.type == null || this.type.isEmpty()) {
            this.type = "Major 2nd";
        }
        this.env.setOuterTemplateController(App.getOuterTemplate());
    }
    
    String getBodyText() {
        final String noteWithOctave = this.rootNote.getNoteWithOctave();
        final StringBuilder allText = new StringBuilder();
        this.parser.executeCommand(String.format("interval(%s, \"%s\")", noteWithOctave, this.type));
        allText.append(String.format("Notes: %s, %s", this.rootNote, this.env.getResponse()));
        this.parser.executeCommand(String.format("enharmonicInterval(\"%s\")", this.type));
        final String result = this.env.getResponse();
        allText.append(String.format("\nEnharmonic Intervals: %s", result.equals(SheetInterval.NO_ENHARMONIC_EQUIVALENT) ? "N/A" : result));
        return allText.toString();
    }
    
    public PopOver setPopOver(final ImageView imageView) {
        (this.elementPopOver = new PopOver()).setDetachable(true);
        this.elementPopOver.setAnimated(true);
        this.elementPopOver.setConsumeAutoHidingEvents(false);
        this.elementPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        this.elementPopOver.setHeaderAlwaysVisible(true);
        this.elementPopOver.setTitle(String.format("     Interval: %s %s     ", this.rootNote, this.type));
        final Label label = new Label();
        label.setMaxWidth(2.147483647E9);
        label.setWrapText(true);
        label.setText(this.getBodyText());
        final Button openIntervalTutor = new Button();
        openIntervalTutor.setOnAction(event -> {
            this.parser.executeCommand("intervalTutor()");
            if (!this.elementPopOver.isDetached()) {
                this.elementPopOver.hide();
            }
        });
        openIntervalTutor.setText("Interval Tutor");
        final Button playNote = new Button();
        playNote.setId("play");
        playNote.setOnAction(event -> this.parser.executeCommand(String.format("playInterval(%s, \"%s\")", this.rootNote.getNoteWithOctave(), this.type)));
        final Image play = new Image("View/LearningCompose/graphic/play_small.png");
        playNote.setGraphic((Node)new ImageView(play));
        final HBox horizBox = new HBox();
        horizBox.setSpacing(10.0);
        horizBox.getChildren().add((Object)playNote);
        horizBox.getChildren().add((Object)openIntervalTutor);
        final VBox verticalBox = new VBox();
        verticalBox.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        verticalBox.setSpacing(10.0);
        verticalBox.getChildren().add((Object)label);
        verticalBox.getChildren().add((Object)horizBox);
        final Pane pane = new Pane();
        pane.getChildren().add((Object)verticalBox);
        this.elementPopOver.setContentNode((Node)pane);
        if (imageView != null) {
            this.elementPopOver.show((Node)imageView);
        }
        return this.elementPopOver;
    }
    
    @Override
    public void placeElements(final Double firstLayoutX) {
        this.placedX = firstLayoutX;
        final SheetController sheet = this.outer.getSheetController();
        sheet.noteWarning.setVisible(false);
        final String warningText = "The following note is not shown:";
        this.elementImages = new HashMap<String, ImageView>();
        final Boolean isWarning = false;
        for (final SheetElement element : this.outer.getSheetElements()) {
            for (final ImageView image : element.elementImages.values()) {
                this.outer.getSheetController().stave.getChildren().remove((Object)image);
            }
        }
        final Integer semitone = IntervalMap.getIntervalWithIntervalName(this.type).getSemitone();
        final Note intervalNote = NoteMap.getNoteFromMidi(this.rootNote.getMidiNumber() + semitone);
        this.placeNoteImage(sheet, firstLayoutX, Arrays.asList(this.rootNote, intervalNote));
        this.addMouseClickedHandler(sheet);
        this.outer.setSelectedElement(this);
        if (isWarning) {
            sheet.noteWarning.setText(warningText);
            sheet.noteWarning.setVisible(true);
        }
    }
    
    static {
        NO_ENHARMONIC_EQUIVALENT = CommandMessages.getMessage("NO_ENHARMONIC_EQUIVALENT");
    }
}
