

package Model.command.TutorCommands;

import Model.CommandMessages;
import Model.Tutor.Options;
import Controller.TutorController;
import Model.Tutor.Tutor;
import Model.Tutor.TutorDefinition;
import Model.Tutor.ScaleTutorOption;
import Controller.ScaleTutorController;
import Model.Tutor.ScaleTutor;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class ScaleTutorCommand extends TutorCommand implements OnSuccess
{
    private static final String TUTOR_NAME = "Scale Recognition Tutor";
    private static final String ORDER_ASC = "ascending order";
    private static final String ORDER_DES = "descending order";
    private static final String ORDER_BTH = "ascending then descending order";
    private static final String INVAILD_SCALE_OCT;
    private final String successMessage = "Started %1$s with %2$s scale%3$s and %4$s octave%5$s in %6$s with %7$s scale types.%nGo to the %1$s tab to begin the tutor.";
    private static final String INVALID_PLAY_SCALE_OVERRIDE;
    private final String overrideError;
    private List<String> validTypes;
    private static final String INVALID_SCALE_TUTOR_TYPE;
    private String returnValue;
    private String error;
    private Boolean paramsValid;
    private static final int SCALES_NUM_POS = 0;
    private static final int OCTAVES_NUM_POS = 1;
    private static final int ORDER_POS = 2;
    private static final int TYPE_POS = 3;
    private int numScales;
    private int numOctaves;
    private String inTexOrder;
    private String order;
    private String type;
    
    public ScaleTutorCommand(final List<String> args) throws ArgumentException {
        this.overrideError = String.format(ScaleTutorCommand.INVALID_PLAY_SCALE_OVERRIDE, "-d", "-b", "-a");
        this.validTypes = new ArrayList<String>(Arrays.asList("all", "scales", "major modes", "minor modes"));
        this.returnValue = "";
        this.error = "";
        this.paramsValid = true;
        this.numScales = 5;
        this.numOctaves = 1;
        this.inTexOrder = "ascending order";
        this.order = "-a";
        this.type = "all";
        if (args != null && args.size() <= 4) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.QUESTION_NUM);
            argTypes.add(ArgType.OCTAVE);
            argTypes.add(ArgType.OVERRIDE);
            argTypes.add(ArgType.STRING);
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(null, args, argTypes, this);
        }
        else if (args != null && args.size() > 4) {
            this.wrongNumOfParamErrorRaiser();
        }
        else {
            this.returnValue = String.format("Started %1$s with %2$s scale%3$s and %4$s octave%5$s in %6$s with %7$s scale types.%nGo to the %1$s tab to begin the tutor.", "Scale Recognition Tutor", this.numScales, (this.numScales == 1) ? "" : "s", this.numOctaves, (this.numOctaves == 1) ? "" : "s", this.inTexOrder, this.type);
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.paramsValid) {
            final ScaleTutor scaleTutor = new ScaleTutor();
            final ScaleTutorController scaleTutorController = new ScaleTutorController();
            scaleTutor.setOptions(new ArrayList<String>(Arrays.asList(String.valueOf(this.numScales), String.valueOf(this.numOctaves), this.order)));
            final Options options = new ScaleTutorOption(this.numScales, this.numOctaves, this.order, this.type);
            final TutorDefinition scaleTutorDefn = new TutorDefinition("Scale Recognition Tutor", scaleTutor, scaleTutorController, options);
            final boolean tutorValid = env.checkAndCreateTutor(scaleTutorDefn);
            if (!tutorValid) {
                this.returnValue = TutorCommand.tutorAlreadyOpen();
            }
        }
        env.setResponse(this.returnValue);
    }
    
    private void processOrder(final String arg) {
        if (ScaleTutorOption.orderValid(arg)) {
            this.order = arg;
            if (this.order.equals("-a")) {
                this.inTexOrder = "ascending order";
            }
            else if (this.order.equals("-d")) {
                this.inTexOrder = "descending order";
            }
            else if (this.order.equals("-b")) {
                this.inTexOrder = "ascending then descending order";
            }
        }
        else {
            this.paramsValid = false;
            if (!this.error.equals("")) {
                this.error += "%n";
            }
            this.error += "%1$s";
            this.error = String.format(this.error, this.overrideError);
        }
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Integer numQuestions = optionalArgs.get(0);
        final Integer octave = optionalArgs.get(1);
        if (optionalArgs.get(3) != null) {
            this.type = optionalArgs.get(3);
            if (!this.validTypes.contains(this.type)) {
                this.error = ScaleTutorCommand.INVALID_SCALE_TUTOR_TYPE;
            }
        }
        if (numQuestions != null) {
            this.numScales = numQuestions;
        }
        if (octave != null && octave > 0 && octave <= 4) {
            this.numOctaves = octave;
        }
        else if (octave != null) {
            this.error = ScaleTutorCommand.INVAILD_SCALE_OCT;
        }
        final List<String> overrides = optionalArgs.get(2);
        if (overrides != null) {
            if (overrides.size() == 1) {
                this.processOrder(overrides.get(0));
            }
            else {
                if (!this.error.equals("")) {
                    this.error += "%n%1$s";
                }
                this.error += "%1$s";
                this.error = String.format(this.error, this.overrideError);
            }
        }
        if (!this.error.equals("")) {
            throw new ArgumentException(this.error);
        }
        return String.format("Started %1$s with %2$s scale%3$s and %4$s octave%5$s in %6$s with %7$s scale types.%nGo to the %1$s tab to begin the tutor.", "Scale Recognition Tutor", this.numScales, (this.numScales == 1) ? "" : "s", this.numOctaves, (this.numOctaves == 1) ? "" : "s", this.inTexOrder, this.type);
    }
    
    @Override
    public String getCommandName() {
        return "scaleTutor";
    }
    
    static {
        INVAILD_SCALE_OCT = String.format(CommandMessages.getMessage("INVAILD_SCALE_OCT"), 4);
        INVALID_PLAY_SCALE_OVERRIDE = CommandMessages.getMessage("INVALID_PLAY_SCALE_OVERRIDE");
        INVALID_SCALE_TUTOR_TYPE = CommandMessages.getMessage("INVALID_SCALE_TUTOR_TYPE");
    }
}
