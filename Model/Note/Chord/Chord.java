// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Chord;

import java.util.Collections;
import java.util.Collection;

public class Chord
{
    private Collection<String> names;
    private Collection<ChordStep> chordSteps;
    private Boolean isPrimaryName;
    
    public Chord(final Collection<String> names, final Collection<ChordStep> chordSteps, final Boolean isPrimaryName) {
        this.names = names;
        this.chordSteps = chordSteps;
        this.isPrimaryName = isPrimaryName;
    }
    
    public Collection<String> getNames() {
        return Collections.unmodifiableCollection((Collection<? extends String>)this.names);
    }
    
    public Collection<ChordStep> getChordSteps() {
        return Collections.unmodifiableCollection((Collection<? extends ChordStep>)this.chordSteps);
    }
    
    public Boolean isPrimaryName() {
        return this.isPrimaryName;
    }
}
