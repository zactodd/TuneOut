

package Controller;

import javafx.event.ActionEvent;
import Model.Tutor.ScaleTutor;
import javafx.beans.value.ChangeListener;
import Model.Tutor.ScaleTutorOption;
import javafx.scene.Node;
import Model.Note.Scale.ScaleMode.ScaleModeMap;
import javafx.scene.control.Tooltip;
import Model.Note.Scale.ScaleMap;

import java.util.Arrays;
import Model.Tutor.Options;
import javafx.scene.control.Control;
import Environment.Environment;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import javafx.scene.control.CheckBox;
import java.util.List;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ScaleTutorOptionController extends TutorOption
{
    private static final String SCALE_ID = "scale";
    private static final String MAJOR_MODE_ID = "majormode";
    private static final String MINOR_MODE_ID = "minormode";
    @FXML
    protected ComboBox<String> numOctaves;
    @FXML
    private ComboBox<String> scaleOrder;
    @FXML
    private ScrollPane advancedOptionScrollPane;
    @FXML
    private VBox scaleCheckBoxes0To3;
    @FXML
    private VBox scaleCheckBoxes4To6;
    @FXML
    private VBox majModeCheckBoxes0To3;
    @FXML
    private VBox majModeCheckBoxes4To6;
    @FXML
    private VBox minModeCheckBoxes0To3;
    @FXML
    private VBox minModeCheckBoxes4To7;
    @FXML
    private VBox scaleAllCheckBoxes;
    @FXML
    private Label numQuestionsError;
    @FXML
    private Button deselectAllBtn;
    @FXML
    private ComboBox<String> scaleNamesComboBox;
    @FXML
    private TitledPane advancedOption;
    @FXML
    private Label scaleError;
    private ScaleTutorAnswerController answerController;
    private OuterTemplateController main;
    private List<CheckBox> scaleCheckBoxes;
    private int currentNumberTests;
    private static final int NUM_OCTAVE_MIN = 1;
    private static final int NUM_OCTAVE_MAX = 4;
    private static final String LESS_THAN_TWO_SELECTED = "Please select at least two scales";
    private static final String SAME_TYPE_SELECTED = "You cannot select %s and %s";
    private TutorController tutorController;
    private Map<String, String> scaleOrderSpecifierToNameMap;
    
    public ScaleTutorOptionController() {
        this.scaleCheckBoxes = new ArrayList<CheckBox>();
        this.currentNumberTests = 0;
        this.scaleOrderSpecifierToNameMap = new HashMap<String, String>() {
            {
                this.put("-a", "ascending");
                this.put("-d", "descending");
                this.put("-b", "ascending & descending");
            }
        };
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (ScaleTutorAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.otherOptionControls = new Control[] { (Control)this.scaleOrder, (Control)this.numOctaves, (Control)this.advancedOption, (Control)this.deselectAllBtn, (Control)this.scaleNamesComboBox };
        this.setUpInputsAndDefaults();
        this.setUpListeners();
    }
    
    @Override
    public void startTutor() {
        if (Options.numQuestionsValid(this.numQuestionsTextField.getText())) {
            if (!this.startBtn.isDisabled()) {
                this.tutorController.show_answerPane();
                this.numQuestionsError.setVisible(false);
                this.answerController.testRepeatError.setVisible(false);
                ++this.currentNumberTests;
                String scaleOrderSpecifier = null;
                for (final String orderName : this.scaleOrderSpecifierToNameMap.keySet()) {
                    if (((String)this.scaleOrder.getValue()).equals(this.scaleOrderSpecifierToNameMap.get(orderName))) {
                        scaleOrderSpecifier = orderName;
                    }
                }
                final List<String> scales = this.gatherTheSelectedCheckBoxes();
                final List<String> scalesWithQuote = new ArrayList<String>();
                for (String scale : scales) {
                    scale = "\"" + scale + "\"";
                    scalesWithQuote.add(scale);
                }
                this.runTutorCommand("start(" + this.numQuestionsTextField.getText() + "," + (String)this.numOctaves.getValue() + "," + scaleOrderSpecifier + "," + Arrays.toString(scalesWithQuote.toArray()) + ")");
                this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
                this.answerController.setAnswerComboBox(this.gatherTheSelectedCheckBoxes());
                this.answerController.disableAnswers(false);
                this.disableOptions(true);
                this.answerController.runQuestion();
                this.answerController.clearResults();
            }
        }
    }
    
    private void setUpInputsAndDefaults() {
        for (final String orderName : this.scaleOrderSpecifierToNameMap.values()) {
            this.scaleOrder.getItems().add((Object)orderName);
        }
        for (int numOctave = 1; numOctave <= 4; ++numOctave) {
            this.numOctaves.getItems().add((Object)String.valueOf(numOctave));
        }
        int counter = 0;
        for (final String scale : ScaleMap.getScales()) {
            final CheckBox scaleCheckBox = new CheckBox(scale);
            scaleCheckBox.setTooltip(new Tooltip(scale));
            scaleCheckBox.setId("scale");
            if (counter <= 3) {
                this.scaleCheckBoxes0To3.getChildren().add((Object)scaleCheckBox);
            }
            else {
                this.scaleCheckBoxes4To6.getChildren().add((Object)scaleCheckBox);
            }
            this.scaleCheckBoxes.add(scaleCheckBox);
            ++counter;
        }
        int counterMajModes = 0;
        int counterMinModes = 0;
        for (final String mode : ScaleModeMap.getAllModeNames()) {
            final CheckBox scaleCheckBox2 = new CheckBox(mode);
            scaleCheckBox2.setTooltip(new Tooltip(mode));
            if (ScaleModeMap.parentOf(mode).getName().contains("major") && counterMajModes <= 3) {
                scaleCheckBox2.setId("majormode");
                this.majModeCheckBoxes0To3.getChildren().add((Object)scaleCheckBox2);
                ++counterMajModes;
            }
            else if (ScaleModeMap.parentOf(mode).getName().contains("major") && counterMajModes > 3) {
                scaleCheckBox2.setId("majormode");
                this.majModeCheckBoxes4To6.getChildren().add((Object)scaleCheckBox2);
                ++counterMajModes;
            }
            else if (ScaleModeMap.parentOf(mode).getName().contains("minor") && counterMinModes <= 3) {
                scaleCheckBox2.setId("minormode");
                this.minModeCheckBoxes0To3.getChildren().add((Object)scaleCheckBox2);
                ++counterMinModes;
            }
            else if (ScaleModeMap.parentOf(mode).getName().contains("minor") && counterMinModes > 3) {
                scaleCheckBox2.setId("minormode");
                this.minModeCheckBoxes4To7.getChildren().add((Object)scaleCheckBox2);
                ++counterMinModes;
            }
            this.scaleCheckBoxes.add(scaleCheckBox2);
        }
        this.advancedOptionScrollPane.setContent((Node)this.scaleAllCheckBoxes);
        this.advancedOption.setExpanded(false);
        this.scaleNamesComboBox.getItems().add((Object)"scales");
        this.scaleNamesComboBox.getItems().add((Object)"major modes");
        this.scaleNamesComboBox.getItems().add((Object)"minor modes");
        this.scaleNamesComboBox.getItems().add((Object)"all");
        final ScaleTutorOption options = (ScaleTutorOption)this.tutorEnvironment.getTutorDefinition().options;
        this.scaleNamesComboBox.getSelectionModel().select((Object)options.getType());
        this.determineWhatToCheck();
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.main.tutorDefn.options.numQuestions));
        this.numOctaves.setValue((Object)String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numOctaves));
        this.scaleOrder.setValue((Object)this.scaleOrderSpecifierToNameMap.get(this.tutorEnvironment.getTutorDefinition().options.order));
    }
    
    private List<String> gatherTheSelectedCheckBoxes() {
        final List<String> chosenScales = new ArrayList<String>();
        for (final CheckBox interval : this.scaleCheckBoxes) {
            if (interval.isSelected()) {
                chosenScales.add(interval.getText());
            }
        }
        return chosenScales;
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
        final ChangeListener<Boolean> scaleOptionsListener = (ChangeListener<Boolean>)((ov, t, t1) -> {
            int selected = 0;
            Boolean incompatibleCheckboxesSelected = false;
            final List<String> selectedCheckBoxes = new ArrayList<String>();
            for (final CheckBox scaleCheckBox : this.scaleCheckBoxes) {
                if (scaleCheckBox.isSelected()) {
                    ++selected;
                    selectedCheckBoxes.add(scaleCheckBox.getText());
                }
            }
            String incompatibleCheckBox1 = "";
            String incompatibleCheckBox2 = "";
            for (final Map.Entry<String, String> entry : ScaleTutor.mutuallyExclusiveScales.entrySet()) {
                if (selectedCheckBoxes.contains(entry.getKey()) && selectedCheckBoxes.contains(entry.getValue())) {
                    incompatibleCheckboxesSelected = true;
                    incompatibleCheckBox1 = entry.getKey();
                    incompatibleCheckBox2 = entry.getValue();
                }
            }
            if (selected < 2) {
                this.scaleError.setVisible(true);
                this.scaleError.setText("Please select at least two scales");
                this.startBtn.setDisable(true);
            }
            else if (incompatibleCheckboxesSelected) {
                this.scaleError.setVisible(true);
                this.scaleError.setText(String.format("You cannot select %s and %s", incompatibleCheckBox1, incompatibleCheckBox2));
                this.startBtn.setDisable(true);
            }
            else {
                this.scaleError.setVisible(false);
                this.startBtn.setDisable(false);
            }
        });
        for (final CheckBox scaleCheckBox : this.scaleCheckBoxes) {
            scaleCheckBox.selectedProperty().addListener((ChangeListener)scaleOptionsListener);
        }
        final ChangeListener<Boolean> scaleTypeOptionListener = (ChangeListener<Boolean>)((ov, t, t1) -> {
            if (this.advancedOption.isExpanded()) {
                this.deselectAllBtn.setVisible(true);
            }
            else {
                this.deselectAllBtn.setVisible(false);
            }
        });
        this.advancedOption.expandedProperty().addListener((ChangeListener)scaleTypeOptionListener);
    }
    
    public void determineWhatToCheck() {
        for (final CheckBox scaleCheckBox : this.scaleCheckBoxes) {
            final String s = (String)this.scaleNamesComboBox.getSelectionModel().getSelectedItem();
            switch (s) {
                case "scales": {
                    if (scaleCheckBox.getId().equals("scale")) {
                        scaleCheckBox.setSelected(true);
                        continue;
                    }
                    scaleCheckBox.setSelected(false);
                    continue;
                }
                case "major modes": {
                    if (scaleCheckBox.getId().equals("majormode")) {
                        scaleCheckBox.setSelected(true);
                        continue;
                    }
                    scaleCheckBox.setSelected(false);
                    continue;
                }
                case "minor modes": {
                    if (scaleCheckBox.getId().equals("minormode")) {
                        scaleCheckBox.setSelected(true);
                        continue;
                    }
                    scaleCheckBox.setSelected(false);
                    continue;
                }
                case "all": {
                    if (!ScaleTutor.mutuallyExclusiveScales.containsKey(scaleCheckBox.getText())) {
                        scaleCheckBox.setSelected(true);
                        continue;
                    }
                    scaleCheckBox.setSelected(false);
                    continue;
                }
            }
        }
    }
    
    public void uncheckAllScale(final ActionEvent actionEvent) {
        for (final CheckBox scaleCheckBox : this.scaleCheckBoxes) {
            scaleCheckBox.setSelected(false);
        }
    }
}
