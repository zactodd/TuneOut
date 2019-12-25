// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import javafx.scene.control.ToggleButton;
import javafx.scene.Node;
import java.util.List;
import java.util.Collection;
import javafx.scene.control.Control;
import Model.Note.Chord.ChordMap;
import Environment.Environment;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ChordTutorAnswerController extends TutorAnswer
{
    @FXML
    private ComboBox<String> answerCombo;
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        this.populateAnswerButtons(ChordMap.getChordTypes());
        final Control[] controls = { (Control)this.answerCombo };
        this.otherAnswerControls = controls;
        this.disableAnswers(true);
    }
    
    public void populateAnswerButtons(final Collection<String> answers) {
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow((List)answers, nodeIn, 160, 40);
    }
    
    @FXML
    public String getAnswer() {
        final ToggleButton selected = (ToggleButton)this.group.getSelectedToggle();
        return selected.getText();
    }
}
