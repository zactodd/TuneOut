

package Controller;

import java.util.LinkedHashSet;
import java.util.Set;

import Model.Note.Scale.Scale;
import java.util.HashSet;
import java.util.Collections;
import Model.Note.Chord.ChordMap;
import Model.Note.Note;
import Model.Note.Scale.ScaleMap;
import java.util.ArrayList;
import javafx.scene.Node;
import java.util.List;
import java.util.Collection;
import Model.command.TutorCommands.TutorCommand;
import javafx.scene.control.ToggleButton;
import Environment.Environment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChordFunctionAnswerController extends TutorAnswer
{
    private static final String KEY_NAME = "major";
    @FXML
    private Button buttonRestart;
    private static final Integer FUNCTION_QUESTION;
    private static final Integer CHORD_QUESTION;
    public static final Integer CHORD_ANSWERS_SIZE;
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        this.disableAnswers(true);
    }
    
    @FXML
    public void restartTest() {
        this.optionController.disableOptions(false);
        this.tutorController.show_optionsPane();
    }
    
    @FXML
    public String getAnswer() {
        final ToggleButton selected = (ToggleButton)this.group.getSelectedToggle();
        return selected.getText();
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
    
    public void setTutorEnvironment(final Environment env) {
        this.tutorEnvironment = env;
    }
    
    private void setUpAnswer() {
        final int questionType = this.tutorEnvironment.getTutorDefinition().tutor.getQuestion().getQuestionType();
        if (questionType == ChordFunctionAnswerController.FUNCTION_QUESTION) {
            this.populateAnswerCombo(this.getFunctionAnswers());
        }
        if (questionType == ChordFunctionAnswerController.CHORD_QUESTION) {
            final String[] questionParts = this.question.getText().split(" ");
            final String keyNote = questionParts[questionParts.length - 2];
            final String theAnswer = this.tutorEnvironment.getTutorDefinition().tutor.getAnswer();
            this.populateAnswerCombo(this.getChordAnswers(keyNote, theAnswer));
        }
    }
    
    public void populateAnswerCombo(final Collection<String> answers) {
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow((List)answers, nodeIn);
    }
    
    public Collection<String> getChordAnswers(final String keyNote, final String theAnswer) {
        final List<String> allChordsForKey = new ArrayList<String>();
        final Scale theScale = ScaleMap.getScale("major");
        theScale.setNotesInScale(keyNote + "4");
        final List<Note> notes = theScale.getNotesInScale();
        for (final Note scaleNote : notes) {
            for (final String qualityName : ChordMap.getUniqueQualityNames()) {
                allChordsForKey.add(scaleNote.getNoteName() + " " + qualityName);
            }
        }
        Collections.shuffle(allChordsForKey);
        final Set<String> uniqueChords = new HashSet<String>();
        uniqueChords.add(theAnswer);
        int counter = 0;
        while (uniqueChords.size() < ChordFunctionAnswerController.CHORD_ANSWERS_SIZE) {
            uniqueChords.add(allChordsForKey.get(counter));
            ++counter;
        }
        final List<String> finalChords = new ArrayList<String>(uniqueChords);
        Collections.shuffle(finalChords);
        return finalChords;
    }
    
    public List<String> getFunctionAnswers() {
        final Set<String> functionAnswers = new LinkedHashSet<String>();
        for (final String function : ChordMap.getAllFunctionNames()) {
            functionAnswers.add(function);
        }
        functionAnswers.add("Non Functional");
        final List<String> answerList = new ArrayList<String>(functionAnswers);
        return answerList;
    }
    
    static {
        FUNCTION_QUESTION = 1;
        CHORD_QUESTION = 2;
        CHORD_ANSWERS_SIZE = 6;
    }
}
