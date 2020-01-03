

package Model.Tutor;

import Model.Note.Scale.Scale;
import Model.Note.Scale.ScaleMap;

import java.util.Map;
import java.util.ArrayList;
import Model.Note.Chord.ChordMap;
import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import Model.Note.NoteMap;
import Model.Note.Note;
import java.util.List;
import org.apache.log4j.Logger;

public class ChordFunctionTutor extends Tutor
{
    private static final int FUNCTION_QUESTION = 1;
    private static final int CHORD_QUESTION = 2;
    private ChordFunctionTutorOption options;
    private static final Integer NUMBER_OF_QUESTION_POS;
    private static final Integer QUESTION_TYPE_POS;
    private static final String KEY_NAME = "major";
    private static Logger log;
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            Question currentQuestion;
            if (this.options.otherOptions.get(0).equals("-f")) {
                currentQuestion = this.getFunctionQuestion();
            }
            else if (this.options.otherOptions.get(0).equals("-c")) {
                currentQuestion = this.getChordQuestion();
            }
            else if (randInt(0, 1) == 0) {
                currentQuestion = this.getFunctionQuestion();
            }
            else {
                currentQuestion = this.getChordQuestion();
            }
            this.questions.put(this.currentQuestionNumber, currentQuestion);
        }
    }
    
    private Question getFunctionQuestion() {
        final List<Note> notes = NoteMap.allNotes.stream().filter(note -> note.getMidiNumber() >= 60 && note.getMidiNumber() <= 71).collect((Collector<? super Object, ?, List<Note>>)Collectors.toList());
        final Note keyNoteObj = notes.get(randInt(0, notes.size() - 1));
        final String keyNote = keyNoteObj.getNoteWithOctave();
        final String keyNoteNoOctave = keyNoteObj.getNoteName();
        final Map<String, String> possibleChords = new HashMap<String, String>();
        for (final String function : ChordMap.getAllFunctionNames()) {
            possibleChords.put(function, ChordMap.chordFunction(function, keyNote, "major"));
        }
        possibleChords.put("non functional", this.getRandomNonFunctionalChord(keyNote));
        final String prevQuestionText = (this.currentQuestionNumber > 1) ? this.questions.get(this.currentQuestionNumber - 1).getQuestionText() : "";
        String questionText;
        String answer;
        do {
            final int num = randInt(0, possibleChords.size() - 1);
            final List<String> keys = new ArrayList<String>(possibleChords.keySet());
            answer = keys.get(num);
            questionText = String.format("In %s %s, what is the degree/function of %s?", keyNoteNoOctave, "major", possibleChords.get(keys.get(num)));
        } while (this.currentQuestionNumber != 1 && this.questions.get(this.currentQuestionNumber - 1).getQuestionText().equals(questionText));
        final Question question = new Question(questionText);
        question.correctAnswer = answer;
        question.setQuestionType(1);
        return question;
    }
    
    private String getRandomNonFunctionalChord(final String keyNote) {
        final List<String[]> allChordsForKey = new ArrayList<String[]>();
        final Scale theScale = ScaleMap.getScale("major");
        theScale.setNotesInScale(keyNote);
        final List<Note> notes = theScale.getNotesInScale();
        for (final Note scaleNote : notes) {
            for (final String qualityName : ChordMap.getUniqueQualityNames()) {
                allChordsForKey.add(new String[] { scaleNote.getNoteName(), qualityName });
            }
        }
        String[] theChord;
        do {
            final int num = randInt(0, allChordsForKey.size() - 1);
            theChord = allChordsForKey.get(num);
        } while (ChordMap.functionOf(theChord[0], theChord[1], keyNote, "major") != null);
        return theChord[0] + " " + theChord[1];
    }
    
    private Question getChordQuestion() {
        final String prevQuestionText = (this.currentQuestionNumber > 1) ? this.questions.get(this.currentQuestionNumber - 1).getQuestionText() : "";
        String questionText;
        String answer;
        do {
            final String keyNote = NoteMap.getNoteString(randInt(60, 71));
            final String keyNoteNoOctave = (keyNote != null) ? keyNote.replaceAll("\\d", "") : null;
            final int num = randInt(0, ChordMap.getAllFunctionNames().size() - 1);
            final List<String> functions = new ArrayList<String>(ChordMap.getAllFunctionNames());
            final String randomFunction = functions.get(num);
            questionText = String.format("What is the %s chord of %s %s?", randomFunction, keyNoteNoOctave, "major");
            answer = ChordMap.chordFunction(randomFunction, keyNoteNoOctave, "major");
        } while (this.currentQuestionNumber != 1 && this.questions.get(this.currentQuestionNumber - 1).getQuestionText().equals(questionText));
        final Question question = new Question(questionText);
        question.correctAnswer = answer;
        question.setQuestionType(2);
        return question;
    }
    
    @Override
    public void runQuestion(final int gapAtStart) {
    }
    
    @Override
    public void setOptions(final List<String> input) {
        try {
            this.currentQuestionNumber = 0;
            this.questions = new HashMap<Integer, Question>();
            this.numQuestions = Integer.parseInt(input.get(ChordFunctionTutor.NUMBER_OF_QUESTION_POS));
            final String questions = input.get(ChordFunctionTutor.QUESTION_TYPE_POS).replace("\"", "");
            this.options = new ChordFunctionTutorOption(this.numQuestions, questions);
        }
        catch (Exception e) {
            ChordFunctionTutor.log.error(e.getMessage());
        }
    }
    
    static {
        NUMBER_OF_QUESTION_POS = 0;
        QUESTION_TYPE_POS = 1;
        ChordFunctionTutor.log = Logger.getLogger(ScaleTutor.class.getName());
    }
}
