

package Model.command.TutorCommands;

import Model.command.Command;
import Model.Note.NoteMap;
import Model.Tutor.Options;
import Model.Tutor.TutorDefinition;
import Model.Tutor.PitchTutorOption;

import java.util.ArrayList;
import java.util.Arrays;
import Controller.PitchTutorController;
import Model.Tutor.PitchTutor;
import Environment.Environment;
import java.util.List;

public class PitchTutorCommand extends TutorCommand
{
    private static final String TUTOR_NAME = "Pitch Tutor";
    private String returnValue;
    private int numQuestions;
    private String[] pitchRange;
    private final int QUESTION_NUM_POS = 0;
    private final int RANGE_POS = 1;
    private Boolean paramsValid;
    private String error;
    
    public PitchTutorCommand(final List<String> args) {
        this.numQuestions = 5;
        this.pitchRange = new String[] { "C4", "C5" };
        this.paramsValid = true;
        this.error = "";
        if (args != null) {
            switch (args.size()) {
                case 2: {
                    this.processRange(args.get(1));
                    this.processNumQuestions(args.get(0));
                    break;
                }
                case 1: {
                    if (args.get(0).charAt(0) == '[') {
                        this.processRange(args.get(0));
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
            this.returnValue = ((this.numQuestions == 1) ? ("Started Pitch Tutor with " + this.numQuestions + " question and pitch range " + this.pitchRange[0] + " to " + this.pitchRange[1]) : ("Started Pitch Tutor with " + this.numQuestions + " questions and pitch range " + this.pitchRange[0] + " to " + this.pitchRange[1]));
            this.returnValue += "\nGo to the Pitch Tutor tab to begin the tutor";
        }
        else {
            this.returnValue = this.error;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.paramsValid) {
            final PitchTutor pitchTutor = new PitchTutor();
            final PitchTutorController pitchTutorController = new PitchTutorController();
            pitchTutor.setOptions(new ArrayList<String>(Arrays.asList(String.valueOf(this.numQuestions), this.pitchRange[0], this.pitchRange[1])));
            final Options options = new PitchTutorOption(this.numQuestions, new ArrayList<String>(Arrays.asList(this.pitchRange)));
            final TutorDefinition pitchTutorDefn = new TutorDefinition("Pitch Tutor", pitchTutor, pitchTutorController, options);
            final boolean tutorValid = env.checkAndCreateTutor(pitchTutorDefn);
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
            this.error = "The number of questions is not valid, it must be a number between 1 and 1000";
        }
    }
    
    private void processRange(final String arg) {
        this.pitchRange = this.getRange(arg);
        if (this.pitchRange.length != 2 || !PitchTutorOption.pitchRangeValid(this.pitchRange[0], this.pitchRange[1])) {
            this.paramsValid = false;
            this.error = "The pitch range is not valid, it must be two notes and the second note must be higher than the first";
        }
    }
    
    private String[] getRange(String argToProcess) {
        argToProcess = argToProcess.replaceAll("\\s+|\\[|\\]", "");
        final String[] noteArray = argToProcess.split(",");
        for (int i = 0; i < noteArray.length; ++i) {
            try {
                final int midi = Integer.parseInt(noteArray[i]);
                noteArray[i] = NoteMap.getNoteString(midi);
            }
            catch (Exception e) {
                noteArray[i] = Command.defaultOctave(noteArray[i].substring(0, 1).toUpperCase() + noteArray[i].substring(1));
            }
        }
        return noteArray;
    }
}
