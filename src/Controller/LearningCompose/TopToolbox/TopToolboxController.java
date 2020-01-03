

package Controller.LearningCompose.TopToolbox;

import Controller.LearningCompose.PercussionSheetInfo;

import java.util.List;
import Model.Note.Note;
import Controller.LearningCompose.ElementDuration;
import Model.Note.NoteMap;
import Controller.LearningCompose.OuterLearningController;
import Controller.LearningCompose.SheetElement.SheetElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import Controller.LearningCompose.OuterExploreCompose;
import Controller.LearningCompose.SheetController;

public class TopToolboxController
{
    protected SheetController sheetController;
    protected OuterExploreCompose outerExploreController;
    private AnchorPane noteAnchorPane;
    private AnchorPane accidentalAnchorPane;
    private AnchorPane scaleAnchorPane;
    private AnchorPane intervalAnchorPane;
    private AnchorPane chordAnchorPane;
    private AnchorPane chordInversionAnchorPane;
    private AnchorPane chordArrangementAnchorPane;
    private AnchorPane noteRestDurationAnchorPane;
    private AnchorPane percussionAnchorPane;
    private AnchorPane signatureAnchorPane;
    NoteController noteController;
    AccidentalController accidentalController;
    ChordController chordController;
    ChordInversionController chordInversionController;
    ChordArrangementController chordArrangementController;
    NoteRestDurationController noteRestDurationController;
    PercussionController percussionController;
    SignatureController signatureController;
    @FXML
    private AnchorPane topToolboxGridPane;
    @FXML
    private VBox vboxTopToolBox;
    @FXML
    public HBox hboxTopToolBox;
    
    public void initializeTopToolBoxController(final OuterExploreCompose outerExploreController) throws IOException {
        this.sheetController = outerExploreController.getSheetController();
        this.outerExploreController = outerExploreController;
        final FXMLLoader noteLoader = new FXMLLoader();
        noteLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/note.fxml"));
        this.noteAnchorPane = (AnchorPane)noteLoader.load();
        (this.noteController = (NoteController)noteLoader.getController()).setTopToolboxController(this);
        final FXMLLoader accidentalLoader = new FXMLLoader();
        accidentalLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/accidental.fxml"));
        this.accidentalAnchorPane = (AnchorPane)accidentalLoader.load();
        (this.accidentalController = (AccidentalController)accidentalLoader.getController()).setTopToolboxController(this);
        this.accidentalController.naturalBtn.setManaged(false);
        final FXMLLoader scaleLoader = new FXMLLoader();
        scaleLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/scale.fxml"));
        this.scaleAnchorPane = (AnchorPane)scaleLoader.load();
        final ScaleController scaleController = (ScaleController)scaleLoader.getController();
        scaleController.setTopToolboxController(this);
        final FXMLLoader intervalLoader = new FXMLLoader();
        intervalLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/interval.fxml"));
        this.intervalAnchorPane = (AnchorPane)intervalLoader.load();
        final IntervalController intervalController = (IntervalController)intervalLoader.getController();
        intervalController.setTopToolboxController(this);
        final FXMLLoader signatureLoader = new FXMLLoader();
        signatureLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/signature.fxml"));
        this.signatureAnchorPane = (AnchorPane)signatureLoader.load();
        (this.signatureController = (SignatureController)signatureLoader.getController()).setTopToolboxController(this);
        final FXMLLoader chordLoader = new FXMLLoader();
        chordLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/chord.fxml"));
        this.chordAnchorPane = (AnchorPane)chordLoader.load();
        (this.chordController = (ChordController)chordLoader.getController()).setTopToolboxController(this);
        final FXMLLoader chordInversionLoader = new FXMLLoader();
        chordInversionLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/chordInversion.fxml"));
        this.chordInversionAnchorPane = (AnchorPane)chordInversionLoader.load();
        (this.chordInversionController = (ChordInversionController)chordInversionLoader.getController()).setTopToolboxController(this);
        final FXMLLoader chordArrangementLoader = new FXMLLoader();
        chordArrangementLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/chordArrangement.fxml"));
        this.chordArrangementAnchorPane = (AnchorPane)chordArrangementLoader.load();
        (this.chordArrangementController = (ChordArrangementController)chordArrangementLoader.getController()).setTopToolboxController(this);
        final FXMLLoader noteRestDurationLoader = new FXMLLoader();
        noteRestDurationLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/notesRests.fxml"));
        this.noteRestDurationAnchorPane = (AnchorPane)noteRestDurationLoader.load();
        (this.noteRestDurationController = (NoteRestDurationController)noteRestDurationLoader.getController()).setTopToolboxController(this);
        final FXMLLoader percussionLoader = new FXMLLoader();
        percussionLoader.setLocation(this.getClass().getResource("/View/LearningCompose/TopToolbox/percussion.fxml"));
        this.percussionAnchorPane = (AnchorPane)percussionLoader.load();
        (this.percussionController = (PercussionController)percussionLoader.getController()).setTopToolboxController(this);
    }
    
