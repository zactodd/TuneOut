

package Model.command;

import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.Collection;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import Model.Note.unitDuration.UnitDurationInformation;
import java.util.List;
import Model.Note.unitDuration.UnitDuration;
import Model.command.ArgumentParsing.OnSuccess;

public class PlayChordCommand extends ChordCommand implements OnSuccess
{
    private static final String PLAY_ARPEGGIATE = "-a";
    private static final String PLAY_SIMULTANEOUS = "-s";
    private static final String ARPEGGIATE = "arpeggiated";
    private static final String SIMULTANEOUS = "simultaneously";
    private static String WRONG_PAR_MSG;
    boolean playChord;
    private final Integer notePos;
    private final Integer chordPos;
    private final Integer overridePos;
    private final Integer durationPos;
    private String playType;
    private UnitDuration unitDuration;
    
    public PlayChordCommand(final List<String> args) throws ArgumentException {
        this.playChord = false;
        this.notePos = 0;
        this.chordPos = 1;
        this.overridePos = 1;
        this.durationPos = 2;
        this.playType = "simultaneously";
        this.unitDuration = UnitDurationInformation.getUnitDuration();
        this.noteOrMidi = args.get(this.notePos);
        this.chordName = args.get(this.chordPos).replace("\"", "").toLowerCase();
        if (args.size() >= 2 && args.size() <= 5) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.NOTE_MIDI);
            argTypes.add(ArgType.CHORD);
            argTypes.add(ArgType.INVERSION);
            argTypes.add(ArgType.OVERRIDE);
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
    public void execute(final Environment env) {
        if (this.returnValue.contains("[")) {
            this.playChord = true;
        }
        if (this.playChord && this.playType.equals("simultaneously")) {
            this.returnValue = String.format("Playing chord %s %s %s: %s", this.noteOrMidi, this.chordName, "simultaneously", this.returnValue);
            env.getPlay().playChordSimultaneous((ArrayList)this.chordMidi, 0, this.unitDuration, false);
        }
        else if (this.playChord && this.playType.equals("arpeggiated")) {
            this.returnValue = String.format("Playing chord %s %s %s: %s", this.noteOrMidi, this.chordName, "arpeggiated", this.returnValue);
            env.getPlay().playChordArpeggiated((ArrayList)this.chordMidi, 0, this.unitDuration);
        }
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        String returnMessage = super.onSuccess(requiredArgs, optionalArgs);
        String error = "";
        final List<String> overrides = optionalArgs.get(this.overridePos);
        if (overrides != null) {
            if (overrides.size() == 1) {
                final String overrideParam = overrides.get(0);
                if (overrideParam.equals("-a")) {
                    this.playType = "arpeggiated";
                }
                else if (overrideParam.equals("-s")) {
                    this.playType = "simultaneously";
                }
                else {
                    error = PlayChordCommand.WRONG_PAR_MSG;
                }
            }
            else {
                error = PlayChordCommand.WRONG_PAR_MSG;
            }
        }
        final UnitDuration unitDuration = optionalArgs.get(this.durationPos);
        if (unitDuration != null) {
            this.unitDuration = unitDuration;
            returnMessage += String.format(". Using \"%1$s%2$s\" as note duration.", unitDuration.getUnitDurationName(), unitDuration.getDots());
        }
        if (error.equals("")) {
            return returnMessage;
        }
        throw new ArgumentException(error);
    }
    
    @Override
    public String getCommandName() {
        return "playChord";
    }
    
    static {
        PlayChordCommand.WRONG_PAR_MSG = "Please use -a as the final paramter to play arpeggiated, or -s to play simultaneously";
    }
}
