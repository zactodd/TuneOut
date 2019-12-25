// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose;

import javafx.event.ActionEvent;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.MouseButton;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import Controller.LearningCompose.SheetElement.SheetElement;
import Model.Note.Scale.KeySignature;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.Iterator;
import Model.Note.NoteMap;
import Model.Play.Play;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import Environment.GrammarParser;
import Environment.Environment;
import javafx.scene.image.ImageView;
import Model.Note.Note;
import java.util.TreeMap;
import javafx.geometry.Rectangle2D;
import java.util.List;

public class SheetController
{
    protected static final Coord TOP_LEFT_BOUND;
    private static final Double BOTTOM_BOUND;
    private static List<String> INVALID_IDS_FOR_NOTE;
    private Coord mouseCoord;
    protected static final double FIRST_POS_X = 250.0;
    private static final double LINE_GAP = 13.0;
    private static final double CROTCHET_X_GAP = 25.0;
    private static final Rectangle2D ONE_LINE;
    private static final Rectangle2D ONE_LINE_REVERSED;
    private static final Rectangle2D TWO_LINES;
    private static final Rectangle2D THREE_LINES;
    private static final Rectangle2D FOUR_LINES;
    private static final Rectangle2D FIVE_LINES;
    private static final Double UPPER_TREBLE_STAVE_Y;
    private static final Double LOWER_BASS_STAVE_Y;
    public static final Double GAP_BETWEEN_NOTES;
    private static TreeMap<Note, Coord> staveBasicNotes;
    public ImageView clef;
    protected Environment env;
    protected GrammarParser parser;
    @FXML
    public AnchorPane stave;
    @FXML
    public TextField noteHint;
    @FXML
    public Button clearBtn;
    public ScrollPane scrolly;
    public HBox hboxForWidth;
    @FXML
    public Label noteWarning;
    public Button playBtn;
    public Button stopBtn;
    private Integer adjustYCoordForCrotchet;
    private Integer adjustXCoordForSharp;
    private Integer adjustYCoordForSharp;
    private Integer adjustXCoordForFlat;
    private Integer adjustYCoordForFlat;
    private Integer adjustXCoordForNatural;
    private Integer adjustYCoordForNatural;
    private Integer adjustXCoordForDoubleFlat;
    private Integer adjustYCoordForDoubleFlat;
    private Integer adjustXCoordForDoubleSharp;
    private Integer adjustYCoordForDoubleSharp;
    private OuterExploreCompose outerExploreController;
    private PositionXTracker positionXTracker;
    private Boolean isMoveNoteMode;
    private Boolean isSnapMode;
    private Boolean isComposeMode;
    private Boolean isCheckingMode;
    private Boolean isKeyboardInputMode;
    private Boolean isPercussionMode;
    
    public SheetController() {
        this.env = new Environment(new Play(Play.PlayType.REPLACE));
        this.parser = new GrammarParser(this.env);
        this.adjustYCoordForCrotchet = -80;
        this.adjustXCoordForSharp = -25;
        this.adjustYCoordForSharp = -30;
        this.adjustXCoordForFlat = -25;
        this.adjustYCoordForFlat = -35;
        this.adjustXCoordForNatural = -25;
        this.adjustYCoordForNatural = 45;
        this.adjustXCoordForDoubleFlat = -35;
        this.adjustYCoordForDoubleFlat = -35;
        this.adjustXCoordForDoubleSharp = -28;
        this.adjustYCoordForDoubleSharp = -15;
        this.positionXTracker = new PositionXTracker();
        this.isMoveNoteMode = false;
        this.isSnapMode = false;
        this.isComposeMode = false;
        this.isCheckingMode = false;
        this.isKeyboardInputMode = false;
        this.isPercussionMode = false;
    }
    
    public void setIsPercussionMode(final Boolean isPercussionMode) {
        this.isPercussionMode = isPercussionMode;
    }
    
    public Boolean getIsPercussionMode() {
        return this.isPercussionMode;
    }
    
    public void setIsKeyboardInputMode(final Boolean isKeyboardInputMode) {
        this.isKeyboardInputMode = isKeyboardInputMode;
    }
    
    public Boolean getIsKeyboardInputMode() {
        return this.isKeyboardInputMode;
    }
    
    public void setMoveNoteMode(final Boolean isMoveNoteMode) {
        this.isMoveNoteMode = isMoveNoteMode;
    }
    