    private HBox combineNotesAccidentals() {
        final HBox hbox = new HBox();
        hbox.setSpacing(10.0);
        hbox.getChildren().addAll((Object[])new Node[] { (Node)this.noteAnchorPane, (Node)this.accidentalAnchorPane });
        return hbox;
    }
    
    private VBox combineInversionAndArrangement() {
        final VBox chordOption = new VBox();
        chordOption.getChildren().add((Object)this.chordInversionAnchorPane);
        chordOption.getChildren().add((Object)this.chordArrangementAnchorPane);
        chordOption.setSpacing(10.0);
        chordOption.setAlignment(Pos.CENTER_RIGHT);
        return chordOption;
    }
    
    private HBox combineChordAndItsOption() {
        final HBox chordAndItsOption = new HBox();
        chordAndItsOption.getChildren().add((Object)this.chordAnchorPane);
        chordAndItsOption.getChildren().add((Object)this.combineInversionAndArrangement());
        chordAndItsOption.setAlignment(Pos.CENTER);
        chordAndItsOption.setSpacing(85.0);
        return chordAndItsOption;
    }
    
    public void showNoteOptions() {
        this.sheetController.setSnapToXMode(true);
        this.sheetController.setIsPercussionMode(false);
        this.vboxTopToolBox.getChildren().clear();
        this.vboxTopToolBox.getChildren().add((Object)this.combineNotesAccidentals());
    }
    
    public void hideOptions() {
        this.vboxTopToolBox.getChildren().clear();
    }
    
    public void showScaleOptions() {
        this.sheetController.setSnapToXMode(false);
        this.sheetController.setComposeMode(false);
        this.sheetController.setIsPercussionMode(false);
        this.vboxTopToolBox.getChildren().clear();
        this.vboxTopToolBox.getChildren().add((Object)this.combineNotesAccidentals());
        this.vboxTopToolBox.getChildren().add((Object)this.scaleAnchorPane);
    }
    
    public void showChordOptions() {
        this.sheetController.setSnapToXMode(false);
        this.sheetController.setComposeMode(false);
        this.sheetController.setIsPercussionMode(false);
        this.vboxTopToolBox.getChildren().add((Object)this.combineNotesAccidentals());
        this.vboxTopToolBox.getChildren().add((Object)this.combineChordAndItsOption());
        this.vboxTopToolBox.setPadding(new Insets(0.0, 0.0, 20.0, 0.0));
        this.chordInversionController.resetToggleBtn();
        this.chordArrangementController.resetToggleBtn();
    }
    
    public void showIntervalOptions() {
        this.showNoteOptions();
        this.sheetController.setSnapToXMode(false);
        this.sheetController.setComposeMode(false);
        this.sheetController.setIsPercussionMode(false);
        this.vboxTopToolBox.getChildren().add((Object)this.intervalAnchorPane);
    }
    
