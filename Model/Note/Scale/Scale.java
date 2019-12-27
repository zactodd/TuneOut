

package Model.Note.Scale;

import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import Model.Note.Enharmonic.EnharmonicMap;
import Model.Note.NoteMap;
import Model.Note.Note;
import java.util.List;

public abstract class Scale
{
    protected List<Note> notesInScale;
    private static final char OUT_OF_NOTE_ALPHABET = 'H';
    private static final char START_OF_NOTE_ALPHABET = 'A';
    private static final char END_OF_NOTE_ALPHABET = 'G';
    private static final int OCTAVE_LENGTH = 12;
    protected List<Integer> scale;
    protected String name;
    protected String sameScaleMode;
    
    public Scale() {
        this.name = "";
        this.sameScaleMode = "";
    }
    
    public Scale(final List<Integer> scale) {
        this.name = "";
        this.sameScaleMode = "";
        this.scale = scale;
    }
    
    private boolean useEnharmonicHigh(final char currentLetter, final char expectedLetter) {
        boolean isEnharmonicHigher = false;
        if (expectedLetter == 'A' && currentLetter == 'G') {
            isEnharmonicHigher = true;
        }
        else if (expectedLetter == 'G' && currentLetter == 'A') {
            isEnharmonicHigher = false;
        }
        else if (currentLetter < expectedLetter) {
            isEnharmonicHigher = true;
        }
        return isEnharmonicHigher;
    }
    
    public String alphabetize(final String lastNote, final String currentNote) {
        char nextLetter = lastNote.charAt(0);
        ++nextLetter;
        if (nextLetter == 'H') {
            nextLetter = 'A';
        }
        final char currentLetter = currentNote.charAt(0);
        String returnNote = null;
        if (nextLetter == currentLetter) {
            returnNote = currentNote;
        }
        else if (this.useEnharmonicHigh(currentLetter, nextLetter)) {
            final Note note = EnharmonicMap.getHigherEquivalentEnharmonic(NoteMap.getNote(currentNote));
            if (note != null) {
                returnNote = note.getNoteWithOctave();
            }
        }
        else {
            final Note note = EnharmonicMap.getLowerEquivalentEnharmonic(NoteMap.getNote(currentNote));
            if (note != null) {
                returnNote = note.getNoteWithOctave();
            }
        }
        if (returnNote == null) {
            returnNote = currentNote;
        }
        return returnNote;
    }
    
    public void setNotesInScale(final String startNote) {
        this.notesInScale = new ArrayList<Note>();
        for (int i = 0; i < this.scale.size(); ++i) {
            String alphabeticalNote;
            final String currentNote = alphabeticalNote = NoteMap.getSemitone(startNote, this.scale.get(i));
            if (i != 0 && currentNote != null) {
                alphabeticalNote = this.alphabetize(this.notesInScale.get(i - 1).getNoteName(), currentNote);
            }
            this.notesInScale.add(i, NoteMap.getNote(alphabeticalNote));
        }
        this.notesInScale = this.afterProcess();
    }
    
    public Boolean isNull() {
        Boolean containsNullNote = false;
        if (this.notesInScale != null) {
            for (final Note noteInScale : this.notesInScale) {
                if (noteInScale == null) {
                    containsNullNote = true;
                }
            }
        }
        else {
            containsNullNote = true;
        }
        return containsNullNote;
    }
    
    public List<Note> getNotesInScale() {
        return this.notesInScale;
    }
    
    public List<Note> getNotesInScale(final Integer numOctaves, final Boolean removeDuplicates) {
        List<Note> notesAcrossOctaves = null;
        String octaveRoot = "";
        final String initNote = this.notesInScale.get(0).getNoteWithOctave();
        Integer previousNote = -2;
        if (numOctaves > 0) {
            notesAcrossOctaves = new ArrayList<Note>(this.notesInScale.size());
            notesAcrossOctaves.addAll(this.notesInScale);
            Collections.copy((List<? super Object>)notesAcrossOctaves, (List<?>)this.notesInScale);
            octaveRoot = notesAcrossOctaves.get(0).getNoteWithOctave();
        }
        for (Integer octaveCount = 1; octaveCount < numOctaves; ++octaveCount) {
            octaveRoot = NoteMap.getSemitone(octaveRoot, 12);
            if (octaveRoot != null) {
                this.setNotesInScale(octaveRoot);
                if (this.isNull() || !removeDuplicates) {
                    notesAcrossOctaves = null;
                    break;
                }
                for (final Note currentNote : this.notesInScale) {
                    if (!previousNote.equals(currentNote.getMidiNumber())) {
                        notesAcrossOctaves.add(currentNote);
                    }
                    previousNote = currentNote.getMidiNumber();
                }
            }
            else {
                notesAcrossOctaves = null;
            }
        }
        this.setNotesInScale(initNote);
        return notesAcrossOctaves;
    }
    
    public List<String> getScaleArrayWithOctave() {
        if (this.notesInScale != null) {
            final List<String> scale = new ArrayList<String>();
            for (final Note note : this.notesInScale) {
                scale.add(note.getNoteWithOctave());
            }
            return scale;
        }
        return null;
    }
    
    public Integer getInterval(final Integer pos) {
        return this.scale.get(pos);
    }
    
    protected List<Note> afterProcess() {
        return this.notesInScale;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSameScaleMode() {
        return this.sameScaleMode;
    }
}
