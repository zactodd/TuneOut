// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose;

import Model.Note.NoteMap;
import Controller.LearningCompose.SheetElement.SheetComposeNoteRest;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import Model.Note.Melody.PlayStyle;
import java.util.List;
import java.util.Collection;
import Model.Note.Note;
import java.util.Collections;
import Model.Note.Melody.NoteCollection;
import java.util.ArrayList;
import Controller.LearningCompose.SheetElement.SheetElement;
import java.util.TreeMap;
import Model.Note.Melody.Melody;
import Controller.OuterTemplateController;
import java.io.IOException;
import Controller.LearningCompose.SheetElement.SheetElementFactory;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import Controller.LearningCompose.TopToolbox.TopToolboxController;
import Controller.LearningCompose.TopToolbox.TopShowEditController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXMLLoader;
import Controller.LearningCompose.Toolbox.ToolboxComposeController;

public class OuterComposeController extends OuterExploreCompose
{
    public void initialize() throws IOException {
        (this.toolboxController = new ToolboxComposeController()).setOuterExploreController(this);
        final FXMLLoader sheetLoader = new FXMLLoader();
        sheetLoader.setLocation(this.getClass().getResource("/View/LearningCompose/sheet.fxml"));
        final ScrollPane sheetAnchorPane = (ScrollPane)sheetLoader.load();
        (this.sheetController = (SheetController)sheetLoader.getController()).setOuterExploreController(this);
        this.outerGridPane.add((Node)sheetAnchorPane, 0, 1);
        GridPane.setColumnSpan((Node)sheetAnchorPane, Integer.valueOf(2));
        final FXMLLoader topShowEditLoader = new FXMLLoader();
        topShowEditLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/topShowEdit.fxml"));
        final AnchorPane topShowEditAnchorPane = (AnchorPane)topShowEditLoader.load();
        (this.topShowEditController = (TopShowEditController)topShowEditLoader.getController()).setOuterController(this);
        final FXMLLoader noteLoader = new FXMLLoader();
        noteLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/topToolbox.fxml"));
        final AnchorPane noteGridPane = (AnchorPane)noteLoader.load();
        (this.topToolboxController = (TopToolboxController)noteLoader.getController()).initializeTopToolBoxController(this);
        final HBox topWideHbox = new HBox();
        topWideHbox.setAlignment(Pos.CENTER);
        topWideHbox.getChildren().addAll((Object[])new Node[] { (Node)topShowEditAnchorPane, (Node)noteGridPane });
        this.outerGridPane.add((Node)topWideHbox, 0, 0);
        GridPane.setColumnSpan((Node)topWideHbox, Integer.valueOf(2));
        GridPane.setHalignment((Node)noteGridPane, HPos.CENTER);
        this.toolboxController.setTopToolboxController(this.topToolboxController);
        final Image cursorUnavailImg = new Image("View/LearningCompose/graphic/Cursors/cursor_unavail.png");
        this.unavailCursor = new ImageCursor(cursorUnavailImg);
        this.dropElementCursor = new ImageCursor();
        this.sheetElementFactory = new SheetElementFactory(this);
        this.setupHandlers();
        this.topToolboxController.showComposeMelodyOptions();
        this.getSheetController().clearBtn.setDisable(false);
        final ElementDuration defaultDuration = ElementDurationMap.ELEMENT_DURATIONS.get("crotchetNote");
        this.setElementDuration(defaultDuration);
        this.topToolboxController.updateDuration(defaultDuration);
        this.getSheetController().playBtn.setVisible(true);
        this.getSheetController().playBtn.setManaged(true);
        this.getSheetController().stopBtn.setVisible(true);
        this.getSheetController().stopBtn.setManaged(true);
    }
    
    public void setOuterTemplateController(final OuterTemplateController outerTemplateController) {
        this.topShowEditController.setOuterTemplateController(outerTemplateController);
    }
    
