

package Model.Note.Melody;

import Model.Note.unitDuration.UnitDurationInformation;
import java.util.Collections;
import java.util.ArrayList;
import Model.Note.unitDuration.UnitDuration;
import Model.Note.Note;
import java.util.List;

public class NoteCollection
{
    private PlayStyle playStyle;
    private List<Note> notes;
    private UnitDuration unitDuration;
    
    public NoteCollection() {
        this.playStyle = PlayStyle.ARPEGGIO;
        this.notes = new ArrayList<Note>();
        this.unitDuration = null;
    }
    
    public NoteCollection(final List<Note> notes, final UnitDuration unitDuration, final PlayStyle playStyle) {
        this.playStyle = playStyle;
        this.unitDuration = unitDuration;
        this.notes = notes;
    }
    
    public PlayStyle getPlayStyle() {
        return this.playStyle;
    }
    
    public void setPlayStyle(final PlayStyle playStyle) {
        this.playStyle = playStyle;
    }
    
    public List<Note> getNotes() {
        return Collections.unmodifiableList((List<? extends Note>)this.notes);
    }
    
    public void setNotes(final List<Note> notes) {
        this.notes = notes;
    }
    
    public UnitDuration getUnitDuration() {
        if (this.unitDuration == null) {
            return UnitDurationInformation.getUnitDuration();
        }
        return this.unitDuration;
    }
    
    public void setUnitDuration(final UnitDuration unitDuration) {
        this.unitDuration = unitDuration;
    }
    
    @Override
    public String toString() {
        String durationStr = "";
        if (this.unitDuration == null) {
            durationStr = "null";
        }
        else {
            durationStr = this.unitDuration.getUnitDurationName();
        }
        return this.notes + ", " + durationStr;
    }
}
