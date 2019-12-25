// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Scale.ScaleMode;

import Model.Note.Scale.HarmonicMinorScale;
import Model.Note.Scale.MelodicMinorScale;
import Model.Note.Scale.MajorScale;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import Model.Note.NoteMap;
import Model.Note.Scale.Scale;
import java.util.Iterator;
import java.util.List;
import Model.Note.Note;
import java.util.Map;

public abstract class ScaleModeMap
{
    private static Map<String, ScaleModes> scaleModesMap;
    
    public static List<Note> getNotesInMode(final Note note, final String modeName) {
        List<Note> scale = null;
        for (final String parentScale : ScaleModeMap.scaleModesMap.keySet()) {
            scale = ScaleModeMap.scaleModesMap.get(parentScale).getScale(note, modeName);
            if (scale != null) {
                break;
            }
        }
        return scale;
    }
    
    public static String modeOf(final Scale parentScale, final Integer quality) {
        String foundScale = null;
        for (final String checkScale : ScaleModeMap.scaleModesMap.keySet()) {
            if (checkScale.equals(parentScale.getClass().getName())) {
                foundScale = checkScale;
                break;
            }
        }
        if (foundScale != null) {
            return ScaleModeMap.scaleModesMap.get(foundScale).modeOf(quality);
        }
        return null;
    }
    
    public static Note getRootNote(final Scale parentScale, final Note key, final Integer quality) {
        final ScaleModes scaleModes = ScaleModeMap.scaleModesMap.get(parentScale.getClass().getName());
        Note rootNote = null;
        if (scaleModes != null) {
            final List<Note> modeScale = scaleModes.getScale(key, scaleModes.getFirstMode().getQuality());
            if (modeScale != null) {
                rootNote = modeScale.get(quality - 1);
            }
        }
        return rootNote;
    }
    
    public static Note getRootNote(final Scale parentScale, final Note key, final String modeName) {
        final ScaleModes scaleModes = ScaleModeMap.scaleModesMap.get(parentScale.getClass().getName());
        Note rootNote = null;
        if (scaleModes != null) {
            final Integer quality = scaleModes.getQuality(modeName);
            if (quality != null) {
                final List<Note> modeScale = scaleModes.getScale(key, 1);
                if (modeScale != null) {
                    rootNote = modeScale.get(quality - 1);
                }
            }
        }
        return rootNote;
    }
    
    public static ScaleMode getScaleMode(final String modeName) {
        ScaleMode mode = null;
        for (final ScaleModes scaleModes : ScaleModeMap.scaleModesMap.values()) {
            mode = scaleModes.getScaleMode(modeName);
            if (mode != null) {
                break;
            }
        }
        return mode;
    }
    
    public static Note getIonianRootNote(final Scale parentScale, final Note key, final String modeName) {
        final ScaleModes scaleModes = ScaleModeMap.scaleModesMap.get(parentScale.getClass().getName());
        Note rootNote = null;
        if (scaleModes != null) {
            final Integer quality = scaleModes.getQuality(modeName);
            if (quality != null) {
                final Integer interval = parentScale.getInterval(quality - 1);
                rootNote = NoteMap.getNote(NoteMap.getSemitone(key.getNoteWithOctave(), -interval));
            }
        }
        return rootNote;
    }
    
    public static Scale parentOf(final String modeName) {
        for (final ScaleModes scaleModes : ScaleModeMap.scaleModesMap.values()) {
            final Scale checkScale = scaleModes.getParentScale(modeName);
            if (checkScale != null) {
                return checkScale;
            }
        }
        return null;
    }
    
    public static List<String> getAllModeNames() {
        final List<String> modeNames = new ArrayList<String>();
        for (final ScaleModes modes : ScaleModeMap.scaleModesMap.values()) {
            for (final ScaleMode mode : modes.getScaleModes()) {
                modeNames.add(mode.getModeName().toLowerCase());
            }
        }
        return modeNames;
    }
    
    public static ScaleModes getScaleModes(final String scaleModesName) {
        return ScaleModeMap.scaleModesMap.get(scaleModesName);
    }
    
    static {
        (ScaleModeMap.scaleModesMap = new LinkedHashMap<String, ScaleModes>()).put(MajorScale.class.getName(), new MajorScaleModes());
        ScaleModeMap.scaleModesMap.put(MelodicMinorScale.class.getName(), new MelodicMinorScaleModes());
        ScaleModeMap.scaleModesMap.put(HarmonicMinorScale.class.getName(), new HarmonicMinorScaleModes());
    }
}
