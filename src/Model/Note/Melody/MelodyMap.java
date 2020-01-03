

package Model.Note.Melody;

import Model.Note.unitDuration.UnitDurationMap;
import Model.Note.NoteMap;
import Model.Note.Note;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import Model.Project.PersistentStatus;

public class MelodyMap
{
    private static PersistentStatus status;
    private static Boolean isUpdated;
    private static Map<String, Melody> melodyMap;
    private static Map<String, Melody> defaultMelodies;
    
    public static Boolean isMelodyExist(final String name) {
        if (getMelodiesMap().containsKey(name.toLowerCase())) {
            return true;
        }
        return false;
    }
    
    public static Melody getMelody(final String melodyName) {
        return MelodyMap.melodyMap.get(melodyName.toLowerCase());
    }
    
    public static void addMelody(final Melody melody) {
        MelodyMap.melodyMap.put(melody.getName().toLowerCase(), melody);
        MelodyMap.isUpdated = true;
    }
    
    public static Map<String, Melody> getMelodiesMap() {
        return MelodyMap.melodyMap;
    }
    
    public static void setMelodiesMap(final Map<String, Melody> melodies) {
        (MelodyMap.melodyMap = melodies).putAll(MelodyMap.defaultMelodies);
    }
    
    public static void resetMelodiesMap() {
        MelodyMap.melodyMap = new HashMap<String, Melody>(MelodyMap.defaultMelodies);
    }
    
    public static void clearUpdateFlag() {
        MelodyMap.isUpdated = false;
    }
    
    public static Boolean isUpdated() {
        return MelodyMap.isUpdated;
    }
    
    public static PersistentStatus getStatus() {
        return MelodyMap.status;
    }
    
    public static Map<String, Melody> getDefaultMelodies() {
        return MelodyMap.defaultMelodies;
    }
    
    public static void removeMelody(final Melody melody) {
        MelodyMap.melodyMap.remove(melody.getName().toLowerCase());
        MelodyMap.isUpdated = true;
    }
    
    private static Map<String, Melody> createSampleMelodies() {
        final Map<String, Melody> sampleMelodies = new HashMap<String, Melody>();
        final Melody nokia = new Melody("Nokia Ringtone");
        nokia.setNoteCollection(new ArrayList<NoteCollection>(Arrays.asList(setNoteCollectionNote("A5", "quaver"), setNoteCollectionNote("G5", "quaver"), setNoteCollectionNote("B4", "crotchet"), setNoteCollectionNote("C#5", "crotchet"), setNoteCollectionNote("F#5", "quaver"), setNoteCollectionNote("E5", "quaver"), setNoteCollectionNote("G4", "crotchet"), setNoteCollectionNote("A4", "crotchet"), setNoteCollectionNote("E5", "quaver"), setNoteCollectionNote("D5", "quaver"), setNoteCollectionNote("F#4", "crotchet"), setNoteCollectionNote("A4", "crotchet"), setNoteCollectionChord("D5,A4,F#4", "semibreve"))));
        sampleMelodies.put(nokia.getName().toLowerCase(), nokia);
        final Melody fifth = new Melody("Beethoven's 5th");
        fifth.setNoteCollection(new ArrayList<NoteCollection>(Arrays.asList(setNoteCollectionNote("R", "quaver"), setNoteCollectionNote("G4", "quaver"), setNoteCollectionNote("G4", "quaver"), setNoteCollectionNote("G4", "quaver"), setNoteCollectionNote("Eb4", "minim"), setNoteCollectionNote("R", "quaver"), setNoteCollectionNote("F4", "quaver"), setNoteCollectionNote("F4", "quaver"), setNoteCollectionNote("F4", "quaver"), setNoteCollectionNote("D4", "minim"), setNoteCollectionNote("R", "quaver"))));
        sampleMelodies.put(fifth.getName().toLowerCase(), fifth);
        final Melody close = new Melody("Close Encounters");
        close.setNoteCollection(new ArrayList<NoteCollection>(Arrays.asList(setNoteCollectionNote("C4", "crotchet"), setNoteCollectionNote("D4", "crotchet"), setNoteCollectionNote("C4", "crotchet"), setNoteCollectionNote("C3", "crotchet"), setNoteCollectionNote("G3", "crotchet"))));
        sampleMelodies.put(close.getName().toLowerCase(), close);
        return sampleMelodies;
    }
    
    private static NoteCollection setNoteCollectionChord(final String chordStr, final String unitDur) {
        final List<Note> chordNoteObjs = new ArrayList<Note>();
        final List<String> chordNoteStrs = new ArrayList<String>(Arrays.asList(chordStr.split(",")));
        for (final String note : chordNoteStrs) {
            chordNoteObjs.add(NoteMap.getNote(note));
        }
        final NoteCollection noteColChord = new NoteCollection();
        noteColChord.setNotes(chordNoteObjs);
        noteColChord.setPlayStyle(PlayStyle.SIMULTANEOUS);
        noteColChord.setUnitDuration(UnitDurationMap.getUnitDurationByName(unitDur));
        return noteColChord;
    }
    
    private static NoteCollection setNoteCollectionNote(final String noteStr, final String unitDur) {
        final List<Note> noteObj = new ArrayList<Note>();
        if (noteStr.toLowerCase().equals("r")) {
            noteObj.add(new Note("R", -1, -1, true));
        }
        else {
            noteObj.add(NoteMap.getNote(noteStr));
        }
        final NoteCollection noteCol = new NoteCollection();
        noteCol.setNotes(noteObj);
        noteCol.setUnitDuration(UnitDurationMap.getUnitDurationByName(unitDur));
        return noteCol;
    }
    
    static {
        MelodyMap.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                MelodyMap.clearUpdateFlag();
            }
            
            @Override
            public Boolean isUpdated() {
                return MelodyMap.isUpdated();
            }
            
            @Override
            public void resetData() {
                MelodyMap.resetMelodiesMap();
            }
        };
        MelodyMap.isUpdated = false;
        MelodyMap.melodyMap = new HashMap<String, Melody>();
        MelodyMap.defaultMelodies = new HashMap<String, Melody>();
        final Melody defaultMelody = new Melody("default melody");
        final NoteCollection noteCol = new NoteCollection();
        noteCol.setNotes(Arrays.asList(NoteMap.getNote("C4")));
        defaultMelody.setNoteCollection(Arrays.asList(noteCol));
        MelodyMap.defaultMelodies.put("default melody", defaultMelody);
        MelodyMap.defaultMelodies.putAll(createSampleMelodies());
        MelodyMap.melodyMap.putAll(MelodyMap.defaultMelodies);
    }
}
