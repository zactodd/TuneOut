

package Controller;

import Environment.Environment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TutorResultsController
{
    @FXML
    private TextArea tutorResults;
    @FXML
    protected Label testRepeatError;
    @FXML
    private Button restartBtn;
    @FXML
    private Button retryBtn;
    OuterTemplateController main;
    TutorOption optionController;
    TutorAnswer answerController;
    Environment environment;
    TutorController tutorController;
    
    public void initialize(final OuterTemplateController main, final TutorAnswer answerController, final Environment tutorEnvironment, final TutorController tutorController) {
        this.main = main;
        this.answerController = answerController;
        this.environment = tutorEnvironment;
        this.tutorController = tutorController;
        this.testRepeatError.setVisible(false);
        this.tutorResults.setWrapText(true);
    }
    
    public void appendtoResults(final String content) {
        this.tutorResults.appendText(content);
    }
    
    public void clearResults() {
        this.tutorResults.clear();
    }
    
    @FXML
    public void showOptions() {
        this.testRepeatError.setVisible(false);
        this.tutorController.show_optionsPane();
    }
    
    @FXML
    public void retryTest() {
        this.answerController.repeatTutor();
    }
    
    public void retryError() {
        this.testRepeatError.setVisible(true);
    }
}
