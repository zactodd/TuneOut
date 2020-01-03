

package Model.command;

import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import Model.Note.unitDuration.UnitDurationInformation;
import java.util.List;
import Model.Note.unitDuration.UnitDuration;
import Model.command.ArgumentParsing.OnSuccess;

public class PlayIntervalCommand extends IntervalCommand implements OnSuccess
{
    private final Integer durationPos;
    private boolean playInterval;
    private UnitDuration unitDuration;
    
    public PlayIntervalCommand(final List<String> args) throws ArgumentException {
        this.durationPos = 0;
        this.playInterval = false;
        this.unitDuration = UnitDurationInformation.getUnitDuration();
        if (args.size() >= 2 && args.size() <= 3) {
            this.setInputNote(args.get(0));
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.NOTE_MIDI);
            argTypes.add(ArgType.INTERVAL);
            argTypes.add(ArgType.UNIT_DURATION);
            final List<String> requiredArgs = new ArrayList<String>();
            requiredArgs.addAll(args.subList(0, 2));
            final List<String> optionalArgs = new ArrayList<String>();
            try {
                optionalArgs.addAll(args.subList(2, args.size()));
            }
            catch (IllegalArgumentException ex) {}
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(requiredArgs, optionalArgs, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        String returnMessage = super.onSuccess(requiredArgs, optionalArgs);
        final UnitDuration unitDuration = optionalArgs.get(this.durationPos);
        if (unitDuration != null) {
            this.unitDuration = unitDuration;
            returnMessage += String.format(". Using \"%1$s%2$s\" as note duration.", unitDuration.getUnitDurationName(), unitDuration.getDots());
        }
        return returnMessage;
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
        if (this.playInterval) {
            env.getPlay().playTwoNotes(0, this.note.getMidiNumber(), 3, this.nextNote.getMidiNumber(), this.unitDuration);
        }
    }
    
    @Override
    protected String correctOutput(final String inputNote, final String translatedNote, final String outputNote) {
        this.playInterval = true;
        return "Played " + translate(inputNote, translatedNote) + " then " + translate(inputNote, outputNote);
    }
    
    @Override
    public String getCommandName() {
        return "playInterval";
    }
}
