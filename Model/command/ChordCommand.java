// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import java.util.Iterator;
import Model.Note.Chord.ChordMap;
import Model.Note.Note;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.List;
import java.util.ArrayList;
import Model.CommandMessages;
import java.util.Collection;
import Model.command.ArgumentParsing.OnSuccess;

public class ChordCommand extends Command implements OnSuccess
{
    private final int PARAM_COUNT = 2;
    private final int OPTIONAL_PARAM_COUNT = 3;
    private final int ROOT_NOTE = 0;
    private final int INVERSION_POS = 0;
    private final int CHORD = 1;
    private Integer inversion;
    private final String INVALID_CHORD;
    private final String OUT_OF_RANGE_CHORD;
    private final String OUT_OF_RANGE_INVERSION;
    private final String OUT_OF_RANGE_MIDI;
    private Boolean hasInversion;
    protected Collection<Integer> chordMidi;
    protected String chordName;
    protected String noteOrMidi;
    
    protected ChordCommand() {
        this.INVALID_CHORD = CommandMessages.getMessage("INVALID_CHORD");
        this.OUT_OF_RANGE_CHORD = CommandMessages.getMessage("OUT_OF_RANGE_CHORD");
        this.OUT_OF_RANGE_INVERSION = CommandMessages.getMessage("OUT_OF_RANGE_INVERSION");
        this.OUT_OF_RANGE_MIDI = CommandMessages.getMessage("OUT_OF_RANGE_MIDI");
        this.hasInversion = false;
        this.chordMidi = new ArrayList<Integer>();
    }
    
    public ChordCommand(final List<String> args) throws ArgumentException {
        this.INVALID_CHORD = CommandMessages.getMessage("INVALID_CHORD");
        this.OUT_OF_RANGE_CHORD = CommandMessages.getMessage("OUT_OF_RANGE_CHORD");
        this.OUT_OF_RANGE_INVERSION = CommandMessages.getMessage("OUT_OF_RANGE_INVERSION");
        this.OUT_OF_RANGE_MIDI = CommandMessages.getMessage("OUT_OF_RANGE_MIDI");
        this.hasInversion = false;
        this.chordMidi = new ArrayList<Integer>();
        if (args.size() >= 2 && args.size() <= 3) {
            this.noteOrMidi = args.get(0);
            this.chordName = args.get(1).replace("\"", "").toLowerCase();
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.NOTE_MIDI);
            argTypes.add(ArgType.CHORD);
            argTypes.add(ArgType.INVERSION);
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
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final List<Note> chord = requiredArgs.get(1);
        Integer inversion = optionalArgs.get(0);
        String returnMessage = "";
        if (inversion == null) {
            inversion = 0;
        }
        final List<Note> invertChord = ChordMap.invertChord(chord, inversion);
        for (final Note chordNote : invertChord) {
            this.chordMidi.add(chordNote.getMidiNumber());
        }
        if (Command.checkMidiInput(this.noteOrMidi)) {
            returnMessage = this.chordMidi.toString();
        }
        else {
            this.noteOrMidi = Command.defaultNote(this.noteOrMidi);
            returnMessage = invertChord.toString();
        }
        return returnMessage;
    }
    
    @Override
    public String getCommandName() {
        return "chord";
    }
}
