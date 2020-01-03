

package Controller;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Control;
import Environment.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class PitchTutorAnswerController extends TutorAnswer
{
    @FXML
    private RadioButton rb1;
    @FXML
    private RadioButton rb2;
    @FXML
    private RadioButton rb3;
    @FXML
    private ToggleGroup pitchTutorAnswerToggle;
    private List<String> types;
    
    public PitchTutorAnswerController() {
        this.types = new ArrayList<String>(Arrays.asList("Higher", "Lower", "Same"));
    }
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.tutorController = tutorController;
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorResultsController = tutorResultsController;
        final Control[] controls = { (Control)this.rb1, (Control)this.rb2, (Control)this.rb3 };
        this.otherAnswerControls = controls;
        this.setAnswerButtons();
        this.disableAnswers(true);
    }
    
    @FXML
    public String getAnswer() {
        final ToggleButton selected = (ToggleButton)this.group.getSelectedToggle();
        return selected.getText();
    }
    
    public void setAnswerButtons() {
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow(this.types, nodeIn);
    }
}
