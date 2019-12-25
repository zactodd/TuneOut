// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.Settings.TempoInformation;
import Model.Percussion.Percussion;
import Model.Percussion.PercussionMap;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.Collection;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.Note.unitDuration.UnitDuration;
import Model.command.ArgumentParsing.OnSuccess;

public class PlayPercussionCommand extends Command implements OnSuccess
{
    private boolean playPercussion;
    private int tempo;
    private UnitDuration unitDuration;
    private Integer midiNumber;
    private String inputInstrument;
    private static final String PLAY_MSG = "Playing percussion instrument %1$s";
    
    public PlayPercussionCommand(final List<String> value) throws ArgumentException {
        this.playPercussion = false;
        if (value.size() >= 1 && value.size() <= 4) {
            this.inputInstrument = value.get(0);
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.PERCUSSION);
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
        if (this.playPercussion) {
            env.getPlay().playPercussion(this.midiNumber, this.tempo, this.unitDuration, false);
        }
    }
    
    private String correctReturnValue(final String input) {
        Percussion inst = PercussionMap.getPercussion(input.replace("\"", ""));
        if (inst == null) {
            final Integer midiNumber = Integer.valueOf(input);
            inst = PercussionMap.getPercussion(midiNumber);
        }
        return String.format("Playing percussion instrument %1$s", inst.getInstrument());
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Percussion percussion = requiredArgs.get(0);
        this.playPercussion = true;
        this.midiNumber = percussion.getMidi();
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
        String returnMessage = this.correctReturnValue(this.inputInstrument);
        if (unitDurationSet) {
            returnMessage += String.format(". Using \"%1$s%2$s\" as duration between percussion sounds.", unitDuration.getUnitDurationName(), unitDuration.getDots());
        }
        return returnMessage;
    }
    
    @Override
    public String getCommandName() {
        return "PlayPercussion";
    }
}
