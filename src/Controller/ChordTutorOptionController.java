

package Controller;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import Model.Tutor.Options;
import Environment.Environment;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChordTutorOptionController extends TutorOption
{
    @FXML
    private Label numQuestionsError;
    private static final Map<String, String> PLAY_STYLE_OPTIONS;
    private ChordTutorAnswerController answerController;
    private OuterTemplateController main;
    private int currentNumberTests;
    private TutorController tutorController;
    
    public ChordTutorOptionController() {
        this.currentNumberTests = 0;
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (ChordTutorAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.setUpPlayStyleCombo();
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
            this.answerController.testRepeatError.setVisible(false);
            ++this.currentNumberTests;
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            final ToggleButton selectedToggle = (ToggleButton)this.group.getSelectedToggle();
            this.runTutorCommand("start(" + this.numQuestionsTextField.getText() + "," + ChordTutorOptionController.PLAY_STYLE_OPTIONS.get(selectedToggle.getText()) + ")");
            this.answerController.disableAnswers(false);
            this.disableOptions(true);
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    private void setUpPlayStyleCombo() {
        final Node nodeIn = this.tutorController.getOptionButtonPane();
        this.setUpButtonsBig(ChordTutorOptionController.PLAY_STYLE_OPTIONS, nodeIn);
    }
    
    private String getLabelFromParam(final String valueToCheck) {
        for (final Map.Entry<String, String> entry : ChordTutorOptionController.PLAY_STYLE_OPTIONS.entrySet()) {
            if (entry.getValue().equals(valueToCheck)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private void setUpInputsAndDefaults() {
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
        final String playTypeParam = this.tutorEnvironment.getTutorDefinition().options.otherOptions.get(0);
        this.selectToggle(this.getLabelFromParam(playTypeParam));
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
    }
    
    static {
        (PLAY_STYLE_OPTIONS = new HashMap<String, String>()).put("Simultaneous", "-s");
        ChordTutorOptionController.PLAY_STYLE_OPTIONS.put("Arpeggio", "-a");
        ChordTutorOptionController.PLAY_STYLE_OPTIONS.put("Both", "-b");
    }
}
