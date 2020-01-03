

package Model.command.TutorCommands;

import java.util.HashMap;

import Model.Tutor.Options;
import Model.Tutor.TutorDefinition;
import Model.Tutor.ChordTutorOption;

import java.util.ArrayList;
import java.util.Arrays;
import Controller.ChordTutorController;
import Model.Tutor.ChordTutor;
import java.util.List;
import java.util.Map;
import Environment.Environment;

public class ChordTutorCommand extends TutorCommand
{
    private static final String TUTOR_NAME = "Chord Tutor";
    private String returnValue;
    private int numQuestions;
    private String playType;
    private Boolean paramsValid;
    private final int QUESTION_NUM_POS = 0;
    private final int PLAY_STYLE_POS = 1;
    private String error;
    private Environment env;
    private static final Map<String, String> PLAY_STYLE_OPTIONS;
    
    public ChordTutorCommand(final List<String> args) {
        this.numQuestions = 5;
        this.playType = "-s";
        this.paramsValid = true;
        this.error = "";
        if (args != null) {
            switch (args.size()) {
                case 2: {
                    this.processPlayType(args.get(1));
                    this.processNumQuestions(args.get(0));
                    break;
                }
                case 1: {
                    if (args.get(0).matches("-[a-z|A-Z]")) {
                        this.processPlayType(args.get(0));
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
            this.returnValue = ((this.numQuestions == 1) ? ("Started Chord Tutor with " + this.numQuestions + " question and play type: " + ChordTutorCommand.PLAY_STYLE_OPTIONS.get(this.playType)) : ("Started Chord Tutor with " + this.numQuestions + " questions and play type: " + ChordTutorCommand.PLAY_STYLE_OPTIONS.get(this.playType)));
            this.returnValue += "\nGo to the Chord Tutor tab to begin the tutor";
        }
        else {
            this.returnValue = this.error;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.paramsValid) {
            final ChordTutor chordTutor = new ChordTutor();
            final ChordTutorController chordTutorController = new ChordTutorController();
            chordTutor.setOptions(new ArrayList<String>(Arrays.asList(String.valueOf(this.numQuestions), this.playType)));
            final Options options = new ChordTutorOption(this.numQuestions, this.playType);
            final TutorDefinition chordTutorDefn = new TutorDefinition("Chord Tutor", chordTutor, chordTutorController, options);
            this.env = env;
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
    
    private void processPlayType(String param) {
        Boolean valid = false;
        param = param.toLowerCase();
        for (final Map.Entry entry : ChordTutorCommand.PLAY_STYLE_OPTIONS.entrySet()) {
            if (param.equals(entry.getKey())) {
                valid = true;
            }
        }
        if (valid) {
            this.playType = param;
        }
        else {
            this.paramsValid = false;
            this.error = "The play type is not valid. Please use -a for arpeggiated, -s for simultaneous or -b for both.";
        }
    }
    
    static {
        (PLAY_STYLE_OPTIONS = new HashMap<String, String>()).put("-s", "Simultaneous");
        ChordTutorCommand.PLAY_STYLE_OPTIONS.put("-a", "Arpeggio");
        ChordTutorCommand.PLAY_STYLE_OPTIONS.put("-b", "Both");
    }
}
