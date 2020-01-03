

package Controller;

import java.util.Arrays;
import java.util.Set;
import java.util.Random;
import java.util.ArrayList;
import Model.Terms.MusicalTerms;
import java.util.HashSet;
import Model.Terms.Term;
import Model.command.TutorCommands.TutorCommand;
import javafx.scene.control.ToggleButton;
import javafx.scene.Node;
import javafx.scene.control.Button;
import Environment.Environment;
import java.util.List;
import javafx.scene.control.Control;
import javafx.scene.layout.FlowPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MusicalTermTutorAnswerController extends TutorAnswer
{
    @FXML
    private TextField answerText;
    @FXML
    private FlowPane answerPane;
    private Control answerControl;
    private static final int LANUAGE_OPTIONS = 2;
    private static final int MAX_OPTONS = 5;
    private Integer questionType;
    private static List<Integer> COMBOBOX_TYPES;
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        (this.answerControl = (Control)new TextField()).setPrefHeight(46.0);
        this.answerControl.setPrefWidth(230.0);
        final Control[] controls = { this.answerControl };
        this.otherAnswerControls = controls;
        this.playBtn = new Button();
        this.disableAnswers(true);
    }
    
    @FXML
    public String getAnswer() {
        String answer = "";
        boolean isToggle = true;
        try {
            final Node node = (Node)this.answerPane.getChildren().get(0);
            if (node instanceof TextField) {
                isToggle = false;
            }
            return (String)isToggle;
        }
        finally {
            if (isToggle) {
                final ToggleButton selected = (ToggleButton)this.group.getSelectedToggle();
                return selected.getText();
            }
            answer = ((TextField)this.answerControl).getText().replace("\"", "");
            answer = ((answer == null) ? "" : answer);
            return answer.toLowerCase();
        }
    }
    
    @FXML
    @Override
    public void runQuestion() {
        this.progress.setProgress(this.doneQuestions / (double)this.numQuestions);
        final String returnedQuestion = this.runTutorCommand("question()");
        this.runTutorCommand("run()");
        this.question.setText(returnedQuestion);
        if (returnedQuestion.equals(TutorCommand.noMoreQuestions())) {
            this.displayStats();
        }
        else {
            this.setUpAnswer();
        }
    }
    
    private String getTypeAnswer(final Term term) {
        String dummyAnswer = "";
        switch (this.questionType) {
            case 0: {
                dummyAnswer = term.getTermName();
                break;
            }
            case 1: {
                dummyAnswer = term.getMeaning();
                break;
            }
        }
        return dummyAnswer.replace("\"", "");
    }
    
    private void setUpAnswer() {
        this.answerPane.getChildren().clear();
        this.questionType = this.tutorEnvironment.getTutorDefinition().tutor.getQuestion().getQuestionType();
        switch (this.questionType) {
            case 0: {
                this.answerWithOptions(this.questionType);
                break;
            }
            case 1: {
                this.answerWithOptions(this.questionType);
                break;
            }
            case 2: {
                this.answerControl = (Control)new TextField();
                this.answerPane.getChildren().add((Object)this.answerControl);
                break;
            }
        }
    }
    
    private void answerWithOptions(final Integer questionType) {
        final Set<String> possibleAnswers = new HashSet<String>();
        possibleAnswers.add(this.tutorEnvironment.getTutorDefinition().tutor.getAnswer());
        final List<Term> terms = new ArrayList<Term>(MusicalTerms.getTerms().values());
        while (possibleAnswers.size() < 5) {
            possibleAnswers.add(this.getTypeAnswer(terms.get(new Random().nextInt(terms.size()))));
        }
        final List<String> answerList = new ArrayList<String>(possibleAnswers);
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow(answerList, nodeIn);
    }
    
    static {
        MusicalTermTutorAnswerController.COMBOBOX_TYPES = new ArrayList<Integer>(Arrays.asList(0, 1));
    }
}
