// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.scene.Node;
import java.util.List;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Control;
import Environment.Environment;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ScaleTutorAnswerController extends TutorAnswer
{
    @FXML
    ComboBox<String> answerControl;
    @FXML
    private ToggleGroup scaleTutorAnswerToggle;
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        final Control[] controls = { (Control)this.answerControl };
        this.otherAnswerControls = controls;
        this.disableAnswers(true);
    }
    
    @FXML
    public String getAnswer() {
        final ToggleButton selected = (ToggleButton)this.group.getSelectedToggle();
        return selected.getText();
    }
    
    public void setAnswerComboBox(final List<String> selectedScales) {
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow(selectedScales, nodeIn, 140, 30);
    }
}
