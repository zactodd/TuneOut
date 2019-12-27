

package Controller;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import Environment.Environment;

public class PitchTutorController extends TutorController
{
    private OuterTemplateController main;
    PitchTutorOptionController optionController;
    PitchTutorAnswerController answerController;
    
    public PitchTutorController() {
        this.main = new OuterTemplateController();
    }
    
    @Override
    public void initialize(final OuterTemplateController outerController, final Environment tutorEnvironment) throws IOException {
        final FXMLLoader basicTutorLoader = new FXMLLoader();
        basicTutorLoader.setLocation(this.getClass().getResource("/View/Tutors/tutorPane.fxml"));
        this.tutorPane = (GridPane)basicTutorLoader.load();
        this.main = outerController;
        final FXMLLoader tutorOptionsLoader = new FXMLLoader();
        tutorOptionsLoader.setLocation(this.getClass().getResource("/View/Tutors/PitchTutor/pitchTutorOptions.fxml"));
        this.tutorOptionsAnchorPane = (AnchorPane)tutorOptionsLoader.load();
        this.tutorPane.add((Node)this.tutorOptionsAnchorPane, 0, 0);
        this.optionController = (PitchTutorOptionController)tutorOptionsLoader.getController();
        final FXMLLoader tutorAnswersLoader = new FXMLLoader();
        tutorAnswersLoader.setLocation(this.getClass().getResource("/View/Tutors/PitchTutor/pitchTutorAnswers.fxml"));
        this.tutorAnswersAnchorPane = (AnchorPane)tutorAnswersLoader.load();
        this.answerController = (PitchTutorAnswerController)tutorAnswersLoader.getController();
        final FXMLLoader tutorResultsLoader = new FXMLLoader();
        tutorResultsLoader.setLocation(this.getClass().getResource("/View/Tutors/resultsPane.fxml"));
        this.tutorResultsPane = (AnchorPane)tutorResultsLoader.load();
        this.tutorResultsController = (TutorResultsController)tutorResultsLoader.getController();
        this.answerController.initialize(this.main, this.optionController, tutorEnvironment, this, this.tutorResultsController);
        this.optionController.initialize(this.main, this.answerController, tutorEnvironment, this);
        this.tutorResultsController.initialize(this.main, this.answerController, tutorEnvironment, this);
    }
}
