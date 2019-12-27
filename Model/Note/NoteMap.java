

package Model.Note;

import Model.Note.Enharmonic.EnharmonicMap;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class NoteMap
{
    public static ArrayList<Note> allNotes;
    public static final int MIDI_NOTE_UPPER_BOUND = 127;
    
    public static Boolean isClosePair(final Note firstNote, final Note secondNote, final Integer range) {
        final Integer midi = getMidi(firstNote.getBaseNoteWithOctave());
        final Integer midi2 = getMidi(secondNote.getBaseNoteWithOctave());
        if (Math.abs(midi - midi2) <= range) {
            return true;
        }
        return false;
    }
    
    public static List<List<Note>> findClosePairs(final List<Note> notesInChord, final Integer betweenGap) {
        final List<List<Note>> solutionPairs = new ArrayList<List<Note>>();
        for (int i = 0; i < notesInChord.size() - 1; ++i) {
            final Note firstNote = notesInChord.get(i);
            final Note secondNote = notesInChord.get(i + 1);
            if (isClosePair(firstNote, secondNote, betweenGap)) {
                if (!solutionPairs.isEmpty()) {
                    solutionPairs.add(Arrays.asList(notesInChord.get(i), notesInChord.get(i + 1)));
                }
                else {
                    solutionPairs.add(Arrays.asList(notesInChord.get(i), notesInChord.get(i + 1)));
                }
            }
        }
        return solutionPairs;
    }
    
    public static String getNoteString(final int midi) {
        for (final Note note : NoteMap.allNotes) {
            if (note.getMidiNumber() == midi && note.isPrimary()) {
                return note.getNoteName() + note.getOctave();
            }
        }
        return null;
    }
    
    public static Note getNoteFromMidi(final int midi) {
        for (final Note note : NoteMap.allNotes) {
            if (note.getMidiNumber() == midi && note.isPrimary()) {
                return note;
            }
        }
        return null;
    }
    
    public static Note getNote(final String noteWithOctave) {
        for (final Note note : NoteMap.allNotes) {
            if (note.getNoteWithOctave().equalsIgnoreCase(noteWithOctave)) {
                return note;
            }
        }
        return null;
    }
    
    public static Integer getMidi(final String noteWithOctave) {
        final int SILENCE_MIDI = -1;
        final String SILENCE_NOTE = " ";
        for (final Note note : NoteMap.allNotes) {
            if (noteWithOctave.equals(" ")) {
                return -1;
            }
            if (note.getNoteWithOctave().equalsIgnoreCase(noteWithOctave)) {
                return note.getMidiNumber();
            }
        }
        return null;
    }
    
    public static String getSemitone(final String noteWithOctave, final int semiToneStep) {
        if (semiToneStep != 0) {
            final int midiNumber = getMidi(noteWithOctave) + semiToneStep;
            final String adjustedNote = getNoteString(midiNumber);
            return adjustedNote;
        }
        return noteWithOctave;
    }
    
    public static Note getSemitone(final Note note, final int semiToneStep) {
        return getNoteFromMidi(note.getMidiNumber() + semiToneStep);
    }
    
    public static List<Integer> getMidiArray(final List<String> notes) {
        final int length = notes.size();
        final AbstractList<Integer> midiArray = new ArrayList<Integer>(length);
        for (int index = 0; index < length; ++index) {
            midiArray.add(index, getMidi(notes.get(index)));
        }
        return midiArray;
    }
    
    public static Boolean compareEnharmonicNotes(final Note note1, final Note note2) {
        final List<Note> list1 = new ArrayList<Note>();
        final List<Note> list2 = new ArrayList<Note>();
        list1.add(note1);
        list2.add(note2);
        if (EnharmonicMap.getLowerEquivalentEnharmonic(note1) != null) {
            list1.add(EnharmonicMap.getLowerEquivalentEnharmonic(note1));
        }
        if (EnharmonicMap.getHigherEquivalentEnharmonic(note1) != null) {
            list1.add(EnharmonicMap.getHigherEquivalentEnharmonic(note1));
        }
        if (EnharmonicMap.getLowerEquivalentEnharmonic(note2) != null) {
            list2.add(EnharmonicMap.getLowerEquivalentEnharmonic(note2));
        }
        if (EnharmonicMap.getHigherEquivalentEnharmonic(note2) != null) {
            list2.add(EnharmonicMap.getHigherEquivalentEnharmonic(note2));
        }
        Boolean equal = false;
        for (final Note n1 : list1) {
            for (final Note n2 : list2) {
                if (n1.getNoteName().equals(n2.getNoteName())) {
                    equal = true;
                }
            }
        }
        return equal;
    }
    
    static {
        NoteMap.allNotes = new ArrayList<Note>();
        int initialMidi = 0;
        int octave;
        final int initialOctave = octave = -1;
        while (initialMidi <= 127) {
            NoteMap.allNotes.add(new Note("C", octave, initialMidi, true));
            if (octave > -1) {
                NoteMap.allNotes.add(new Note("B#", octave - 1, initialMidi, false));
            }
            NoteMap.allNotes.add(new Note("Dbb", octave, initialMidi++, false));
            NoteMap.allNotes.add(new Note("C#", octave, initialMidi, true));
            if (octave > -1) {
                NoteMap.allNotes.add(new Note("Bx", octave - 1, initialMidi, false));
            }
            NoteMap.allNotes.add(new Note("Db", octave, initialMidi++, false));
            NoteMap.allNotes.add(new Note("D", octave, initialMidi, true));
            NoteMap.allNotes.add(new Note("Cx", octave, initialMidi, false));
            NoteMap.allNotes.add(new Note("Ebb", octave, initialMidi++, false));
            NoteMap.allNotes.add(new Note("D#", octave, initialMidi, true));
            NoteMap.allNotes.add(new Note("Eb", octave, initialMidi, false));
            NoteMap.allNotes.add(new Note("Fbb", octave, initialMidi++, false));
            NoteMap.allNotes.add(new Note("E", octave, initialMidi, true));
            NoteMap.allNotes.add(new Note("Dx", octave, initialMidi, false));
            NoteMap.allNotes.add(new Note("Fb", octave, initialMidi++, false));
            NoteMap.allNotes.add(new Note("F", octave, initialMidi, true));
            NoteMap.allNotes.add(new Note("E#", octave, initialMidi, false));
            NoteMap.allNotes.add(new Note("Gbb", octave, initialMidi++, false));
            NoteMap.allNotes.add(new Note("F#", octave, initialMidi, true));
            NoteMap.allNotes.add(new Note("Ex", octave, initialMidi, false));
            NoteMap.allNotes.add(new Note("Gb", octave, initialMidi++, false));
            NoteMap.allNotes.add(new Note("G", octave, initialMidi, true));
            NoteMap.allNotes.add(new Note("Fx", octave, initialMidi, false));
            if (octave <= 8) {
                NoteMap.allNotes.add(new Note("Abb", octave, initialMidi, false));
            }
            if (++initialMidi <= 127) {
                NoteMap.allNotes.add(new Note("G#", octave, initialMidi, true));
                NoteMap.allNotes.add(new Note("Ab", octave, initialMidi++, false));
                NoteMap.allNotes.add(new Note("A", octave, initialMidi, true));
                NoteMap.allNotes.add(new Note("Gx", octave, initialMidi, false));
                NoteMap.allNotes.add(new Note("Bbb", octave, initialMidi++, false));
                NoteMap.allNotes.add(new Note("A#", octave, initialMidi, true));
                NoteMap.allNotes.add(new Note("Bb", octave, initialMidi, false));
                NoteMap.allNotes.add(new Note("Cbb", octave + 1, initialMidi++, false));
                NoteMap.allNotes.add(new Note("B", octave, initialMidi, true));
                NoteMap.allNotes.add(new Note("Ax", octave, initialMidi, false));
                NoteMap.allNotes.add(new Note("Cb", octave + 1, initialMidi++, false));
            }
            ++octave;
        }
    }
}
