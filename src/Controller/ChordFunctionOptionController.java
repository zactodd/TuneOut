

package Controller;

import Model.Tutor.ChordFunctionTutorOption;
import javafx.scene.Node;
import Model.Tutor.Options;
import Environment.Environment;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChordFunctionOptionController extends TutorOption
{
    @FXML
    private Label numQuestionsError;
    private final String chordQuestiononly = "Chord Questions";
    private final String functionQuestiononly = "Function Questions";
    private final String bothQuestiontype = "Both";
    private final String chordQuestionOverride = "-c";
    private final String functionQuestionOverride = "-f";
    private final String bothQuestionOverride = "-b";
    private Map<String, String> questionTypeMap;
    private ChordFunctionAnswerController answerController;
    private OuterTemplateController main;
    private int currentNumberTests;
    
    public ChordFunctionOptionController() {
        this.questionTypeMap = new HashMap<String, String>() {
            {
                this.put("Chord Questions", "-c");
                this.put("Function Questions", "-f");
                this.put("Both", "-b");
            }
        };
        this.currentNumberTests = 0;
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (ChordFunctionAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.setUpQuestionTypeCombo();
        this.setUpInputsAndDefaults();
        this.setUpListeners();
    }
    
    @Override
    public void startTutor() {
        if (this.nullcheckToggled()) {
            this.showWarning();
        }
        else if (!this.startBtn.isDisabled() && Options.numQuestionsValid(this.numQuestionsTextField.getText())) {
            this.numQuestionsError.setVisible(false);
            this.answerController.testRepeatError.setVisible(false);
            ++this.currentNumberTests;
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            this.runTutorCommand("start(" + this.numQuestionsTextField.getText() + "," + this.questionTypeMap.get(this.getSelectedToggle().getText()) + ")");
            this.answerController.disableAnswers(false);
            this.disableOptions(true);
            this.tutorController.show_answerPane();
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    private void setUpQuestionTypeCombo() {
        final Node nodeIn = this.tutorController.getOptionButtonPane();
        this.setUpButtonsBig(this.questionTypeMap, nodeIn);
    }
    
    private String getLabelFromParam(final String valueToCheck) {
        for (final Map.Entry<String, String> entry : this.questionTypeMap.entrySet()) {
            if (entry.getValue().equals(valueToCheck)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private void setUpInputsAndDefaults() {
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
        final ChordFunctionTutorOption options = (ChordFunctionTutorOption)this.tutorEnvironment.getTutorDefinition().options;
        final String override = this.tutorEnvironment.getTutorDefinition().options.otherOptions.get(0);
        this.selectToggle(this.getLabelFromParam(override));
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
    }
}
