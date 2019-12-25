// 
// Decompiled by Procyon v0.5.36
// 

package Controller.Keyboard;

import javafx.scene.input.MouseEvent;
import Model.Note.Settings.TempoInformation;
import Model.command.Command;
import Model.keyboardInput.KeyboardInput;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import Model.Note.NoteMap;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;
import Model.Note.unitDuration.UnitDuration;
import Model.Play.Play;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class KeyboardController
{
    @FXML
    private Button C_1;
    @FXML
    private Button D_1;
    @FXML
    private Button E_1;
    @FXML
    private Button F_1;
    @FXML
    private Button G_1;
    @FXML
    private Button A_1;
    @FXML
    private Button B_1;
    @FXML
    private Button C_2;
    @FXML
    private Button D_2;
    @FXML
    private Button E_2;
    @FXML
    private Button F_2;
    @FXML
    private Button G_2;
    @FXML
    private Button A_2;
    @FXML
    private Button B_2;
    @FXML
    private Button CS_1;
    @FXML
    private Button DS_1;
    @FXML
    private Button FS_1;
    @FXML
    private Button GS_1;
    @FXML
    private Button AS_1;
    @FXML
    private Button CS_2;
    @FXML
    private Button DS_2;
    @FXML
    private Button FS_2;
    @FXML
    private Button GS_2;
    @FXML
    private Button AS_2;
    @FXML
    private Button C_3;
    @FXML
    private Button CS_3;
    @FXML
    private Button D_3;
    @FXML
    private Button DS_3;
    @FXML
    private Button E_3;
    @FXML
    private Button F_3;
    @FXML
    private Button FS_3;
    @FXML
    private Button G_3;
    @FXML
    private Text TC_1;
    @FXML
    private Text TD_1;
    @FXML
    private Text TE_1;
    @FXML
    private Text TF_1;
    @FXML
    private Text TG_1;
    @FXML
    private Text TA_1;
    @FXML
    private Text TB_1;
    @FXML
    private Text TC_2;
    @FXML
    private Text TD_2;
    @FXML
    private Text TE_2;
    @FXML
    private Text TF_2;
    @FXML
    private Text TG_2;
    @FXML
    private Text TA_2;
    @FXML
    private Text TB_2;
    @FXML
    private Text TCS_1;
    @FXML
    private Text TDS_1;
    @FXML
    private Text TFS_1;
    @FXML
    private Text TGS_1;
    @FXML
    private Text TAS_1;
    @FXML
    private Text TCS_2;
    @FXML
    private Text TDS_2;
    @FXML
    private Text TFS_2;
    @FXML
    private Text TGS_2;
    @FXML
    private Text TAS_2;
    @FXML
    private Text TC_3;
    @FXML
    private Text TCS_3;
    @FXML
    private Text TD_3;
    @FXML
    private Text TDS_3;
    @FXML
    private Text TE_3;
    @FXML
    private Text TF_3;
    @FXML
    private Text TFS_3;
    @FXML
    private Text TG_3;
    @FXML
    private ImageView DC_1;
    @FXML
    private ImageView DD_1;
    @FXML
    private ImageView DE_1;
    @FXML
    private ImageView DF_1;
    @FXML
    private ImageView DG_1;
    @FXML
    private ImageView DA_1;
    @FXML
    private ImageView DB_1;
    @FXML
    private ImageView DC_2;
    @FXML
    private ImageView DD_2;
    @FXML
    private ImageView DE_2;
    @FXML
    private ImageView DF_2;
    @FXML
    private ImageView DG_2;
    @FXML
    private ImageView DA_2;
    @FXML
    private ImageView DB_2;
    @FXML
    private ImageView DCS_1;
    @FXML
    private ImageView DDS_1;
    @FXML
    private ImageView DFS_1;
    @FXML
    private ImageView DGS_1;
    @FXML
    private ImageView DAS_1;
    @FXML
    private ImageView DCS_2;
    @FXML
    private ImageView DDS_2;
    @FXML
    private ImageView DFS_2;
    @FXML
    private ImageView DGS_2;
    @FXML
    private ImageView DAS_2;
    @FXML
    private ImageView DC_3;
    @FXML
    private ImageView DCS_3;
    @FXML
    private ImageView DD_3;
    @FXML
    private ImageView DDS_3;
    @FXML
    private ImageView DE_3;
    @FXML
    private ImageView DF_3;
    @FXML
    private ImageView DFS_3;
    @FXML
    private ImageView DG_3;
    @FXML
    private Rectangle shiftControlOnHold;
    @FXML
    private AnchorPane keyboardAnchorPane;
    private boolean allOrOne;
    private Play playEnv;
    private final String left = "l";
    private final String right = "r";
    private final String order = "-a";
    private final int initialGap = 0;
    private final Play.PlayStyle swingOff;
    private UnitDuration unitDuration;
    private boolean dotNotationState;
    private List<Button> keys;
    private List<Text> labels;
    private List<ImageView> highlights;
    private List<String> notesWithOctaves;
    private boolean controlPressStatus;
    private boolean shiftPressStatus;
    private List<Integer> toBePlayedMidi;
    private List<String> toBePlayedNotes;
    private List<String> scaleToPlay;
    
    public KeyboardController() {
        this.swingOff = Play.PlayStyle.NONE;
        this.dotNotationState = false;
        this.keys = new ArrayList<Button>();
        this.labels = new ArrayList<Text>();
        this.highlights = new ArrayList<ImageView>();
        this.notesWithOctaves = new ArrayList<String>();
        this.controlPressStatus = false;
        this.shiftPressStatus = false;
        this.toBePlayedMidi = new ArrayList<Integer>();
        this.toBePlayedNotes = new ArrayList<String>();
    }
    
    protected void focusOnPane() {
        Platform.runLater((Runnable)new Runnable() {
            @Override
            public void run() {
                KeyboardController.this.keyboardAnchorPane.requestFocus();
            }
        });
    }
    
    public void setUpKeys(final String view, Integer currentOctave) {
        this.focusOnPane();
        this.listsOfElements(view);
        if (view.equals("r")) {
            --currentOctave;
        }
        for (final Button key : this.keys) {
            final String noteName = this.findNoteNameWithOctaves(key.getId().split("_"), currentOctave);
            this.notesWithOctaves.add(noteName);
        }
        this.playEnv = new Play(Play.PlayType.OVERLAPPING);
        this.setUpMouseListener();
        this.setUpControlShiftListener();
    }
    
    private void listsOfElements(final String view) {
        switch (view) {
            case "l": {
                this.keys.addAll(Arrays.asList(this.A_1, this.AS_1, this.B_1, this.C_2, this.CS_2, this.D_2, this.DS_2, this.E_2, this.F_2, this.FS_2, this.G_2, this.GS_2, this.A_2, this.AS_2, this.B_2, this.C_3, this.CS_3, this.D_3, this.DS_3, this.E_3, this.F_3, this.FS_3, this.G_3));
                this.labels.addAll(Arrays.asList(this.TA_1, this.TAS_1, this.TB_1, this.TC_2, this.TCS_2, this.TD_2, this.TDS_2, this.TE_2, this.TF_2, this.TFS_2, this.TG_2, this.TGS_2, this.TA_2, this.TAS_2, this.TB_2, this.TC_3, this.TCS_3, this.TD_3, this.TDS_3, this.TE_3, this.TF_3, this.TFS_3, this.TG_3));
                this.highlights.addAll(Arrays.asList(this.DA_1, this.DAS_1, this.DB_1, this.DC_2, this.DCS_2, this.DD_2, this.DDS_2, this.DE_2, this.DF_2, this.DFS_2, this.DG_2, this.DGS_2, this.DA_2, this.DAS_2, this.DB_2, this.DC_3, this.DCS_3, this.DD_3, this.DDS_3, this.DE_3, this.DF_3, this.DFS_3, this.DG_3));
                break;
            }
            case "r": {
                this.keys.addAll(Arrays.asList(this.D_1, this.DS_1, this.E_1, this.F_1, this.FS_1, this.G_1, this.GS_1, this.A_1, this.AS_1, this.B_1, this.C_2, this.CS_2, this.D_2, this.DS_2, this.E_2, this.F_2, this.FS_2, this.G_2, this.GS_2, this.A_2, this.AS_2, this.B_2, this.C_3));
                this.labels.addAll(Arrays.asList(this.TD_1, this.TDS_1, this.TE_1, this.TF_1, this.TFS_1, this.TG_1, this.TGS_1, this.TA_1, this.TAS_1, this.TB_1, this.TC_2, this.TCS_2, this.TD_2, this.TDS_2, this.TE_2, this.TF_2, this.TFS_2, this.TG_2, this.TGS_2, this.TA_2, this.TAS_2, this.TB_2, this.TC_3));
                this.highlights.addAll(Arrays.asList(this.DD_1, this.DDS_1, this.DE_1, this.DF_1, this.DFS_1, this.DG_1, this.DGS_1, this.DA_1, this.DAS_1, this.DB_1, this.DC_2, this.DCS_2, this.DD_2, this.DDS_2, this.DE_2, this.DF_2, this.DFS_2, this.DG_2, this.DGS_2, this.DA_2, this.DAS_2, this.DB_2, this.DC_3));
                break;
            }
            default: {
                this.keys.addAll(Arrays.asList(this.C_1, this.CS_1, this.D_1, this.DS_1, this.E_1, this.F_1, this.FS_1, this.G_1, this.GS_1, this.A_1, this.AS_1, this.B_1, this.C_2, this.CS_2, this.D_2, this.DS_2, this.E_2, this.F_2, this.FS_2, this.G_2, this.GS_2, this.A_2, this.AS_2, this.B_2));
                this.labels.addAll(Arrays.asList(this.TC_1, this.TCS_1, this.TD_1, this.TDS_1, this.TE_1, this.TF_1, this.TFS_1, this.TG_1, this.TGS_1, this.TA_1, this.TAS_1, this.TB_1, this.TC_2, this.TCS_2, this.TD_2, this.TDS_2, this.TE_2, this.TF_2, this.TFS_2, this.TG_2, this.TGS_2, this.TA_2, this.TAS_2, this.TB_2));
                this.highlights.addAll(Arrays.asList(this.DC_1, this.DCS_1, this.DD_1, this.DDS_1, this.DE_1, this.DF_1, this.DFS_1, this.DG_1, this.DGS_1, this.DA_1, this.DAS_1, this.DB_1, this.DC_2, this.DCS_2, this.DD_2, this.DDS_2, this.DE_2, this.DF_2, this.DFS_2, this.DG_2, this.DGS_2, this.DA_2, this.DAS_2, this.DB_2));
                break;
            }
        }
    }
    
    private String findNoteNameWithOctaves(final String[] noteAndOct, final Integer currentOctave) {
        final int nextOct = currentOctave + Integer.parseInt(noteAndOct[1]) - 1;
        final String noteName = noteAndOct[0].replace("S", "#") + nextOct;
        return noteName;
    }
    
    private void setUpMouseListener() {
        int index = 0;
        for (final Button key : this.keys) {
            final String noteName = this.notesWithOctaves.get(index);
            final int jindex = index;
            key.setOnMousePressed(event -> {
                final Integer midi = NoteMap.getMidi(noteName);
                if (!this.highlights.get(jindex).isVisible()) {
                    this.highlights.get(jindex).setVisible(true);
                    if (this.allOrOne) {
                        this.labels.get(jindex).setVisible(true);
                    }
                    if (this.controlPressStatus) {
                        this.toBePlayedMidi.add(midi);
                    }
                    else if (this.shiftPressStatus) {
                        this.toBePlayedNotes.add(noteName);
                    }
                    else if (!KeyboardInput.isInputAccepted()) {
                        this.playEnv.playNote(midi, TempoInformation.getTempInBpm(), this.unitDuration, true);
                    }
                }
                else {
                    this.highlights.get(jindex).setVisible(false);
                    if (this.allOrOne) {
                        this.labels.get(jindex).setVisible(false);
                    }
                    if (this.toBePlayedNotes.contains(noteName)) {
                        this.toBePlayedNotes.remove(noteName);
                    }
                    if (this.toBePlayedMidi.contains(midi)) {
                        this.toBePlayedMidi.remove(midi);
                    }
                }
                if (KeyboardInput.isInputAccepted()) {
                    KeyboardInput.copyToInputField(this.labels.get(jindex).getText());
                }
            });
            key.setOnMouseReleased(event -> {
                if (!this.controlPressStatus && !this.shiftPressStatus) {
                    this.highlights.get(jindex).setVisible(false);
                    if (this.allOrOne) {
                        this.labels.get(jindex).setVisible(false);
                    }
                }
                this.focusOnPane();
            });
            ++index;
        }
    }
    
    private void setUpControlShiftListener() {
        this.keyboardAnchorPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.CONTROL && !this.shiftPressStatus) {
                this.shiftControlOnHold.setOpacity(0.2);
                if (!this.controlPressStatus) {
                    this.clearBuffer();
                }
                this.controlPressStatus = true;
                if (KeyboardInput.isInputAccepted()) {
                    KeyboardInput.setGroupMode(true);
                }
            }
            else if (event.getCode() == KeyCode.SHIFT && !this.controlPressStatus) {
                this.shiftControlOnHold.setOpacity(0.2);
                if (!this.shiftPressStatus) {
                    this.clearBuffer();
                }
                this.shiftPressStatus = true;
                if (KeyboardInput.isInputAccepted()) {
                    KeyboardInput.setGroupMode(true);
                }
            }
        });
        this.keyboardAnchorPane.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.CONTROL) {
                if (!this.toBePlayedMidi.isEmpty() && !KeyboardInput.isInputAccepted()) {
                    this.playEnv.playChordSimultaneous((ArrayList)this.toBePlayedMidi, 0, this.unitDuration, true);
                }
                if (this.controlPressStatus) {
                    if (KeyboardInput.isInputAccepted()) {
                        final List<String> notes = new ArrayList<String>();
                        for (final Integer midi : this.toBePlayedMidi) {
                            notes.add(Command.translate(this.labels.get(0).getText().toString(), midi.toString()));
                        }
                        KeyboardInput.copyToInputField(notes);
                    }
                    this.changeKeyboardStateAfterShiftControlReleased();
                }
                this.controlPressStatus = false;
            }
            else if (event.getCode() == KeyCode.SHIFT) {
                if (this.toBePlayedNotes.size() > 1 && !KeyboardInput.isInputAccepted()) {
                    this.scaleToPlay = this.toBePlayedNotes;
                    this.playEnv.playScale(this.scaleToPlay, "-a", 0, this.swingOff, this.unitDuration, true);
                }
                else if (this.toBePlayedNotes.size() == 1 && !KeyboardInput.isInputAccepted()) {
                    final int toBePlayNote = NoteMap.getMidi(this.toBePlayedNotes.get(0));
                    this.playEnv.playNote(toBePlayNote, TempoInformation.getTempInBpm(), this.unitDuration, true);
                }
                if (this.shiftPressStatus) {
                    if (KeyboardInput.isInputAccepted()) {
                        final List<String> notes = new ArrayList<String>();
                        for (final String note : this.toBePlayedNotes) {
                            notes.add(Command.translate(this.labels.get(0).getText().toString(), note.toString()));
                        }
                        KeyboardInput.copyToInputField(notes);
                    }
                    this.changeKeyboardStateAfterShiftControlReleased();
                }
                this.shiftPressStatus = false;
            }
        });
    }
    
    protected void clearBuffer() {
        this.toBePlayedMidi.clear();
        this.toBePlayedNotes.clear();
    }
    
    protected void clearStatusShiftControl() {
        this.shiftPressStatus = false;
        this.controlPressStatus = false;
    }
    
    protected void changeKeyboardStateAfterShiftControlReleased() {
        this.shiftControlOnHold.setOpacity(0.0);
        this.disableAllHighlights();
    }
    
    private void setUpLabels(final boolean noteMidi) {
        int index = 0;
        for (Text label : this.labels) {
            String noteName = this.notesWithOctaves.get(index);
            if (noteMidi) {
                final Integer midiNum = NoteMap.getMidi(noteName);
                noteName = midiNum.toString();
            }
            label = this.labels.get(index);
            label.setText(noteName);
            ++index;
        }
    }
    
    protected void turnLabelsOn(final boolean on) {
        if (!on) {
            this.allOrOne = false;
        }
        for (final Text label : this.labels) {
            label.setVisible(on);
        }
        this.focusOnPane();
    }
    
    protected void noteOrMidi(final boolean noteMidi) {
        this.setUpLabels(noteMidi);
        this.focusOnPane();
    }
    
    protected void setAllOrOne(final boolean status) {
        this.allOrOne = status;
    }
    
    protected boolean controlKeyStatus() {
        return this.controlPressStatus;
    }
    
    protected boolean shiftKeyStatus() {
        return this.shiftPressStatus;
    }
    
    protected void setUnitDurationValue(final UnitDuration ud) {
        this.unitDuration = ud;
    }
    
    protected boolean getDotNotationState() {
        return this.dotNotationState;
    }
    
    protected void setDotNotationState(final boolean dotNotationState) {
        this.dotNotationState = dotNotationState;
    }
    
    protected void disableAllHighlights() {
        int index = 0;
        for (final ImageView highlight : this.highlights) {
            highlight.setVisible(false);
            if (this.allOrOne) {
                this.labels.get(index).setVisible(false);
            }
            ++index;
        }
    }
}