    public void setIsCheckingMode(final Boolean isCheckingMode) {
        this.isCheckingMode = isCheckingMode;
    }
    
    public Boolean getMoveNoteMode() {
        return this.isMoveNoteMode;
    }
    
    public void setSnapToXMode(final Boolean isSnapMode) {
        this.isSnapMode = isSnapMode;
    }
    
    public Boolean getSnapToXMode() {
        return this.isSnapMode;
    }
    
    public void setComposeMode(final Boolean isComposeMode) {
        this.isComposeMode = isComposeMode;
    }
    
    public Boolean getComposeMode() {
        return this.isComposeMode;
    }
    
    public PositionXTracker getPositionXTracker() {
        return this.positionXTracker;
    }
    
    private static void addAccidentalNotesToStaveNoteMap() {
        for (final Note note : NoteMap.allNotes) {
            final Note baseNote = NoteMap.getNote(note.getNoteName().substring(0, 1) + note.getOctave());
            if (SheetController.staveBasicNotes.containsKey(baseNote)) {
                final Coord baseNoteCoords = SheetController.staveBasicNotes.get(baseNote);
                SheetController.staveBasicNotes.put(note, baseNoteCoords);
            }
        }
    }
    
    public void initialize() throws IOException {
        this.setUpListeners();
        this.scrolly.setFitToWidth(true);
        this.stave.setPrefWidth(1800.0);
    }
    
    private void addGridLines() {
        final Image imgX = new Image("View/LearningCompose/graphic/lineX.png");
        final ImageView imgViewX = new ImageView(imgX);
        this.stave.getChildren().add((Object)imgViewX);
        imgViewX.setLayoutX(250.0);
        Integer counter = 0;
        for (final Map.Entry<Note, Coord> entry : SheetController.staveBasicNotes.entrySet()) {
            final Image img = new Image("View/LearningCompose/graphic/line.png");
            final ImageView imgView = new ImageView(img);
            this.stave.getChildren().add((Object)imgView);
            imgView.setLayoutY(entry.getValue().getY());
            ++counter;
        }
        final Image img2 = new Image("View/LearningCompose/graphic/mark.png");
        final ImageView imgView2 = new ImageView(img2);
        this.stave.getChildren().add((Object)imgView2);
        imgView2.setLayoutY(SheetController.TOP_LEFT_BOUND.getY());
        imgView2.setLayoutX(SheetController.TOP_LEFT_BOUND.getX());
        final Image img3 = new Image("View/LearningCompose/graphic/mark.png");
        final ImageView imgView3 = new ImageView(img3);
        this.stave.getChildren().add((Object)imgView3);
        imgView3.setLayoutY((double)SheetController.BOTTOM_BOUND);
        imgView3.setLayoutX(700.0);
    }
    
    protected void setOuterExploreController(final OuterExploreCompose outerExploreController) {
        this.outerExploreController = outerExploreController;
    }
    
    public ImageView insertAccidental(final String accidentalType, final Coord coords) {
        Image img = null;
        switch (accidentalType) {
            case "#": {
                img = new Image("View/LearningCompose/graphic/Sharp.png");
                break;
            }
            case "b": {
                img = new Image("View/LearningCompose/graphic/Flat.png");
                break;
            }
            case "bb": {
                img = new Image("View/LearningCompose/graphic/Double flat.png");
                break;
            }
            case "x": {
                img = new Image("View/LearningCompose/graphic/Double sharp.png");
                break;
            }
            case "n": {
                img = new Image("View/LearningCompose/graphic/Natural.png");
                break;
            }
        }
        final ImageView imgView = new ImageView(img);
        imgView.setId("accidental");
        if (coords != null) {
            if (accidentalType.equals("#")) {
                imgView.setLayoutX(coords.getX() + this.adjustXCoordForSharp);
                imgView.setLayoutY(coords.getY() + this.adjustYCoordForSharp);
            }
            else if (accidentalType.equals("b")) {
                imgView.setLayoutX(coords.getX() + this.adjustXCoordForFlat);
                imgView.setLayoutY(coords.getY() + this.adjustYCoordForFlat);
            }
            else if (accidentalType.equals("bb")) {
                imgView.setLayoutX(coords.getX() + this.adjustXCoordForDoubleFlat);
                imgView.setLayoutY(coords.getY() + this.adjustYCoordForDoubleFlat);
            }
            else if (accidentalType.equals("x")) {
                imgView.setLayoutX(coords.getX() + this.adjustXCoordForDoubleSharp);
                imgView.setLayoutY(coords.getY() + this.adjustYCoordForDoubleSharp);
            }
            else if (accidentalType.equals("n")) {
                imgView.setLayoutX(coords.getX() + this.adjustXCoordForNatural);
                imgView.setLayoutY(coords.getY() + this.adjustYCoordForNatural);
            }
        }
        this.stave.getChildren().add((Object)imgView);
        return imgView;
    }
    
