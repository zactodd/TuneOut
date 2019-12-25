// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.TutorCommands;

import Model.CommandMessages;
import Model.Tutor.Options;
import Controller.TutorController;
import Model.Tutor.Tutor;
import Model.Tutor.TutorDefinition;
import Model.Tutor.ScaleModeTutorOption;
import Controller.ScaleModeTutorController;
import Model.Tutor.ScaleModeTutor;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class ScaleModeTutorCommand extends TutorCommand implements OnSuccess
{
    private static final String TUTOR_NAME = "Scale Mode Tutor";
    private static final int QUESTIONS_NUM_POS = 0;
    private static final int TYPE_POS = 1;
    private final String successMessage = "Started %1$s with %2$s question%3$s and %4$s modes.%nGo to the %1$s tab to begin the tutor.";
    private static final String INVALID_MODE_TUTOR_TYPE;
    private String type;
    private int numQuestions;
    private String error;
    private List<String> validTypes;
    
    public ScaleModeTutorCommand(final List<String> args) throws ArgumentException {
        this.type = "both";
        this.numQuestions = 5;
        this.error = "";
        this.validTypes = new ArrayList<String>(Arrays.asList("major", "melodic minor", "both"));
        if (args != null && args.size() <= 2) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.QUESTION_NUM);
            argTypes.add(ArgType.STRING);
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(null, args, argTypes, this);
        }
        else if (args != null && args.size() > 2) {
            this.wrongNumOfParamErrorRaiser();
        }
        else {
            this.returnValue = this.formattedSuccessMessage();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        final ScaleModeTutor scaleModeTutor = new ScaleModeTutor();
        final ScaleModeTutorController scaleModeTutorController = new ScaleModeTutorController();
        final Options options = new ScaleModeTutorOption(this.numQuestions, this.type);
        final TutorDefinition scaleModeTutorDefn = new TutorDefinition("Scale Mode Tutor", scaleModeTutor, scaleModeTutorController, options);
        final boolean tutorValid = env.checkAndCreateTutor(scaleModeTutorDefn);
        if (!tutorValid) {
            this.returnValue = TutorCommand.tutorAlreadyOpen();
        }
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Integer numQuestions = optionalArgs.get(0);
        if (numQuestions != null) {
            this.numQuestions = numQuestions;
        }
        if (optionalArgs.get(1) != null) {
            this.type = optionalArgs.get(1);
            if (!this.validTypes.contains(this.type)) {
                this.error = ScaleModeTutorCommand.INVALID_MODE_TUTOR_TYPE;
            }
        }
        if (!this.error.equals("")) {
            throw new ArgumentException(this.error);
        }
        return this.formattedSuccessMessage();
    }
    
    @Override
    public String getCommandName() {
        return "scaleModeTutor";
    }
    
    private String formattedSuccessMessage() {
        return String.format("Started %1$s with %2$s question%3$s and %4$s modes.%nGo to the %1$s tab to begin the tutor.", "Scale Mode Tutor", this.numQuestions, (this.numQuestions == 1) ? "" : "s", this.type);
    }
    
    static {
        INVALID_MODE_TUTOR_TYPE = CommandMessages.getMessage("INVALID_MODE_TUTOR_TYPE");
    }
}
