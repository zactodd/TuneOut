

package Model.Note.Enharmonic;

import java.util.Arrays;

import Model.Note.Note;
import java.util.List;
import java.util.ArrayList;

public class EnharmonicMap
{
    private static ArrayList<EnharmonicEquivalent> allNotesWithEnharmonic;
    private static final int FULL_OCTAVE_LENGTH = 12;
    private static final List<Integer> NON_ENHARMONIC_POSS;
    private static final String DOUBLE_SHARP = "x";
    private static final String DOUBLE_FLAT = "bb";
    private static final List<Integer> START_OF_MAP;
    private static final List<Integer> END_OF_MAP;
    private static final int LAST_NOTES = 127;
    private static final List<Integer> FIRST_NOTES;
    
    public static Note getHigherEquivalentEnharmonic(final Note note) {
        if (note == null) {
            return null;
        }
        int octave = note.getOctave();
        final String noteName = note.getNoteName();
        if (note.getMidiNumber() == 127 && note.isPrimary()) {
            return null;
        }
        for (final EnharmonicEquivalent enharmonic : EnharmonicMap.allNotesWithEnharmonic) {
            if (enharmonic.getNoteName().equals(noteName)) {
                if (EnharmonicMap.END_OF_MAP.contains(EnharmonicMap.allNotesWithEnharmonic.indexOf(enharmonic))) {
                    ++octave;
                }
                return enharmonic.getHigherEnharmonic(octave);
            }
        }
        return null;
    }
    
    public static Note getLowerEquivalentEnharmonic(final Note note) {
        if (note == null) {
            return null;
        }
        int octave = note.getOctave();
        final String noteName = note.getNoteName();
        if (EnharmonicMap.FIRST_NOTES.contains(note.getMidiNumber()) && note.isPrimary()) {
            return null;
        }
        for (final EnharmonicEquivalent enharmonic : EnharmonicMap.allNotesWithEnharmonic) {
            if (enharmonic.getNoteName().equals(noteName)) {
                if (EnharmonicMap.START_OF_MAP.contains(EnharmonicMap.allNotesWithEnharmonic.indexOf(enharmonic))) {
                    --octave;
                }
                return enharmonic.getLowerEnharmonic(octave);
            }
        }
        return null;
    }
    
    public static boolean checkSimpleEnharmonic(final Note note) {
        boolean result = true;
        if (note == null) {
            result = false;
        }
        else {
            final String noteName = note.getNoteName();
            final int midiNumber = note.getMidiNumber();
            for (final int nonEnharmonicPos : EnharmonicMap.NON_ENHARMONIC_POSS) {
                if ((midiNumber - nonEnharmonicPos) % 12 == 0 || noteName.contains("x") || noteName.contains("bb")) {
                    result = false;
                }
            }
        }
        return result;
    }
    
    public static Note getEquivalentEnharmonic(final Note note) {
        if (!checkSimpleEnharmonic(note)) {
            return null;
        }
        final Note enharmonic = getHigherEquivalentEnharmonic(note);
        if (enharmonic == null || enharmonic.getNoteName().contains("bb")) {
            return getLowerEquivalentEnharmonic(note);
        }
        return enharmonic;
    }
    
    static {
        EnharmonicMap.allNotesWithEnharmonic = new ArrayList<EnharmonicEquivalent>();
        NON_ENHARMONIC_POSS = Arrays.asList(2, 7, 9);
        START_OF_MAP = Arrays.asList(0, 3, 31, 34);
        END_OF_MAP = Arrays.asList(1, 4, 30, 32);
        FIRST_NOTES = Arrays.asList(0, 1);
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("C", "B#", "Dbb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("B#", null, "C"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Dbb", "C", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("C#", "Bx", "Db"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Bx", null, "C#"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Db", "C#", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("D", "Cx", "Ebb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Cx", null, "D"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Ebb", "D", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("D#", null, "Eb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Eb", "D#", "Fbb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Fbb", "Eb", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("E", "Dx", "Fb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Dx", null, "E"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Fb", "E", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("F", "E#", "Gbb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("E#", null, "F"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Gbb", "F", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("F#", "Ex", "Gb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Ex", null, "F#"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Gb", "F#", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("G", "Fx", "Abb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Fx", null, "G"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Abb", "G", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("G#", null, "Ab"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Ab", "G#", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("A", "Gx", "Bbb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Gx", null, "A"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Bbb", "A", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("A#", null, "Bb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Bb", "A#", "Cbb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Cbb", "Bb", null));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("B", "Ax", "Cb"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Ax", null, "B"));
        EnharmonicMap.allNotesWithEnharmonic.add(new EnharmonicEquivalent("Cb", "B", null));
    }
}
