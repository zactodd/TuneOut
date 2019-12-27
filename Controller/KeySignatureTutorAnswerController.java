

package Controller;

import javafx.scene.Node;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import Model.Note.Scale.KeySignature;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.Set;
import Model.command.TutorCommands.TutorCommand;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import Environment.Environment;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class KeySignatureTutorAnswerController extends TutorAnswer
{
    @FXML
    private ComboBox answerComboBox;
    private final int MAX_OPTIONS = 5;
    
    public void initialize(final OuterTemplateController main, final TutorOption optionController, final Environment tutorEnvironment, final TutorController tutorController, final TutorResultsController tutorResultsController) {
        this.main = main;
        this.optionController = optionController;
        this.tutorEnvironment = tutorEnvironment;
        this.tutorController = tutorController;
        this.tutorResultsController = tutorResultsController;
        final Control[] controls = { (Control)this.answerComboBox };
        this.otherAnswerControls = controls;
        this.playBtn = new Button();
        this.disableAnswers(true);
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
    
    private void createDummyAnswer(final Set<String> possibleAnswers, final List<String> allKeySignatures, final int questionType, final String scaleType) {
        final String randomKeySignature = allKeySignatures.get(new Random().nextInt(allKeySignatures.size()));
        final List<String> scaleName = Arrays.asList(randomKeySignature.split(" "));
        final List<String> listOfNotesInKeySignature = KeySignature.getKeySignature(scaleName.get(0), scaleName.get(1));
        final String noOfModifiers = KeySignature.getNumberOfmModifier(listOfNotesInKeySignature);
        if (questionType == 1 || questionType == 2) {
            if (questionType == 1) {
                String answerWithRemovedBracket = listOfNotesInKeySignature.toString();
                if (listOfNotesInKeySignature.toString().equals("[]")) {
                    answerWithRemovedBracket = "No note";
                }
                possibleAnswers.add(answerWithRemovedBracket.replaceAll("\\[", "").replaceAll("\\]", ""));
            }
            else if (questionType == 2) {
                possibleAnswers.add(noOfModifiers);
            }
        }
        else if (questionType == 3 || questionType == 4) {
            String equiKeySignature = "";
            if (scaleType.equals("-b")) {
                if (scaleName.get(1).equals("major")) {
                    equiKeySignature = KeySignature.getKeySignatureFromModifierMinor(noOfModifiers);
                }
                if (scaleName.get(1).equals("minor")) {
                    equiKeySignature = KeySignature.getKeySignatureFromModifierMajor(noOfModifiers);
                }
            }
            if (!possibleAnswers.contains(equiKeySignature)) {
                possibleAnswers.add(randomKeySignature.toString());
            }
        }
    }
    
    public void setUpAnswer() {
        final String scaleType = this.tutorEnvironment.getTutorDefinition().tutor.getQuestion().getScaleType();
        final int questionType = this.tutorEnvironment.getTutorDefinition().tutor.getQuestion().getQuestionType();
        final Set<String> possibleAnswers = new HashSet<String>();
        String answerWithRemovedBracket = this.tutorEnvironment.getTutorDefinition().tutor.getAnswer();
        if (answerWithRemovedBracket.toString().equals("[]")) {
            answerWithRemovedBracket = "No note";
        }
        possibleAnswers.add(answerWithRemovedBracket.replaceAll("\\[", "").replaceAll("\\]", ""));
        final List<String> allKeySignatures = KeySignature.getListOfScaleNames(scaleType);
        while (possibleAnswers.size() < 5) {
            this.createDummyAnswer(possibleAnswers, allKeySignatures, questionType, scaleType);
        }
        final List<String> answerList = new ArrayList<String>(possibleAnswers);
        final Node nodeIn = this.tutorController.getAnswerButtonPane();
        this.setUpButtonsSmallFLow(answerList, nodeIn, 160, 60);
    }
}
