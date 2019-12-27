

package Controller;

import javafx.stage.WindowEvent;
import Model.Tutor.RecordedTutorStats;
import javafx.stage.Stage;
import Environment.GrammarParser;
import Environment.Environment;
import Model.Play.Play;
import javafx.scene.Scene;
import Model.CommandMessages;
import javafx.scene.control.Alert;
import seng302.App;
import java.util.Iterator;
import Model.instrument.InstrumentInformation;
import Model.instrument.InstrumentsMap;
import java.util.Collection;
import Model.Settings.StyleMap;
import Model.Note.Settings.TempoInformation;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.unitDuration.UnitDurationMap;
import java.io.IOException;
import Model.Note.unitDuration.UnitDuration;
import Model.instrument.Instrument;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SettingsController
{
    @FXML
    private ComboBox<String> styleCombo;
    @FXML
    private ComboBox instrumentSelection;
    @FXML
    private Button testInstrument;
    @FXML
    private Parent root;
    @FXML
    private CheckBox checkboxStats;
    @FXML
    private CheckBox checkOverrideTempo;
    @FXML
    private ComboBox<Double> textSizeCombo;
    @FXML
    private TextField textTempo;
    @FXML
    private ComboBox<String> comboUnitDuration;
    private TranscriptController transcript;
    private OuterTemplateController outerTemplateController;
    private String previousStyle;
    private Double previousTextSize;
    private boolean previousTempoOverride;
    private boolean previousSaveUserStats;
    private String previousUnitDuration;
    private int previousTempo;
    private String previousInstrument;
    private List<Instrument> instruments;
    private List<UnitDuration> unitDurations;
    
    public void start(final OuterTemplateController outerTemplateController) {
        this.outerTemplateController = outerTemplateController;
        this.setUpCloseListener();
    }
    
    public void initialize() throws IOException {
        this.setUpSettingData();
    }
    
    public void setUpSettingData() {
        this.setUpCheckbox();
        this.unitDurations = UnitDurationMap.getAvailableUnitDurationName();
        for (final UnitDuration ud : this.unitDurations) {
            this.comboUnitDuration.getItems().add((Object)ud.getUnitDurationName());
        }
        this.comboUnitDuration.getSelectionModel().select((Object)UnitDurationInformation.getUnitDuration().getUnitDurationName());
        this.textTempo.setText(String.valueOf(TempoInformation.getTempInBpm()));
        this.styleCombo.getItems().setAll((Collection)StyleMap.getStyleNames());
        this.styleCombo.getSelectionModel().select((Object)StyleMap.getCurrentStyleName());
        this.previousStyle = StyleMap.getCurrentStyleName();
        this.instruments = InstrumentsMap.getAllInstruments();
        for (final Instrument i : this.instruments) {
            if (i.getAvailability()) {
                this.instrumentSelection.getItems().add((Object)i.getInstrumentName());
            }
        }
        final Integer selectedInstrumentId = InstrumentInformation.getInstrumentId();
        final String selectName = InstrumentsMap.getInstrument(selectedInstrumentId).getInstrumentName();
        this.instrumentSelection.getSelectionModel().select((Object)selectName);
        this.previousInstrument = selectName;
        this.previousTempo = TempoInformation.getTempInBpm();
        this.previousUnitDuration = UnitDurationInformation.getUnitDuration().getUnitDurationName();
    }
    
    public void setTextSizeCombo(final TranscriptController transcript) {
        this.transcript = transcript;
        this.textSizeCombo.getItems().setAll((Collection)transcript.getAllowableFontSizes());
        this.textSizeCombo.getSelectionModel().select((Object)transcript.getFontSize());
        this.previousTextSize = transcript.getFontSize();
    }
    
    @FXML
    protected void updateStyle() {
        StyleMap.setCurrentStyle((String)this.styleCombo.getSelectionModel().getSelectedItem());
        this.setStyle();
    }
    
    @FXML
    private void previewStyleChange() {
        this.previousStyle = StyleMap.getCurrentStyleName();
        this.updateStyle();
    }
    
    @FXML
    private void changeText() {
        this.transcript.setFontSize((double)this.textSizeCombo.getSelectionModel().getSelectedItem());
    }
    
    private void setStyle() {
        App.setStyle(this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.root.getScene().getWindow().getScene().getStylesheets().clear();
        this.root.getScene().getWindow().getScene().getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        this.outerTemplateController.updatePopUpStyle();
    }
    
    private void showTempoError(final String errorMsg) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        final Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add((Object)this.getClass().getResource(StyleMap.getCurrentStyle()).toExternalForm());
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300.0, 200.0);
        alert.setTitle("Tempo error");
        if (errorMsg.contains(CommandMessages.getMessage("OUT_OF_SENSIBLE_RANGE_TEMPO"))) {
            alert.setContentText(String.format("%s %s", errorMsg, "Tick the checkbox to override the tempo."));
        }
        else {
            alert.setContentText(errorMsg);
        }
        alert.showAndWait();
    }
    
    @FXML
    private void okBtn() {
        final Environment env = new Environment(new Play(Play.PlayType.QUEUED));
        if (this.checkOverrideTempo.isSelected()) {
            new GrammarParser(env).executeCommand(String.format("setTempo(%s, -f)", this.textTempo.getText()));
        }
        else {
            new GrammarParser(env).executeCommand(String.format("setTempo(%s)", this.textTempo.getText()));
        }
        if (env.getResponse().contains(CommandMessages.getMessage("SUCCESS_TEMPO"))) {
            this.updateStyle();
            ((Stage)this.root.getScene().getWindow()).close();
            final String selectedInstrument = (String)this.instrumentSelection.getSelectionModel().getSelectedItem();
            RecordedTutorStats.setSaveStats(this.checkboxStats.isSelected());
            new GrammarParser(env).executeCommand(String.format("setInstrument(\"%s\")", selectedInstrument));
        }
        else {
            this.showTempoError(env.getResponse());
        }
        this.outerTemplateController.updateAppTitle();
    }
    
    private void setUpCloseListener() {
        this.root.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            this.cancel();
            this.outerTemplateController.closeSettingPopUp();
        });
    }
    
    @FXML
    public void cancel() {
        this.transcript.setFontSize(this.previousTextSize);
        this.textSizeCombo.getSelectionModel().select((Object)this.previousTextSize);
        if (this.previousStyle != null) {
            StyleMap.setCurrentStyle(this.previousStyle);
        }
        this.setStyle();
        this.styleCombo.getSelectionModel().select((Object)StyleMap.getCurrentStyleName());
        this.instrumentSelection.getSelectionModel().select((Object)this.previousInstrument);
        this.comboUnitDuration.getSelectionModel().select((Object)this.previousUnitDuration);
        this.textTempo.setText(String.valueOf(this.previousTempo));
        this.checkboxStats.setSelected(this.previousSaveUserStats);
        this.checkOverrideTempo.setSelected(this.previousTempoOverride);
        final Stage stage = (Stage)this.root.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void setUpCheckbox() {
        this.previousSaveUserStats = RecordedTutorStats.isSaveStats();
        this.checkboxStats.setSelected(this.previousSaveUserStats);
        this.previousTempoOverride = TempoInformation.checkTempoInSuitableRange(TempoInformation.getTempInBpm());
        this.checkOverrideTempo.setSelected(!this.previousTempoOverride);
    }
    
    @FXML
    private void testinstrument() {
        final String selectedInstrument = (String)this.instrumentSelection.getSelectionModel().getSelectedItem();
        final Environment env = new Environment(new Play(Play.PlayType.QUEUED));
        final GrammarParser parser = new GrammarParser(env);
        parser.executeCommand("getInstrument()");
        final String previousInstrument = env.getResponse();
        parser.executeCommand(String.format("setInstrument(\"%s\")", selectedInstrument));
        parser.executeCommand("playScale(C, \"major\")");
        parser.executeCommand(String.format("setInstrument(\"%s\")", previousInstrument));
    }
    
    @FXML
    public void changeUnitDuration() {
        final String selectedDurationStr = (String)this.comboUnitDuration.getSelectionModel().getSelectedItem();
        final UnitDuration selectedDuration = UnitDurationMap.getUnitDurationByName(selectedDurationStr);
        UnitDurationInformation.setUnitDuration(selectedDuration);
    }
}
