

package Controller;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import Model.Note.Intervals.Interval;
import Model.Note.Intervals.IntervalMap;

import java.util.Arrays;
import Model.Tutor.Options;
import javafx.scene.control.Control;
import Environment.Environment;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import java.util.List;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class IntervalTutorOptionController extends TutorOption
{
    @FXML
    private ScrollPane advancedOptionScrollPane;
    @FXML
    private VBox intervalCheckBoxes0To6;
    @FXML
    private VBox intervalCheckBoxes7To12;
    @FXML
    private VBox intervalCheckBoxes13To18;
    @FXML
    private VBox intervalCheckBoxes19To24;
    @FXML
    private VBox intervalAllCheckBoxes;
    @FXML
    private Label secondOctLabel;
    @FXML
    private Button deselectAllBtn;
    @FXML
    private ComboBox numOfOctavesComboBox;
    @FXML
    private ComboBox intervalNamesComboBox;
    @FXML
    private TitledPane advancedOption;
    @FXML
    private Label intervalError;
    private final String ALL_INTERVALS = "All";
    private final String MAJOR_ONLY = "Major";
    private final String MINOR_ONLY = "Minor";
    private List<CheckBox> intervalCheckBoxes;
    private IntervalTutorAnswerController answerController;
    private OuterTemplateController main;
    private int currentNumberTests;
    private TutorController tutorController;
    
    public IntervalTutorOptionController() {
        this.intervalCheckBoxes = new ArrayList<CheckBox>();
        this.currentNumberTests = 0;
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (IntervalTutorAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        final Control[] controls = { (Control)this.advancedOption, (Control)this.deselectAllBtn, (Control)this.numOfOctavesComboBox, (Control)this.intervalNamesComboBox };
        this.otherOptionControls = controls;
        this.setUpInputsAndDefaults();
        this.setUpListeners();
    }
    
    @Override
    public void startTutor() {
        if (!this.startBtn.isDisabled() && Options.numQuestionsValid(this.numQuestionsTextField.getText())) {
            this.tutorController.show_answerPane();
            this.numQuestionsError.setVisible(false);
            this.answerController.testRepeatError.setVisible(false);
            ++this.currentNumberTests;
            String command;
            if (this.advancedOption.isExpanded()) {
                final List<String> intervals = this.gatherTheSelectedCheckBoxes();
                final List<String> intervalsWithQuote = new ArrayList<String>();
                for (String interval : intervals) {
                    interval = "\"" + interval + "\"";
                    intervalsWithQuote.add(interval);
                }
                command = "start(" + this.numQuestionsTextField.getText() + "," + Arrays.toString(intervalsWithQuote.toArray()) + ")";
            }
            else {
                command = "start(" + this.numQuestionsTextField.getText() + "," + this.numOfOctavesComboBox.getSelectionModel().getSelectedItem().toString() + "," + "\"" + this.intervalNamesComboBox.getSelectionModel().getSelectedItem().toString() + "\"" + ")";
            }
            this.runTutorCommand(command);
            this.answerController.setAnswerComboBox(this.gatherTheSelectedCheckBoxes());
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            this.answerController.disableAnswers(false);
            this.disableOptions(true);
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    private List<String> gatherTheSelectedCheckBoxes() {
        final List<String> chosenIntervals = new ArrayList<String>();
        for (final CheckBox interval : this.intervalCheckBoxes) {
            if (interval.isSelected()) {
                chosenIntervals.add(interval.getText());
            }
        }
        return chosenIntervals;
    }
    
    private void setUpInputsAndDefaults() {
        final List<Interval> intervals = IntervalMap.filterIntervalsWithMaxSemitone(IntervalMap.getAllIntervals(), 24);
        for (final Interval interval : intervals) {
            final CheckBox intervalCheckBox = new CheckBox(interval.getPrettyIntervalName());
            if (interval.getSemitone() <= 6) {
                this.intervalCheckBoxes0To6.getChildren().add((Object)intervalCheckBox);
            }
            else if (interval.getSemitone() <= 12) {
                this.intervalCheckBoxes7To12.getChildren().add((Object)intervalCheckBox);
            }
            else if (interval.getSemitone() <= 19) {
                this.intervalCheckBoxes13To18.getChildren().add((Object)intervalCheckBox);
            }
            else if (interval.getSemitone() <= 24) {
                this.intervalCheckBoxes19To24.getChildren().add((Object)intervalCheckBox);
            }
            this.intervalCheckBoxes.add(intervalCheckBox);
        }
        this.advancedOptionScrollPane.setContent((Node)this.intervalAllCheckBoxes);
        this.advancedOption.setExpanded(false);
        this.numOfOctavesComboBox.getItems().add((Object)1);
        this.numOfOctavesComboBox.getItems().add((Object)2);
        this.intervalNamesComboBox.getItems().add((Object)"All");
        this.intervalNamesComboBox.getItems().add((Object)"Major");
        this.intervalNamesComboBox.getItems().add((Object)"Minor");
        final List<String> intervalRange = this.tutorEnvironment.getTutorDefinition().options.otherOptions;
        final String numOfOctaves = intervalRange.get(0);
        final String intervalType = intervalRange.get(1);
        this.numOfOctavesComboBox.getSelectionModel().select((Object)numOfOctaves);
        this.intervalNamesComboBox.getSelectionModel().select((Object)intervalType);
        this.determineWhatToCheck();
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
    }
    
    public void determineWhatToCheck() {
        if (this.intervalNamesComboBox.getSelectionModel().getSelectedItem().equals("All")) {
            this.checkAllInterval();
        }
        else {
            this.checkInterval(this.intervalNamesComboBox.getSelectionModel().getSelectedItem().toString());
        }
    }
    
    public void checkAllInterval() {
        boolean visible = true;
        for (final CheckBox intervalCheckBox : this.intervalCheckBoxes) {
            if (this.numOfOctavesComboBox.getSelectionModel().getSelectedItem().toString().equals("1")) {
                this.hideSecondOctave(false);
                if (intervalCheckBox.getText().equals("Perfect Octave")) {
                    intervalCheckBox.setSelected(visible);
                    visible = false;
                }
                else {
                    intervalCheckBox.setSelected(visible);
                }
            }
            else {
                if (!this.numOfOctavesComboBox.getSelectionModel().getSelectedItem().toString().equals("2")) {
                    continue;
                }
                this.hideSecondOctave(true);
                intervalCheckBox.setSelected(visible);
            }
        }
    }
    
    private void hideSecondOctave(final boolean hideOrNot) {
        this.secondOctLabel.setVisible(hideOrNot);
        this.intervalCheckBoxes13To18.setVisible(hideOrNot);
        this.intervalCheckBoxes19To24.setVisible(hideOrNot);
        this.secondOctLabel.setManaged(hideOrNot);
        this.intervalCheckBoxes13To18.setManaged(hideOrNot);
        this.intervalCheckBoxes19To24.setManaged(hideOrNot);
    }
    
    public void checkInterval(final String type) {
        boolean visible = true;
        for (final CheckBox intervalCheckBox : this.intervalCheckBoxes) {
            if (this.numOfOctavesComboBox.getSelectionModel().getSelectedItem().toString().equals("1")) {
                this.hideSecondOctave(false);
                if (intervalCheckBox.getText().equals("Perfect Octave")) {
                    visible = false;
                    intervalCheckBox.setSelected(visible);
                }
                else if (intervalCheckBox.getText().startsWith(type)) {
                    intervalCheckBox.setSelected(visible);
                }
                else {
                    intervalCheckBox.setSelected(false);
                }
            }
            else {
                if (!this.numOfOctavesComboBox.getSelectionModel().getSelectedItem().toString().equals("2")) {
                    continue;
                }
                this.hideSecondOctave(true);
                if (intervalCheckBox.getText().startsWith(type)) {
                    intervalCheckBox.setSelected(true);
                }
                else {
                    intervalCheckBox.setSelected(false);
                }
            }
        }
    }
    
    @FXML
    public void uncheckAllInterval() {
        for (final CheckBox intervalCheckBox : this.intervalCheckBoxes) {
            intervalCheckBox.setSelected(false);
        }
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
        final ChangeListener<Boolean> intervalOptionsListener = (ChangeListener<Boolean>)((ov, t, t1) -> {
            int selected = 0;
            for (final CheckBox intervalCheckBox : this.intervalCheckBoxes) {
                if (intervalCheckBox.isSelected()) {
                    ++selected;
                }
            }
            if (selected < 2) {
                this.intervalError.setVisible(true);
                this.startBtn.setDisable(true);
            }
            else {
                this.intervalError.setVisible(false);
                this.startBtn.setDisable(false);
            }
        });
        for (final CheckBox intervalCheckBox : this.intervalCheckBoxes) {
            intervalCheckBox.selectedProperty().addListener((ChangeListener)intervalOptionsListener);
        }
        final ChangeListener<Boolean> intervalAdvancedOptionListener = (ChangeListener<Boolean>)((ov, t, t1) -> {
            if (this.advancedOption.isExpanded()) {
                this.deselectAllBtn.setVisible(true);
            }
            else {
                this.deselectAllBtn.setVisible(false);
            }
        });
        this.advancedOption.expandedProperty().addListener((ChangeListener)intervalAdvancedOptionListener);
    }
}
