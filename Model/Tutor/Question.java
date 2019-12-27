

package Model.Tutor;

import Model.Terms.Term;

public class Question
{
    String questionText;
    String correctAnswer;
    boolean isCorrect;
    boolean hasAnswered;
    int firstRandomNote;
    int secondRandomNote;
    int firstRandomParam;
    int secondRandomParam;
    private int questionType;
    String keySignature;
    String scaleType;
    Term term;
    
    public Question(final String questionText) {
        this.questionText = questionText;
    }
    
    protected void setQuestionType(final int questionType) {
        this.questionType = questionType;
    }
    
    public String getScaleType() {
        return this.scaleType;
    }
    
    protected void setScaleType(final String scaleType) {
        this.scaleType = scaleType;
    }
    
    public int getQuestionType() {
        return this.questionType;
    }
    
    public String getAnswer() {
        return this.correctAnswer;
    }
    
    public String getQuestionText() {
        return this.questionText;
    }
    
    public boolean HasAnswered() {
        return this.hasAnswered;
    }
}
