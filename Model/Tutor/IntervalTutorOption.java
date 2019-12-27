

package Model.Tutor;

import java.util.List;

public class IntervalTutorOption extends Options
{
    public IntervalTutorOption(final int numQuestions, final List<String> intervalRange) {
        this.numQuestions = numQuestions;
        this.otherOptions = intervalRange;
    }
    
    public static boolean numOctavesValid(final String numOctavesToCheck) {
        int questions;
        try {
            questions = Integer.parseInt(numOctavesToCheck);
        }
        catch (Exception e) {
            return false;
        }
        return questions > 0 && questions <= 2;
    }
}
