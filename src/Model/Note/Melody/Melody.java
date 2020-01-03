

package Model.Note.Melody;

import java.util.Collections;
import Model.instrument.InstrumentInformation;
import java.util.ArrayList;
import java.util.List;
import Model.instrument.Instrument;

public class Melody
{
    private Instrument instrument;
    private String name;
    private List<NoteCollection> noteCollections;
    
    public Melody(final String name) {
        this.instrument = null;
        this.name = name;
        this.noteCollections = new ArrayList<NoteCollection>();
    }
    
    public Instrument getInstrument() {
        if (this.instrument == null) {
            return InstrumentInformation.getInstrument();
        }
        return this.instrument;
    }
    
    public void setInstrument(final Instrument instrument) {
        this.instrument = instrument;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setNoteCollection(final List<NoteCollection> noteCollections) {
        this.noteCollections = noteCollections;
    }
    
    public List<NoteCollection> getNoteCollections() {
        return Collections.unmodifiableList((List<? extends NoteCollection>)this.noteCollections);
    }
    
    @Override
    public String toString() {
        return "Melody{instrument=" + this.instrument + ", name='" + this.name + '\'' + ", noteCollections=" + this.noteCollections + '}';
    }
}
