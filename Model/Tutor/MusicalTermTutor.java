

package Model.Tutor;

import java.util.Map;
import Model.Terms.Term;
import java.util.Collection;
import java.util.ArrayList;
import Model.Terms.MusicalTerms;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public class MusicalTermTutor extends Tutor
{
    static Logger log;
    private static final int TYPES_OF_QUESTIONS = 2;
    private static final String DEF_TO_TERM_QUESTION = "What term has the definition ";
    private static final String TERM_TO_DEF_QUESTION = "What is the definition of ";
    private static final String TERM_TO_LANGUAGE_QUESTION = "What language does this term have ";
    
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
        }
        catch (Exception e) {
            MusicalTermTutor.log.error(e.getMessage());
        }
    }
    
    private Question nextQuestion() {
        final Map<String, Term> terms = MusicalTerms.getTerms();
        final List<String> keys = new ArrayList<String>(terms.keySet());
        Question currentQuestion = null;
        String tempAns = "";
        Term term;
        int questionInt;
        do {
            term = terms.get(keys.get(Tutor.randInt(0, keys.size() - 1)));
            questionInt = Tutor.randInt(0, 2);
        } while (this.currentQuestionNumber > 1 && term.getTermName().equals(this.questions.get(this.currentQuestionNumber - 1).term.getTermName()) && questionInt == this.questions.get(this.currentQuestionNumber - 1).getQuestionType());
        switch (questionInt) {
            case 0: {
                currentQuestion = new Question("What term has the definition " + term.getMeaning() + "?");
                tempAns = term.getTermName();
                break;
            }
            case 1: {
                currentQuestion = new Question("What is the definition of " + term.getTermName() + "?");
                tempAns = term.getMeaning();
                break;
            }
            case 2: {
                currentQuestion = new Question("What language does this term have " + term.getTermName() + "?");
                tempAns = term.getSourceLanguage();
                break;
            }
        }
        if (currentQuestion != null) {
            currentQuestion.correctAnswer = tempAns.replace("\"", "");
            currentQuestion.setQuestionType(questionInt);
            currentQuestion.term = term;
        }
        return currentQuestion;
    }
    
    static {
        MusicalTermTutor.log = Logger.getLogger(MusicalTermTutor.class.getName());
    }
}
