// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Enharmonic;

import Model.Note.NoteMap;
import Model.Note.Note;

public class EnharmonicEquivalent
{
    private String note;
    private String lowerEnharmonic;
    private String higherEnharmonic;
    
    public EnharmonicEquivalent(final String note, final String lowerEnharmonic, final String higherEnharmonic) {
        this.note = note;
        this.lowerEnharmonic = lowerEnharmonic;
        this.higherEnharmonic = higherEnharmonic;
    }
    
    public String getNoteName() {
        return this.note;
    }
    
    public Note getHigherEnharmonic(final Integer octave) {
        return NoteMap.getNote(this.higherEnharmonic + octave);
    }
    
    public Note getLowerEnharmonic(final Integer octave) {
        return NoteMap.getNote(this.lowerEnharmonic + octave);
    }
}