    public void showComposeMelodyOptions() {
        this.sheetController.setIsPercussionMode(false);
        this.sheetController.setSnapToXMode(true);
        this.sheetController.setComposeMode(true);
        this.vboxTopToolBox.getChildren().clear();
        this.vboxTopToolBox.getChildren().add((Object)this.combineNotesAccidentals());
        this.vboxTopToolBox.getChildren().add((Object)this.noteRestDurationAnchorPane);
        this.noteRestDurationController.setUpMapButtons();
        this.outerExploreController.topShowEditController.setUpMelodiesMenuList();
    }
    
    public void showPercussionOptions() {
        this.sheetController.setIsPercussionMode(true);
        this.sheetController.setSnapToXMode(true);
        this.sheetController.setComposeMode(true);
        this.vboxTopToolBox.getChildren().clear();
        this.vboxTopToolBox.getChildren().add((Object)this.percussionAnchorPane);
    }
    
    public void disableThirdInversionBtn(final Boolean disable) {
        this.chordInversionController.disableThirdInversionBtn(disable);
    }
    
    public void setInversion(final String inversion) {
        this.outerExploreController.setInversion(inversion);
    }
    
    public void setArrangement(final String arrangement) {
        this.outerExploreController.setArrangement(arrangement);
    }
    
    public void showSignatureOptions() {
        this.showNoteOptions();
        this.sheetController.setSnapToXMode(true);
        this.sheetController.setComposeMode(false);
        this.vboxTopToolBox.getChildren().add((Object)this.signatureAnchorPane);
    }
    
    public void placeNote(final String typeFromToolbox) {
        this.sheetController.setMoveNoteMode(true);
        Boolean placeStraightAway = false;
        this.setOptions(typeFromToolbox, null);
        if (this.outerExploreController.getClass().equals(OuterLearningController.class)) {
            placeStraightAway = true;
        }
        if (placeStraightAway || this.outerExploreController.getSelectedElement() != null) {
            Note note = null;
            String accidental = null;
            if (this.noteController.getSelectedNote() != null) {
                note = NoteMap.getNote(this.noteController.getSelectedNote());
                final String baseNote = this.noteController.getSelectedNote().substring(0, 1);
                final String noteMinusOctave = note.getNoteName();
                final Integer noteOctave = note.getOctave();
                accidental = this.accidentalController.getSelectedAccidental();
                if (accidental != null) {
                    final String s = accidental;
                    switch (s) {
                        case "#": {
                            note = NoteMap.getNote(noteMinusOctave + "#" + noteOctave);
                            break;
                        }
                        case "b": {
                            note = NoteMap.getNote(noteMinusOctave + "b" + noteOctave);
                            break;
                        }
                        default: {
                            note = NoteMap.getNote(baseNote + noteOctave);
                            break;
                        }
                    }
                }
            }
            else if (this.outerExploreController.getElementDuration().getElementType().equals(ElementDuration.ElementType.REST)) {
                note = new Note("R", -1, -1, true);
            }
            else if (!this.outerExploreController.getElementDuration().getElementType().equals(ElementDuration.ElementType.REST)) {}
            if (note != null) {
                SheetElement element = this.outerExploreController.getSelectedElement();
                Boolean selectFirstNote = false;
                if (element == null && !this.sheetController.getComposeMode() && this.sheetController.getSnapToXMode()) {
                    final List<SheetElement> sheetElementList = this.outerExploreController.getSheetElements();
                    if (sheetElementList != null) {
                        Double min = Double.MAX_VALUE;
                        for (final SheetElement sheetElement : this.outerExploreController.getSheetElements()) {
                            if (sheetElement.getPlacedX() < min) {
                                element = sheetElement;
                                min = element.getPlacedX();
                                selectFirstNote = true;
                            }
                        }
                    }
                }
                if (element == null) {
                    element = this.outerExploreController.getSheetElementFactory().getNewElement();
                    element.setRootNote(note);
                    element.setAccidental(accidental);
                    this.setOptions(typeFromToolbox, element);
                    this.sheetController.setSnapToXMode(true);
                    element.placeElements(element.getFirstPosX());
                    this.outerExploreController.addElementToList(element);
                }
                else {
                    if (!selectFirstNote) {
                        element = this.outerExploreController.getSelectedElement();
                    }
                    this.setOptions(typeFromToolbox, element);
                    element.setRootNote(note);
                    element.setAccidental(accidental);
                    final Boolean initialSnapMode = this.sheetController.getSnapToXMode();
                    this.sheetController.setSnapToXMode(false);
                    element.moveElement();
                    this.sheetController.setSnapToXMode(initialSnapMode);
                }
                this.outerExploreController.setSelectedElement(element);
            }
        }
        this.sheetController.setMoveNoteMode(false);
    }
    
