

package Model.Tutor;

import Model.Note.Scale.KeySignature;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public class KeySignatureTutor extends Tutor
{
    static Logger log;
    private static final String LISTED_KEY_TO_SCALE_QUESTION = "What scale has the following key signatures: ";
    private static final String LISTED_SCALE_TO_KEY_QUESTION = "List all the notes in the key signatures of ";
    private static final String NUMBERED_KEY_TO_SCALE_QUESTION = "What scale has ";
    private static final String NUMBERED_SCALE_TO_KEY_QUESTION = "How many sharps or flats does the following scale have: ";
    private String questionType;
    private String keySignatureOption;
    private String scaleNameOption;
    
    public KeySignatureTutor() {
        this.questionType = "-t";
        this.keySignatureOption = "-c";
        this.scaleNameOption = "-b";
    }
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            this.questions.put(this.currentQuestionNumber, this.nextQuestion());
        }
    }
    
    @Override
    public void runQuestion(final int gapAtStart) {
    }
    
    @Override
    public void setOptions(final List<String> options) {
        this.currentQuestionNumber = 0;
        this.questions = new HashMap<Integer, Question>();
        try {
            this.numQuestions = Integer.parseInt(options.get(0));
            this.questionType = options.get(1);
            this.keySignatureOption = options.get(2);
            this.scaleNameOption = options.get(3);
        }
        catch (Exception e) {
            KeySignatureTutor.log.error(e.getMessage());
        }
    }
    
    private Integer assignQuestionType() {
        Integer whichQuesType = null;
        final String questionType = this.questionType;
        int n = -1;
        switch (questionType.hashCode()) {
            case 1511: {
                if (questionType.equals("-t")) {
                    n = 0;
                    break;
                }
                break;
            }
            case 1502: {
                if (questionType.equals("-k")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 1510: {
                if (questionType.equals("-s")) {
                    n = 2;
                    break;
                }
                break;
            }
        }
        Label_0629: {
            switch (n) {
                case 0: {
                    final int randomInt = randInt(0, 1);
                    final String keySignatureOption = this.keySignatureOption;
                    switch (keySignatureOption) {
                        case "-l": {
                            final List<Integer> range = Arrays.asList(1, 3);
                            whichQuesType = range.get(randomInt);
                            break;
                        }
                        case "-n": {
                            final List<Integer> range = Arrays.asList(2, 4);
                            whichQuesType = range.get(randomInt);
                            break;
                        }
                        case "-c": {
                            whichQuesType = randInt(1, 4);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    final String keySignatureOption2 = this.keySignatureOption;
                    switch (keySignatureOption2) {
                        case "-l": {
                            whichQuesType = 1;
                            break;
                        }
                        case "-n": {
                            whichQuesType = 2;
                            break;
                        }
                        case "-c": {
                            whichQuesType = randInt(1, 2);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    final String keySignatureOption3 = this.keySignatureOption;
                    switch (keySignatureOption3) {
                        case "-l": {
                            whichQuesType = 3;
                            break Label_0629;
                        }
                        case "-n": {
                            whichQuesType = 4;
                            break Label_0629;
                        }
                        case "-c": {
                            whichQuesType = randInt(3, 4);
                            break Label_0629;
                        }
                    }
                    break;
                }
            }
        }
        return whichQuesType;
    }
    
    private Question nextQuestion() {
        Question currentQuestion = null;
        String tempAns = "";
        final List<String> allKeySignatures = KeySignature.getListOfScaleNames(this.scaleNameOption);
        final Integer whichQuesType = this.assignQuestionType();
        String randomKeySignature;
        do {
            final int randomKeySigIndex = randInt(0, allKeySignatures.size() - 1);
            randomKeySignature = allKeySignatures.get(randomKeySigIndex);
        } while (this.currentQuestionNumber != 1 && this.currentQuestionNumber > 1 && randomKeySignature.equals(this.questions.get(this.currentQuestionNumber - 1).keySignature) && whichQuesType == this.questions.get(this.currentQuestionNumber - 1).getQuestionType());
        final List<String> scaleName = Arrays.asList(randomKeySignature.split(" "));
        final List<String> listOfNotesInKeySignature = KeySignature.getKeySignature(scaleName.get(0), scaleName.get(1));
        switch (whichQuesType) {
            case 1: {
                currentQuestion = new Question("List all the notes in the key signatures of " + randomKeySignature + "?");
                tempAns = listOfNotesInKeySignature.toString();
                if (tempAns.equals("[]")) {
                    tempAns = "No note";
                    break;
                }
                tempAns = listOfNotesInKeySignature.toString().replaceAll("\\[", "").replaceAll("\\]", "");
                break;
            }
            case 2: {
                currentQuestion = new Question("How many sharps or flats does the following scale have: " + randomKeySignature + "?");
                tempAns = KeySignature.getNumberOfmModifier(listOfNotesInKeySignature);
                break;
            }
            case 3: {
                String quesWithRemovedBracket = listOfNotesInKeySignature.toString();
                if (listOfNotesInKeySignature.toString().equals("[]")) {
                    quesWithRemovedBracket = "No note";
                }
                currentQuestion = new Question("What scale has the following key signatures: " + quesWithRemovedBracket.replaceAll("\\[", "").replaceAll("\\]", "") + "?");
                tempAns = randomKeySignature;
                break;
            }
            case 4: {
                currentQuestion = new Question("What scale has " + KeySignature.getNumberOfmModifier(listOfNotesInKeySignature) + "?");
                tempAns = randomKeySignature;
                break;
            }
        }
        currentQuestion.keySignature = randomKeySignature;
        currentQuestion.scaleType = this.scaleNameOption;
        currentQuestion.setQuestionType(whichQuesType);
        currentQuestion.correctAnswer = tempAns;
        return currentQuestion;
    }
    
    static {
        KeySignatureTutor.log = Logger.getLogger(KeySignatureTutor.class.getName());
    }
}
