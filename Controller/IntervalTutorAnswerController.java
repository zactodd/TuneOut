

package Controller;

import javafx.scene.Node;
import java.util.List;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Control;
import Environment.Environment;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class IntervalTutorAnswerController extends TutorAnswer
{
    @FXML
    private ComboBox comboBoxIntervalName;
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        final Control[] controls = { (Control)this.comboBoxIntervalName };
        this.otherAnswerControls = controls;
        this.disableAnswers(true);
    }
    
    @FXML
    public String getAnswer() {
        final ToggleButton selected = (ToggleButton)this.group.getSelectedToggle();
        return selected.getText();
    }
    
    public void setAnswerComboBox(final List<String> selectedIntervals) {
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow(selectedIntervals, nodeIn, 160, 40);
    }
}
