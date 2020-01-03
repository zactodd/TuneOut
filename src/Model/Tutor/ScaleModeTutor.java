

package Model.Tutor;

import java.util.HashMap;
import Model.Play.Play;
import Model.Note.Note;
import Model.Note.NoteMap;
import java.util.ArrayList;
import Environment.GrammarParser;
import Environment.Environment;
import java.util.List;
import org.apache.log4j.Logger;

public class ScaleModeTutor extends Tutor
{
    static Logger log;
    private final Integer numberQuestionPos;
    private final Integer typePos;
    private final String MAJOR = "major";
    private final String MINOR = "melodic minor";
    private final String BOTH = "both";
    private final String modeOfCommand = "modeOf(%s, \"%s\", %s)";
    private final String questionText = "Do the following scales %s and %s share the same notes?";
    private final List<String> rootNotes;
    private String type;
    private final Environment env;
    private GrammarParser parser;
    
    public ScaleModeTutor() {
        this.numberQuestionPos = 0;
        this.typePos = 1;
        this.rootNotes = new ArrayList<String>() {
            {
                for (final Note note : NoteMap.allNotes) {
                    if (note.getOctave() >= 3 && note.getOctave() <= 5) {
                        this.add(note.getNoteWithOctave());
                    }
                }
            }
        };
        this.type = "";
        this.env = new Environment(new Play(Play.PlayType.QUEUED));
        this.parser = new GrammarParser(this.env);
    }
    
    @Override
    public void generateQuestion() {
        ++this.currentQuestionNumber;
        while (this.questions.containsKey(this.currentQuestionNumber) && this.questions.get(this.currentQuestionNumber).isCorrect) {
            ++this.currentQuestionNumber;
        }
        if (this.currentQuestionNumber <= this.numQuestions && !this.questions.containsKey(this.currentQuestionNumber)) {
            this.questions.put(this.currentQuestionNumber, this.getScaleMode());
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
            this.numQuestions = Integer.parseInt(input.get(this.numberQuestionPos));
            this.type = input.get(this.typePos);
        }
        catch (Exception e) {
            ScaleModeTutor.log.error(e.getMessage());
        }
    }
    
    private Question getScaleMode() {
        String scaleTypeName = this.type.replace("\"", "");
        String rootNoteName = "";
        final Integer scaleType = randInt(0, 1);
        if (scaleTypeName.equalsIgnoreCase("both")) {
            if (scaleType == 1) {
                scaleTypeName = "major";
            }
            else {
                scaleTypeName = "melodic minor";
            }
        }
        String degree = Integer.toString(randInt(1, 7));
        rootNoteName = this.rootNotes.get(randInt(0, this.rootNotes.size() - 1));
        this.parser.executeCommand(String.format("modeOf(%s, \"%s\", %s)", rootNoteName, scaleTypeName, degree));
        final String firstMode = this.env.getResponse();
        final Integer correctAns = randInt(0, 1);
        String answerString;
        if (correctAns == 1) {
            for (String previousDegree = degree; previousDegree.equals(degree); degree = Integer.toString(randInt(1, 7))) {}
            answerString = "True";
        }
        else {
            for (String previousRootNoteName = rootNoteName; previousRootNoteName.equals(rootNoteName); rootNoteName = this.rootNotes.get(randInt(0, this.rootNotes.size() - 1))) {}
            answerString = "False";
        }
        this.parser.executeCommand(String.format("modeOf(%s, \"%s\", %s)", rootNoteName, scaleTypeName, degree));
        final String secondMode = this.env.getResponse();
        final Question currentQuestion = new Question(String.format("Do the following scales %s and %s share the same notes?", firstMode, secondMode));
        currentQuestion.correctAnswer = answerString;
        return currentQuestion;
    }
    
    static {
        ScaleModeTutor.log = Logger.getLogger(ScaleTutor.class.getName());
    }
}
