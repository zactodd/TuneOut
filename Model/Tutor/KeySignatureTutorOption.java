// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import java.util.ArrayList;

public class KeySignatureTutorOption extends Options
{
    public static final String KEY_SIGNATURES_ONLY = "-k";
    public static final String SCALE_NAMES_ONLY = "-s";
    public static final String BOTH_QUESTION_TYPES = "-t";
    public static final String KEY_SIGNATURES_NUMBERED = "-n";
    public static final String KEY_SIGNATURES_LISTED = "-l";
    public static final String BOTH_FORMAT_TYPES = "-c";
    public static final String MAJOR_SCALES_ONLY = "-M";
    public static final String MINOR_SCALES_ONLY = "-m";
    public static final String MAJOR_AND_MINOR_SCALES = "-b";
    
    public KeySignatureTutorOption(final int numQuestions, final ArrayList<String> questionType) {
        this.numQuestions = numQuestions;
        this.otherOptions = questionType;
    }
}
