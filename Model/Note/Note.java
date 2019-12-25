// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;

public class Note implements Comparable<Note>
{
    private String noteName;
    private int octave;
    private Integer midiIndex;
    private Boolean primary;
    
    public Note(final String noteName, final int octave, final int midiIndex, final Boolean primary) {
        this.noteName = noteName;
        this.octave = octave;
        this.midiIndex = midiIndex;
        this.primary = primary;
    }
    
    public String getNoteName() {
        return this.noteName;
    }
    
    public String getBaseNoteWithOctave() {
        return this.getNoteWithOctave().replace(this.getAccidental(), "");
    }
    
    public int getOctave() {
        return this.octave;
    }
    
    public Integer getMidiNumber() {
        return this.midiIndex;
    }
    
    public String getNoteWithOctave() {
        return this.noteName + this.octave;
    }
    
    public Note getBaseNote() {
        final String baseNote = this.getNoteName().substring(0, 1) + this.getOctave();
        return NoteMap.getNote(baseNote);
    }
    
    public String getBaseNoteNoOctave() {
        return this.getNoteName().substring(0, 1);
    }
    
    public Boolean hasAccidental() {
        return this.noteName.endsWith("b") || this.noteName.endsWith("#") || this.noteName.endsWith("x");
    }
    
    public String getAccidental() {
        if (this.hasAccidental()) {
            return this.noteName.substring(1);
        }
        return " ";
    }
    
    public Boolean isPrimary() {
        return this.primary;
    }
    
    @Override
    public String toString() {
        return this.noteName + this.octave;
    }
    
    @Override
    public int compareTo(final Note note) {
        final List<String> accOrder = new ArrayList<String>(Arrays.asList("bb", "b", " ", "#", "x"));
        final List<Character> noteOrder = new ArrayList<Character>(Arrays.asList('C', 'D', 'E', 'F', 'G', 'A', 'B'));
        if (this.getNoteWithOctave().equals(note.getNoteWithOctave())) {
            return 0;
        }
        if (this.getOctave() < note.getOctave()) {
            return 1;
        }
        if (this.getOctave() > note.getOctave()) {
            return -1;
        }
        if (noteOrder.indexOf(this.getNoteName().charAt(0)) < noteOrder.indexOf(note.getNoteName().charAt(0))) {
            return 1;
        }
        if (noteOrder.indexOf(this.getNoteName().charAt(0)) > noteOrder.indexOf(note.getNoteName().charAt(0))) {
            return -1;
        }
        if (accOrder.indexOf(this.getAccidental()) < accOrder.indexOf(note.getAccidental())) {
            return 1;
        }
        if (accOrder.indexOf(this.getAccidental()) > accOrder.indexOf(note.getAccidental())) {
            return -1;
        }
        return 0;
    }
}
