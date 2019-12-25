// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import Model.Note.Note;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.NoteMap;
import Model.Note.Chord.ChordMap;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class ChordTutor extends Tutor
{
    private static final int FIRST_MIDI = 60;
    private static final int MAX_MIDI = 71;
    private static final int FIRST_CHORD = 0;
    private String playType;
    private static Logger log;
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            final Question currentQuestion = new Question("A chord will play. What chord type is this?");
            final ArrayList<String> chords = (ArrayList<String>)(ArrayList)ChordMap.getChordTypes();
            do {
                currentQuestion.firstRandomParam = Tutor.randInt(60, 71);
                currentQuestion.correctAnswer = chords.get(Tutor.randInt(0, chords.size() - 1));
            } while (this.currentQuestionNumber != 1 && this.currentQuestionNumber > 1 && currentQuestion.firstRandomParam == this.questions.get(this.currentQuestionNumber - 1).firstRandomParam && currentQuestion.correctAnswer.equals(this.questions.get(this.currentQuestionNumber - 1).correctAnswer));
            this.questions.put(this.currentQuestionNumber, currentQuestion);
        }
    }
    
    @Override
    public void runQuestion(final int gapAtStart) {
        final Question currentQuestion = this.questions.get(this.currentQuestionNumber);
        final Note note = NoteMap.getNoteFromMidi(currentQuestion.firstRandomParam);
        final Collection<Integer> chordToPlay = ChordMap.notesInChordMidi(note, currentQuestion.correctAnswer);
        if (this.questions.containsKey(this.currentQuestionNumber)) {
            final String playType = this.playType;
            switch (playType) {
                case "-a": {
                    this.play.playChordArpeggiated((ArrayList)chordToPlay, gapAtStart, UnitDurationInformation.getUnitDuration());
                    break;
                }
                case "-s": {
                    this.play.playChordSimultaneous((ArrayList)chordToPlay, gapAtStart, UnitDurationInformation.getUnitDuration(), false);
                    break;
                }
                case "-b": {
                    this.play.playChordArpeggiatedThenSimultaneous((ArrayList)chordToPlay, gapAtStart, UnitDurationInformation.getUnitDuration());
                    break;
                }
            }
        }
    }
    
    @Override
    public void setOptions(final List<String> options) {
        this.currentQuestionNumber = 0;
        this.questions = new HashMap<Integer, Question>();
        try {
            this.numQuestions = Integer.parseInt(options.get(0));
            this.playType = options.get(1);
        }
        catch (Exception e) {
            ChordTutor.log.error(e.getMessage());
        }
    }
    
    static {
        ChordTutor.log = Logger.getLogger(ScaleTutor.class.getName());
    }
}