    public Map<String, ImageView> placeNote(final Note note, final Double layoutX, final Image img) throws NoteNotShownException, NoteNotSnapException {
        final Map<String, ImageView> images = new HashMap<String, ImageView>();
        final ImageView noteImg = new ImageView(img);
        noteImg.setId("note");
        final Coord coords = SheetController.staveBasicNotes.get(note);
        this.noteWarning.setVisible(false);
        if (!SheetController.staveBasicNotes.containsKey(note)) {
            throw new NoteNotShownException();
        }
        this.resizeStave(layoutX);
        Double coordY = 0.0;
        if (coords != null) {
            coordY = coords.getY();
        }
        final Coord snapCoord = this.snapToStave(new Coord(layoutX, coordY));
        if (snapCoord != null) {
            noteImg.setLayoutX(snapCoord.getX());
            noteImg.setLayoutY(snapCoord.getY() + this.adjustYCoordForCrotchet);
            final ImageView ledgerLines = this.setLedgerLines(note, snapCoord, img.getWidth());
            if (ledgerLines != null) {
                images.put("ledger", ledgerLines);
            }
            this.stave.getChildren().add((Object)noteImg);
            if (this.checkRotateStem(note)) {
                this.rotateImage(noteImg, img, true);
            }
            if (note.hasAccidental()) {
                final String accidental = note.getAccidental();
                final ImageView accidentalImg = this.insertAccidental(accidental, snapCoord);
                images.put("accidental", accidentalImg);
            }
            images.put("note", noteImg);
            this.positionXTracker.addXCoord(snapCoord.getX());
            return images;
        }
        throw new NoteNotSnapException();
    }
    
    public void moveNote(final String direction) {
        Note newNote = null;
        final SheetElement element = this.outerExploreController.getSelectedElement();
        if (element != null && !element.getRootNote().getMidiNumber().equals(-1)) {
            this.isMoveNoteMode = true;
            final Note note = element.getRootNote();
            if (direction.equals("up")) {
                newNote = this.getItemInList(note, 1);
            }
            else if (direction.equals("down")) {
                newNote = this.getItemInList(note, -1);
            }
            this.outerExploreController.getSelectedElement().clearPopOver();
            if (newNote.getAccidental().equals("x")) {
                newNote = this.getItemInList(newNote, 2);
            }
            if (newNote.getAccidental().equals("bb")) {
                newNote = this.getItemInList(newNote, -2);
            }
            Boolean accidentalSet = false;
            if (this.outerExploreController.getSignature() != null) {
                final String[] keyArr = this.outerExploreController.getSignature().getType().split(" ");
                final List<String> keyNotes = KeySignature.getKeySignature(keyArr[0], keyArr[1]);
                for (final String keyNoteStr : keyNotes) {
                    final Note keyNoteObj = NoteMap.getNote(keyNoteStr + "4");
                    if (newNote.getAccidental().equals(" ") && keyNoteObj.getBaseNoteNoOctave().equals(newNote.getNoteName())) {
                        element.setAccidental("n");
                        accidentalSet = true;
                    }
                }
            }
            element.setRootNote(newNote);
            if (!accidentalSet) {
                element.setAccidental(newNote.getAccidental());
            }
            element.moveElement();
            this.outerExploreController.setSelectedElement(element);
            this.outerExploreController.syncTopToolbox(element);
        }
    }
    
    private Note getItemInList(final Note note, final Integer num) {
        Note newNote = note;
        for (int x = 0; x < Math.abs(num); ++x) {
            if (num > 0) {
                if (SheetController.staveBasicNotes.lowerKey(note) != null) {
                    newNote = SheetController.staveBasicNotes.lowerKey(newNote);
                }
            }
            else if (num < 0 && SheetController.staveBasicNotes.higherKey(note) != null) {
                newNote = SheetController.staveBasicNotes.higherKey(newNote);
            }
        }
        return newNote;
    }
    
    public Coord getMouseCoord() {
        return this.mouseCoord;
    }
    
