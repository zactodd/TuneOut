

package Controller;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Control;
import Environment.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ScaleModeAnswerController extends TutorAnswer
{
    @FXML
    private ComboBox<String> answerComboBox;
    private List<String> types;
    
    public ScaleModeAnswerController() {
        this.types = new ArrayList<String>(Arrays.asList("True", "False"));
    }
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        this.setAnswerComboBox();
        final Control[] controls = { (Control)this.answerComboBox };
        this.otherAnswerControls = controls;
        this.disableAnswers(true);
    }
    
    @FXML
    public String getAnswer() {
        final ToggleButton selected = (ToggleButton)this.group.getSelectedToggle();
        return selected.getText();
    }
    
    public void setAnswerComboBox() {
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow(this.types, nodeIn);
    }
}
