

package Controller;

import Model.keyboardInput.KeyboardInput;
import javafx.scene.control.Control;
import Environment.Environment;
import javafx.scene.control.IndexRange;
import Model.keyboardInput.TextFieldInput;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ChordSpellingAnswerController extends TutorAnswer
{
    @FXML
    private TextField answerTextField;
    private TextFieldInput answerInput;
    private IndexRange currentSelection;
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        final Control[] controls = { (Control)this.answerTextField };
        this.otherAnswerControls = controls;
        this.disableAnswers(true);
        this.answerSetup();
    }
    
    @FXML
    public String getAnswer() {
        return this.answerTextField.getText();
    }
    
    private void answerSetup() {
        this.answerInput = new TextFieldInput(this.answerTextField);
        this.answerTextField.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (wasFocused && !isFocused) {
                this.answerInput.reStart();
                KeyboardInput.setNoteInputField(this.answerInput);
                this.currentSelection = this.answerTextField.getSelection();
            }
        });
    }
    
    @Override
    public void checkAnswer() {
        this.results.setText("");
        this.tutorResultsController.clearResults();
        this.results.setText(this.runTutorCommand("answer(\"" + this.getAnswer() + "\")"));
        final String response = this.runTutorCommand("answer(\"" + this.getAnswer() + "\")");
        this.tutorResultsController.appendtoResults(response);
        if (!response.contains("incorrect")) {
            final Integer rightQuestions = this.rightQuestions;
            ++this.rightQuestions;
        }
        final Integer doneQuestions = this.doneQuestions;
        ++this.doneQuestions;
        this.runQuestion();
        this.answerTextField.clear();
    }
}
