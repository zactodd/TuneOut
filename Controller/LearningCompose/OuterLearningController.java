

package Controller.LearningCompose;

import java.util.Iterator;
import Model.Note.Note;
import Controller.LearningCompose.SheetElement.SheetElement;
import Model.Note.NoteMap;
import java.io.IOException;
import Controller.LearningCompose.SheetElement.SheetElementFactory;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.geometry.HPos;
import Controller.LearningCompose.TopToolbox.TopToolboxController;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import Controller.LearningCompose.Toolbox.Toolbox;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class OuterLearningController extends OuterExploreCompose
{
    @FXML
    public GridPane outerGridPane;
    
    public void initialize() throws IOException {
        final FXMLLoader toolboxLoader = new FXMLLoader();
        toolboxLoader.setLocation(this.getClass().getResource("/View/LearningCompose/toolboxLearning.fxml"));
        final AnchorPane toolboxAnchorPane = (AnchorPane)toolboxLoader.load();
        (this.toolboxController = (Toolbox)toolboxLoader.getController()).setOuterExploreController(this);
        this.outerGridPane.add((Node)toolboxAnchorPane, 0, 1);
        final FXMLLoader sheetLoader = new FXMLLoader();
        sheetLoader.setLocation(this.getClass().getResource("/View/LearningCompose/sheet.fxml"));
        final ScrollPane sheetAnchorPane = (ScrollPane)sheetLoader.load();
        (this.sheetController = (SheetController)sheetLoader.getController()).setOuterExploreController(this);
        this.outerGridPane.add((Node)sheetAnchorPane, 1, 1);
        final FXMLLoader noteLoader = new FXMLLoader();
        noteLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/topToolbox.fxml"));
        final AnchorPane noteGridPane = (AnchorPane)noteLoader.load();
        (this.topToolboxController = (TopToolboxController)noteLoader.getController()).initializeTopToolBoxController(this);
        this.outerGridPane.add((Node)noteGridPane, 1, 0);
        GridPane.setHalignment((Node)noteGridPane, HPos.CENTER);
        this.toolboxController.setTopToolboxController(this.topToolboxController);
        final Image cursorUnavailImg = new Image("View/LearningCompose/graphic/Cursors/cursor_unavail.png");
        this.unavailCursor = new ImageCursor(cursorUnavailImg);
        this.dropElementCursor = new ImageCursor();
        this.sheetElementFactory = new SheetElementFactory(this);
        this.setElementDuration(ElementDurationMap.ELEMENT_DURATIONS.get("crotchetNote"));
        this.setupHandlers();
    }
    
    public void insertNote(final String note) {
        final SheetElement sheetElement = this.sheetElementFactory.getNewElement();
        if (sheetElement != null) {
            Double xPos = sheetElement.getFirstPosX();
            if (sheetElement.getAllowMultiple()) {
                xPos = this.getNextX();
            }
            Note noteObj;
            try {
                final Integer noteInt = Integer.parseInt(note);
                noteObj = NoteMap.getNoteFromMidi(noteInt);
            }
            catch (NumberFormatException e) {
                noteObj = NoteMap.getNote(note);
            }
            Boolean noteExists = false;
            for (final SheetElement element : this.getSheetElements()) {
                if (element.getPlacedX().equals(xPos) && element.getRootNote().equals(noteObj)) {
                    noteExists = true;
                }
            }
            if (!noteExists) {
                if (!this.getType().isEmpty()) {
                    sheetElement.setType(this.getType());
                }
                if (!this.getArrangement().isEmpty()) {
                    sheetElement.setArrangement(this.getArrangement());
                }
                if (!this.getInversion().isEmpty()) {
                    sheetElement.setInversion(this.getInversion());
                }
                if (this.getElementDuration() != null) {
                    sheetElement.setElementDuration(this.topToolboxController.getDuration());
                }
                final SheetElement selectedElement = this.getSelectedElement();
                if (selectedElement == null) {
                    sheetElement.setRootNote(noteObj);
                    sheetElement.setAccidental(noteObj.getAccidental());
                    this.getSheetElements().add(sheetElement);
                    sheetElement.placeElements(xPos);
                    this.syncTopToolbox(sheetElement);
                }
                else {
                    selectedElement.setRootNote(noteObj);
                    selectedElement.setAccidental(noteObj.getAccidental());
                    final Boolean initialSnapMode = this.sheetController.getSnapToXMode();
                    selectedElement.setElementDuration(this.topToolboxController.getDuration());
                    this.sheetController.setMoveNoteMode(true);
                    selectedElement.moveElement();
                    this.setSelectedElement(selectedElement);
                    this.syncTopToolbox(selectedElement);
                    this.sheetController.setMoveNoteMode(false);
                }
            }
        }
    }
    
    @Override
    public void playSheet() {
    }
}
