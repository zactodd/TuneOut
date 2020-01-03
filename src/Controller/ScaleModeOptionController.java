

package Controller;

import javafx.scene.Node;
import Model.Tutor.ScaleModeTutorOption;
import Model.Tutor.Options;
import Environment.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScaleModeOptionController extends TutorOption
{
    private int currentNumberTests;
    private ScaleModeAnswerController answerController;
    private OuterTemplateController main;
    private List<String> types;
    
    public ScaleModeOptionController() {
        this.currentNumberTests = 0;
        this.types = new ArrayList<String>(Arrays.asList("major", "melodic minor", "both"));
    }
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.answerController = (ScaleModeAnswerController)answerController;
        this.main = main;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
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
            final String selectedModes = this.getSelectedToggle().getText().toLowerCase();
            final String commandString = String.format("start(%s, \"%s\")", this.numQuestionsTextField.getText(), selectedModes);
            this.runTutorCommand(commandString);
            this.answerController.setNumQuestions(Integer.valueOf(this.numQuestionsTextField.getText()));
            this.answerController.disableAnswers(false);
            this.disableOptions(true);
            this.answerController.runQuestion();
            this.answerController.clearResults();
        }
    }
    
    private void setUpListeners() {
        this.setUpNumQuestionsListeners();
    }
    
    private void setUpInputsAndDefaults() {
        final Node nodeIn = this.tutorController.getOptionButtonPane();
        this.setUpButtonsBig(this.types, nodeIn);
        this.numQuestionsError.setText(this.numQuestionsError.getText() + 1000);
        this.numQuestionsTextField.appendText(String.valueOf(this.tutorEnvironment.getTutorDefinition().options.numQuestions));
        final String defaultType = ((ScaleModeTutorOption)this.tutorEnvironment.getTutorDefinition().options).getType();
        this.selectToggle(defaultType);
    }
}
