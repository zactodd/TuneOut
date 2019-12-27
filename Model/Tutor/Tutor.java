

package Model.Tutor;

import java.util.Random;
import java.util.Iterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import Model.Play.Play;
import java.util.AbstractMap;
import java.util.HashMap;

public abstract class Tutor
{
    protected int currentQuestionNumber;
    protected HashMap<Integer, Question> questions;
    protected int numQuestions;
    protected AbstractMap<Integer, String> userAnswers;
    protected Play play;
    
    public Tutor() {
        this.currentQuestionNumber = 0;
        this.questions = new HashMap<Integer, Question>();
        this.userAnswers = new HashMap<Integer, String>();
        this.play = new Play(Play.PlayType.REPLACE);
    }
    
    public abstract void generateQuestion();
    
    public abstract void runQuestion(final int p0);
    
    public boolean checkAnswer(String userAnswer) {
        userAnswer = userAnswer.replace("\"", "");
        this.userAnswers.put(this.currentQuestionNumber, userAnswer);
        if (!this.questions.containsKey(this.currentQuestionNumber)) {
            return Boolean.parseBoolean(null);
        }
        this.questions.get(this.currentQuestionNumber).hasAnswered = true;
        if (this.questions.get(this.currentQuestionNumber).correctAnswer.toLowerCase().equals(userAnswer.toLowerCase())) {
            return this.questions.get(this.currentQuestionNumber).isCorrect = true;
        }
        return this.questions.get(this.currentQuestionNumber).isCorrect = false;
    }
    
    public abstract void setOptions(final List<String> p0);
    
    public String getQuestionString() {
        if (this.questions.containsKey(this.currentQuestionNumber)) {
            return this.questions.get(this.currentQuestionNumber).questionText;
        }
        return "";
    }
    
    public Question getQuestion() {
        return this.questions.get(this.currentQuestionNumber);
    }
    
    public Map<Integer, Question> getQuestions() {
        return Collections.unmodifiableMap((Map<? extends Integer, ? extends Question>)this.questions);
    }
    
    public Map<Integer, String> getAnswers() {
        return Collections.unmodifiableMap((Map<? extends Integer, ? extends String>)this.userAnswers);
    }
    
    public String getAnswer() {
        if (this.questions.containsKey(this.currentQuestionNumber)) {
            return this.questions.get(this.currentQuestionNumber).correctAnswer;
        }
        return "";
    }
    
    public ArrayList<String> getStats() {
        final ArrayList<String> stats = new ArrayList<String>();
        final DecimalFormat format = new DecimalFormat("0.##");
        if (!this.questions.isEmpty()) {
            final Results results = new Results(this.questions);
            stats.add("Questions answered: " + results.getNumQuestions());
            if (results.getNumQuestions() > 0 && results.getNumIncorrect() == 0) {
                stats.add("Congratulations, all your answers were correct!");
            }
            else {
                stats.add("You got " + results.getNumIncorrect() + " incorrect");
                stats.add("You got " + format.format(results.getPercentCorrect()) + "% correct");
            }
            return stats;
        }
        return null;
    }
    
    public boolean checkAndRepeatQuestions() {
        boolean hasIncorrectAnswers = false;
        if (this.questions.isEmpty()) {
            return false;
        }
        for (final Question question : this.questions.values()) {
            if (!question.isCorrect) {
                hasIncorrectAnswers = true;
            }
        }
        if (hasIncorrectAnswers || this.questions.size() != this.numQuestions) {
            this.currentQuestionNumber = 0;
            return true;
        }
        return false;
    }
    
    public static int randInt(final int min, final int max) {
        final Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
    
    public int getNumQuestions() {
        return this.numQuestions;
    }
    
    public void setNumQuestions(final int numQuestions) {
        this.numQuestions = numQuestions;
    }
    
    public Play getPlay() {
        return this.play;
    }
}
