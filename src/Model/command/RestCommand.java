

package Model.command;

import Environment.Environment;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.Settings.TempoInformation;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.Note.unitDuration.UnitDuration;
import Model.command.ArgumentParsing.OnSuccess;

public class RestCommand extends Command implements OnSuccess
{
    private int tempo;
    private UnitDuration unitDuration;
    private boolean playRest;
    private String returnValue;
    
    public RestCommand(List<String> args) throws ArgumentException {
        this.playRest = false;
        if (args == null) {
            args = new ArrayList<String>();
        }
        if (args.size() <= 3) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.TEMPO);
            argTypes.add(ArgType.UNIT_DURATION);
            final ArgumentParser argumentParser = new ArgumentParser();
            final List<String> optionalArgs = new ArrayList<String>();
            try {
                optionalArgs.addAll(args.subList(0, args.size()));
            }
            catch (IllegalArgumentException ex) {}
            this.returnValue = argumentParser.parseArgs(null, args, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        this.playRest = true;
        Integer bpm = optionalArgs.get(0);
        if (bpm == null) {
            bpm = TempoInformation.getTempInBpm();
        }
        UnitDuration unitDuration = optionalArgs.get(1);
        if (unitDuration == null) {
            unitDuration = UnitDurationInformation.getUnitDuration();
        }
        this.tempo = bpm;
        this.unitDuration = unitDuration;
        return this.correctReturnValue(unitDuration, this.tempo);
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
        if (this.playRest) {
            env.getPlay().playNote(-1, this.tempo, this.unitDuration, false);
        }
    }
    
    private String correctReturnValue(final UnitDuration ud, final int tempo) {
        String nameOfDuration = ud.getUnitDurationName();
        final Integer numberOfDots = ud.getNumberOfDots();
        if (numberOfDots == 1) {
            nameOfDuration = "dotted " + nameOfDuration;
        }
        else if (numberOfDots == 2) {
            nameOfDuration = "double-dotted " + nameOfDuration;
        }
        else if (numberOfDots == 3) {
            nameOfDuration = "triple-dotted " + nameOfDuration;
        }
        return "Rest for " + nameOfDuration + " duration with " + tempo + " bpm tempo";
    }
    
    @Override
    public String getCommandName() {
        return "Rest";
    }
}
