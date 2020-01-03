

package Model.Note.Scale;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KeySignature
{
    private static Map<Integer, List<String>> keySignatureSharpMap;
    private static Map<Integer, List<String>> keySignatureFlatMap;
    private static Map<String, Integer> rootNotesMajorSharpMap;
    private static Map<String, Integer> rootNotesMinorSharpMap;
    private static Map<String, Integer> rootNotesMajorFlatMap;
    private static Map<String, Integer> rootNotesMinorFlatMap;
    private static Map<String, String> MAJOR_MINOR_EQUIVALENT;
    private static final List<String> ROOT_MAJOR_SHARP_ORDER;
    private static final List<String> ROOT_MINOR_SHARP_ORDER;
    private static final List<String> ROOT_MAJOR_FLAT_ORDER;
    private static final List<String> ROOT_MINOR_FLAT_ORDER;
    private static final List<String> SHARP_ORDER;
    private static final List<String> FLAT_ORDER;
    private static final Integer KEY_SIGNATURES_SIZE;
    private static final String SHARP = "#";
    private static final String FLAT = "b";
    private static final String NO_KEY_SIG = "C major";
    private static final String MAJOR = "major";
    private static final String MINOR = "minor";
    
    public static Boolean isValidKeyName(final String keyName) {
        return keyName.equalsIgnoreCase("major") || keyName.equalsIgnoreCase("minor");
    }
    
    public static List<String> getKeySignature(String note, final String scale) {
        final List<String> keysig = new ArrayList<String>();
        note = note.toLowerCase();
        if (scale.equals("minor")) {
            if (KeySignature.ROOT_MINOR_SHARP_ORDER.contains(note)) {
                return capitalizeKeys(KeySignature.keySignatureSharpMap.get(KeySignature.rootNotesMinorSharpMap.get(note)));
            }
            if (KeySignature.ROOT_MINOR_FLAT_ORDER.contains(note)) {
                return capitalizeKeys(KeySignature.keySignatureFlatMap.get(KeySignature.rootNotesMinorFlatMap.get(note)));
            }
        }
        if (scale.equals("major")) {
            if (KeySignature.ROOT_MAJOR_SHARP_ORDER.contains(note)) {
                return capitalizeKeys(KeySignature.keySignatureSharpMap.get(KeySignature.rootNotesMajorSharpMap.get(note)));
            }
            if (KeySignature.ROOT_MAJOR_FLAT_ORDER.contains(note)) {
                return capitalizeKeys(KeySignature.keySignatureFlatMap.get(KeySignature.rootNotesMajorFlatMap.get(note)));
            }
        }
        return null;
    }
    
    public static String getNumberOfmModifier(final List<String> keySig) {
        if (keySig.size() == 0) {
            return "0#/b";
        }
        if (keySig.get(0).contains("#")) {
            return keySig.size() + "#";
        }
        return keySig.size() + "b";
    }
    
    public static String getKeySignatureFromModifierMajor(final String modifier) {
        final int pos = Integer.parseInt(modifier.substring(0, 1));
        String rootNote;
        if (modifier.contains("#")) {
            rootNote = KeySignature.ROOT_MAJOR_SHARP_ORDER.get(pos);
        }
        else {
            rootNote = KeySignature.ROOT_MAJOR_FLAT_ORDER.get(pos);
        }
        return rootNote.substring(0, 1).toUpperCase() + rootNote.substring(1) + " " + "major";
    }
    
    public static String getKeySignatureFromModifierMinor(final String modifier) {
        final int pos = Integer.parseInt(modifier.substring(0, 1));
        String rootNote;
        if (modifier.contains("#")) {
            rootNote = KeySignature.ROOT_MINOR_SHARP_ORDER.get(pos);
        }
        else {
            rootNote = KeySignature.ROOT_MINOR_FLAT_ORDER.get(pos);
        }
        return rootNote.substring(0, 1).toUpperCase() + rootNote.substring(1) + " " + "minor";
    }
    
    public static String getKeySignatureFromNoteList(final List<String> noteList) {
        for (final String note : noteList) {
            note.toLowerCase();
        }
        if (noteList.get(0).contains("#")) {
            return getKeyForSharps(noteList);
        }
        if (noteList.get(0).contains("b")) {
            return getKeyForFlats(noteList);
        }
        return null;
    }
    
    private static String getKeyForSharps(final List<String> noteList) {
        String keysig = "";
        for (final Map.Entry<Integer, List<String>> entry : KeySignature.keySignatureSharpMap.entrySet()) {
            final Integer key = entry.getKey();
            final List<String> noteArray = entry.getValue();
            if (noteList.equals(noteArray)) {
                for (final Map.Entry<String, Integer> entry2 : KeySignature.rootNotesMajorSharpMap.entrySet()) {
                    if (entry2.getValue().equals(key)) {
                        keysig = entry2.getKey();
                    }
                }
            }
        }
        if (keysig.equals("")) {
            return null;
        }
        return keysig.substring(0, 1).toUpperCase() + keysig.substring(1) + " " + "major";
    }
    
    private static String getKeyForFlats(final List<String> noteList) {
        String keysig = "";
        for (final Map.Entry<Integer, List<String>> entry : KeySignature.keySignatureFlatMap.entrySet()) {
            final Integer key = entry.getKey();
            final List<String> noteArray = entry.getValue();
            if (noteList.equals(noteArray)) {
                for (final Map.Entry<String, Integer> entry2 : KeySignature.rootNotesMajorFlatMap.entrySet()) {
                    if (entry2.getValue().equals(key)) {
                        keysig = entry2.getKey();
                    }
                }
            }
        }
        if (keysig.equals("")) {
            return null;
        }
        return keysig.substring(0, 1).toUpperCase() + keysig.substring(1) + " " + "major";
    }
    
    public static String getEquivalentMinor(final String majorScale) {
        return KeySignature.MAJOR_MINOR_EQUIVALENT.get(majorScale);
    }
    
    public static String getKeySignatureWithNoSharpsorFlats() {
        return "C major";
    }
    
    public static boolean isInMajKeySigMap(String note) {
        note = note.toLowerCase();
        return KeySignature.ROOT_MAJOR_SHARP_ORDER.contains(note) || KeySignature.ROOT_MAJOR_FLAT_ORDER.contains(note);
    }
    
    public static boolean isInMinKeySigMap(String note) {
        note = note.toLowerCase();
        return KeySignature.ROOT_MINOR_SHARP_ORDER.contains(note) || KeySignature.ROOT_MINOR_FLAT_ORDER.contains(note);
    }
    
    public static List<String> getListOfScaleNames(final String delimeter) {
        final List<String> keySignatureList = new ArrayList<String>();
        if (delimeter.equals("-M")) {
            for (final String keySig : KeySignature.ROOT_MAJOR_FLAT_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " major");
            }
            for (final String keySig : KeySignature.ROOT_MAJOR_SHARP_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " major");
            }
        }
        else if (delimeter.equals("-m")) {
            for (final String keySig : KeySignature.ROOT_MINOR_FLAT_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " minor");
            }
            for (final String keySig : KeySignature.ROOT_MINOR_SHARP_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " minor");
            }
        }
        else if (delimeter.equals("-b")) {
            for (final String keySig : KeySignature.ROOT_MAJOR_FLAT_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " major");
            }
            for (final String keySig : KeySignature.ROOT_MAJOR_SHARP_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " major");
            }
            for (final String keySig : KeySignature.ROOT_MINOR_FLAT_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " minor");
            }
            for (final String keySig : KeySignature.ROOT_MINOR_SHARP_ORDER) {
                keySignatureList.add(keySig.substring(0, 1).toUpperCase() + keySig.substring(1) + " minor");
            }
        }
        return keySignatureList;
    }
    
    private static List<String> capitalizeKeys(final List<String> keySig) {
        final List<String> finalKeySig = new ArrayList<String>();
        for (final String key : keySig) {
            final String newKey = key.substring(0, 1).toUpperCase() + key.substring(1);
            finalKeySig.add(newKey);
        }
        return finalKeySig;
    }
    
    static {
        KeySignature.keySignatureSharpMap = new HashMap<Integer, List<String>>();
        KeySignature.keySignatureFlatMap = new HashMap<Integer, List<String>>();
        KeySignature.rootNotesMajorSharpMap = new HashMap<String, Integer>();
        KeySignature.rootNotesMinorSharpMap = new HashMap<String, Integer>();
        KeySignature.rootNotesMajorFlatMap = new HashMap<String, Integer>();
        KeySignature.rootNotesMinorFlatMap = new HashMap<String, Integer>();
        KeySignature.MAJOR_MINOR_EQUIVALENT = new HashMap<String, String>();
        ROOT_MAJOR_SHARP_ORDER = Arrays.asList("c", "g", "d", "a", "e", "b", "f#", "c#");
        ROOT_MINOR_SHARP_ORDER = Arrays.asList("a", "e", "b", "f#", "c#", "g#", "d#", "a#");
        ROOT_MAJOR_FLAT_ORDER = Arrays.asList("c", "f", "bb", "eb", "ab", "db", "gb", "cb");
        ROOT_MINOR_FLAT_ORDER = Arrays.asList("a", "d", "g", "c", "f", "bb", "eb", "ab");
        SHARP_ORDER = Arrays.asList("f#", "c#", "g#", "d#", "a#", "e#", "b#");
        FLAT_ORDER = Arrays.asList("bb", "eb", "ab", "db", "gb", "cb", "fb");
        KEY_SIGNATURES_SIZE = KeySignature.FLAT_ORDER.size();
        for (int i = 0; i <= KeySignature.KEY_SIGNATURES_SIZE; ++i) {
            KeySignature.keySignatureFlatMap.put(i, KeySignature.FLAT_ORDER.subList(0, i));
            KeySignature.keySignatureSharpMap.put(i, KeySignature.SHARP_ORDER.subList(0, i));
            KeySignature.rootNotesMajorSharpMap.put(KeySignature.ROOT_MAJOR_SHARP_ORDER.get(i), i);
            KeySignature.rootNotesMinorSharpMap.put(KeySignature.ROOT_MINOR_SHARP_ORDER.get(i), i);
            KeySignature.rootNotesMajorFlatMap.put(KeySignature.ROOT_MAJOR_FLAT_ORDER.get(i), i);
            KeySignature.rootNotesMinorFlatMap.put(KeySignature.ROOT_MINOR_FLAT_ORDER.get(i), i);
        }
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("C major", "A minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("G major", "E minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("D major", "B minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("A major", "F# minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("E major", "C# minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("B major", "Ab minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("F# major", "Eb minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("C# major", "Bb minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("Ab major", "F minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("Eb major", "C minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("Bb major", "G minor");
        KeySignature.MAJOR_MINOR_EQUIVALENT.put("F major", "D minor");
    }
}
