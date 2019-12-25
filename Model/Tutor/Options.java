// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import java.util.List;

public abstract class Options
{
    public int numQuestions;
    public int numOctaves;
    public String order;
    public List<String> otherOptions;
    public static final int MAX_NUM_QUESTIONS = 1000;
    
    public static boolean numQuestionsValid(final String numQuestionsToCheck) {
        int questions;
        try {
            questions = Integer.parseInt(numQuestionsToCheck);
        }
        catch (Exception e) {
            return false;
        }
        return questions > 0 && questions <= 1000;
    }
}
