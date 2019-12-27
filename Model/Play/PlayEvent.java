

package Model.Play;

import java.util.Collection;

public class PlayEvent
{
    private Collection<Integer> notes;
    private int volume;
    private double ticks;
    private boolean fromKeyboard;
    private int midiChannel;
    
    public PlayEvent(final Collection<Integer> notes, final int volume, final long ticks, final boolean fromKeyboard, final int midiChannel) {
        this.volume = 80;
        this.notes = notes;
        this.volume = volume;
        this.ticks = (double)ticks;
        this.fromKeyboard = fromKeyboard;
        this.midiChannel = midiChannel;
    }
    
    public PlayEvent(final Collection<Integer> notes, final long ticks, final boolean fromKeyboard, final int midiChannel) {
        this.volume = 80;
        this.notes = notes;
        this.ticks = (double)ticks;
        this.fromKeyboard = fromKeyboard;
        this.midiChannel = midiChannel;
    }
    
    public Collection<Integer> getNotes() {
        return this.notes;
    }
    
    public int getVolume() {
        return this.volume;
    }
    
    public long getTicks() {
        return (long)this.ticks;
    }
    
    public boolean getfromKeyboard() {
        return this.fromKeyboard;
    }
    
    public int getMidiChannel() {
        return this.midiChannel;
    }
    
    @Override
    public String toString() {
        return "PlayEvent{notes=" + this.notes + ", volume=" + this.volume + ", ticks=" + (int)this.ticks + '}';
    }
}
