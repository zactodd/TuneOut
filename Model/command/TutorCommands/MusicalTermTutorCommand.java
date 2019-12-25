// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.TutorCommands;

import Model.Tutor.Options;
import Controller.TutorController;
import Model.Tutor.Tutor;
import Model.Tutor.TutorDefinition;
import Model.Tutor.MusicalTermTutorOption;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import Controller.MusicalTermTutorController;
import Model.Tutor.MusicalTermTutor;
import Environment.Environment;
import java.util.List;

public class MusicalTermTutorCommand extends TutorCommand
{
    private static final String TUTOR_NAME = "Musical Term Tutor";
    private String returnValue;
    private int numQuestions;
    private final int QUESTION_NUM_POS = 0;
    private Boolean commandValid;
    private String error;
    
    public MusicalTermTutorCommand(final List<String> args) {
        this.numQuestions = 5;
        this.commandValid = true;
        this.error = "";
        if (args != null) {
            switch (args.size()) {
                case 1: {
                    this.processNumQuestions(args.get(0));
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    this.wrongNumOfParamErrorRaiser();
                    break;
                }
            }
        }
        if (this.commandValid) {
            this.returnValue = "Started Musical Term Tutor with " + this.numQuestions + " question";
            this.returnValue += ((this.numQuestions == 1) ? "" : "s");
            this.returnValue += "\nGo to the Musical Term Tutor tab to begin the tutor";
        }
        else {
            this.returnValue = this.error;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.commandValid) {
            final MusicalTermTutor tutor = new MusicalTermTutor();
            final MusicalTermTutorController tutorController = new MusicalTermTutorController();
            tutor.setOptions(new ArrayList<String>(Arrays.asList(String.valueOf(this.numQuestions))));
            final Options options = new MusicalTermTutorOption(this.numQuestions);
            final TutorDefinition tutorDefinition = new TutorDefinition("Musical Term Tutor", tutor, tutorController, options);
            final boolean tutorValid = env.checkAndCreateTutor(tutorDefinition);
            if (!tutorValid) {
                this.returnValue = TutorCommand.tutorAlreadyOpen();
            }
        }
        env.setResponse(this.returnValue);
    }
    
    private void processNumQuestions(final String arg) {
        if (Options.numQuestionsValid(arg)) {
            this.numQuestions = Integer.parseInt(arg);
        }
        else {
            this.commandValid = false;
            this.error = "The number of questions is not valid, it must be a number between 1 and 1000";
        }
    }
}
