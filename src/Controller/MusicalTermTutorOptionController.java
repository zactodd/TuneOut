

package Controller;

import Model.Tutor.Options;
import javafx.scene.control.Control;
import Environment.Environment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MusicalTermTutorOptionController extends TutorOption
{
    @FXML
    private Label numQuestionsError;
    private int currentNumberTests;
    private MusicalTermTutorAnswerController answerController;
    private OuterTemplateController main;
    private TutorController tutorController;
    
    public MusicalTermTutorOptionController() {
        this.currentNumberTests = 0;
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (MusicalTermTutorAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.setUpInputsAndDefaults();
        this.setUpListeners();
        this.otherOptionControls = new Control[0];
    }
    
    @Override
    public void startTutor() {
        if (!this.startBtn.isDisabled() && Options.numQuestionsValid(this.numQuestionsTextField.getText())) {
            this.tutorController.show_answerPane();
            this.numQuestionsError.setVisible(false);
            this.answerController.testRepeatError.setVisible(false);
            ++this.currentNumberTests;
            this.runTutorCommand("start(" + this.numQuestionsTextField.getText() + ")");
            this.answerController.disableAnswers(false);
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            this.disableOptions(true);
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    private void setUpInputsAndDefaults() {
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
    }
}
