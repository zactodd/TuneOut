// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.HashMap;

final class Results
{
    private final int numQuestions;
    private final double percentCorrect;
    private final int numIncorrect;
    
    Results(final HashMap<Integer, Question> questions) {
        this.numQuestions = this.calculateQuestionsAnswered(questions);
        this.percentCorrect = this.calculatePercentCorrect(questions);
        this.numIncorrect = this.calculateNumIncorrect(questions);
    }
    
    private int calculateQuestionsAnswered(final HashMap<Integer, Question> questions) {
        int numAnswered = 0;
        if (!questions.isEmpty()) {
            for (final Question question : questions.values()) {
                if (question.hasAnswered) {
                    ++numAnswered;
                }
            }
        }
        return numAnswered;
    }
    
    private double calculatePercentCorrect(final HashMap<Integer, Question> questions) {
        int numCorrect = 0;
        double percentIncorrect = 0.0;
        if (!questions.isEmpty()) {
            for (final Question question : questions.values()) {
                if (question.hasAnswered && question.isCorrect) {
                    ++numCorrect;
                }
            }
            if (numCorrect != 0) {
                BigDecimal bd = new BigDecimal(numCorrect * 100.0 / this.numQuestions);
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                percentIncorrect = bd.doubleValue();
            }
        }
        return percentIncorrect;
    }
    
    private int calculateNumIncorrect(final HashMap<Integer, Question> questions) {
        int numIncorrect = 0;
        if (!questions.isEmpty()) {
            for (final Question question : questions.values()) {
                if (question.hasAnswered && !question.isCorrect) {
                    ++numIncorrect;
                }
            }
        }
        return numIncorrect;
    }
    
    int getNumQuestions() {
        return this.numQuestions;
    }
    
    double getPercentCorrect() {
        return this.percentCorrect;
    }
    
    int getNumIncorrect() {
        return this.numIncorrect;
    }
}
