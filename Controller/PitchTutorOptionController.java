

package Controller;

import Model.Tutor.PitchTutorOption;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListView;
import Model.keyboardInput.NoteInputField;
import Model.keyboardInput.KeyboardInput;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import java.util.Iterator;
import Model.Note.Note;
import Model.Note.NoteMap;
import Model.Tutor.Options;
import javafx.scene.control.Control;
import Environment.Environment;
import Model.keyboardInput.ComboBoxInput;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PitchTutorOptionController extends TutorOption
{
    @FXML
    private ComboBox<String> pitchRangeMin;
    @FXML
    private ComboBox<String> pitchRangeMax;
    @FXML
    private Label numQuestionsError;
    @FXML
    private Label pitchRangeError;
    private PitchTutorAnswerController answerController;
    private OuterTemplateController main;
    private int currentNumberTests;
    private final int PITCH_RANGE_MIN_POS = 0;
    private final int PITCH_RANGE_MAX_POS = 1;
    private ComboBoxInput pitchMinInput;
    private ComboBoxInput pitchMaxInput;
    private TutorController tutorController;
    
    public PitchTutorOptionController() {
        this.currentNumberTests = 0;
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (PitchTutorAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.setUpInputsAndDefaults();
        this.setUpListeners();
        this.otherOptionControls = new Control[] { (Control)this.pitchRangeMin, (Control)this.pitchRangeMax };
    }
    
    @Override
    public void startTutor() {
        if (!this.startBtn.isDisable() && Options.numQuestionsValid(this.numQuestionsTextField.getText())) {
            this.tutorController.show_answerPane();
            this.numQuestionsError.setVisible(false);
            this.pitchRangeError.setVisible(false);
            this.answerController.testRepeatError.setVisible(false);
            ++this.currentNumberTests;
            this.runTutorCommand("start(" + this.numQuestionsTextField.getText() + "," + (String)this.pitchRangeMin.getValue() + "," + (String)this.pitchRangeMax.getValue() + ")");
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            this.answerController.disableAnswers(false);
            this.disableOptions(true);
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    private void setUpInputsAndDefaults() {
        for (final Note note : NoteMap.allNotes) {
            if (note.isPrimary()) {
                this.pitchRangeMin.getItems().add((Object)note.getNoteWithOctave());
                this.pitchRangeMax.getItems().add((Object)note.getNoteWithOctave());
            }
        }
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
        this.pitchRangeMin.getSelectionModel().select((Object)this.tutorEnvironment.getTutorDefinition().options.otherOptions.get(0));
        this.pitchRangeMax.getSelectionModel().select((Object)this.tutorEnvironment.getTutorDefinition().options.otherOptions.get(1));
        this.pitchMinInput = new ComboBoxInput(this.pitchRangeMin);
        this.pitchMaxInput = new ComboBoxInput(this.pitchRangeMax);
    }
    
    private void pitchRangeFocusListener(final ComboBox<String> pitchRange, final ComboBoxInput pitchInput, final boolean wasFocused, final boolean isFocused) {
        final ListView lvMin = ((ComboBoxListViewSkin)pitchRange.getSkin()).getListView();
        lvMin.scrollTo(lvMin.getSelectionModel().getSelectedIndex());
        if (wasFocused && !isFocused) {
            KeyboardInput.setNoteInputField(pitchInput);
        }
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
        final ChangeListener<String> pitchOptionsListener = (ChangeListener<String>)((ov, t, t1) -> {
            if (!PitchTutorOption.pitchRangeValid((String)this.pitchRangeMin.getValue(), (String)this.pitchRangeMax.getValue())) {
                this.pitchRangeError.setVisible(true);
                this.startBtn.setDisable(true);
            }
            else {
                this.pitchRangeError.setVisible(false);
                this.startBtn.setDisable(false);
            }
        });
        this.pitchRangeMin.valueProperty().addListener((ChangeListener)pitchOptionsListener);
        this.pitchRangeMax.valueProperty().addListener((ChangeListener)pitchOptionsListener);
        final ChangeListener<Boolean> pitchRangeMinFocus = (ChangeListener<Boolean>)((observable, wasFocused, isFocused) -> this.pitchRangeFocusListener(this.pitchRangeMin, this.pitchMinInput, wasFocused, isFocused));
        final ChangeListener<Boolean> pitchRangeMaxFocus = (ChangeListener<Boolean>)((observable, wasFocused, isFocused) -> this.pitchRangeFocusListener(this.pitchRangeMax, this.pitchMaxInput, wasFocused, isFocused));
        this.pitchRangeMin.focusedProperty().addListener((ChangeListener)pitchRangeMinFocus);
        this.pitchRangeMax.focusedProperty().addListener((ChangeListener)pitchRangeMaxFocus);
    }
}
