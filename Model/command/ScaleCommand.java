// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Model.CommandMessages;
import Model.Note.Scale.ScaleMode.ScaleMode;
import Environment.Environment;
import Model.Note.Scale.ScaleMap;
import java.util.Collections;
import Model.Note.Scale.Scale;
import java.util.Iterator;
import Model.Note.Note;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.Collection;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class ScaleCommand extends Command implements OnSuccess
{
    private static final int PARAM_COUNT = 2;
    private static final int NOTE_POS = 0;
    private static final int SCALE_POS = 1;
    private static final int OVERRIDE_POS = 0;
    private static final int DIGITAL_PATTERN_POS = 1;
    private static final String NOTE = "C4";
    private static final String MIDI = "60";
    private static final String OVERRIDE_KEY = "-m";
    private static final String ORDER_BOTH = "both";
    private static final String ORDER_DESCENDING = "descending";
    private static final String ORDER_ASCENDING = "ascending";
    private static final String ASCENDING = "ascending";
    private static final String DESCENDING = "descending";
    private static final String BOTH = "ascending then descending";
    private static final String INVALID_SCALE_OVERRIDE;
    private static final String OUT_OF_RANGE_SCALE;
    private static final String INCORRECT_OVERRIDE;
    private String orderType;
    private static final String INVALID_PLAY_SCALE_ORDER;
    private final String invalidOrder;
    private String noteOrMidi;
    
    public ScaleCommand(final List<String> value) throws ArgumentException {
        this.orderType = "";
        this.invalidOrder = String.format(ScaleCommand.INVALID_PLAY_SCALE_ORDER, "ascending", "descending", "both");
        this.noteOrMidi = "";
        if (value.size() >= 2 && value.size() <= 4) {
            this.noteOrMidi = value.get(0);
            final List<ArgType> types = new ArrayList<ArgType>();
            types.add(ArgType.NOTE_MIDI);
            types.add(ArgType.SCALE);
            types.add(ArgType.OVERRIDE);
            types.add(ArgType.DIGITAL_PATTERN);
            final List<String> requiredArgs = new ArrayList<String>();
            requiredArgs.addAll(value.subList(0, 2));
            final List<String> optionalArgs = new ArrayList<String>();
            try {
                optionalArgs.addAll(value.subList(2, value.size()));
            }
            catch (IllegalArgumentException ex) {}
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(requiredArgs, optionalArgs, types, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    private void parseScale(final boolean override, final List<Note> notes) {
        final List<String> returnArray = new ArrayList<String>();
        final boolean isMidi = Command.checkMidiInput(this.noteOrMidi);
        for (final Note note : notes) {
            if (override) {
                if (isMidi) {
                    returnArray.add(note.getNoteWithOctave());
                }
                else {
                    returnArray.add(note.getMidiNumber().toString());
                }
            }
            else {
                returnArray.add(Command.translate(Command.defaultOctave(this.noteOrMidi), note.getNoteWithOctave()));
            }
            this.returnValue = returnArray.toString();
        }
    }
    
    private List<Note> processPattern(final List<Integer> pattern, final Scale scale, final Note rootNote) {
        scale.setNotesInScale(rootNote.getNoteWithOctave());
        final Integer maxPattern = Collections.max((Collection<? extends Integer>)pattern);
        final Integer sizeOfScale = scale.getNotesInScale().size();
        Integer octavesUsed = maxPattern / sizeOfScale;
        if (maxPattern % sizeOfScale != 0) {
            ++octavesUsed;
        }
        final List<Note> notesOfScale = scale.getNotesInScale(octavesUsed, false);
        List<Note> patterNotes = null;
        if (notesOfScale != null) {
            patterNotes = ScaleMap.reorderScaleToPattern(notesOfScale, pattern);
        }
        return patterNotes;
    }
    
    private String processOrder(final String order) {
        String error = "";
        if (order.equalsIgnoreCase("ascending")) {
            this.orderType = "ascending";
        }
        else if (order.equalsIgnoreCase("descending")) {
            this.orderType = "descending";
        }
        else if (order.equalsIgnoreCase("both")) {
            this.orderType = "ascending then descending";
        }
        else {
            error = this.invalidOrder;
        }
        return error;
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Note note = requiredArgs.get(0);
        final Object scaleObject = requiredArgs.get(1);
        String error = "";
        Scale scale = null;
        ScaleMode mode = null;
        if (scaleObject instanceof Scale) {
            scale = (Scale)scaleObject;
        }
        else if (scaleObject instanceof ScaleMode) {
            mode = (ScaleMode)scaleObject;
        }
        final List<String> overrides = optionalArgs.get(0);
        boolean override = false;
        if (overrides != null && overrides.size() == 1 && overrides.get(0).equalsIgnoreCase("-m")) {
            override = true;
        }
        else if (overrides != null) {
            error = ScaleCommand.INVALID_SCALE_OVERRIDE;
        }
        final Object patternObject = optionalArgs.get(1);
        List<Note> patternNotes = null;
        if (patternObject != null) {
            if (patternObject instanceof List) {
                final List<Integer> patternList = (List<Integer>)patternObject;
                if (scale != null) {
                    patternNotes = this.processPattern(patternList, scale, note);
                }
                else {
                    patternNotes = this.processPattern(patternList, mode.getParentScale(), note);
                }
                if (patternNotes == null) {
                    if (!error.equals("")) {
                        error += "%n%1$s";
                    }
                    error += "%1$s";
                    error = String.format(error, ScaleCommand.OUT_OF_RANGE_SCALE);
                }
            }
            else if (patternObject instanceof String) {
                final String order = (String)patternObject;
                if (!error.equals("")) {
                    error += "%n%1$s";
                }
                error += "%1$s";
                error = String.format(error, this.processOrder(order));
            }
        }
        List<Note> notes = null;
        if (patternNotes != null) {
            notes = patternNotes;
        }
        else if (scale != null) {
            notes = scale.getNotesInScale();
        }
        else {
            notes = mode.getScaleMode(note);
        }
        if (this.orderType.equals("descending")) {
            notes = ScaleMap.descendScale(notes);
        }
        else if (this.orderType.equals("ascending then descending")) {
            notes = ScaleMap.ascendDescendScale(notes);
        }
        this.parseScale(override, notes);
        if (error.equals("")) {
            return this.returnValue;
        }
        throw new ArgumentException(error);
    }
    
    @Override
    public String getCommandName() {
        return "Scale";
    }
    
    static {
        INVALID_SCALE_OVERRIDE = CommandMessages.getMessage("INVALID_SCALE_OVERRIDE");
        OUT_OF_RANGE_SCALE = CommandMessages.getMessage("OUT_OF_RANGE_SCALE");
        INCORRECT_OVERRIDE = CommandMessages.getMessage("INVALID_OVERRIDE_SCALE");
        INVALID_PLAY_SCALE_ORDER = CommandMessages.getMessage("INVALID_PLAY_SCALE_ORDER");
    }
}
