

package Model.Note.Chord;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import Model.Note.Scale.Scale;
import Model.Note.Enharmonic.EnharmonicMap;
import Model.Note.NoteMap;
import Model.Note.Scale.ScaleMap;
import java.util.ArrayList;
import Model.Note.Note;
import java.util.List;
import java.util.Collection;
import java.util.AbstractMap;

public class ChordMap
{
    private static AbstractMap<String, Chord> chordMap;
    private static Collection<String> chordTriads;
    private static final Integer OCTAVE_SIZE;
    private static AbstractMap<String, String> functionMap;
    private static List<String> allFunctions;
    private static List<String> primaryNameForTriads;
    private static List<String> primaryNameForTetrads;
    
    public static boolean isValidChordName(final String chordName) {
        return ChordMap.chordMap.containsKey(chordName);
    }
    
    public static List<Note> notesInChord(final Note note, final String chordName) {
        final Chord chord = ChordMap.chordMap.get(chordName);
        final List<Note> chordNotes = new ArrayList<Note>();
        for (final ChordStep step : chord.getChordSteps()) {
            final Scale scale = ScaleMap.getScale(step.getScale());
            scale.setNotesInScale(note.getNoteWithOctave());
            final List<Note> notes = scale.getNotesInScale();
            Note noteInChord = notes.get(step.getNoteNumber());
            switch (step.getStepType()) {
                case FLAT: {
                    noteInChord = NoteMap.getSemitone(noteInChord, -1);
                    final Note equivalentFlatNote = EnharmonicMap.getEquivalentEnharmonic(noteInChord);
                    if (equivalentFlatNote != null && equivalentFlatNote.getNoteName().contains("b")) {
                        noteInChord = EnharmonicMap.getEquivalentEnharmonic(noteInChord);
                        break;
                    }
                    break;
                }
                case SHARP: {
                    noteInChord = NoteMap.getSemitone(noteInChord, 1);
                    final Note equivalentSharpNote = EnharmonicMap.getEquivalentEnharmonic(noteInChord);
                    if (equivalentSharpNote != null && equivalentSharpNote.getNoteName().contains("#")) {
                        noteInChord = EnharmonicMap.getEquivalentEnharmonic(noteInChord);
                        break;
                    }
                    break;
                }
            }
            chordNotes.add(noteInChord);
        }
        return chordNotes;
    }
    
    public static Collection<Integer> notesInChordMidi(final Note note, final String chordName) {
        final Collection<Integer> chordNotesMidi = new ArrayList<Integer>();
        final Collection<Note> chordNotes = notesInChord(note, chordName);
        chordNotesMidi.addAll(chordNotes.stream().map((Function<? super Note, ?>)Note::getMidiNumber).collect((Collector<? super Object, ?, Collection<? extends Integer>>)Collectors.toList()));
        return chordNotesMidi;
    }
    
    public static List<Note> invertChord(final List<Note> chord, final Integer invertBy) {
        final Integer notesInChord = chord.size();
        final List<Note> invertedChord = new ArrayList<Note>();
        for (Integer i = 0; i < notesInChord; ++i) {
            invertedChord.add(null);
        }
        for (Integer index = 0; index < notesInChord; ++index) {
            Integer newIndex = (index - invertBy) % notesInChord;
            newIndex = ((newIndex < 0) ? (newIndex + notesInChord) : newIndex);
            invertedChord.set(newIndex, chord.get(index));
        }
        for (Integer j = 1; j < notesInChord; ++j) {
            final Note note = invertedChord.get(j);
            if (notesInChord - invertBy % notesInChord <= j) {
                invertedChord.set(j, NoteMap.getNote(note.getNoteName() + (note.getOctave() + 1)));
            }
        }
        return invertedChord;
    }
    
    public static Collection<String> getChordTypes() {
        return ChordMap.chordMap.entrySet().stream().filter(entry -> entry.getValue().isPrimaryName()).map((Function<? super Object, ?>)Map.Entry::getKey).collect((Collector<? super Object, ?, Collection<String>>)Collectors.toCollection((Supplier<R>)ArrayList::new));
    }
    
