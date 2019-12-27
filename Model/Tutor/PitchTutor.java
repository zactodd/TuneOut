

package Model.Tutor;

import Model.Note.NoteMap;
import java.util.HashMap;
import java.util.List;
import Model.Note.unitDuration.UnitDurationInformation;
import org.apache.log4j.Logger;
import Model.Note.unitDuration.UnitDuration;

public class PitchTutor extends Tutor
{
    private int pitchRangeMinMidi;
    private int pitchRangeMaxMidi;
    private UnitDuration unitDuration;
    static Logger log;
    
    public PitchTutor() {
        this.unitDuration = UnitDurationInformation.getUnitDuration();
    }
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            final Question currentQuestion = new Question("Two notes will play. Is the pitch of the second note higher, lower or the same?");
            do {
                currentQuestion.firstRandomNote = Tutor.randInt(this.pitchRangeMinMidi, this.pitchRangeMaxMidi);
                currentQuestion.secondRandomNote = Tutor.randInt(this.pitchRangeMinMidi, this.pitchRangeMaxMidi);
            } while (this.currentQuestionNumber > 1 && currentQuestion.firstRandomNote == this.questions.get(this.currentQuestionNumber - 1).firstRandomNote && currentQuestion.secondRandomNote == this.questions.get(this.currentQuestionNumber - 1).secondRandomNote);
            if (currentQuestion.firstRandomNote > currentQuestion.secondRandomNote) {
                currentQuestion.correctAnswer = "lower";
            }
            else if (currentQuestion.firstRandomNote < currentQuestion.secondRandomNote) {
                currentQuestion.correctAnswer = "higher";
            }
            else {
                currentQuestion.correctAnswer = "same";
            }
            this.questions.put(this.currentQuestionNumber, currentQuestion);
        }
    }
    
    @Override
    public void runQuestion(final int gapAtStart) {
        if (this.questions.containsKey(this.currentQuestionNumber)) {
            final int firstNote = this.questions.get(this.currentQuestionNumber).firstRandomNote;
            final int secondNote = this.questions.get(this.currentQuestionNumber).secondRandomNote;
            this.play.playTwoNotes(gapAtStart, firstNote, 3, secondNote, this.unitDuration);
        }
    }
    
    @Override
    public void setOptions(final List<String> options) {
        this.currentQuestionNumber = 0;
        this.questions = new HashMap<Integer, Question>();
        try {
            this.numQuestions = Integer.parseInt(options.get(0));
            this.pitchRangeMinMidi = NoteMap.getMidi(options.get(1));
            this.pitchRangeMaxMidi = NoteMap.getMidi(options.get(2));
        }
        catch (Exception e) {
            PitchTutor.log.error(e.getMessage());
        }
    }
    
    static {
        PitchTutor.log = Logger.getLogger(PitchTutor.class.getName());
    }
}
