// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose.SheetElement;

import javafx.event.ActionEvent;
import Model.Note.Scale.Scale;
import java.util.Iterator;
import Controller.LearningCompose.SheetController;
import Model.Note.Scale.ScaleMap;
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

public class SheetScale extends SheetElement
{
    private static final double FIRST_POS_X_SCALE = 200.0;
    
    public SheetScale(final OuterExploreCompose outer) {
        this.sheetImage = new Image("View/LearningCompose/graphic/Crotchet.png");
        this.cursorImage = new Image("View/LearningCompose/graphic/Cursors/cursor_scale.png");
        this.outer = outer;
        this.firstPosX = 200.0;
        if (this.type == null || this.type.isEmpty()) {
            this.type = "Major";
        }
        this.env.setOuterTemplateController(App.getOuterTemplate());
    }
    
    private String getBodyText() {
        final String noteWithOctave = this.rootNote.getNoteWithOctave();
        final StringBuilder allText = new StringBuilder();
        this.parser.executeCommand(String.format("scale(%s, \"%s\")", noteWithOctave, this.type));
        allText.append("   Notes in scale:\n").append(this.env.getResponse());
        return allText.toString();
    }
    
    public PopOver setPopOver(final ImageView imageView) {
        final Note popoverNote = this.rootNote;
        (this.elementPopOver = new PopOver()).setDetachable(true);
        this.elementPopOver.setAnimated(true);
        this.elementPopOver.setConsumeAutoHidingEvents(false);
        this.elementPopOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        this.elementPopOver.setHeaderAlwaysVisible(true);
        this.elementPopOver.setTitle(String.format("Scale: %s %s", popoverNote.getNoteWithOctave(), this.type));
        final Label label = new Label();
        label.setMaxWidth(2.147483647E9);
        label.setWrapText(true);
        label.setText(this.getBodyText());
        final Button openScaleTutor = new Button();
        openScaleTutor.setOnAction(event -> {
            this.parser.executeCommand("scaleTutor()");
            if (!this.elementPopOver.isDetached()) {
                this.elementPopOver.hide();
            }
        });
        openScaleTutor.setText("Scale Tutor");
        final Button playScale = new Button();
        playScale.setId("play");
        playScale.setOnAction(event -> this.parser.executeCommand(String.format("playScale(%s, \"%s\")", popoverNote.getNoteWithOctave(), this.type)));
        final Image play = new Image("View/LearningCompose/graphic/play_small.png");
        playScale.setGraphic((Node)new ImageView(play));
        final HBox horizBox = new HBox();
        horizBox.setSpacing(10.0);
        horizBox.getChildren().add((Object)playScale);
        horizBox.getChildren().add((Object)openScaleTutor);
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
        final SheetController sheet = this.outer.getSheetController();
        sheet.noteWarning.setVisible(false);
        this.elementImages = new HashMap<String, ImageView>();
        for (final SheetElement element : this.outer.getSheetElements()) {
            for (final ImageView image : element.elementImages.values()) {
                this.outer.getSheetController().stave.getChildren().remove((Object)image);
            }
        }
        final Scale scale = ScaleMap.getScale(this.type);
        scale.setNotesInScale(this.rootNote.getNoteWithOctave());
        this.outer.setSelectedElement(this);
        this.placeNoteImage(sheet, firstLayoutX, scale.getNotesInScale());
        this.addMouseClickedHandler(sheet);
    }
}
