

package Model.Tutor;

import java.util.Collections;

public class ChordFunctionTutorOption extends Options
{
    public static final String CHORD_QUESTIONS = "-c";
    public static final String FUNCTION_QUESTIONS = "-f";
    public static final String BOTH_QUESTIONS = "-b";
    
    public ChordFunctionTutorOption(final int numQuestions, final String questionTypes) {
        this.numQuestions = numQuestions;
        this.otherOptions = Collections.singletonList(questionTypes);
    }
}
