

package Model.command.TutorCommands;

import Model.Tutor.Options;
import Controller.TutorController;
import Model.Tutor.Tutor;
import Model.Tutor.TutorDefinition;
import Model.Tutor.IntervalTutorOption;
import java.util.Arrays;
import Controller.IntervalTutorController;
import Model.Tutor.IntervalTutor;
import Environment.Environment;
import java.util.ArrayList;
import java.util.List;

public class IntervalTutorCommand extends TutorCommand
{
    private int numQuestions;
    private final int QUESTION_NUM_POS = 0;
    private final int OCTAVES_NUM_POS = 1;
    private final int INTERVAL_TYPE_POS = 2;
    private final int RANGE_POS = 3;
    private Boolean commandValid;
    private String error;
    private static final String TUTOR_NAME = "Interval Tutor";
    private Integer numOfOctaves;
    private String intervalType;
    private List<String> intervalRangeToProcess;
    private String returnValue;
    private final String MAJOR_ONLY = "Major";
    private final String MINOR_ONLY = "Minor";
    private final String ALL_INTERVALS = "All";
    
    public IntervalTutorCommand(final List<String> args) {
        this.numQuestions = 5;
        this.commandValid = true;
        this.error = "";
        this.numOfOctaves = 1;
        this.intervalType = "All";
        this.intervalRangeToProcess = new ArrayList<String>();
        if (args != null) {
            switch (args.size()) {
                case 3: {
                    this.processNumQuestions(args.get(0));
                    this.processNumOctaves(args.get(1));
                    this.processIntervalType(args.get(2));
                    break;
                }
                case 2: {
                    if (args.get(0).startsWith("oct")) {
                        this.processNumOctaves(args.get(0));
                        this.processIntervalType(args.get(1));
                        break;
                    }
                    if (args.get(1).startsWith("oct")) {
                        this.processNumQuestions(args.get(0));
                        this.processNumOctaves(args.get(1));
                        break;
                    }
                    this.processNumQuestions(args.get(0));
                    this.processIntervalType(args.get(1));
                    break;
                }
                case 1: {
                    if (args.get(0).startsWith("oct")) {
                        this.processNumOctaves(args.get(0));
                        break;
                    }
                    if (args.get(0).replace("\"", "").equalsIgnoreCase("Major") || args.get(0).replace("\"", "").equalsIgnoreCase("Minor") || args.get(0).replace("\"", "").equalsIgnoreCase("All")) {
                        this.processIntervalType(args.get(0));
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
        if (this.commandValid) {
            this.returnValue = "Started Interval Tutor with " + this.numQuestions + " question";
            this.returnValue += ((this.numQuestions == 1) ? "" : "s");
            this.returnValue += " and all of the currently available ";
            this.returnValue += (this.intervalType.equalsIgnoreCase("All") ? "" : (this.intervalType.toLowerCase() + " "));
            this.returnValue = this.returnValue + "intervals up to " + this.numOfOctaves + " octave";
            this.returnValue += ((this.numOfOctaves == 1) ? "" : "s");
            this.returnValue += ".\nGo to the Interval Tutor tab to begin the tutor.";
        }
        else {
            this.returnValue = this.error;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.commandValid) {
            final IntervalTutor intervalTutor = new IntervalTutor();
            final IntervalTutorController intervalTutorController = new IntervalTutorController();
            intervalTutor.setOptions(this.intervalRangeToProcess);
            final Options options = new IntervalTutorOption(this.numQuestions, Arrays.asList(this.numOfOctaves.toString(), this.intervalType));
            final TutorDefinition intervalTutorDefn = new TutorDefinition("Interval Tutor", intervalTutor, intervalTutorController, options);
            final boolean tutorValid = env.checkAndCreateTutor(intervalTutorDefn);
            if (!tutorValid) {
                this.returnValue = TutorCommand.tutorAlreadyOpen();
            }
        }
        env.setResponse(this.returnValue);
    }
    
    private void processIntervalType(String argToProcess) {
        argToProcess = argToProcess.replace("\"", "");
        if (argToProcess.equalsIgnoreCase("Major")) {
            this.intervalType = "Major";
        }
        else if (argToProcess.equalsIgnoreCase("Minor")) {
            this.intervalType = "Minor";
        }
        else if (argToProcess.equalsIgnoreCase("All")) {
            this.intervalType = "All";
        }
        else {
            if (!this.error.equals("")) {
                this.error += "\n";
            }
            this.error += "Invalid interval type, try \"all\", \"major\", or \"minor\"";
            this.commandValid = false;
        }
    }
    
    private void processNumQuestions(final String arg) {
        if (Options.numQuestionsValid(arg)) {
            this.numQuestions = Integer.parseInt(arg);
        }
        else {
            this.commandValid = false;
            if (!this.error.equals("")) {
                this.error += "\n";
            }
            this.error += "The number of questions is not valid, it must be a number between 1 and 1000";
        }
    }
    
    private void processNumOctaves(String arg) {
        boolean paramValid = false;
        if (arg.contains("oct")) {
            arg = arg.replace("oct", "");
            if (IntervalTutorOption.numOctavesValid(arg)) {
                this.numOfOctaves = Integer.parseInt(arg);
                paramValid = true;
            }
        }
        if (!paramValid) {
            this.commandValid = false;
            if (!this.error.equals("")) {
                this.error += "\n";
            }
            this.error += "The number of octaves is not valid, it must be a parameter between oct1 and oct2";
        }
    }
}
