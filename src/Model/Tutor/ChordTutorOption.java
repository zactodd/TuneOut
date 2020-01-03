

package Model.Tutor;

import java.util.ArrayList;
import java.util.Collections;

public class ChordTutorOption extends Options
{
    public static final String PLAY_ARPEGGIATE = "-a";
    public static final String PLAY_SIMULTANEOUS = "-s";
    public static final String PLAY_BOTH = "-b";
    
    public ChordTutorOption(final int numQuestions, final String playType) {
        this.numQuestions = numQuestions;
        this.otherOptions = new ArrayList<String>(Collections.singletonList(playType));
    }
}
