// 
// Decompiled by Procyon v0.5.36
// 

package Controller;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import Environment.Environment;
import javafx.scene.layout.GridPane;

public class KeySignatureTutorController extends TutorController
{
    private OuterTemplateController main;
    KeySignatureTutorOptionController optionController;
    KeySignatureTutorAnswerController answerController;
    
    public KeySignatureTutorController() {
        this.main = new OuterTemplateController();
    }
    
    @Override
    public GridPane getContent() {
        return this.tutorPane;
    }
    
    @Override
    public void initialize(final OuterTemplateController outerController, final Environment tutorEnvironment) throws IOException {
        final FXMLLoader basicTutorLoader = new FXMLLoader();
        basicTutorLoader.setLocation(this.getClass().getResource("/View/Tutors/tutorPane.fxml"));
        this.tutorPane = (GridPane)basicTutorLoader.load();
        this.main = outerController;
        final FXMLLoader tutorOptionsLoader = new FXMLLoader();
        tutorOptionsLoader.setLocation(this.getClass().getResource("/View/Tutors/KeySignatureTutor/keySignatureTutorOptions.fxml"));
        this.tutorOptionsAnchorPane = (AnchorPane)tutorOptionsLoader.load();
        this.tutorPane.add((Node)this.tutorOptionsAnchorPane, 0, 0);
        this.optionController = (KeySignatureTutorOptionController)tutorOptionsLoader.getController();
        final FXMLLoader tutorAnswersLoader = new FXMLLoader();
        tutorAnswersLoader.setLocation(this.getClass().getResource("/View/Tutors/KeySignatureTutor/keySignatureTutorAnswers.fxml"));
        this.tutorAnswersAnchorPane = (AnchorPane)tutorAnswersLoader.load();
        this.answerController = (KeySignatureTutorAnswerController)tutorAnswersLoader.getController();
        final FXMLLoader tutorResultsLoader = new FXMLLoader();
        tutorResultsLoader.setLocation(this.getClass().getResource("/View/Tutors/resultsPane.fxml"));
        this.tutorResultsPane = (AnchorPane)tutorResultsLoader.load();
        this.tutorResultsController = (TutorResultsController)tutorResultsLoader.getController();
        this.answerController.initialize(this.main, this.optionController, tutorEnvironment, this, this.tutorResultsController);
        this.optionController.initialize(this.main, this.answerController, tutorEnvironment, this);
        this.tutorResultsController.initialize(this.main, this.answerController, tutorEnvironment, this);
    }
}