    public Melody convertSheetToMelody(final String melodyName) {
        final Map<Double, SheetElement> orderedElements = new TreeMap<Double, SheetElement>();
        for (final SheetElement element : this.getSheetElements()) {
            if (orderedElements.containsKey(element.getPlacedX())) {
                final SheetElement elementToAdjust = orderedElements.get(element.getPlacedX());
                final Set<Note> chord = elementToAdjust.getChord();
                chord.add(elementToAdjust.getRootNote());
                chord.add(element.getRootNote());
            }
            else {
                orderedElements.put(element.getPlacedX(), element);
            }
        }
        final Melody melody = new Melody(melodyName);
        final List<NoteCollection> noteCollections = new ArrayList<NoteCollection>();
        for (final SheetElement orderedElement : orderedElements.values()) {
            final NoteCollection noteCol = new NoteCollection();
            noteCol.setUnitDuration(orderedElement.getElementDuration().getUnitDuration());
            if (orderedElement.getChord().isEmpty()) {
                noteCol.setNotes(new ArrayList<Note>(Collections.singletonList(orderedElement.getRootNote())));
            }
            else {
                noteCol.setNotes(new ArrayList<Note>(orderedElement.getChord()));
                noteCol.setPlayStyle(PlayStyle.SIMULTANEOUS);
            }
            noteCollections.add(noteCol);
        }
        melody.setNoteCollection(noteCollections);
        return melody;
    }
    
    public void clearStave() {
        this.sheetController.clearStave();
    }
    
    private void placeEachNoteOnSheet(final NoteCollection noteCollection, final Note note, final Double runningX) {
        final SheetElement sheetElement = new SheetComposeNoteRest(this);
        for (final ElementDuration elementDuration : ElementDurationMap.ELEMENT_DURATIONS.values()) {
            if (noteCollection.getUnitDuration().equals(elementDuration.getUnitDuration()) && note.getMidiNumber().equals(-1) == elementDuration.getElementType().equals(ElementDuration.ElementType.REST)) {
                sheetElement.setElementDuration(elementDuration);
            }
        }
        if (note.getMidiNumber().equals(-1)) {
            sheetElement.setRootNote(NoteMap.getNote("C4"));
        }
        else {
            sheetElement.setRootNote(note);
            sheetElement.setAccidental(note.getAccidental());
        }
        this.getSheetElements().add(sheetElement);
        sheetElement.placeElements(runningX);
    }
    
    public void placeMelodyOnSheet(final Melody melody) {
        this.sheetController.clearStave();
        Double runningX = 200.0;
        for (final NoteCollection noteCollection : melody.getNoteCollections()) {
            for (final Note note : noteCollection.getNotes()) {
                if (noteCollection.getPlayStyle().equals(PlayStyle.SIMULTANEOUS)) {
                    this.placeEachNoteOnSheet(noteCollection, note, runningX);
                }
                else {
                    this.placeEachNoteOnSheet(noteCollection, note, runningX);
                    runningX += 70.0;
                }
            }
            if (noteCollection.getPlayStyle().equals(PlayStyle.SIMULTANEOUS)) {
                runningX += 70.0;
            }
        }
    }
    
    public void insertNote(final String note, final Double layoutX) {
        Double xPos;
        if (layoutX == 0.0) {
            xPos = this.getNextX();
        }
        else {
            xPos = layoutX;
        }
        Note noteObj;
        try {
            final Integer noteInt = Integer.parseInt(note);
            noteObj = NoteMap.getNoteFromMidi(noteInt);
        }
        catch (NumberFormatException e) {
            noteObj = NoteMap.getNote(note);
        }
        final SheetElement selectedElement = this.getSelectedElement();
        if (selectedElement == null) {
            final SheetElement sheetElement = new SheetComposeNoteRest(this);
            sheetElement.setRootNote(noteObj);
            sheetElement.setAccidental(noteObj.getAccidental());
            sheetElement.setElementDuration(this.topToolboxController.getDuration());
            this.getSheetElements().add(sheetElement);
            sheetElement.placeElements(xPos);
            this.syncTopToolbox(sheetElement);
        }
        else {
            selectedElement.setRootNote(noteObj);
            selectedElement.setAccidental(noteObj.getAccidental());
            selectedElement.setElementDuration(this.topToolboxController.getDuration());
            this.sheetController.setMoveNoteMode(true);
            selectedElement.moveElement();
            this.setSelectedElement(selectedElement);
            this.syncTopToolbox(selectedElement);
            this.sheetController.setMoveNoteMode(false);
        }
    }
    
    public void insertNotes(final List<String> notes) {
        final Double freeX = this.getNextX();
        for (final String note : notes) {
            this.insertNote(note, freeX);
        }
    }
    
    @Override
    public void playSheet() {
        final Melody temp = this.convertSheetToMelody("temp");
        this.env.getPlay().playMelody(temp);
    }
}
