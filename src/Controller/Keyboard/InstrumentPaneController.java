

package Controller.Keyboard;

import Controller.OuterTemplateController;

import java.util.ArrayList;
import Model.Note.unitDuration.UnitDurationMap;
import javafx.event.ActionEvent;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.binding.Bindings;
import Model.Note.unitDuration.UnitDuration;
import javafx.beans.property.SimpleStringProperty;
import Model.keyboardInput.KeyboardInput;
import Model.Note.unitDuration.UnitDurationInformation;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import Controller.CommandLineController;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.ToggleButton;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class InstrumentPaneController
{
    @FXML
    private GridPane instrumentPane;
    @FXML
    private ToggleButton labelsOnOffBtn;
    @FXML
    private ToggleButton labelsAllOneBtn;
    @FXML
    private ToggleButton labelsNoteMidiBtn;
    @FXML
    private ImageView regimeLabel;
    @FXML
    private ScrollPane scrollPaneForRegimeLabel;
    @FXML
    private Tooltip tooltipIndication;
    @FXML
    private Tooltip tooltipOnOffBtn;
    @FXML
    private Tooltip tooltipOneAllBtn;
    @FXML
    private Tooltip tooltipNoteMidiBtn;
    @FXML
    private ToggleButton semiquaverBtn;
    @FXML
    private ToggleButton quaverBtn;
    @FXML
    private ToggleButton crotchetBtn;
    @FXML
    private ToggleButton minimBtn;
    @FXML
    private ToggleButton semibreveBtn;
    @FXML
    private Tooltip tooltipSemiquaverBtn;
    @FXML
    private Tooltip tooltipQuaverBtn;
    @FXML
    private Tooltip tooltipCrotchetBtn;
    @FXML
    private Tooltip tooltipMinimBtn;
    @FXML
    private Tooltip tooltipSemibreveBtn;
    private FullPianoKeyboardController fullPianoKeyboardController;
    private TwoOctavesKeyboardController twoOctavesKeyboardController;
    private final int singlePlay = 0;
    private final int simultaneous = 1;
    private final int arpeggio = 2;
    private final int input = 3;
    private boolean controlStatus;
    private boolean shiftStatus;
    private final String semibreveImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/ud_1.jpg')";
    private final String minimImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/ud_2.jpg')";
    private final String crotchetImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/ud_3.jpg')";
    private final String quaverImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/ud_4.jpg')";
    private final String semiquaverImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/ud_5.jpg')";
    private final String semibreveDottedImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/udd_1.jpg')";
    private final String minimDottedImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/udd_2.jpg')";
    private final String crotchetDottedImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/udd_3.jpg')";
    private final String quaverDottedImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/udd_4.jpg')";
    private final String semiquaverDottedImageInactive = "-fx-background-image: url('/View/Keyboard/graphic/udd_5.jpg')";
    private final String semibreveImageActive = "-fx-background-image: url('/View/Keyboard/graphic/ud_1a.jpg')";
    private final String minimImageActive = "-fx-background-image: url('/View/Keyboard/graphic/ud_2a.jpg')";
    private final String crotchetImageActive = "-fx-background-image: url('/View/Keyboard/graphic/ud_3a.jpg')";
    private final String quaverImageActive = "-fx-background-image: url('/View/Keyboard/graphic/ud_4a.jpg')";
    private final String semiquaverImageActive = "-fx-background-image: url('/View/Keyboard/graphic/ud_5a.jpg')";
    private final String semibreveDottedImageActive = "-fx-background-image: url('/View/Keyboard/graphic/udd_1a.jpg')";
    private final String minimDottedImageActive = "-fx-background-image: url('/View/Keyboard/graphic/udd_2a.jpg')";
    private final String crotchetDottedImageActive = "-fx-background-image: url('/View/Keyboard/graphic/udd_3a.jpg')";
    private final String quaverDottedImageActive = "-fx-background-image: url('/View/Keyboard/graphic/udd_4a.jpg')";
    private final String semiquaverDottedImageActive = "-fx-background-image: url('/View/Keyboard/graphic/udd_5a.jpg')";
    private final String tooltipTextIndication = "Default is single play or input\nHold Shift to play notes in sequence\nHold Ctrl to play notes simultaneously or input multiply notes";
    private final String tooltipTextOnOff = "Show labels on keyboard keys";
    private final String tooltipTextOneAll = "Show labels on every key,\nor only the key pressed";
    private final String tooltipTextNoteMidi = "Show labels as notes or midi numbers";
    private final String tooltipTextNoteMidiInput = "Input as notes or midi numbers";
    private final String tooltipSemiquaver = "Semiquaver (1/16)";
    private final String tooltipQuaver = "Quaver (1/8)";
    private final String tooltipCrotchet = "Crotchet (1/4)";
    private final String tooltipMinim = "Minim (1/2)";
    private final String tooltipSemibreve = "Semibreve (1)";
    private Image imageSinglePlay;
    private Image imageSimultaneous;
    private Image imageArpeggio;
    private Image imageInput;
    private String labelsNoteMidiPlay1;
    private String labelsNoteMidiPlay2;
    private String labelsNoteMidiInput1;
    private String labelsNoteMidiInput2;
    private String labelsNoteMidiStyle;
    private Image[] regimeArray;
    
    public InstrumentPaneController() {
        this.tooltipIndication = new Tooltip();
        this.tooltipOnOffBtn = new Tooltip();
        this.tooltipOneAllBtn = new Tooltip();
        this.tooltipNoteMidiBtn = new Tooltip();
        this.tooltipSemiquaverBtn = new Tooltip();
        this.tooltipQuaverBtn = new Tooltip();
        this.tooltipCrotchetBtn = new Tooltip();
        this.tooltipMinimBtn = new Tooltip();
        this.tooltipSemibreveBtn = new Tooltip();
        this.controlStatus = false;
        this.shiftStatus = false;
        this.imageSinglePlay = new Image("/View/Keyboard/graphic/regime1.jpg");
        this.imageSimultaneous = new Image("/View/Keyboard/graphic/regime2.jpg");
        this.imageArpeggio = new Image("/View/Keyboard/graphic/regime3.jpg");
        this.imageInput = new Image("/View/Keyboard/graphic/regime4.jpg");
        this.labelsNoteMidiPlay1 = "View/Keyboard/graphic/labelsNoteOrMidiState2.jpg";
        this.labelsNoteMidiPlay2 = "View/Keyboard/graphic/labelsNoteOrMidiState1.jpg";
        this.labelsNoteMidiInput1 = "View/Keyboard/graphic/labelsNoteOrMidiInputState2.jpg";
        this.labelsNoteMidiInput2 = "View/Keyboard/graphic/labelsNoteOrMidiInputState1.jpg";
        this.labelsNoteMidiStyle = "-fx-background-image: url(%1$s)";
        this.regimeArray = new Image[] { this.imageSinglePlay, this.imageSimultaneous, this.imageArpeggio, this.imageInput };
    }
    
    public void start(final Stage stage, final CommandLineController commandLine) {
        try {
            final FXMLLoader fullPianoKeyboardLoader = new FXMLLoader();
            fullPianoKeyboardLoader.setLocation(this.getClass().getResource("/View/Keyboard/fullPianoKeyboard.fxml"));
            final AnchorPane fullPianoKeyboardAnchorPane = (AnchorPane)fullPianoKeyboardLoader.load();
            this.instrumentPane.add((Node)fullPianoKeyboardAnchorPane, 0, 1);
            this.fullPianoKeyboardController = (FullPianoKeyboardController)fullPianoKeyboardLoader.getController();
            final FXMLLoader twoOctavesKeyboardLoader = new FXMLLoader();
            twoOctavesKeyboardLoader.setLocation(this.getClass().getResource("/View/Keyboard/twoOctavesKeyboard.fxml"));
            final AnchorPane twoOctavesKeyboardAnchorPane = (AnchorPane)twoOctavesKeyboardLoader.load();
            this.instrumentPane.add((Node)twoOctavesKeyboardAnchorPane, 0, 2);
            this.twoOctavesKeyboardController = (TwoOctavesKeyboardController)twoOctavesKeyboardLoader.getController();
            this.fullPianoKeyboardController.initialize(this.twoOctavesKeyboardController);
            this.twoOctavesKeyboardController.initialize();
            final Scene scene = new Scene((Parent)this.instrumentPane, 962.0, 576.0);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Piano style keyboard");
            final KeyCombination exitMnemonic = (KeyCombination)new KeyCodeCombination(KeyCode.ESCAPE, new KeyCombination.Modifier[0]);
            scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                if (exitMnemonic.match(event)) {
                    commandLine.turnOffKeyboardInput();
                    stage.close();
                }
            });
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setOnCloseRequest(windowEvent -> {
                commandLine.turnOffKeyboardInput();
                OuterTemplateController.removeKeyboardFromMemory();
            });
            this.setLabelsNoteMidiBtnStyle();
            stage.show();
            this.mouseListener();
            this.keyboardListener();
            this.setToolTips();
            this.setUnitDurationValue(UnitDurationInformation.getUnitDuration());
            this.unitDurationBtnId();
            this.unitDurationBtnImageSet();
            if (KeyboardInput.isInputAccepted()) {
                this.turnOnInput();
            }
            else {
                this.turnOffInput();
            }
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> this.isFocusLost(newValue));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private SimpleStringProperty noteOrInputSelected() {
        if (KeyboardInput.isInputAccepted()) {
            return new SimpleStringProperty(String.format(this.labelsNoteMidiStyle, this.labelsNoteMidiInput1));
        }
        return new SimpleStringProperty(String.format(this.labelsNoteMidiStyle, this.labelsNoteMidiPlay1));
    }
    
    private SimpleStringProperty noteOrInputUnSelected() {
        if (KeyboardInput.isInputAccepted()) {
            return new SimpleStringProperty(String.format(this.labelsNoteMidiStyle, this.labelsNoteMidiInput2));
        }
        return new SimpleStringProperty(String.format(this.labelsNoteMidiStyle, this.labelsNoteMidiPlay2));
    }
    
    private void isFocusLost(final Boolean state) {
        if (!state) {
            this.twoOctavesKeyboardController.changeKeyboardParametersForLostFocusSituation();
            if (!KeyboardInput.isInputAccepted()) {
                this.regimeLabel.setImage(this.regimeArray[0]);
            }
            else {
                this.regimeLabel.setImage(this.regimeArray[3]);
            }
        }
        else {
            final UnitDuration ud = this.createUnitDurationObject(UnitDurationInformation.getUnitDuration().getUnitDurationName());
            this.setUnitDurationValue(ud);
            this.unitDurationBtnId();
            this.unitDurationBtnImageSet();
        }
        if (state && !KeyboardInput.isInputAccepted()) {
            if (this.regimeLabel.getImage().equals(this.imageInput)) {
                this.regimeLabel.setImage(this.regimeArray[0]);
            }
            if (!this.labelsOnOffBtn.isSelected()) {
                this.labelsNoteMidiBtn.setVisible(false);
            }
        }
        else if (state && KeyboardInput.isInputAccepted()) {
            this.labelsNoteMidiBtn.setVisible(true);
        }
    }
    
    public void turnOnInput() {
        this.setLabelsNoteMidiBtnStyle();
        this.tooltipNoteMidiBtn.setText("Input as notes or midi numbers");
        this.labelsNoteMidiBtn.setTooltip(this.tooltipNoteMidiBtn);
        this.labelsNoteMidiBtn.setVisible(true);
        this.regimeLabel.setImage(this.regimeArray[3]);
    }
    
    public void turnOffInput() {
        this.setLabelsNoteMidiBtnStyle();
        this.tooltipNoteMidiBtn.setText("Show labels as notes or midi numbers");
        this.labelsNoteMidiBtn.setTooltip(this.tooltipNoteMidiBtn);
        if (!this.labelsOnOffBtn.isSelected()) {
            this.labelsNoteMidiBtn.setVisible(false);
        }
        this.regimeLabel.setImage(this.regimeArray[0]);
    }
    
    private void setLabelsNoteMidiBtnStyle() {
        this.labelsNoteMidiBtn.styleProperty().bind((ObservableValue)Bindings.when((ObservableBooleanValue)this.labelsNoteMidiBtn.selectedProperty()).then((ObservableStringValue)this.noteOrInputSelected()).otherwise((ObservableStringValue)this.noteOrInputUnSelected()));
    }
    
    private void mouseListener() {
        this.labelsAllOneBtn.setOnMousePressed(event -> {
            if (event.isControlDown() || event.isShiftDown()) {
                this.twoOctavesKeyboardController.setFocus();
            }
        });
        this.labelsNoteMidiBtn.setOnMousePressed(event -> {
            if (event.isControlDown() || event.isShiftDown()) {
                this.twoOctavesKeyboardController.setFocus();
            }
        });
        this.labelsOnOffBtn.setOnMousePressed(event -> {
            if (event.isControlDown() || event.isShiftDown()) {
                this.twoOctavesKeyboardController.setFocus();
            }
        });
        this.semiquaverBtn.setOnMousePressed(event -> this.twoOctavesKeyboardController.setFocus());
        this.quaverBtn.setOnMousePressed(event -> this.twoOctavesKeyboardController.setFocus());
        this.crotchetBtn.setOnMousePressed(event -> this.twoOctavesKeyboardController.setFocus());
        this.minimBtn.setOnMousePressed(event -> this.twoOctavesKeyboardController.setFocus());
        this.semibreveBtn.setOnMousePressed(event -> this.twoOctavesKeyboardController.setFocus());
        this.scrollPaneForRegimeLabel.setOnMousePressed(event -> this.twoOctavesKeyboardController.setFocus());
    }
    
    private void keyboardListener() {
        this.instrumentPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.CONTROL && !this.twoOctavesKeyboardController.shiftStatus()) {
                if (!KeyboardInput.isInputAccepted()) {
                    this.regimeLabel.setImage(this.regimeArray[1]);
                }
                else {
                    this.regimeLabel.setImage(this.regimeArray[3]);
                }
                this.controlStatus = true;
            }
            else if (event.getCode() == KeyCode.SHIFT && !this.twoOctavesKeyboardController.controlStatus()) {
                if (!KeyboardInput.isInputAccepted()) {
                    this.regimeLabel.setImage(this.regimeArray[2]);
                }
                else {
                    this.regimeLabel.setImage(this.regimeArray[3]);
                }
                this.shiftStatus = true;
            }
        });
        this.instrumentPane.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.CONTROL && this.controlStatus) {
                if (!KeyboardInput.isInputAccepted()) {
                    this.regimeLabel.setImage(this.regimeArray[0]);
                }
                else {
                    this.regimeLabel.setImage(this.regimeArray[3]);
                }
                this.controlStatus = false;
            }
            else if (event.getCode() == KeyCode.SHIFT && this.shiftStatus) {
                if (!KeyboardInput.isInputAccepted()) {
                    this.regimeLabel.setImage(this.regimeArray[0]);
                }
                else {
                    this.regimeLabel.setImage(this.regimeArray[3]);
                }
                this.shiftStatus = false;
            }
            else if (event.getCode() == KeyCode.PERIOD) {
                if (!this.getDotNotationState()) {
                    this.setDotNotationState(true);
                }
                else {
                    this.setDotNotationState(false);
                }
                final UnitDuration ud = this.createUnitDurationObject(UnitDurationInformation.getUnitDuration().getUnitDurationName());
                this.setUnitDurationValue(ud);
                this.unitDurationBtnImageSet();
            }
        });
    }
    
    private void setToolTips() {
        this.tooltipIndication.setText("Default is single play or input\nHold Shift to play notes in sequence\nHold Ctrl to play notes simultaneously or input multiply notes");
        this.tooltipOnOffBtn.setText("Show labels on keyboard keys");
        this.tooltipOneAllBtn.setText("Show labels on every key,\nor only the key pressed");
        this.tooltipNoteMidiBtn.setText("Show labels as notes or midi numbers");
        this.scrollPaneForRegimeLabel.setTooltip(this.tooltipIndication);
        this.labelsOnOffBtn.setTooltip(this.tooltipOnOffBtn);
        this.labelsAllOneBtn.setTooltip(this.tooltipOneAllBtn);
        this.labelsNoteMidiBtn.setTooltip(this.tooltipNoteMidiBtn);
        this.tooltipSemiquaverBtn.setText("Semiquaver (1/16)");
        this.tooltipQuaverBtn.setText("Quaver (1/8)");
        this.tooltipCrotchetBtn.setText("Crotchet (1/4)");
        this.tooltipMinimBtn.setText("Minim (1/2)");
        this.tooltipSemibreveBtn.setText("Semibreve (1)");
        this.semiquaverBtn.setTooltip(this.tooltipSemiquaverBtn);
        this.quaverBtn.setTooltip(this.tooltipQuaverBtn);
        this.crotchetBtn.setTooltip(this.tooltipCrotchetBtn);
        this.minimBtn.setTooltip(this.tooltipMinimBtn);
        this.semibreveBtn.setTooltip(this.tooltipSemibreveBtn);
    }
    
    @FXML
    private void labelsOnOff() {
        if (this.labelsOnOffBtn.isSelected()) {
            this.twoOctavesKeyboardController.noteOrMidi(false);
            this.twoOctavesKeyboardController.turnLabelsOn(true);
            this.twoOctavesKeyboardController.allOrOne(false);
            this.labelsAllOneBtn.setVisible(true);
            this.labelsAllOneBtn.setSelected(false);
            this.labelsNoteMidiBtn.setVisible(true);
            this.labelsNoteMidiBtn.setSelected(false);
        }
        else {
            this.twoOctavesKeyboardController.turnLabelsOn(false);
            this.labelsAllOneBtn.setVisible(false);
            if (!KeyboardInput.isInputAccepted()) {
                this.labelsNoteMidiBtn.setVisible(false);
            }
        }
    }
    
    @FXML
    private void labelsAllOrOne() {
        if (this.labelsAllOneBtn.isSelected()) {
            this.twoOctavesKeyboardController.turnLabelsOn(false);
            this.twoOctavesKeyboardController.allOrOne(true);
        }
        else {
            this.twoOctavesKeyboardController.turnLabelsOn(true);
            this.twoOctavesKeyboardController.allOrOne(false);
        }
    }
    
    @FXML
    private void labelsNoteOrMidi() {
        if (this.labelsNoteMidiBtn.isSelected()) {
            this.twoOctavesKeyboardController.noteOrMidi(true);
        }
        else {
            this.twoOctavesKeyboardController.noteOrMidi(false);
        }
    }
    
    @FXML
    private void selectUnitDuration(final ActionEvent eventPressBtn) {
        final ToggleButton clickedToggleButton = (ToggleButton)eventPressBtn.getSource();
        final String id;
        final String btnId = id = clickedToggleButton.getId();
        switch (id) {
            case "ud1": {
                final UnitDuration ud = this.createUnitDurationObject("Semibreve");
                this.setUnitDurationValue(ud);
                break;
            }
            case "ud2": {
                final UnitDuration ud = this.createUnitDurationObject("Minim");
                this.setUnitDurationValue(ud);
                break;
            }
            case "ud3": {
                final UnitDuration ud = this.createUnitDurationObject("Crotchet");
                this.setUnitDurationValue(ud);
                break;
            }
            case "ud4": {
                final UnitDuration ud = this.createUnitDurationObject("Quaver");
                this.setUnitDurationValue(ud);
                break;
            }
            case "ud5": {
                final UnitDuration ud = this.createUnitDurationObject("Semiquaver");
                this.setUnitDurationValue(ud);
                break;
            }
        }
        this.unitDurationBtnId();
        this.unitDurationBtnImageSet();
    }
    
    private void unitDurationBtnId() {
        final String udName = UnitDurationInformation.getUnitDuration().getUnitDurationName();
        this.semiquaverBtn.setSelected(false);
        this.quaverBtn.setSelected(false);
        this.crotchetBtn.setSelected(false);
        this.minimBtn.setSelected(false);
        this.semibreveBtn.setSelected(false);
        final String s = udName;
        switch (s) {
            case "Semibreve": {
                this.semibreveBtn.setSelected(true);
                break;
            }
            case "Minim": {
                this.minimBtn.setSelected(true);
                break;
            }
            case "Crotchet": {
                this.crotchetBtn.setSelected(true);
                break;
            }
            case "Quaver": {
                this.quaverBtn.setSelected(true);
                break;
            }
            case "Semiquaver": {
                this.semiquaverBtn.setSelected(true);
                break;
            }
        }
    }
    
    private void unitDurationBtnImageSet() {
        if (this.getDotNotationState()) {
            if (this.semiquaverBtn.isSelected()) {
                this.semiquaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_5a.jpg')");
            }
            else {
                this.semiquaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_5.jpg')");
            }
            if (this.quaverBtn.isSelected()) {
                this.quaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_4a.jpg')");
            }
            else {
                this.quaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_4.jpg')");
            }
            if (this.crotchetBtn.isSelected()) {
                this.crotchetBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_3a.jpg')");
            }
            else {
                this.crotchetBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_3.jpg')");
            }
            if (this.minimBtn.isSelected()) {
                this.minimBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_2a.jpg')");
            }
            else {
                this.minimBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_2.jpg')");
            }
            if (this.semibreveBtn.isSelected()) {
                this.semibreveBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_1a.jpg')");
            }
            else {
                this.semibreveBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/udd_1.jpg')");
            }
        }
        else {
            if (this.semiquaverBtn.isSelected()) {
                this.semiquaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_5a.jpg')");
            }
            else {
                this.semiquaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_5.jpg')");
            }
            if (this.quaverBtn.isSelected()) {
                this.quaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_4a.jpg')");
            }
            else {
                this.quaverBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_4.jpg')");
            }
            if (this.crotchetBtn.isSelected()) {
                this.crotchetBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_3a.jpg')");
            }
            else {
                this.crotchetBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_3.jpg')");
            }
            if (this.minimBtn.isSelected()) {
                this.minimBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_2a.jpg')");
            }
            else {
                this.minimBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_2.jpg')");
            }
            if (this.semibreveBtn.isSelected()) {
                this.semibreveBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_1a.jpg')");
            }
            else {
                this.semibreveBtn.setStyle("-fx-background-image: url('/View/Keyboard/graphic/ud_1.jpg')");
            }
        }
    }
    
    private UnitDuration createUnitDurationObject(final String udName) {
        int numberOfDots = 0;
        if (this.getDotNotationState()) {
            numberOfDots = 1;
        }
        final UnitDuration ud = new UnitDuration(udName, UnitDurationMap.getUnitDurationByName(udName).getUnitDurationDivider(), true, numberOfDots);
        return ud;
    }
    
    protected void setUnitDurationValue(final UnitDuration ud) {
        this.twoOctavesKeyboardController.setUnitDurationValue(ud);
        if (ud != null) {
            final UnitDuration udWithoutDotNotation = UnitDurationMap.getUnitDurationByName(ud.getUnitDurationName());
            Boolean valid = false;
            if (!UnitDurationInformation.getStatus().isUpdated()) {
                valid = true;
            }
            UnitDurationInformation.setUnitDuration(udWithoutDotNotation);
            if (valid) {
                UnitDurationInformation.clearDurationInfoUpdated();
            }
        }
    }
    
    private boolean getDotNotationState() {
        return this.twoOctavesKeyboardController.getDotNotationState();
    }
    
    private void setDotNotationState(final boolean dotNotationState) {
        this.twoOctavesKeyboardController.setDotNotationState(dotNotationState);
    }
    
    public void highlightEnable(final ArrayList<Integer> midiList) {
        this.fullPianoKeyboardController.highlightEnable(midiList);
    }
    
    public void highlightsDisable() {
        this.fullPianoKeyboardController.highlightsDisable();
    }
}
