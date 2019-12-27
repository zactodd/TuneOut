

package Controller.LearningCompose.SheetElement;

import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import java.util.Iterator;
import Controller.LearningCompose.SheetController;
import javafx.scene.input.MouseEvent;
import java.util.Map;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;
import javafx.scene.image.ImageView;
import seng302.App;
import javafx.scene.image.Image;
import Controller.LearningCompose.OuterExploreCompose;

public class SheetSignature extends SheetElement
{
    public SheetSignature(final OuterExploreCompose outer) {
        this.sheetImage = new Image("View/LearningCompose/graphic/Crotchet.png");
        this.cursorImage = new Image("View/LearningCompose/graphic/Cursors/cursor_note.png");
        this.outer = outer;
        this.firstPosX = 250.0;
        this.env.setOuterTemplateController(App.getOuterTemplate());
    }
    
    private String getBodyText() {
        final StringBuilder allText = new StringBuilder();
        final String[] sigArr = this.outer.getSignature().type.split(" ");
        this.parser.executeCommand(String.format("keySignature(%1$s, \"%2$s\")", sigArr[0], sigArr[1]));
        final String result = this.env.getResponse();
        allText.append(String.format("Signature: %s", result.equals("[]") ? "None" : result));
        return allText.toString();
    }
    
    @Override
    PopOver setPopOver(final ImageView imageView) {
        (this.elementPopOver = new PopOver()).setDetachable(true);
        this.elementPopOver.setAnimated(true);
        this.elementPopOver.setConsumeAutoHidingEvents(false);
        this.elementPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        this.elementPopOver.setHeaderAlwaysVisible(true);
        this.elementPopOver.setTitle(String.format("Key: %s", this.outer.getSignature().type));
        final Label label = new Label();
        label.setMaxWidth(150.0);
        label.setWrapText(true);
        label.setText(this.getBodyText());
        final Button openPitchTutor = new Button();
        openPitchTutor.setOnAction(event -> {
            this.parser.executeCommand("keySignatureTutor()");
            if (!this.elementPopOver.isDetached()) {
                this.elementPopOver.hide();
            }
        });
        openPitchTutor.setText("Key Signature Tutor");
        final VBox verticalBox = new VBox();
        verticalBox.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        verticalBox.setSpacing(10.0);
        verticalBox.getChildren().add((Object)label);
        verticalBox.getChildren().add((Object)openPitchTutor);
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
        final String[] typeArr = this.type.split(" ");
        final String keyNote = typeArr[0];
        final String keyScale = typeArr[1];
        if (this.elementImages != null && !this.elementImages.isEmpty()) {
            for (final ImageView image : this.elementImages.values()) {
                sheet.stave.getChildren().remove((Object)image);
            }
        }
        (this.elementImages = new HashMap<String, ImageView>()).putAll(sheet.placeKey(keyNote, keyScale));
        sheet.noteWarning.setVisible(false);
        for (final SheetElement element : this.outer.getSheetElements()) {
            if (element.getElementImages().containsKey("note")) {
                if (element.accidental.equals(" ")) {
                    element.rootNote = element.rootNote.getBaseNote();
                    element.moveElement();
                    element.clearPopOver();
                }
                else {
                    final Pane pane = (Pane)element.elementPopOver.getContentNode();
                    final VBox verticalBox = (VBox)pane.getChildren().get(0);
                    final Label label = (Label)verticalBox.getChildren().get(0);
                    label.setText(this.type);
                }
            }
        }
        for (final SheetElement element : this.outer.getSheetElements()) {
            if (element.getSelected()) {
                element.setSelectedBorder();
            }
        }
        final Image keySigRectImg = new Image("View/LearningCompose/graphic/KeyRect.png");
        final ImageView keySigRectImgView = new ImageView(keySigRectImg);
        sheet.stave.getChildren().add((Object)keySigRectImgView);
        keySigRectImgView.setLayoutX(140.0);
        keySigRectImgView.setLayoutY(60.0);
        this.elementImages.put("Key Rectangle", keySigRectImgView);
        keySigRectImgView.setId("keySigRect");
        keySigRectImgView.setPickOnBounds(true);
        this.elementPopOver = this.setPopOver(keySigRectImgView);
        keySigRectImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.elementPopOver.show((Node)keySigRectImgView));
        keySigRectImgView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> keySigRectImgView.setCursor(Cursor.HAND));
    }
}
