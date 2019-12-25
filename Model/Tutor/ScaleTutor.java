// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import java.util.Collection;
import Model.Note.Scale.Scale;
import Model.Note.Note;
import Model.Note.Scale.ScaleMode.ScaleModeMap;
import Model.Note.Scale.ScaleMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Play.Play;
import Model.Note.NoteMap;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;
import java.util.List;

public class ScaleTutor extends Tutor
{
    private static final int FIRST_MIDI = 60;
    private static final int MAX_MIDI = 71;
    private static final int OCTAVE_LENGTH = 12;
    private static final int FIRST_SCALE = 0;
    private int numOctaves;
    private String order;
    private List<String> scales;
    private static Logger log;
    public static final List<String> allScales;
    public static Map<String, String> mutuallyExclusiveScales;
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            final Question currentQuestion = new Question("A scale will play. Please select correct type of scale.");
            do {
                currentQuestion.firstRandomParam = Tutor.randInt(60, 71);
                currentQuestion.secondRandomParam = Tutor.randInt(0, this.scales.size() - 1);
            } while (this.currentQuestionNumber > 1 && currentQuestion.firstRandomParam == this.questions.get(this.currentQuestionNumber - 1).firstRandomParam && currentQuestion.secondRandomParam == this.questions.get(this.currentQuestionNumber - 1).secondRandomParam);
            currentQuestion.correctAnswer = this.scales.toArray()[currentQuestion.secondRandomParam].toString();
            this.questions.put(this.currentQuestionNumber, currentQuestion);
        }
    }
    
    @Override
    public void runQuestion(final int gapAtStart) {
        if (this.questions.containsKey(this.currentQuestionNumber)) {
            int firstNoteMidi = this.questions.get(this.currentQuestionNumber).firstRandomParam;
            final ArrayList<String> fullScale = new ArrayList<String>();
            String previousNote = "";
            for (int i = 0; i < this.numOctaves; ++i) {
                if (i > 0) {
                    firstNoteMidi += 12;
                }
                final String firstNote = NoteMap.getNoteString(firstNoteMidi);
                final List<String> scaleOneOctave = this.oneOctaveScale(firstNote);
                for (final String currentNote : scaleOneOctave) {
                    if (!previousNote.equals(currentNote)) {
                        fullScale.add(currentNote);
                    }
                    previousNote = currentNote;
                }
            }
            this.play.playScale(fullScale, this.order, gapAtStart, Play.PlayStyle.NONE, UnitDurationInformation.getUnitDuration(), false);
        }
    }
    
    @Override
    public void setOptions(final List<String> options) {
        this.scales = new ArrayList<String>();
        this.currentQuestionNumber = 0;
        this.questions = new HashMap<Integer, Question>();
        try {
            this.numQuestions = Integer.parseInt(options.get(0));
            this.numOctaves = Integer.parseInt(options.get(1));
            this.order = options.get(2);
            final String scalesInStr = options.get(3).replace("[", "").replace("]", "").replace("\"", "");
            final List<String> scaleStrList = Arrays.asList(scalesInStr.split("\\s*,\\s*"));
            for (final String scaleStr : scaleStrList) {
                if (ScaleTutor.allScales.contains(scaleStr)) {
                    this.scales.add(scaleStr);
                }
            }
            for (final Map.Entry<String, String> entry : ScaleTutor.mutuallyExclusiveScales.entrySet()) {
                if (this.scales.contains(entry.getKey()) && this.scales.contains(entry.getValue())) {
                    this.scales.remove(entry.getKey());
                }
            }
        }
        catch (Exception e) {
            ScaleTutor.log.error(e.getMessage());
        }
    }
    
    private List<String> oneOctaveScale(final String firstNoteInOctave) {
        final String scaleName = this.questions.get(this.currentQuestionNumber).correctAnswer;
        List<String> returnArray = new ArrayList<String>();
        final Scale scale = ScaleMap.getScale(scaleName);
        if (scale != null) {
            scale.setNotesInScale(firstNoteInOctave);
            returnArray = scale.getScaleArrayWithOctave();
        }
        else {
            final Note note = NoteMap.getNote(firstNoteInOctave);
            final List<Note> scaleNotes = ScaleModeMap.getNotesInMode(note, scaleName);
            for (final Note scaleNote : scaleNotes) {
                returnArray.add(scaleNote.getNoteWithOctave());
            }
        }
        return returnArray;
    }
    
    static {
        ScaleTutor.log = Logger.getLogger(ScaleTutor.class.getName());
        allScales = new ArrayList<String>();
        ScaleTutor.mutuallyExclusiveScales = new HashMap<String, String>();
        ScaleTutor.allScales.addAll(ScaleMap.getScales());
        ScaleTutor.allScales.addAll(ScaleModeMap.getAllModeNames());
        for (final String scale : ScaleMap.getScales()) {
            final Scale scaleObj = ScaleMap.getScale(scale);
            if (!scaleObj.getSameScaleMode().isEmpty()) {
                ScaleTutor.mutuallyExclusiveScales.put(scaleObj.getSameScaleMode().toLowerCase(), scale);
            }
        }
    }
}
