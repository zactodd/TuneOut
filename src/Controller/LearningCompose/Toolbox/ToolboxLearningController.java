

package Controller.LearningCompose.Toolbox;

import javafx.scene.Cursor;
import Controller.LearningCompose.SheetElement.SheetChord;
import Controller.LearningCompose.SheetElement.SheetElement;
import Controller.LearningCompose.SheetElement.SheetSignature;
import Controller.LearningCompose.SheetElement.SheetInterval;
import Controller.LearningCompose.SheetElement.SheetScale;
import javafx.scene.ImageCursor;
import Controller.LearningCompose.SheetElement.SheetNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class ToolboxLearningController extends Toolbox
{
    @FXML
    public ToggleButton noteBtn;
    @FXML
    public ToggleButton scaleBtn;
    @FXML
    public ToggleButton intervalBtn;
    @FXML
    public ToggleButton chordBtn;
    @FXML
    public ToggleButton keySignatureBtn;
    
    public void inputNote(final ActionEvent actionEvent) {
        this.clearFromToolbox();
        if (this.noteBtn.isSelected()) {
            this.topToolboxController.showNoteOptions();
            final SheetNote noteElement = new SheetNote(this.outerExploreController);
            this.outerExploreController.setDropElementCursor(new ImageCursor(noteElement.getCursorImage()));
            this.outerExploreController.getSheetController().clearBtn.setDisable(false);
        }
    }
    
    public void inputScale(final ActionEvent actionEvent) {
        this.clearFromToolbox();
        if (this.scaleBtn.isSelected()) {
            this.topToolboxController.showScaleOptions();
            final SheetScale scaleElement = new SheetScale(this.outerExploreController);
            this.outerExploreController.setDropElementCursor(new ImageCursor(scaleElement.getCursorImage()));
            this.outerExploreController.getSheetController().clearBtn.setDisable(false);
            this.outerExploreController.setType("Major");
        }
    }
    
    @FXML
    public void inputInterval(final ActionEvent actionEvent) {
        this.clearFromToolbox();
        if (this.intervalBtn.isSelected()) {
            this.topToolboxController.showIntervalOptions();
            final SheetInterval intervalElement = new SheetInterval(this.outerExploreController);
            this.outerExploreController.setDropElementCursor(new ImageCursor(intervalElement.getCursorImage()));
            this.outerExploreController.getSheetController().clearBtn.setDisable(false);
            this.outerExploreController.setType("Major 2nd");
        }
    }
    
    @FXML
    public void inputKeySignature(final ActionEvent actionEvent) {
        this.clearFromToolbox();
        if (this.keySignatureBtn.isSelected()) {
            this.topToolboxController.showSignatureOptions();
            final SheetSignature signatureElement = new SheetSignature(this.outerExploreController);
            this.outerExploreController.setDropElementCursor(new ImageCursor(signatureElement.getCursorImage()));
            this.outerExploreController.getSheetController().clearBtn.setDisable(false);
            final SheetElement signature = new SheetSignature(this.outerExploreController);
            signature.setType(this.topToolboxController.getSignatureType());
            this.outerExploreController.setSignature(signature);
            signature.placeElements(signature.getFirstPosX());
            this.topToolboxController.enableNaturalButton(true);
        }
    }
    
    @FXML
    public void inputChord(final ActionEvent actionEvent) {
        this.clearFromToolbox();
        if (this.chordBtn.isSelected()) {
            this.topToolboxController.showChordOptions();
            final SheetChord chordElement = new SheetChord(this.outerExploreController);
            this.toolbox.setCursor((Cursor)new ImageCursor(chordElement.getCursorImage()));
            this.outerExploreController.setDropElementCursor(new ImageCursor(chordElement.getCursorImage()));
            this.outerExploreController.getSheetController().clearBtn.setDisable(false);
            this.outerExploreController.setType("Major");
            this.outerExploreController.setInversion("");
            this.outerExploreController.setArrangement("Simultaneous");
        }
    }
}
