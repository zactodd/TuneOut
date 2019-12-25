// 
// Decompiled by Procyon v0.5.36
// 

package Model.Percussion;

public class Percussion
{
    private Integer midi;
    private String instrument;
    
    public Percussion(final Integer midi, final String instrument) {
        this.midi = midi;
        this.instrument = instrument;
    }
    
    public Integer getMidi() {
        return this.midi;
    }
    
    public String getInstrument() {
        return this.instrument;
    }
}
