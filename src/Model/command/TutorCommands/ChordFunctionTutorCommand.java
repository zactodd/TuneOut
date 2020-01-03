

package Model.command.TutorCommands;

import java.util.HashMap;

import Model.Tutor.Options;
import Model.Tutor.TutorDefinition;
import Model.Tutor.ChordFunctionTutorOption;

import java.util.ArrayList;
import java.util.Arrays;
import Controller.ChordFunctionTutorController;
import Model.Tutor.ChordFunctionTutor;
import Environment.Environment;
import java.util.List;
import java.util.Map;

public class ChordFunctionTutorCommand extends TutorCommand
{
    private static final String TUTOR_NAME = "Chord Function Tutor";
    private String returnValue;
    private int numQuestions;
    private String questionType;
    private Boolean paramsValid;
    private static final int QUESTION_NUM_POS = 0;
    private static final int QUESTION_TYPE_POS = 1;
    private String error;
    private static final Map<String, String> QUESTION_TYPE_OPTIONS;
    
    public ChordFunctionTutorCommand(final List<String> args) {
        this.numQuestions = 5;
        this.questionType = "-b";
        this.paramsValid = true;
        this.error = "";
        if (args != null) {
            switch (args.size()) {
                case 2: {
                    this.processQuestionType(args.get(1));
                    this.processNumQuestions(args.get(0));
                    break;
                }
                case 1: {
                    if (args.get(0).matches("-[a-z|A-Z]")) {
                        this.processQuestionType(args.get(0));
                        break;
                    }
                    this.processNumQuestions(args.get(0));
                    break;
                }
                default: {
                    this.wrongNumOfParamErrorRaiser();
                    break;
                }
            }
        }
        if (this.paramsValid) {
            this.returnValue = ((this.numQuestions == 1) ? ("Started Chord Function Tutor with " + this.numQuestions + " question and " + ChordFunctionTutorCommand.QUESTION_TYPE_OPTIONS.get(this.questionType) + " questions") : ("Started Chord Function Tutor with " + this.numQuestions + " questions and " + ChordFunctionTutorCommand.QUESTION_TYPE_OPTIONS.get(this.questionType) + " questions"));
            this.returnValue = this.returnValue + "\nGo to the " + "Chord Function Tutor" + " tab to begin the tutor";
        }
        else {
            this.returnValue = this.error;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.paramsValid) {
            final ChordFunctionTutor chordFunctionTutor = new ChordFunctionTutor();
            final ChordFunctionTutorController chordFunctionTutorController = new ChordFunctionTutorController();
            chordFunctionTutor.setOptions(new ArrayList<String>(Arrays.asList(String.valueOf(this.numQuestions), this.questionType)));
            final Options options = new ChordFunctionTutorOption(this.numQuestions, this.questionType);
            final TutorDefinition chordTutorDefn = new TutorDefinition("Chord Function Tutor", chordFunctionTutor, chordFunctionTutorController, options);
            final boolean tutorValid = env.checkAndCreateTutor(chordTutorDefn);
            if (!tutorValid) {
                this.returnValue = tutorAlreadyOpen();
            }
        }
        env.setResponse(this.returnValue);
    }
    
    private void processNumQuestions(final String arg) {
        if (Options.numQuestionsValid(arg)) {
            this.numQuestions = Integer.parseInt(arg);
        }
        else {
            this.paramsValid = false;
            if (arg.matches("-[a-z|A-Z]")) {
                this.error = "You must enter a number of questions and/or a single valid override.";
            }
            else {
                this.error = "The number of questions is not valid, it must be a number between 1 and 1000";
            }
        }
    }
    
    private void processQuestionType(String param) {
        Boolean valid = false;
        param = param.toLowerCase();
        for (final Map.Entry entry : ChordFunctionTutorCommand.QUESTION_TYPE_OPTIONS.entrySet()) {
            if (param.equals(entry.getKey())) {
                valid = true;
            }
        }
        if (valid) {
            this.questionType = param;
        }
        else {
            this.paramsValid = false;
            this.error = "The question type is not valid. Please use -c for chord questions, -f for function questions or -b for both.";
        }
    }
    
    static {
        (QUESTION_TYPE_OPTIONS = new HashMap<String, String>()).put("-c", "chord");
        ChordFunctionTutorCommand.QUESTION_TYPE_OPTIONS.put("-f", "function");
        ChordFunctionTutorCommand.QUESTION_TYPE_OPTIONS.put("-b", "both chord and function");
    }
}