    public static Boolean checkChordTypeExists(final String chordName, final Integer noOfNotesInChord) {
        if (noOfNotesInChord == 3) {
            for (final String chord : ChordMap.primaryNameForTriads) {
                if (chordName.equalsIgnoreCase(chord)) {
                    return true;
                }
            }
        }
        if (noOfNotesInChord == 4) {
            for (final String chord : ChordMap.primaryNameForTetrads) {
                if (chordName.equalsIgnoreCase(chord)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static Collection<String> getAllChordTypes(final Integer noOfNotesInChord) {
        final Collection<String> resultChordNames = new ArrayList<String>();
        if (noOfNotesInChord == 3) {
            for (final String chord : ChordMap.primaryNameForTriads) {
                resultChordNames.add(chord);
            }
        }
        if (noOfNotesInChord == 4) {
            for (final String chord : ChordMap.primaryNameForTetrads) {
                resultChordNames.add(chord);
            }
        }
        return resultChordNames;
    }
    
    public static List<String> findChordsFromNotes(final List<Note> notes, final Boolean inverted) {
        final List<String> foundChords = new ArrayList<String>();
        for (final Note note : notes) {
            String noteName = "";
            if (note != null) {
                noteName = note.getNoteName();
            }
            for (final String chordType : getChordTypes()) {
                try {
                    final List<Note> chordNotes = notesInChord(note, chordType);
                    if (inverted) {
                        if (compareChordsConsideringInversions(chordNotes, notes)) {
                            foundChords.add(String.format("%s %s (no inversion)", noteName, chordType));
                        }
                        if (compareChordsConsideringInversions(invertChord(chordNotes, 1), notes)) {
                            foundChords.add(String.format("%s %s (1st inversion)", noteName, chordType));
                        }
                        if (compareChordsConsideringInversions(invertChord(chordNotes, 2), notes)) {
                            foundChords.add(String.format("%s %s (2nd inversion)", noteName, chordType));
                        }
                        if (notes.size() != 4 || !compareChordsConsideringInversions(invertChord(chordNotes, 3), notes)) {
                            continue;
                        }
                        foundChords.add(String.format("%s %s (3rd inversion)", noteName, chordType));
                    }
                    else {
                        if (!compareChordsAnyOrder(chordNotes, notes)) {
                            continue;
                        }
                        foundChords.add(String.format("%s %s", noteName, chordType));
                    }
                }
                catch (NullPointerException ex) {}
            }
        }
        return foundChords;
    }
    
    private static Boolean compareChordsConsideringInversions(final List<Note> chordNotes, final List<Note> notesToFind) {
        Boolean returnBool = false;
        if (notesToFind.size() == chordNotes.size()) {
            Boolean foundAllNotes = true;
            for (int x = 0; x < notesToFind.size(); ++x) {
                if (!NoteMap.compareEnharmonicNotes(notesToFind.get(x), chordNotes.get(x))) {
                    foundAllNotes = false;
                }
            }
            returnBool = foundAllNotes;
        }
        return returnBool;
    }
    
    private static Boolean compareChordsAnyOrder(final List<Note> chordNotes, final List<Note> notesToFind) {
        Boolean returnBool = false;
        if (notesToFind.size() == chordNotes.size()) {
            final List<Boolean> foundNotes = new ArrayList<Boolean>();
            for (int x = 0; x < notesToFind.size(); ++x) {
                foundNotes.add(false);
                for (final Note chordNote : chordNotes) {
                    if (NoteMap.compareEnharmonicNotes(notesToFind.get(x), chordNote)) {
                        foundNotes.set(x, true);
                    }
                }
            }
            if (!foundNotes.contains(false)) {
                returnBool = true;
            }
        }
        return returnBool;
    }
    
    public static String qualityName(final String function) {
        if (ChordMap.functionMap.containsKey(function)) {
            return ChordMap.functionMap.get(function);
        }
        return "";
    }
    
    public static Collection<String> getAllFunctionNames() {
        return ChordMap.functionMap.keySet();
    }
    
    public static Collection<String> getUniqueQualityNames() {
        return new HashSet<String>(ChordMap.functionMap.values());
    }
    
    public static String chordFunction(final String function, final String keyNote, final String key) {
        final Scale theScale = ScaleMap.getScale(key);
        String scaleNoteWithOctave;
        if (keyNote.matches(".*\\d+.*")) {
            scaleNoteWithOctave = keyNote;
        }
        else {
            scaleNoteWithOctave = keyNote + "4";
        }
        theScale.setNotesInScale(scaleNoteWithOctave);
        final List<String> notesInScale = theScale.getScaleArrayWithOctave();
        if (ChordMap.allFunctions.contains(function)) {
            final List<String> allFunctions = new ArrayList<String>(ChordMap.functionMap.keySet());
            final String note = notesInScale.get(allFunctions.indexOf(function));
            return note.replaceAll("\\d", "") + " " + ChordMap.functionMap.get(function);
        }
        return null;
    }
    
    public static String functionOf(final String chordNote, final String chord, final String keyNote, final String key) {
        final Scale theScale = ScaleMap.getScale(key);
        String scaleNoteWithOctave;
        if (keyNote.matches(".*\\d+.*")) {
            scaleNoteWithOctave = keyNote;
        }
        else {
            scaleNoteWithOctave = keyNote + "4";
        }
        theScale.setNotesInScale(scaleNoteWithOctave);
        final List<String> notesInScale = theScale.getScaleArrayWithOctave();
        final List<String> allFunctions = new ArrayList<String>(ChordMap.functionMap.keySet());
        for (int index = 0; index < notesInScale.size() - 1; ++index) {
            final String note = notesInScale.get(index).replaceAll("\\d", "");
            if (chordNote.equals(note)) {
                final String function = allFunctions.get(index);
                if (ChordMap.functionMap.get(function).equals(chord)) {
                    return allFunctions.get(index);
                }
            }
        }
        return null;
    }
    
    static {
        ChordMap.chordMap = new HashMap<String, Chord>();
        ChordMap.chordTriads = new HashSet<String>();
        OCTAVE_SIZE = 12;
        (ChordMap.functionMap = new LinkedHashMap<String, String>()).put("I", "major 7th");
        ChordMap.functionMap.put("II", "minor 7th");
        ChordMap.functionMap.put("III", "minor 7th");
        ChordMap.functionMap.put("IV", "major 7th");
        ChordMap.functionMap.put("V", "7th");
        ChordMap.functionMap.put("VI", "minor 7th");
        ChordMap.functionMap.put("VII", "half diminished 7th");
        ChordMap.allFunctions = new ArrayList<String>(ChordMap.functionMap.keySet());
        ChordMap.primaryNameForTriads = new ArrayList<String>();
        ChordMap.primaryNameForTetrads = new ArrayList<String>();
        ChordMap.primaryNameForTriads.add("major");
        final Map<String, Boolean> majorTriadNames = new HashMap<String, Boolean>();
        majorTriadNames.put("major", true);
        majorTriadNames.put("major triad", false);
        majorTriadNames.put("maj", false);
        final Collection<ChordStep> majorTriadSteps = new ArrayList<ChordStep>();
        majorTriadSteps.add(new ChordStep("major", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        majorTriadSteps.add(new ChordStep("major", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        majorTriadSteps.add(new ChordStep("major", 4, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry : majorTriadNames.entrySet()) {
            ChordMap.chordMap.put(entry.getKey(), new Chord(new ArrayList<String>(majorTriadNames.keySet()), majorTriadSteps, entry.getValue()));
        }
        ChordMap.primaryNameForTriads.add("minor");
        final Map<String, Boolean> minorTriadNames = new HashMap<String, Boolean>();
        minorTriadNames.put("minor", true);
        minorTriadNames.put("minor triad", false);
        minorTriadNames.put("mn", false);
        final Collection<ChordStep> minorTriadSteps = new ArrayList<ChordStep>();
        minorTriadSteps.add(new ChordStep("minor", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        minorTriadSteps.add(new ChordStep("minor", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        minorTriadSteps.add(new ChordStep("minor", 4, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry2 : minorTriadNames.entrySet()) {
            ChordMap.chordMap.put(entry2.getKey(), new Chord(new ArrayList<String>(minorTriadNames.keySet()), minorTriadSteps, entry2.getValue()));
        }
        ChordMap.primaryNameForTriads.add("diminished");
        final Map<String, Boolean> diminishedTriadNames = new HashMap<String, Boolean>();
        diminishedTriadNames.put("diminished", true);
        diminishedTriadNames.put("diminished triad", false);
        diminishedTriadNames.put("dim", false);
        final Collection<ChordStep> diminishedTriadSteps = new ArrayList<ChordStep>();
        diminishedTriadSteps.add(new ChordStep("minor", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        diminishedTriadSteps.add(new ChordStep("minor", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        diminishedTriadSteps.add(new ChordStep("minor", 4, ChordStep.CHORD_STEP_TYPE.FLAT));
        for (final Map.Entry<String, Boolean> entry3 : diminishedTriadNames.entrySet()) {
            ChordMap.chordMap.put(entry3.getKey(), new Chord(new ArrayList<String>(diminishedTriadNames.keySet()), diminishedTriadSteps, entry3.getValue()));
        }
        ChordMap.primaryNameForTriads.add("augmented");
        final Map<String, Boolean> augmentedTriadNames = new HashMap<String, Boolean>();
        augmentedTriadNames.put("augmented", true);
        augmentedTriadNames.put("augmented triad", false);
        augmentedTriadNames.put("aug", false);
        final Collection<ChordStep> augmentedTriadSteps = new ArrayList<ChordStep>();
        augmentedTriadSteps.add(new ChordStep("major", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        augmentedTriadSteps.add(new ChordStep("major", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        augmentedTriadSteps.add(new ChordStep("major", 4, ChordStep.CHORD_STEP_TYPE.SHARP));
        for (final Map.Entry<String, Boolean> entry4 : augmentedTriadNames.entrySet()) {
            ChordMap.chordMap.put(entry4.getKey(), new Chord(new ArrayList<String>(augmentedTriadNames.keySet()), augmentedTriadSteps, entry4.getValue()));
        }
        ChordMap.primaryNameForTetrads.add("major 6th");
        ChordMap.primaryNameForTetrads.add("major 7th");
        ChordMap.primaryNameForTetrads.add("minor 6th");
        ChordMap.primaryNameForTetrads.add("minor 7th");
        ChordMap.primaryNameForTetrads.add("diminished 7th");
        ChordMap.primaryNameForTetrads.add("half diminished 7th");
        ChordMap.primaryNameForTetrads.add("7th");
        final Map<String, Boolean> majorTetradNames = new HashMap<String, Boolean>();
        majorTetradNames.put("major 7th", true);
        majorTetradNames.put("major seventh", false);
        majorTetradNames.put("maj7", false);
        final Collection<ChordStep> majorTetradSteps = new ArrayList<ChordStep>();
        majorTetradSteps.add(new ChordStep("major", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        majorTetradSteps.add(new ChordStep("major", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        majorTetradSteps.add(new ChordStep("major", 4, ChordStep.CHORD_STEP_TYPE.EXACT));
        majorTetradSteps.add(new ChordStep("major", 6, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry5 : majorTetradNames.entrySet()) {
            ChordMap.chordMap.put(entry5.getKey(), new Chord(new ArrayList<String>(majorTetradNames.keySet()), majorTetradSteps, entry5.getValue()));
        }
        final Map<String, Boolean> minorTetradNames = new HashMap<String, Boolean>();
        minorTetradNames.put("minor 7th", true);
        minorTetradNames.put("minor seventh", false);
        minorTetradNames.put("mn7", false);
        final Collection<ChordStep> minorTetradSteps = new ArrayList<ChordStep>();
        minorTetradSteps.add(new ChordStep("minor", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        minorTetradSteps.add(new ChordStep("minor", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        minorTetradSteps.add(new ChordStep("minor", 4, ChordStep.CHORD_STEP_TYPE.EXACT));
        minorTetradSteps.add(new ChordStep("minor", 6, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry6 : minorTetradNames.entrySet()) {
            ChordMap.chordMap.put(entry6.getKey(), new Chord(new ArrayList<String>(minorTetradNames.keySet()), minorTetradSteps, entry6.getValue()));
        }
        final Map<String, Boolean> seventhTetradNames = new HashMap<String, Boolean>();
        seventhTetradNames.put("7th", true);
        seventhTetradNames.put("seventh", false);
        final Collection<ChordStep> seventhTetradSteps = new ArrayList<ChordStep>();
        seventhTetradSteps.add(new ChordStep("major", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        seventhTetradSteps.add(new ChordStep("major", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        seventhTetradSteps.add(new ChordStep("major", 4, ChordStep.CHORD_STEP_TYPE.EXACT));
        seventhTetradSteps.add(new ChordStep("minor", 6, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry7 : seventhTetradNames.entrySet()) {
            ChordMap.chordMap.put(entry7.getKey(), new Chord(new ArrayList<String>(seventhTetradNames.keySet()), seventhTetradSteps, entry7.getValue()));
        }
        final Map<String, Boolean> halfDiminishedTetradNames = new HashMap<String, Boolean>();
        halfDiminishedTetradNames.put("half diminished 7th", true);
        halfDiminishedTetradNames.put("half diminished seventh", false);
        halfDiminishedTetradNames.put("hd7", false);
        final Collection<ChordStep> halfDiminishedTetradSteps = new ArrayList<ChordStep>();
        halfDiminishedTetradSteps.add(new ChordStep("minor", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        halfDiminishedTetradSteps.add(new ChordStep("minor", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        halfDiminishedTetradSteps.add(new ChordStep("minor", 4, ChordStep.CHORD_STEP_TYPE.FLAT));
        halfDiminishedTetradSteps.add(new ChordStep("minor", 6, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry8 : halfDiminishedTetradNames.entrySet()) {
            ChordMap.chordMap.put(entry8.getKey(), new Chord(new ArrayList<String>(halfDiminishedTetradNames.keySet()), halfDiminishedTetradSteps, entry8.getValue()));
        }
        final Map<String, Boolean> major6thTetradNames = new HashMap<String, Boolean>();
        major6thTetradNames.put("major 6th", true);
        major6thTetradNames.put("major sixth", false);
        major6thTetradNames.put("maj6", false);
        final Collection<ChordStep> major6thTetradSteps = new ArrayList<ChordStep>();
        major6thTetradSteps.add(new ChordStep("major", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        major6thTetradSteps.add(new ChordStep("major", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        major6thTetradSteps.add(new ChordStep("major", 4, ChordStep.CHORD_STEP_TYPE.EXACT));
        major6thTetradSteps.add(new ChordStep("major", 5, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry9 : major6thTetradNames.entrySet()) {
            ChordMap.chordMap.put(entry9.getKey(), new Chord(new ArrayList<String>(major6thTetradNames.keySet()), major6thTetradSteps, entry9.getValue()));
        }
        final Map<String, Boolean> minor6thTetradNames = new HashMap<String, Boolean>();
        minor6thTetradNames.put("minor 6th", true);
        minor6thTetradNames.put("minor sixth", false);
        minor6thTetradNames.put("mn6", false);
        final Collection<ChordStep> minor6thTetradSteps = new ArrayList<ChordStep>();
        minor6thTetradSteps.add(new ChordStep("minor", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        minor6thTetradSteps.add(new ChordStep("minor", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        minor6thTetradSteps.add(new ChordStep("minor", 4, ChordStep.CHORD_STEP_TYPE.EXACT));
        minor6thTetradSteps.add(new ChordStep("major", 5, ChordStep.CHORD_STEP_TYPE.EXACT));
        for (final Map.Entry<String, Boolean> entry10 : minor6thTetradNames.entrySet()) {
            ChordMap.chordMap.put(entry10.getKey(), new Chord(new ArrayList<String>(minor6thTetradNames.keySet()), minor6thTetradSteps, entry10.getValue()));
        }
        final Map<String, Boolean> diminishedTetradNames = new HashMap<String, Boolean>();
        diminishedTetradNames.put("diminished 7th", true);
        diminishedTetradNames.put("diminished seventh", false);
        diminishedTetradNames.put("dm7", false);
        final Collection<ChordStep> diminishedTetradSteps = new ArrayList<ChordStep>();
        diminishedTetradSteps.add(new ChordStep("minor", 0, ChordStep.CHORD_STEP_TYPE.EXACT));
        diminishedTetradSteps.add(new ChordStep("minor", 2, ChordStep.CHORD_STEP_TYPE.EXACT));
        diminishedTetradSteps.add(new ChordStep("minor", 4, ChordStep.CHORD_STEP_TYPE.FLAT));
        diminishedTetradSteps.add(new ChordStep("minor", 6, ChordStep.CHORD_STEP_TYPE.FLAT));
        for (final Map.Entry<String, Boolean> entry11 : diminishedTetradNames.entrySet()) {
            ChordMap.chordMap.put(entry11.getKey(), new Chord(new ArrayList<String>(diminishedTetradNames.keySet()), diminishedTetradSteps, entry11.getValue()));
        }
    }
}
