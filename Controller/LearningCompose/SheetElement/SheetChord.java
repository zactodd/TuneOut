

package Controller.LearningCompose.SheetElement;

import javafx.event.ActionEvent;
import Controller.LearningCompose.NoteNotSnapException;
import Model.CommandMessages;
import Controller.LearningCompose.NoteNotShownException;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import Model.Note.Chord.ChordMap;
import java.util.HashMap;
import Model.Note.NoteMap;
import Controller.LearningCompose.SheetController;
import java.util.Iterator;
import java.util.List;
import Model.Note.Note;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.controlsfx.control.PopOver;
import javafx.scene.image.ImageView;
import seng302.App;
import javafx.scene.image.Image;
import com.google.common.collect.HashBiMap;
import Controller.LearningCompose.OuterExploreCompose;
import javafx.scene.layout.HBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import com.google.common.collect.BiMap;
import javafx.scene.control.Button;
import java.util.Map;

public class SheetChord extends SheetElement
{
    private static final double FIRST_POS_X_CHORD = 250.0;
    private Map<String, Button> tutorDSLToButton;
    private BiMap<ToggleButton, ComboBox<String>> buttonToCombo;
    private HBox chord3TypeHbox;
    
    public SheetChord(final OuterExploreCompose outer) {
        this.buttonToCombo = (BiMap<ToggleButton, ComboBox<String>>)HashBiMap.create();
        this.sheetImage = new Image("View/LearningCompose/graphic/Crotchet.png");
        this.cursorImage = new Image("View/LearningCompose/graphic/Cursors/cursor_chord.png");
        this.inversion = "";
        this.arrangement = "";
        this.outer = outer;
        this.firstPosX = 250.0;
        if (this.type == null || this.type.isEmpty()) {
            this.type = "Major";
        }
        if (this.arrangement == null || this.arrangement.isEmpty()) {
            this.type = "Simultaneous";
        }
        this.env.setOuterTemplateController(App.getOuterTemplate());
    }
    
    String getBodyText() {
        final String noteWithOctave = this.rootNote.getNoteWithOctave();
        final StringBuilder allText = new StringBuilder();
        if (this.inversion.isEmpty()) {
            this.parser.executeCommand(String.format("chord(%s, \"%s\")", noteWithOctave, this.type));
        }
        else {
            this.parser.executeCommand(String.format("chord(%s, \"%s\", %s)", noteWithOctave, this.type, this.inversion));
        }
        allText.append("Notes in chord: ").append(this.env.getResponse());
        if (!this.inversion.isEmpty()) {
            allText.append("\nInversion: ").append(this.inversion);
        }
        return allText.toString();
    }
    
