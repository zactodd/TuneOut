// 
// Decompiled by Procyon v0.5.36
// 

package Model.File;

import com.google.gson.JsonSyntaxException;
import Model.Percussion.PercussionLoopMap;
import Model.Note.Settings.TempoInformation;
import Model.Note.Settings.SwingMap;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.instrument.InstrumentInformation;
import Model.Note.Melody.MelodyMap;
import Model.DigitalPattern.DigitalPattern;
import Model.Terms.MusicalTerms;
import Model.Note.unitDuration.UnitDuration;
import Model.instrument.Instrument;
import Model.Terms.Term;
import Model.Note.Melody.Melody;
import Model.Note.NoteMap;
import Model.Note.Note;
import java.util.ArrayList;
import Model.Note.Melody.NoteCollection;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import Model.Percussion.PercussionLoop;
import com.google.gson.Gson;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class TuneOutProjectFile extends TuneOutFile
{
    public TuneOutProjectFile(final File file) {
        super(file);
    }
    
    private Map<String, String> reconstructDigitalPatterns(final Map digitalPatternsFromJson) {
        final Map<String, String> digitalPatternsMap = new HashMap<String, String>();
        for (final Object name : digitalPatternsFromJson.keySet()) {
            final String digitalPatternName = (String)name;
            final String digitalPattern = digitalPatternsFromJson.get(digitalPatternName);
            digitalPatternsMap.put(digitalPatternName, digitalPattern);
        }
        return digitalPatternsMap;
    }
    
    private Map<String, PercussionLoop> reconstructPercussionLoop(final Map percussionLoopMapFromJson, final Gson gson) {
        final Map<String, PercussionLoop> percussionLoopMap = new HashMap<String, PercussionLoop>();
        for (final Object name : percussionLoopMapFromJson.keySet()) {
            final String percussionLoopName = (String)name;
            final String percussionLoopEventStr = String.valueOf(percussionLoopMapFromJson.get(name));
            final PercussionLoop percussionLoop = gson.fromJson(percussionLoopEventStr, new TypeToken<PercussionLoop>() {}.getType());
            percussionLoopMap.put(percussionLoopName, percussionLoop);
        }
        return percussionLoopMap;
    }
    
    private List<NoteCollection> reconstructNoteCollection(final Map melodyMap, final Gson gson) {
        final List<NoteCollection> noteCollectionList = new ArrayList<NoteCollection>();
        final List<Note> newNotes = new ArrayList<Note>();
        final List noteCollections = melodyMap.get("noteCollections");
        for (final Object noteCollectionObj : noteCollections) {
            try {
                NoteCollection noteCollection = null;
                noteCollection = gson.fromJson(String.valueOf(noteCollectionObj), new TypeToken<NoteCollection>() {}.getType());
                noteCollectionList.add(noteCollection);
            }
            catch (Exception e) {
                final String noteCollectionObjNew = noteCollectionObj.toString().replaceAll("#", "`");
                final NoteCollection noteCollection = gson.fromJson(String.valueOf(noteCollectionObjNew), new TypeToken<NoteCollection>() {}.getType());
                newNotes.clear();
                for (final Note note : noteCollection.getNotes()) {
                    if (note.getNoteName().contains("`")) {
                        final String without = note.getNoteWithOctave().replace("`", "#");
                        newNotes.add(NoteMap.getNote(without));
                    }
                    else {
                        newNotes.add(note);
                    }
                }
                noteCollection.setNotes(newNotes);
                noteCollectionList.add(noteCollection);
            }
        }
        return noteCollectionList;
    }
    
    private Map<String, Melody> reconstructMelodies(final Map melodiesFromJson, final Gson gson) {
        final HashMap<String, Melody> melodiesMap = new HashMap<String, Melody>();
        for (final Object m : melodiesFromJson.keySet()) {
            final Map melodyMap = melodiesFromJson.get(m);
            final String name = melodyMap.get("name");
            final Melody melody = new Melody(name);
            if (melodyMap.containsKey("instrument")) {
                final Map instruments = melodyMap.get("instrument");
                melody.setInstrument(this.reconstructInstrument(instruments));
            }
            melody.setNoteCollection(this.reconstructNoteCollection(melodyMap, gson));
            melodiesMap.put(name, melody);
        }
        return melodiesMap;
    }
    
    private Map<String, Term> reconstructTerms(final Map termsFromJson) {
        final Map<String, Term> termsMap = new HashMap<String, Term>();
        for (final Object t : termsFromJson.keySet()) {
            final Map termMap = termsFromJson.get(t);
            final String category = termMap.get("category");
            final String sourceLanguage = termMap.get("sourceLanguage");
            final String meaning = termMap.get("meaning");
            final String termName = termMap.get("termName");
            final Term term = new Term(termName, sourceLanguage, meaning, category);
            termsMap.put(termName, term);
        }
        return termsMap;
    }
    
    private Instrument reconstructInstrument(final Map instrumentFromJson) {
        final String instrumentName = instrumentFromJson.get("instrumentName");
        final String instrumentType = instrumentFromJson.get("instrumentType");
        final Boolean availability = (Boolean)instrumentFromJson.get("availability");
        final Double instrumentNum = (Double)instrumentFromJson.get("instrumentNumber");
        final Integer instrumentNumber = instrumentNum.intValue();
        final Instrument instrument = new Instrument(instrumentNumber, instrumentName, instrumentType, availability);
        return instrument;
    }
    
    private UnitDuration reconstructUnitDuration(final Map unitDurationFromJson) {
        final String unitDurationName = unitDurationFromJson.get("unitDurationName");
        final Double unitDurationDivider = (Double)unitDurationFromJson.get("unitDurationDivider");
        final Double numberOfDotsDouble = (Double)unitDurationFromJson.get("numberOfDots");
        final Integer numberOfDots = numberOfDotsDouble.intValue();
        final UnitDuration unitDuration = new UnitDuration(unitDurationName, unitDurationDivider, true, numberOfDots);
        return unitDuration;
    }
    
    public String convertObjectsToJson() {
        final Gson gson = new Gson();
        final HashMap<String, Object> all = new HashMap<String, Object>();
        all.put("terms", MusicalTerms.getTerms());
        all.put("digitalPatterns", DigitalPattern.getPatternHashMap());
        all.put("melodies", MelodyMap.getMelodiesMap());
        all.put("instrument", InstrumentInformation.getInstrumentId());
        all.put("unitDuration", UnitDurationInformation.getUnitDuration());
        all.put("swing", SwingMap.getCurrentSwing());
        all.put("tempo", TempoInformation.getTempInBpm());
        all.put("percussionLoop", PercussionLoopMap.getPercussionLoopMap());
        final String json = gson.toJson(all);
        return json;
    }
    
    public void convertObjectsFromJson(final String json) {
        final Gson gson = new Gson();
        try {
            final HashMap<String, Object> all = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
            if (all.containsKey("terms")) {
                final Map terms = all.get("terms");
                MusicalTerms.setTermsFromFile(this.reconstructTerms(terms));
            }
            if (all.containsKey("digitalPatterns")) {
                final Map digitalPatterns = all.get("digitalPatterns");
                DigitalPattern.setDigitalPatternsFromFile(this.reconstructDigitalPatterns(digitalPatterns));
            }
            if (all.containsKey("melodies")) {
                final Map melodies = all.get("melodies");
                MelodyMap.setMelodiesMap(this.reconstructMelodies(melodies, gson));
            }
            if (all.containsKey("instrument")) {
                final Double instrumentDouble = all.get("instrument");
                final Integer instrument = instrumentDouble.intValue();
                InstrumentInformation.setInstrumentId(instrument, false);
            }
            if (all.containsKey("unitDuration")) {
                final Map unitDuration = all.get("unitDuration");
                UnitDurationInformation.setUnitDuration(this.reconstructUnitDuration(unitDuration));
            }
            if (all.containsKey("swing")) {
                final String swing = all.get("swing");
                SwingMap.setCurrentSwing(swing);
            }
            if (all.containsKey("tempo")) {
                final Double tempoDouble = all.get("tempo");
                final Integer tempo = tempoDouble.intValue();
                TempoInformation.setTempInBpm(tempo);
            }
            if (all.containsKey("percussionLoop")) {
                final Map percussionLoop = all.get("percussionLoop");
                PercussionLoopMap.setPercussionLoopMapFromFile(this.reconstructPercussionLoop(percussionLoop, gson));
            }
        }
        catch (Exception e) {
            TuneOutProjectFile.log.error(e.toString());
        }
    }
    
    @Override
    public boolean isValid(final String fileText) {
        if (fileText.isEmpty()) {
            return false;
        }
        final Gson gson = new Gson();
        try {
            gson.fromJson(fileText, new TypeToken<HashMap<String, Object>>() {}.getType());
            return true;
        }
        catch (JsonSyntaxException ex) {
            return false;
        }
    }
    
    public boolean hasChanges() {
        String fileText = this.fileToText(this.file);
        fileText = fileText.substring(0, fileText.indexOf(10));
        final String musicTermsText = this.convertObjectsToJson();
        return !fileText.equals(musicTermsText);
    }
}
