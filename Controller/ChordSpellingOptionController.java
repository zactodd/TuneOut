// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import Model.Tutor.ChordSpellingTutorOption;
import java.util.Iterator;
import Model.Tutor.Options;
import javafx.scene.control.Control;
import Environment.Environment;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChordSpellingOptionController extends TutorOption
{
    @FXML
    private Label numQuestionsError;
    @FXML
    private CheckBox randomNotesCheckBox;
    @FXML
    private CheckBox majorCheckBox;
    @FXML
    private CheckBox minorCheckBox;
    @FXML
    private CheckBox augmentedCheckBox;
    @FXML
    private CheckBox diminishedCheckBox;
    @FXML
    private CheckBox sixthCheckBox;
    @FXML
    private CheckBox seventhCheckBox;
    @FXML
    private Button selectAllButton;
    @FXML
    private Button deselectAllButton;
    private int currentNumberTests;
    private ChordSpellingAnswerController answerController;
    private OuterTemplateController main;
    private final String SPELLING = "Spelling Question";
    private final String CHORD = "Chord Question";
    private List<CheckBox> chord_comboboxes;
    private TutorController tutorController;
    private List<String> types;
    
    public ChordSpellingOptionController() {
        this.currentNumberTests = 0;
        this.types = new ArrayList<String>(Arrays.asList("Spelling Question", "Chord Question"));
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (ChordSpellingAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.otherOptionControls = new Control[] { (Control)this.randomNotesCheckBox, (Control)this.majorCheckBox, (Control)this.minorCheckBox, (Control)this.augmentedCheckBox, (Control)this.diminishedCheckBox, (Control)this.sixthCheckBox, (Control)this.seventhCheckBox, (Control)this.selectAllButton, (Control)this.deselectAllButton };
        this.setUpInputsAndDefaults();
        this.setUpListeners();
    }
    
    @Override
    public void startTutor() {
        if (this.nullcheckToggled()) {
            this.showWarning();
        }
        else if (!this.startBtn.isDisabled() && Options.numQuestionsValid(this.numQuestionsTextField.getText())) {
            this.tutorController.show_answerPane();
            this.numQuestionsError.setVisible(false);
            this.answerController.testRepeatError.setVisible(false);
            ++this.currentNumberTests;
            final Boolean isSpellingQuestion = this.getSelectedToggle().getText().equals("Spelling Question");
            final Boolean allowsRandomNotes = isSpellingQuestion && this.randomNotesCheckBox.isSelected();
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            String commandString = "start(" + this.numQuestionsTextField.getText() + ", \"" + isSpellingQuestion + "\", \"" + allowsRandomNotes + "\"";
            for (final CheckBox checkBox : this.chord_comboboxes) {
                if (checkBox.isSelected()) {
                    commandString = commandString + ", \"" + checkBox.getText().toLowerCase() + "\"";
                }
            }
            commandString += ")";
            this.runTutorCommand(commandString);
            this.answerController.disableAnswers(false);
            this.disableOptions(true);
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    public void disableRandomQuestion() {
        if (this.getSelectedToggle() == null || !this.getSelectedToggle().getText().equals("Spelling Question")) {
            this.randomNotesCheckBox.setDisable(true);
        }
        else {
            this.randomNotesCheckBox.setDisable(false);
        }
    }
    
    private void disableSelectionButtons() {
        Boolean hasSelectedCheckBox = false;
        Boolean hasDeselectedCheckBox = false;
        for (final CheckBox checkBox : this.chord_comboboxes) {
            if (checkBox.isSelected()) {
                hasSelectedCheckBox = true;
            }
            else {
                hasDeselectedCheckBox = true;
            }
            if (hasSelectedCheckBox && hasDeselectedCheckBox) {
                break;
            }
        }
        this.selectAllButton.setDisable(false);
        this.deselectAllButton.setDisable(false);
        this.startBtn.setDisable(false);
        if (!hasSelectedCheckBox && hasDeselectedCheckBox) {
            this.deselectAllButton.setDisable(true);
            this.startBtn.setDisable(true);
        }
        else if (hasSelectedCheckBox && !hasDeselectedCheckBox) {
            this.selectAllButton.setDisable(true);
        }
    }
    
    private void setUpInputsAndDefaults() {
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
        this.chord_comboboxes = new ArrayList<CheckBox>() {
            {
                this.add(ChordSpellingOptionController.this.majorCheckBox);
                this.add(ChordSpellingOptionController.this.minorCheckBox);
                this.add(ChordSpellingOptionController.this.augmentedCheckBox);
                this.add(ChordSpellingOptionController.this.diminishedCheckBox);
                this.add(ChordSpellingOptionController.this.sixthCheckBox);
                this.add(ChordSpellingOptionController.this.seventhCheckBox);
            }
        };
        final Node nodeIn = this.tutorController.getOptionButtonPane();
        this.setUpButtonsSmall(this.types, nodeIn);
        final ChordSpellingTutorOption tutorOptions = (ChordSpellingTutorOption)this.tutorEnvironment.getTutorDefinition().options;
        if (tutorOptions.getSpellingQuestion()) {
            this.selectToggle("Spelling Question");
        }
        else {
            this.selectToggle("Chord Question");
        }
        this.disableRandomQuestion();
        if (tutorOptions.allowsRandomNotes()) {
            this.randomNotesCheckBox.selectedProperty().setValue(Boolean.valueOf(true));
        }
        for (final String chordType : tutorOptions.getChordTypes()) {
            if ("major".equals(chordType)) {
                this.majorCheckBox.selectedProperty().setValue(Boolean.valueOf(true));
            }
            else if ("minor".equals(chordType)) {
                this.minorCheckBox.selectedProperty().setValue(Boolean.valueOf(true));
            }
            else if ("augmented".equals(chordType)) {
                this.augmentedCheckBox.selectedProperty().setValue(Boolean.valueOf(true));
            }
            else if ("diminished".equals(chordType)) {
                this.diminishedCheckBox.selectedProperty().setValue(Boolean.valueOf(true));
            }
            else if ("6th".equals(chordType)) {
                this.sixthCheckBox.selectedProperty().setValue(Boolean.valueOf(true));
            }
            else {
                if (!"7th".equals(chordType)) {
                    continue;
                }
                this.seventhCheckBox.selectedProperty().setValue(Boolean.valueOf(true));
            }
        }
        this.selectAll();
        this.disableSelectionButtons();
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
        this.setupQuestionTypeOnChangeLister();
        this.setupSelectionListens();
        this.checkStartBtnListern();
    }
    
    private void setupQuestionTypeOnChangeLister() {
        this.group.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>() {
            public void changed(final ObservableValue ov, final Toggle oldToggle, final Toggle newToggle) {
                ChordSpellingOptionController.this.disableRandomQuestion();
            }
        });
    }
    
    private void setupSelectionListens() {
        for (final CheckBox checkBox : this.chord_comboboxes) {
            checkBox.selectedProperty().addListener((ChangeListener)new ChangeListener<Boolean>() {
                public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
                    ChordSpellingOptionController.this.disableSelectionButtons();
                }
            });
        }
    }
    
    @FXML
    private void selectAll() {
        for (final CheckBox checkBox : this.chord_comboboxes) {
            checkBox.selectedProperty().setValue(Boolean.valueOf(true));
            this.disableSelectionButtons();
        }
    }
    
    @FXML
    private void deselectAll() {
        for (final CheckBox checkBox : this.chord_comboboxes) {
            checkBox.selectedProperty().setValue(Boolean.valueOf(false));
            this.disableSelectionButtons();
        }
    }
    
    @Override
    public void disableOptions(final boolean disable) {
        super.disableOptions(disable);
        if (!disable) {
            this.disableRandomQuestion();
        }
    }
    
    public void checkStartBtnListern() {
        this.numQuestionsTextField.focusedProperty().addListener((ChangeListener)new ChangeListener<Boolean>() {
            public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
                if (!newValue) {
                    ChordSpellingOptionController.this.disableSelectionButtons();
                }
            }
        });
    }
}
