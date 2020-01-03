

package Model.Tutor;

import java.util.Arrays;
import Model.Note.NoteMap;
import Model.Note.Chord.ChordMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import Model.Note.Note;
import java.util.List;

public class ChordSpellingTutor extends Tutor
{
    private ChordSpellingTutorOption options;
    private static final List<Note> NOTES;
    private static final Integer INVERSION_PERCENTAGE;
    private static final Integer RANDOM_CHORD_PERECENTAGE;
    private static final Integer PERCENTAGE;
    private static final String SPELLING_QUESTION = "What is the name of this chord? ";
    private static final String SPELLING_QUESTION_SUFFIX = " or if it isn't a chord type 'Not a Chord'.";
    private static final String CHORD_QUESTION = "What are the notes in this chord? ";
    private static final Integer NUMBER_OF_QUESTION_POS;
    private static final Integer SPELLING_QUESTION_POS;
    private static final Integer RANDOM_QUESTION_POS;
    private static final Integer ALLOW_CHORD_TYPES_STARTING_POS;
    private static final Integer MIN_NUM_NOTE;
    private static final Integer MAX_NUM_NOTE;
    private static final List<String> INVERSIONS;
    private static Logger log;
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            Question currentQuestion;
            if (this.options.getSpellingQuestion()) {
                if (this.options.allowsRandomNotes() && randInt(0, ChordSpellingTutor.PERCENTAGE) <= ChordSpellingTutor.RANDOM_CHORD_PERECENTAGE) {
                    currentQuestion = this.randomNotesQuestion();
                }
                else {
                    currentQuestion = this.getSpellingQuestion();
                }
            }
            else {
                currentQuestion = this.getChordQuestion();
            }
            this.questions.put(this.currentQuestionNumber, currentQuestion);
        }
    }
    
    @Override
    public void runQuestion(final int gapAtStart) {
    }
    
    @Override
    public void setOptions(final List<String> input) {
        try {
            this.currentQuestionNumber = 0;
            this.questions = new HashMap<Integer, Question>();
            this.numQuestions = Integer.parseInt(input.get(ChordSpellingTutor.NUMBER_OF_QUESTION_POS));
            final List<String> chordsAllowed = new ArrayList<String>();
            for (final String chordType : input.subList(ChordSpellingTutor.ALLOW_CHORD_TYPES_STARTING_POS, input.size())) {
                chordsAllowed.add(chordType.replace("\"", ""));
            }
            final Boolean allowRandomNotes = Boolean.parseBoolean(input.get(ChordSpellingTutor.RANDOM_QUESTION_POS).replace("\"", ""));
            final Boolean isSpellingQuestion = Boolean.parseBoolean(input.get(ChordSpellingTutor.SPELLING_QUESTION_POS).replace("\"", ""));
            this.options = new ChordSpellingTutorOption(this.numQuestions, chordsAllowed, allowRandomNotes, isSpellingQuestion);
        }
        catch (Exception e) {
            ChordSpellingTutor.log.error(e.getMessage());
        }
    }
    
    private Question getSpellingQuestion() {
        final String prevQuestionText = (this.currentQuestionNumber > 1) ? this.questions.get(this.currentQuestionNumber - 1).getQuestionText() : "";
        String questionText;
        Collection<Note> chord;
        do {
            chord = this.getRandomChord();
            final List<String> chordWithoutOctave = new ArrayList<String>();
            for (final Note note : chord) {
                chordWithoutOctave.add(note.getNoteName());
            }
            questionText = "What is the name of this chord? " + chordWithoutOctave.toString().replaceAll("\\[|\\]", "") + " or if it isn't a chord type 'Not a Chord'.";
        } while (this.currentQuestionNumber != 1 && this.questions.get(this.currentQuestionNumber - 1).getQuestionText().equals(questionText));
        final Question question = new Question(questionText);
        question.correctAnswer = ChordMap.findChordsFromNotes(new ArrayList<Note>(chord), false).get(0);
        return question;
    }
    
    private Question getChordQuestion() {
        final String prevQuestionText = (this.currentQuestionNumber > 1) ? this.questions.get(this.currentQuestionNumber - 1).getQuestionText() : "";
        String questionText;
        List<Note> chord;
        do {
            chord = this.getRandomChord();
            questionText = "What are the notes in this chord? " + ChordMap.findChordsFromNotes(chord, false).get(0);
            if (randInt(0, ChordSpellingTutor.PERCENTAGE) <= ChordSpellingTutor.INVERSION_PERCENTAGE) {
                final Integer inversionDegree = randInt(1, chord.size() - 1);
                chord = ChordMap.invertChord(new ArrayList<Note>(chord), inversionDegree);
                questionText = questionText + " (" + ChordSpellingTutor.INVERSIONS.get(inversionDegree - 1) + " inversion).";
            }
        } while (this.currentQuestionNumber != 1 && this.questions.get(this.currentQuestionNumber - 1).getQuestionText().equals(questionText));
        final Question question = new Question(questionText);
        final List<String> chordWithoutOctave = new ArrayList<String>();
        for (final Note note : chord) {
            chordWithoutOctave.add(note.getNoteName());
        }
        question.correctAnswer = chordWithoutOctave.toString().replaceAll("\\[|\\]", "");
        return question;
    }
    
    private List<Note> getRandomChord() {
        final Note rootNote = ChordSpellingTutor.NOTES.get(randInt(0, ChordSpellingTutor.NOTES.size() - 1));
        final Collection<String> chordTypes = ChordMap.getChordTypes();
        Boolean selectedChordType = false;
        String chordType = "";
        final List<String> vaildChordTypes = this.options.getChordTypes();
        do {
            Integer randNum = randInt(0, chordTypes.size() - 1);
            final Iterator<String> iterator = chordTypes.iterator();
            while (iterator.hasNext()) {
                final String tempChordType = chordType = iterator.next();
                if (--randNum < 0) {
                    break;
                }
            }
            for (final String checkChordType : vaildChordTypes) {
                if (chordType.endsWith(checkChordType)) {
                    selectedChordType = true;
                    break;
                }
            }
        } while (!selectedChordType);
        return ChordMap.notesInChord(rootNote, chordType);
    }
    
    private Question randomNotesQuestion() {
        final String prevQuestionText = (this.currentQuestionNumber > 1) ? this.questions.get(this.currentQuestionNumber - 1).getQuestionText() : "";
        String questionText;
        do {
            final List<String> chordWithoutOctave = new ArrayList<String>();
            List<Note> chord;
            do {
                chord = new ArrayList<Note>();
                for (Integer numOfNotes = randInt(ChordSpellingTutor.MIN_NUM_NOTE, ChordSpellingTutor.MAX_NUM_NOTE), i = 0; i < numOfNotes; ++i) {
                    chord.add(ChordSpellingTutor.NOTES.get(randInt(0, ChordSpellingTutor.NOTES.size() - 1)));
                }
            } while (ChordMap.findChordsFromNotes(chord, false).size() == 0);
            for (final Note note : chord) {
                chordWithoutOctave.add(note.getNoteName());
            }
            questionText = "What is the name of this chord? " + chordWithoutOctave.toString().replaceAll("\\[|\\]", "") + " or if it isn't a chord type 'Not a Chord'.";
        } while (prevQuestionText.equals(this.currentQuestionNumber - 1 + ". " + questionText));
        final Question question = new Question(this.currentQuestionNumber + ". " + questionText);
        question.correctAnswer = "Not a Chord";
        return question;
    }
    
    static {
        NOTES = new ArrayList<Note>() {
            {
                for (final Note note : NoteMap.allNotes) {
                    if (note.isPrimary() && note.getOctave() == 4) {
                        this.add(note);
                    }
                }
            }
        };
        INVERSION_PERCENTAGE = 100;
        RANDOM_CHORD_PERECENTAGE = 20;
        PERCENTAGE = 100;
        NUMBER_OF_QUESTION_POS = 0;
        SPELLING_QUESTION_POS = 1;
        RANDOM_QUESTION_POS = 2;
        ALLOW_CHORD_TYPES_STARTING_POS = 3;
        MIN_NUM_NOTE = 3;
        MAX_NUM_NOTE = 4;
        INVERSIONS = Arrays.asList("1st", "2nd", "3rd");
        ChordSpellingTutor.log = Logger.getLogger(ScaleTutor.class.getName());
    }
}
