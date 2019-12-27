

package Model.command;

import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.Settings.TempoInformation;
import Model.Note.Note;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.Collection;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.Note.unitDuration.UnitDuration;
import Model.command.ArgumentParsing.OnSuccess;

public class PlayCommand extends Command implements OnSuccess
{
    private boolean playNote;
    private int tempo;
    private UnitDuration unitDuration;
    private Integer midi;
    private String inputNote;
    private static final String PLAY_NOTE_MSG = "Playing %1$s";
    
    public PlayCommand(final List<String> value) throws ArgumentException {
        this.playNote = false;
        if (value.size() >= 1 && value.size() <= 4) {
            this.inputNote = value.get(0);
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.NOTE_MIDI);
            argTypes.add(ArgType.TEMPO);
            argTypes.add(ArgType.UNIT_DURATION);
            final List<String> requiredArgs = new ArrayList<String>();
            requiredArgs.addAll(value.subList(0, 1));
            final List<String> optionalArgs = new ArrayList<String>();
            try {
                optionalArgs.addAll(value.subList(1, value.size()));
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
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
        if (this.playNote) {
            env.getPlay().playNote(this.midi, this.tempo, this.unitDuration, false);
        }
    }
    
    private String correctReturnValue(final String input) {
        return String.format("Playing %1$s", input);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Note note = requiredArgs.get(0);
        this.playNote = true;
        this.midi = note.getMidiNumber();
        Integer bpm = optionalArgs.get(0);
        if (bpm == null) {
            bpm = TempoInformation.getTempInBpm();
        }
        UnitDuration unitDuration = optionalArgs.get(1);
        boolean unitDurationSet = true;
        if (unitDuration == null) {
            unitDurationSet = false;
            unitDuration = UnitDurationInformation.getUnitDuration();
        }
        this.tempo = bpm;
        this.unitDuration = unitDuration;
        String returnMessage = this.correctReturnValue(this.inputNote);
        if (unitDurationSet) {
            returnMessage += String.format(". Using \"%1$s%2$s\" as note duration.", unitDuration.getUnitDurationName(), unitDuration.getDots());
        }
        return returnMessage;
    }
    
    @Override
    public String getCommandName() {
        return "Play";
    }
}
