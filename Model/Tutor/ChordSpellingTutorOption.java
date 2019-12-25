// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import java.util.List;

public class ChordSpellingTutorOption extends Options
{
    private Boolean isSpellingQuestion;
    private List<String> chordTypes;
    private Boolean allowRandomNotes;
    
    public ChordSpellingTutorOption(final Integer numQuestions, final List<String> chordTypes, final Boolean allowRandomNotes, final Boolean isSpellingQuestion) {
        this.numQuestions = numQuestions;
        this.isSpellingQuestion = isSpellingQuestion;
        this.allowRandomNotes = allowRandomNotes;
        this.chordTypes = chordTypes;
    }
    
    public boolean allowsRandomNotes() {
        return this.allowRandomNotes;
    }
    
    public Boolean getSpellingQuestion() {
        return this.isSpellingQuestion;
    }
    
    public List<String> getChordTypes() {
        return this.chordTypes;
    }
}
