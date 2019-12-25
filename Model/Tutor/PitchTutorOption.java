// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import Model.Note.NoteMap;
import java.util.ArrayList;

public class PitchTutorOption extends Options
{
    public PitchTutorOption(final int numQuestions, final ArrayList<String> pitchRange) {
        this.numQuestions = numQuestions;
        this.otherOptions = pitchRange;
    }
    
    public static boolean pitchRangeValid(final String pitchMin, final String pitchMax) {
        int pitchMinMidi;
        int pitchMaxMidi;
        try {
            pitchMinMidi = NoteMap.getMidi(pitchMin);
            pitchMaxMidi = NoteMap.getMidi(pitchMax);
        }
        catch (Exception e) {
            return false;
        }
        return pitchMinMidi < pitchMaxMidi;
    }
}