    public PopOver setPopOver(final ImageView imageView) {
        this.initTutorMap();
        final Note popoverNote = this.rootNote;
        (this.elementPopOver = new PopOver()).setDetachable(true);
        this.elementPopOver.setAnimated(true);
        this.elementPopOver.setConsumeAutoHidingEvents(false);
        this.elementPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
        this.elementPopOver.setHeaderAlwaysVisible(true);
        this.elementPopOver.setTitle(String.format("Chord: %s %s", popoverNote.getNoteWithOctave(), this.type));
        final Label label = new Label();
        label.setMaxWidth(2.147483647E9);
        label.setWrapText(true);
        label.setText(this.getBodyText());
        final Button playChord = new Button();
        playChord.setOnAction(event -> {
            String arrangementType = null;
            if (!this.arrangement.isEmpty()) {
                if (this.arrangement.equals("Simultaneous")) {
                    arrangementType = "-s";
                }
                else if (this.arrangement.equals("Arpeggio")) {
                    arrangementType = "-a";
                }
                if (this.inversion.isEmpty()) {
                    this.parser.executeCommand(String.format("playChord(%s, \"%s\", %s)", popoverNote.getNoteWithOctave(), this.type, arrangementType));
                }
                else {
                    this.parser.executeCommand(String.format("playChord(%s, \"%s\", %d, %s)", popoverNote.getNoteWithOctave(), this.type, Integer.parseInt(this.inversion), arrangementType));
                }
            }
        });
        final Image play = new Image("View/LearningCompose/graphic/play_small.png");
        playChord.setId("play");
        playChord.setGraphic((Node)new ImageView(play));
        final HBox horizBox = new HBox();
        horizBox.setSpacing(10.0);
        horizBox.getChildren().add((Object)playChord);
        final HBox hBox;
        this.tutorDSLToButton.forEach((tutorCommand, button) -> {
            button.setOnAction(event -> {
                this.parser.executeCommand(tutorCommand);
                if (!this.elementPopOver.isDetached()) {
                    this.elementPopOver.hide();
                }
            });
            hBox.getChildren().add((Object)button);
            return;
        });
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
    
    private Map<Note, Boolean> rotateNotes(final Map<Note, Boolean> rotateNotesMap, final List<Note> notesInChord, final List<List<Note>> needRotatedPairs, final Boolean allHeadUp) {
        Note needToRotate = null;
        for (final Note note : notesInChord) {
            Note lastNote = null;
            for (final List<Note> needRotatedPair : needRotatedPairs) {
                if (note.getNoteWithOctave().equals(needRotatedPair.get(0).toString())) {
                    if (allHeadUp) {
                        if (lastNote == null) {
                            rotateNotesMap.put(note, true);
                        }
                        else if (lastNote.toString().equals(needRotatedPair.get(0).toString())) {
                            needToRotate = needRotatedPair.get(1);
                        }
                        else {
                            rotateNotesMap.put(note, true);
                        }
                    }
                }
                else if (note.getNoteWithOctave().equals(needRotatedPair.get(1).toString()) && !allHeadUp && (lastNote == null || !lastNote.toString().equals(needRotatedPair.get(0).toString()))) {
                    rotateNotesMap.put(note, false);
                }
                lastNote = needRotatedPair.get(1);
            }
            if (needToRotate != null && needToRotate.toString().equals(note.getNoteWithOctave())) {
                rotateNotesMap.put(note, true);
            }
        }
        return rotateNotesMap;
    }
    
    private Map<Note, Boolean> flipNotes(final Map<Note, Boolean> flipNotesMap, final List<Note> notesInChord, final Integer noOfFlippedNotes) {
        final SheetController sheet = this.outer.getSheetController();
        for (final Note note : notesInChord) {
            if (noOfFlippedNotes <= notesInChord.size() / 2) {
                if (!sheet.checkRotateStem(note)) {
                    continue;
                }
                flipNotesMap.put(note, false);
            }
            else {
                if (sheet.checkRotateStem(note)) {
                    continue;
                }
                flipNotesMap.put(note, true);
            }
        }
        return flipNotesMap;
    }
    
    private Map<Note, Double> moveAccidental(final Map<Note, Double> accidentalPosMap, final List<Note> notes, final Boolean isRotated) {
        Note thirdNote = null;
        final List<List<Note>> overlappingPairs = NoteMap.findClosePairs(notes, 2);
        List<Note> previousPair = null;
        for (final List<Note> currentPair : overlappingPairs) {
            if (previousPair != null) {
                if (!previousPair.get(1).equals(currentPair.get(0))) {
                    continue;
                }
                thirdNote = currentPair.get(1);
                previousPair = currentPair;
            }
            else {
                previousPair = currentPair;
            }
        }
        Double gap = 0.0;
        Double initial = 0.0;
        Boolean alternate = true;
        if (isRotated) {
            initial = (gap = 30.0);
        }
        Note previousNote = null;
        Boolean nextNote = false;
        Boolean nextNote2 = false;
        for (final Note note : notes) {
            if (previousNote != null && note.hasAccidental()) {
                final Boolean isOverlapAccidental = NoteMap.isClosePair(previousNote, note, 6);
                if (isOverlapAccidental) {
                    if (alternate) {
                        if (thirdNote != null && thirdNote.equals(note)) {
                            nextNote2 = true;
                            gap += 60.0;
                        }
                        else if (thirdNote != null && nextNote) {
                            gap -= 30.0;
                        }
                        else {
                            gap += 30.0;
                        }
                    }
                    else if (thirdNote != null && nextNote2) {
                        gap = initial;
                    }
                    else if (thirdNote != null && thirdNote.equals(note)) {
                        nextNote = true;
                        gap += 30.0;
                    }
                    else {
                        gap = initial;
                    }
                    alternate = !alternate;
                }
                else {
                    gap = initial;
                }
                previousNote = note;
            }
            else if (note.hasAccidental()) {
                previousNote = note;
            }
            accidentalPosMap.put(note, gap);
        }
        return accidentalPosMap;
    }
    
    private Boolean isAllHeadUp(final List<Note> notesInChord, final Integer flippedNotes) {
        if (flippedNotes > notesInChord.size() / 2) {
            return true;
        }
        return false;
    }
    
    @Override
    public void placeElements(final Double firstLayoutX) {
        this.placedX = firstLayoutX;
        final SheetController sheet = this.outer.getSheetController();
        sheet.noteWarning.setVisible(false);
        String missingNoteText = "";
        this.elementImages = new HashMap<String, ImageView>();
        Boolean isWarning = false;
        for (final SheetElement element : this.outer.getSheetElements()) {
            for (final ImageView image : element.elementImages.values()) {
                this.outer.getSheetController().stave.getChildren().remove((Object)image);
            }
        }
        List<Note> notesInChord = ChordMap.notesInChord(this.rootNote, this.type.toLowerCase());
        if (!this.inversion.isEmpty()) {
            notesInChord = ChordMap.invertChord(notesInChord, Integer.parseInt(this.inversion));
        }
        final List<Note> notes = new ArrayList<Note>();
        Map<Note, Boolean> flipNotesMap = new LinkedHashMap<Note, Boolean>();
        Map<Note, Boolean> rotateNotesMap = new LinkedHashMap<Note, Boolean>();
        Map<Note, Double> accidentalPosMap = new LinkedHashMap<Note, Double>();
        for (final Note note : notesInChord) {
            notes.add(note);
            rotateNotesMap.put(note, null);
            flipNotesMap.put(note, null);
            accidentalPosMap.put(note, 0.0);
        }
        Integer numOfFlippedNotes = 0;
        for (final Note note2 : notes) {
            if (sheet.checkRotateStem(note2)) {
                ++numOfFlippedNotes;
            }
        }
        flipNotesMap = this.flipNotes(flipNotesMap, notes, numOfFlippedNotes);
        final Boolean isAllHeadUp = this.isAllHeadUp(notes, numOfFlippedNotes);
        final List<List<Note>> needRotatedPairs = NoteMap.findClosePairs(notes, 2);
        rotateNotesMap = this.rotateNotes(rotateNotesMap, notes, needRotatedPairs, isAllHeadUp);
        final Boolean isRotated = rotateNotesMap.values().contains(true) || rotateNotesMap.values().contains(false);
        accidentalPosMap = this.moveAccidental(accidentalPosMap, notes, isRotated);
        Double runningX = firstLayoutX;
        Integer numOfMissingNotes = 0;
        int counter = 1;
        sheet.setSnapToXMode(false);
        for (final Note note3 : notes) {
            Map<String, ImageView> images = null;
            try {
                images = sheet.placeNote(note3, runningX, this.sheetImage);
                if (!this.arrangement.equals("Arpeggio")) {
                    if (flipNotesMap.get(note3) != null) {
                        sheet.rotateImage(images.get("note"), images.get("note").getImage(), flipNotesMap.get(note3));
                    }
                    if (rotateNotesMap.get(note3) != null) {
                        sheet.rotateHeadOfNote(images.get("note"), images, rotateNotesMap.get(note3));
                    }
                    if (images.get("accidental") != null) {
                        final ImageView accidentalImg = images.get("accidental");
                        accidentalImg.setLayoutX(accidentalImg.getLayoutX() - accidentalPosMap.get(note3));
                    }
                }
                for (final Map.Entry<String, ImageView> entry : images.entrySet()) {
                    if (counter == 1) {
                        this.elementImages.put(entry.getKey(), entry.getValue());
                    }
                    else {
                        this.elementImages.put(entry.getKey() + counter, entry.getValue());
                    }
                }
                this.outer.setSelectedElement(this);
            }
            catch (NoteNotShownException e) {
                isWarning = true;
                missingNoteText = missingNoteText + " " + note3.getNoteWithOctave();
                ++numOfMissingNotes;
            }
            catch (NoteNotSnapException e2) {
                sheet.noteWarning.setText(CommandMessages.getMessage("CANNOT_SNAP_NOTE"));
                sheet.noteWarning.setVisible(true);
            }
            if (this.arrangement.equals("Arpeggio")) {
                runningX += SheetController.GAP_BETWEEN_NOTES;
            }
            ++counter;
        }
        if (!this.elementImages.isEmpty()) {
            this.addMouseClickedHandler(sheet);
        }
        if (isWarning) {
            String warningText;
            if (numOfMissingNotes == 1) {
                warningText = "The following note is not shown:";
            }
            else {
                warningText = "The following notes are not shown:";
            }
            warningText += missingNoteText;
            sheet.noteWarning.setText(warningText);
            sheet.noteWarning.setVisible(true);
        }
    }
    
    private void initTutorMap() {
        this.tutorDSLToButton = new HashMap<String, Button>() {
            {
                final Button openChordTutor = new Button();
                openChordTutor.setText("Chord Tutor");
                this.put("chordTutor()", openChordTutor);
                final Button openChordSpellingTutor = new Button();
                openChordSpellingTutor.setText("Spelling Tutor");
                this.put("chordSpellingTutor()", openChordSpellingTutor);
                final Button openChordFunctionTutor = new Button();
                openChordFunctionTutor.setText("Function Tutor");
                this.put("chordFunctionTutor()", openChordFunctionTutor);
            }
        };
    }
}