    private void setOptions(final String typeFromToolbox, final SheetElement element) {
        if (!typeFromToolbox.isEmpty()) {
            this.outerExploreController.setType(typeFromToolbox);
        }
        this.outerExploreController.setInversion(this.chordInversionController.getSelectedChordInversion());
        this.outerExploreController.setArrangement(this.chordArrangementController.getSelectedChordArrangement());
        if (element != null) {
            if (!this.outerExploreController.getType().isEmpty()) {
                element.setType(this.outerExploreController.getType());
            }
            element.setInversion(this.outerExploreController.getInversion());
            element.setArrangement(this.outerExploreController.getArrangement());
            element.setElementDuration(this.getDuration());
            element.setPercussionInfo(this.getPercussion());
        }
    }
    
    public void syncTopToolbox(final Note note, final String accidental, final ElementDuration elementDuration, final PercussionSheetInfo percussionInfo) {
        if (note != null && note.getMidiNumber() != -1) {
            this.noteController.updateOctaveAndButton(note);
            this.updateAccidentalButton(accidental);
        }
        if (elementDuration != null) {
            this.updateDuration(elementDuration);
        }
        if (percussionInfo != null) {
            this.updatePercussion(percussionInfo);
        }
    }
    
    public String getAccidental() {
        return this.accidentalController.getSelectedAccidental();
    }
    
    public void updateAccidentalButton(final String acc) {
        this.accidentalController.sharpBtn.setSelected(false);
        this.accidentalController.flatBtn.setSelected(false);
        if (acc != null) {
            switch (acc) {
                case "#": {
                    this.accidentalController.sharpBtn.setSelected(true);
                    break;
                }
                case "b": {
                    this.accidentalController.flatBtn.setSelected(true);
                    break;
                }
                case "n": {
                    this.accidentalController.naturalBtn.setSelected(true);
                    break;
                }
            }
        }
    }
    
    public void unSelectNote() {
        this.noteController.unselectNote();
    }
    
    public void changeSignature(final String newValue) {
        final SheetElement signature = this.outerExploreController.getSignature();
        signature.setType(newValue);
        this.sheetController.setSnapToXMode(false);
        signature.placeElements(signature.getFirstPosX());
        this.sheetController.setSnapToXMode(true);
    }
    
    public void enableNaturalButton(final Boolean enable) {
        this.accidentalController.naturalBtn.setManaged((boolean)enable);
        this.accidentalController.naturalBtn.setVisible((boolean)enable);
    }
    
    public String getSignatureType() {
        return this.signatureController.getSelectedKeyToggle();
    }
    
    public ElementDuration getDuration() {
        return this.noteRestDurationController.getElementDuration();
    }
    
    public PercussionSheetInfo getPercussion() {
        return this.percussionController.getPercussionInfo();
    }
    
    public void updateDuration(final ElementDuration elementDuration) {
        this.noteRestDurationController.updateDuration(elementDuration);
    }
    
    private void updatePercussion(final PercussionSheetInfo percussionInfo) {
        this.percussionController.updatePercussion(percussionInfo);
    }
    
    public void toggleDots() {
        this.noteRestDurationController.toggleDots();
    }
    
    public void toggleDisableNotesAccidentals(final Boolean disable) {
        this.noteController.toggleDisableNotes(disable);
        this.accidentalController.toggleDisableAccidentals(disable);
    }
    
    public void updateMenus() {
        this.outerExploreController.topShowEditController.updateMenus();
    }
}