    private void setUpListeners() {
        final List<Button> buttons = new ArrayList<Button>(Arrays.asList(this.clearBtn, this.playBtn, this.stopBtn));
        for (final Button btn : buttons) {
            btn.setOnMouseMoved((EventHandler)new EventHandler<MouseEvent>() {
                public void handle(final MouseEvent evt) {
                    SheetController.this.stave.setCursor(Cursor.DEFAULT);
                }
            });
        }
        this.stave.addEventFilter(MouseEvent.MOUSE_MOVED, (EventHandler)new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent evt) {
                SheetController.this.outerExploreController.requestFocus();
                final Coord mouseCoords = new Coord(evt.getX(), evt.getY());
                SheetController.this.noteHint.setVisible(false);
                if (SheetController.this.outerExploreController.getToolboxController().toolboxIsSelected()) {
                    if (SheetController.this.checkedAllowedBounds(mouseCoords)) {
                        final Note note = SheetController.this.getNoteNameFromStave(mouseCoords, true);
                        if (note != null) {
                            SheetController.this.noteHint.setVisible(true);
                            SheetController.this.noteHint.setPrefWidth((double)(30 + SheetController.this.noteHint.getText().length() * 7));
                            SheetController.this.noteHint.setText(SheetController.this.outerExploreController.translateNoteHint(note.getNoteWithOctave()));
                            final Double stavePosY = SheetController.staveBasicNotes.get(note).getY();
                            SheetController.this.noteHint.setLayoutY(stavePosY - 13.0);
                            SheetController.this.stave.setCursor((Cursor)SheetController.this.outerExploreController.dropElementCursor);
                        }
                        else {
                            SheetController.this.stave.setCursor((Cursor)SheetController.this.outerExploreController.unavailCursor);
                        }
                    }
                    else {
                        SheetController.this.stave.setCursor((Cursor)SheetController.this.outerExploreController.unavailCursor);
                    }
                }
                else {
                    SheetController.this.stave.setCursor(Cursor.DEFAULT);
                }
            }
        });
        this.stave.addEventFilter(MouseEvent.MOUSE_CLICKED, (EventHandler)new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent evt) {
                SheetController.this.noteWarning.setVisible(false);
                final Node node = (Node)evt.getTarget();
                if (SheetController.this.outerExploreController.getToolboxController().toolboxIsSelected()) {
                    final SheetElement element = SheetController.this.outerExploreController.sheetElementFactory.getNewElement();
                    final Coord mouseCoords = new Coord(evt.getX(), evt.getY());
                    SheetController.this.mouseCoord = mouseCoords;
                    if (evt.getButton().equals((Object)MouseButton.PRIMARY) && element != null && SheetController.this.checkedAllowedBounds(mouseCoords)) {
                        if (!SheetController.this.checkExcludedIds(node.getId())) {
                            final Note theNote = SheetController.this.getNoteNameFromStave(mouseCoords, false);
                            if (theNote != null) {
                                final Double clickedX = mouseCoords.getX() - 20.0;
                                element.setRootNote(theNote);
                                element.setAccidental(SheetController.this.outerExploreController.getTopToolboxController().getAccidental());
                                if (!SheetController.this.outerExploreController.getType().isEmpty()) {
                                    element.setType(SheetController.this.outerExploreController.getType());
                                }
                                if (!SheetController.this.outerExploreController.getArrangement().isEmpty()) {
                                    element.setArrangement(SheetController.this.outerExploreController.getArrangement());
                                }
                                if (!SheetController.this.outerExploreController.getInversion().isEmpty()) {
                                    element.setInversion(SheetController.this.outerExploreController.getInversion());
                                }
                                if (SheetController.this.outerExploreController.getElementDuration() != null) {
                                    element.setElementDuration(SheetController.this.outerExploreController.topToolboxController.getDuration());
                                }
                                if (SheetController.this.outerExploreController.getPercussionInfo() != null) {
                                    element.setPercussionInfo(SheetController.this.outerExploreController.topToolboxController.getPercussion());
                                }
                                SheetController.this.outerExploreController.addElementToList(element);
                                element.placeElements(mouseCoords.getX() - 20.0);
                                if (element.getElementImages() != null) {
                                    SheetController.this.outerExploreController.syncTopToolbox(element);
                                }
                            }
                        }
                    }
                    else {
                        SheetController.this.outerExploreController.clearSelectedElement();
                    }
                    SheetController.this.outerExploreController.requestFocus();
                }
            }
        });
        this.stave.widthProperty().addListener((ChangeListener)new ChangeListener<Number>() {
            public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
                Boolean hasWideImage = false;
                Double width = 0.0;
                for (final SheetElement element : SheetController.this.outerExploreController.getSheetElements()) {
                    if (element.getElementImages() != null && element.getElementImages().values() != null) {
                        for (final ImageView image : element.getElementImages().values()) {
                            if (image.getLayoutX() + 25.0 > newValue.doubleValue()) {
                                hasWideImage = true;
                                if (image.getLayoutX() + 25.0 <= width) {
                                    continue;
                                }
                                width = image.getLayoutX() + 25.0;
                            }
                        }
                    }
                }
                if (hasWideImage) {
                    SheetController.this.scrolly.setFitToWidth(false);
                    SheetController.this.hboxForWidth.setPrefWidth((double)width);
                }
                else {
                    SheetController.this.scrolly.setFitToWidth(true);
                    SheetController.this.hboxForWidth.setPrefWidth(1800.0);
                }
            }
        });
    }
    
    private boolean checkExcludedIds(final String id) {
        return id != null && SheetController.INVALID_IDS_FOR_NOTE.contains(id);
    }
    
    private ImageView setLedgerLines(final Note note, final Coord coords, final double width) {
        double ledgerY = 0.0;
        Boolean placeLedgerLines = false;
        Rectangle2D viewPort = null;
        final String baseNoteStr = note.getNoteName().substring(0, 1) + note.getOctave();
        final Note baseNote = NoteMap.getNote(baseNoteStr);
        if (baseNote != null) {
            final String noteWithOctave = baseNote.getNoteWithOctave();
            switch (noteWithOctave) {
                case "C2": {
                    placeLedgerLines = true;
                    ledgerY = SheetController.LOWER_BASS_STAVE_Y;
                    viewPort = SheetController.TWO_LINES;
                    break;
                }
                case "D2":
                case "E2": {
                    placeLedgerLines = true;
                    ledgerY = SheetController.LOWER_BASS_STAVE_Y;
                    viewPort = SheetController.ONE_LINE;
                    break;
                }
                case "C4": {
                    placeLedgerLines = true;
                    ledgerY = SheetController.staveBasicNotes.get(note).getY();
                    viewPort = SheetController.ONE_LINE;
                    break;
                }
                case "A5":
                case "B5": {
                    placeLedgerLines = true;
                    ledgerY = SheetController.UPPER_TREBLE_STAVE_Y;
                    viewPort = SheetController.ONE_LINE_REVERSED;
                    break;
                }
            }
        }
        ImageView llImgView = null;
        if (placeLedgerLines) {
            if (width >= 60.0) {
                viewPort = new Rectangle2D(viewPort.getMinX(), viewPort.getMinY(), viewPort.getWidth() + 25.0, viewPort.getHeight());
            }
            else if (width >= 40.0) {
                viewPort = new Rectangle2D(viewPort.getMinX(), viewPort.getMinY(), viewPort.getWidth() + 7.0, viewPort.getHeight());
            }
            final Image llImg = new Image("View/LearningCompose/graphic/LedgerLines.png");
            llImgView = new ImageView(llImg);
            llImgView.setId("ledger");
            llImgView.setViewport(viewPort);
            this.stave.getChildren().add((Object)llImgView);
            llImgView.setLayoutY(ledgerY);
            llImgView.setLayoutX(coords.getX() - 7.0);
        }
        return llImgView;
    }
    
    private Boolean checkedAllowedBounds(final Coord coords) {
        return coords.getX() > SheetController.TOP_LEFT_BOUND.getX() && coords.getY() > SheetController.TOP_LEFT_BOUND.getY() && coords.getX() < this.calculateRightBound() && coords.getY() < SheetController.BOTTOM_BOUND;
    }
    
    public Double calculateRightBound() {
        return this.stave.getBoundsInLocal().getMaxX() - SheetController.GAP_BETWEEN_NOTES;
    }
    
    public Note getNoteNameFromStave(final Coord coords, final Boolean alterForKey) {
        Note returnNote = null;
        final String accidental = this.outerExploreController.getTopToolboxController().getAccidental();
        final Map<String, Note> possibleNotes = new HashMap<String, Note>();
        for (final Map.Entry<Note, Coord> note : SheetController.staveBasicNotes.entrySet()) {
            final Double upper = note.getValue().getY() + 6.5;
            final Double lower = note.getValue().getY() - 6.5;
            if (coords.getY() > lower && coords.getY() < upper) {
                possibleNotes.put(note.getKey().getAccidental(), note.getKey());
            }
        }
        if (accidental != null && accidental.equals("#")) {
            returnNote = possibleNotes.get("#");
        }
        else if (accidental != null && accidental.equals("b")) {
            returnNote = possibleNotes.get("b");
        }
        else {
            returnNote = possibleNotes.get(" ");
        }
        if (alterForKey && this.outerExploreController.getSignature() != null && returnNote != null) {
            final String[] signatureArr = this.outerExploreController.getSignature().getType().split(" ");
            returnNote = this.adjustNoteForKey(returnNote, signatureArr[0], signatureArr[1]);
        }
        return returnNote;
    }
    
    public void clearStave() {
        this.positionXTracker.clearPositionXTracker();
        for (final SheetElement element : this.outerExploreController.getSheetElements()) {
            if (element.getElementImages() != null) {
                for (final ImageView image : element.getElementImages().values()) {
                    this.stave.getChildren().remove((Object)image);
                }
            }
        }
        this.outerExploreController.getSheetElements().clear();
        if (this.outerExploreController.getTopToolboxController() != null) {
            this.outerExploreController.getTopToolboxController().unSelectNote();
            this.outerExploreController.getTopToolboxController().updateAccidentalButton(null);
        }
        this.noteWarning.setVisible(false);
    }
    
    public void rotateImage(final ImageView imgView, final Image img, final Boolean headUp) {
        Double degree;
        Integer adjustHeight;
        if (headUp) {
            degree = 180.0;
            adjustHeight = -25;
        }
        else {
            degree = 360.0;
            adjustHeight = -92;
        }
        imgView.setRotate((double)degree);
        final Double imageTranslate = img.getHeight() + adjustHeight;
        imgView.setTranslateY((double)imageTranslate);
        if (img.impl_getUrl().endsWith("Semiquaver.png")) {
            final Image semiquaverFlipped = new Image("View/LearningCompose/graphic/Notes/Semiquaver_flipped.png");
            imgView.setImage(semiquaverFlipped);
        }
        else if (img.impl_getUrl().endsWith("Quaver.png")) {
            final Image quaverFlipped = new Image("View/LearningCompose/graphic/Notes/Quaver_flipped.png");
            imgView.setImage(quaverFlipped);
        }
    }
    
    public Map<String, ImageView> rotateHeadOfNote(final ImageView imgView, final Map<String, ImageView> images, final Boolean headUp) {
        Double degree;
        Integer adjustedWidth;
        Integer adjustedHeight;
        Image image;
        if (headUp) {
            degree = 0.0;
            adjustedWidth = -64;
            adjustedHeight = -25;
            image = new Image("View/LearningCompose/graphic/Crotchet_Rotated_Left.png");
            final ImageView img = images.get("accidental");
            final ImageView staveImg = images.get("ledger");
            if (staveImg != null) {
                staveImg.setLayoutX(staveImg.getLayoutX() - 27.0);
            }
        }
        else {
            degree = 0.0;
            adjustedWidth = -7;
            adjustedHeight = -91;
            image = new Image("View/LearningCompose/graphic/Crotchet_Rotate_Right.png");
            final ImageView staveImg2 = images.get("ledger");
            if (staveImg2 != null) {
                staveImg2.setLayoutX(staveImg2.getLayoutX() + 27.0);
            }
        }
        imgView.setImage(image);
        imgView.setRotate((double)degree);
        final Double imageTranslate = image.getHeight() + adjustedHeight;
        imgView.setTranslateY((double)imageTranslate);
        imgView.setTranslateX(image.getWidth() + adjustedWidth);
        return images;
    }
    
    public Boolean checkRotateStem(final Note rootNote) {
        final List<String> borderlineNotes = new ArrayList<String>(Arrays.asList("Eb3", "B3", "B#3", "C5", "Cb5"));
        final Integer midi = rootNote.getMidiNumber();
        return (midi > 51 && midi < 59) || midi > 72 || borderlineNotes.contains(rootNote.getNoteWithOctave());
    }
    
    public Double snapToXCoord(final Double xcoord) {
        if (this.isMoveNoteMode) {
            this.isMoveNoteMode = false;
            return xcoord;
        }
        Double snappedCoord = null;
        final Integer max = Integer.MAX_VALUE;
        final Double distanceBtwCol = 70.0;
        final Double initial = 140.0;
        Double middle = distanceBtwCol / 2.0;
        int col = 1;
        while (col < max) {
            final Double start = col * distanceBtwCol;
            final Double end = (col + 1) * distanceBtwCol;
            if (xcoord <= end) {
                if (xcoord < middle) {
                    snappedCoord = start;
                    break;
                }
                snappedCoord = end;
                break;
            }
            else {
                middle += distanceBtwCol;
                ++col;
            }
        }
        if (snappedCoord != null && this.positionXTracker.isXCoordTaken(snappedCoord) && !this.isComposeMode && !this.isCheckingMode) {
            this.isMoveNoteMode = false;
            return null;
        }
        this.isMoveNoteMode = false;
        return snappedCoord;
    }
    
    private Coord snapToStave(final Coord coord) {
        Double xCoord;
        if (this.isSnapMode) {
            xCoord = this.snapToXCoord(coord.getX());
        }
        else {
            xCoord = coord.getX();
        }
        if (xCoord != null) {
            final Double yCoord = coord.getY();
            for (final Map.Entry<Note, Coord> note : SheetController.staveBasicNotes.entrySet()) {
                final Double upper = note.getValue().getY() + 6.5;
                final Double lower = note.getValue().getY() - 6.5;
                if (yCoord > lower && yCoord < upper) {
                    return new Coord(xCoord, note.getValue().getY());
                }
            }
        }
        return null;
    }
    
    public Map<String, ImageView> placeKey(final String noteNoOctave, final String scaleName) {
        final Map<String, ImageView> images = new HashMap<String, ImageView>();
        final List<String> keySignature = KeySignature.getKeySignature(noteNoOctave, scaleName);
        if (keySignature != null) {
            Double runningX = 180.0;
            for (final String note : keySignature) {
                Note noteObj = NoteMap.getNote(note + "5");
                if ((noteObj.getMidiNumber() > 80 && noteObj.getAccidental().equals("#")) || (noteObj.getMidiNumber() > 75 && noteObj.getAccidental().equals("b"))) {
                    noteObj = NoteMap.getNote(noteObj.getNoteName() + "4");
                }
                images.put("key" + noteObj.getNoteWithOctave(), this.insertAccidental(noteObj.getAccidental(), new Coord(runningX, SheetController.staveBasicNotes.get(noteObj).getY())));
                Note noteObjBass = NoteMap.getNote(note + "3");
                if ((noteObjBass.getMidiNumber() > 57 && noteObjBass.getAccidental().equals("#")) || (noteObjBass.getMidiNumber() > 52 && noteObjBass.getAccidental().equals("b"))) {
                    noteObjBass = NoteMap.getNote(noteObjBass.getNoteName() + "2");
                }
                images.put("key" + noteObjBass.getNoteWithOctave(), this.insertAccidental(noteObjBass.getAccidental(), new Coord(runningX, SheetController.staveBasicNotes.get(noteObjBass).getY())));
                runningX += 15.0;
            }
        }
        return images;
    }
    
    public Note adjustNoteForKey(final Note rootNote, final String keyNote, final String keyScale) {
        Note newNote = rootNote;
        if (rootNote.getAccidental().equals(" ")) {
            final List<String> keyNotes = KeySignature.getKeySignature(keyNote, keyScale);
            for (final String keyNoteStr : keyNotes) {
                final Note keyNoteObj = NoteMap.getNote(keyNoteStr + "4");
                if (keyNoteObj.getBaseNoteNoOctave().equals(rootNote.getBaseNoteNoOctave())) {
                    if (keyNoteObj.getAccidental().equals("#")) {
                        final String newNoteStr = rootNote.getBaseNote().getNoteName() + "#" + rootNote.getOctave();
                        newNote = NoteMap.getNote(newNoteStr);
                    }
                    else {
                        if (!keyNoteObj.getAccidental().equals("b")) {
                            continue;
                        }
                        final String newNoteStr = rootNote.getBaseNote().getNoteName() + "b" + rootNote.getOctave();
                        newNote = NoteMap.getNote(newNoteStr);
                    }
                }
            }
        }
        return newNote;
    }
    
    public Map<String, ImageView> placeRest(final Double layoutX, final Boolean treble, final Image img, final Double offsetY) throws NoteNotShownException {
        this.resizeStave(layoutX);
        final ImageView restImg = new ImageView(img);
        restImg.setId("rest");
        Double coordY;
        if (treble) {
            coordY = SheetController.staveBasicNotes.get(NoteMap.getNote("B4")).getY();
        }
        else {
            coordY = SheetController.staveBasicNotes.get(NoteMap.getNote("D3")).getY();
        }
        Double coordX = this.snapToXCoord(layoutX);
        if (coordX == null) {
            coordX = layoutX;
        }
        restImg.setLayoutX((double)coordX);
        restImg.setLayoutY(coordY + offsetY);
        this.positionXTracker.addXCoord(coordX);
        this.stave.getChildren().add((Object)restImg);
        final Map<String, ImageView> images = new HashMap<String, ImageView>();
        images.put("rest", restImg);
        return images;
    }
    
    private void resizeStave(final Double layoutX) {
        if (layoutX > this.calculateRightBound()) {
            this.scrolly.setFitToWidth(false);
            this.hboxForWidth.setPrefWidth(layoutX + 25.0);
        }
        else {
            this.scrolly.setFitToWidth(true);
            this.hboxForWidth.setPrefWidth(1800.0);
        }
    }
    
    public ImageView placeDot(final ImageView imageViewForDot) {
        final Image img = new Image("View/LearningCompose/graphic/dot.png");
        final ImageView imgView = new ImageView(img);
        imgView.setId("dot");
        final Double imageRight = imageViewForDot.getLayoutX() + imageViewForDot.getImage().getWidth();
        final Double imageBottom = imageViewForDot.getLayoutY() + imageViewForDot.getImage().getHeight();
        imgView.setLayoutX((double)imageRight);
        imgView.setLayoutY(imageBottom - 20.0);
        this.stave.getChildren().add((Object)imgView);
        return imgView;
    }
    
    public void playSheet(final ActionEvent actionEvent) {
        this.outerExploreController.playSheet();
    }
    
    public void stopSheet(final ActionEvent actionEvent) {
        this.outerExploreController.stopSheetPlaying();
    }
    
    static {
        TOP_LEFT_BOUND = new Coord(180.0, 40.0);
        BOTTOM_BOUND = 420.0;
        SheetController.INVALID_IDS_FOR_NOTE = new ArrayList<String>(Arrays.asList("note", "clearBtn", "sheetPlayBtn", "sheetStopBtn", "keySigRect", "rest"));
        ONE_LINE = new Rectangle2D(0.0, 0.0, 45.0, 3.0);
        ONE_LINE_REVERSED = new Rectangle2D(0.0, 96.0, 45.0, 3.0);
        TWO_LINES = new Rectangle2D(0.0, 0.0, 45.0, 27.0);
        THREE_LINES = new Rectangle2D(0.0, 0.0, 45.0, 51.0);
        FOUR_LINES = new Rectangle2D(0.0, 0.0, 45.0, 75.0);
        FIVE_LINES = new Rectangle2D(0.0, 0.0, 45.0, 99.0);
        UPPER_TREBLE_STAVE_Y = 62.0;
        LOWER_BASS_STAVE_Y = 382.0;
        GAP_BETWEEN_NOTES = 70.0;
        (SheetController.staveBasicNotes = new TreeMap<Note, Coord>()).put(NoteMap.getNote("B5"), new Coord(250.0, 51.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("A5"), new Coord(250.0, 62.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("G5"), new Coord(250.0, 75.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("F5"), new Coord(250.0, 86.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("E5"), new Coord(250.0, 99.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("D5"), new Coord(250.0, 110.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("C5"), new Coord(250.0, 123.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("B4"), new Coord(250.0, 134.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("A4"), new Coord(250.0, 147.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("G4"), new Coord(250.0, 158.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("F4"), new Coord(250.0, 171.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("E4"), new Coord(250.0, 182.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("D4"), new Coord(250.0, 195.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("C4"), new Coord(250.0, 206.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("B3"), new Coord(250.0, 251.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("A3"), new Coord(250.0, 262.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("G3"), new Coord(250.0, 275.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("F3"), new Coord(250.0, 286.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("E3"), new Coord(250.0, 299.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("D3"), new Coord(250.0, 310.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("C3"), new Coord(250.0, 323.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("B2"), new Coord(250.0, 334.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("A2"), new Coord(250.0, 347.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("G2"), new Coord(250.0, 358.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("F2"), new Coord(250.0, 371.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("E2"), new Coord(250.0, 382.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("D2"), new Coord(250.0, 395.0));
        SheetController.staveBasicNotes.put(NoteMap.getNote("C2"), new Coord(250.0, 406.0));
        addAccidentalNotesToStaveNoteMap();
    }
}
