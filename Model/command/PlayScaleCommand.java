

package Model.command;

import Model.CommandMessages;
import java.util.Iterator;
import Model.Note.Scale.ScaleMode.ScaleMode;
import Model.Note.NoteMap;
import Model.Note.Scale.ScaleMap;
import java.util.Collections;
import Model.Note.Note;
import Model.Note.Scale.Scale;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.Collection;
import Model.command.ArgumentParsing.ArgType;
import Model.Note.unitDuration.UnitDurationInformation;
import java.util.ArrayList;
import Model.Note.unitDuration.UnitDuration;
import Model.Play.Play;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class PlayScaleCommand extends Command implements OnSuccess
{
    private List<String> returnArray;
    private boolean playScale;
    private Play.PlayStyle style;
    private UnitDuration unitDuration;
    private static final int NOTE_POS = 0;
    private static final int SCALE_POS = 1;
    private static final int PATTERN_POS = 0;
    private static final int OVERRIDE_POS = 1;
    private static final int UNIT_DURATION_POS = 2;
    private static final String ORDER_DIGITAL = "pattern";
    private static final String ORDER_BOTH = "both";
    private static final String ORDER_DESCENDING = "descending";
    private static final String ORDER_ASCENDING = "ascending";
    private static final String SWING_STYLE_SWING = "-s";
    private static final String TRIPLE_FEEL_OVERRIDE = "-t";
    private static final String SWING_STYLE_NORMAL = "-n";
    private static final String OUT_OF_RANGE_SCALE;
    private static final String INVALID_OVERRIDE;
    private final String invalidSwingOverride;
    private static final String INVALID_PLAY_SCALE_ORDER;
    private final String invalidOrder;
    private static final String ASCENDING = "ascending";
    private static final String DESCENDING = "descending";
    private static final String BOTH = "ascending then descending";
    private static final String SWING = "swing";
    private static final String STRAIGHT = "straight";
    private static final String TRIPLET_FEEL = "triplet feel";
    private String order;
    private String orderType;
    private String swingType;
    private String inputNote;
    private String scaleName;
    
    public PlayScaleCommand(final List<String> value) throws ArgumentException {
        this.returnArray = new ArrayList<String>();
        this.playScale = false;
        this.style = Play.PlayStyle.NONE;
        this.unitDuration = UnitDurationInformation.getUnitDuration();
        this.invalidSwingOverride = String.format(PlayScaleCommand.INVALID_OVERRIDE, "-s", "-n", "-t");
        this.invalidOrder = String.format(PlayScaleCommand.INVALID_PLAY_SCALE_ORDER, "ascending", "descending", "both");
        this.order = "ascending";
        this.orderType = "ascending";
        this.swingType = "straight";
        this.inputNote = "";
        this.scaleName = "";
        if (value.size() >= 2 && value.size() <= 5) {
            this.inputNote = value.get(0);
            this.scaleName = value.get(1).replace("\"", "").toLowerCase();
            final List<ArgType> types = new ArrayList<ArgType>();
            types.add(ArgType.NOTE_MIDI);
            types.add(ArgType.SCALE);
            types.add(ArgType.DIGITAL_PATTERN);
            types.add(ArgType.OVERRIDE);
            types.add(ArgType.UNIT_DURATION);
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
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
        if (this.playScale) {
            env.getPlay().playScale(this.returnArray, this.order, 0, this.style, this.unitDuration, false);
        }
    }
    
    private String correctReturnValue(final String scaleName, final String input, final String output, final String orderType, final String swingType) {
        return String.format("%s scale %s played in %s order and %s style: ", scaleName, Command.translate(input, output), orderType, swingType);
    }
    
    private String processOverride(final String override) {
        String error = "";
        if (override.equals("-s")) {
            this.style = Play.PlayStyle.SWING;
            this.swingType = "swing";
        }
        else if (override.equals("-n")) {
            this.style = Play.PlayStyle.NONE;
            this.swingType = "straight";
        }
        else if (override.equals("-t")) {
            this.style = Play.PlayStyle.BLUES;
            this.swingType = "triplet feel";
        }
        else {
            error = this.invalidSwingOverride;
        }
        return error;
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
        this.orderType = "pattern";
        return patterNotes;
    }
    
    private String processOrder(final String order) {
        String error = "";
        if (order.equalsIgnoreCase("ascending")) {
            this.order = "ascending";
            this.orderType = "ascending";
        }
        else if (order.equalsIgnoreCase("descending")) {
            this.order = "descending";
            this.orderType = "descending";
        }
        else if (order.equalsIgnoreCase("both")) {
            this.order = "both";
            this.orderType = "ascending then descending";
        }
        else {
            error = this.invalidOrder;
        }
        return error;
    }
    
    private String processScale() {
        this.playScale = true;
        String returnValue = this.correctReturnValue(this.scaleName, this.inputNote, Command.defaultNote(this.inputNote), this.orderType, this.swingType);
        if (Command.checkMidiInput(this.inputNote)) {
            returnValue += NoteMap.getMidiArray(Play.arrayForPlay(this.returnArray, this.order)).toString();
        }
        else {
            returnValue += Play.arrayForPlay(this.returnArray, this.order).toString();
        }
        return returnValue;
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Note note = requiredArgs.get(0);
        final Object scaleObject = requiredArgs.get(1);
        final List<String> overrides = optionalArgs.get(1);
        String error = "";
        Scale scale = null;
        ScaleMode mode = null;
        if (scaleObject instanceof Scale) {
            scale = (Scale)scaleObject;
        }
        else if (scaleObject instanceof ScaleMode) {
            mode = (ScaleMode)scaleObject;
        }
        if (overrides != null) {
            if (overrides.size() == 1) {
                error = this.processOverride(overrides.get(0));
            }
            else {
                error = this.invalidSwingOverride;
            }
        }
        final Object patternObject = optionalArgs.get(0);
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
                    error = String.format(error, PlayScaleCommand.OUT_OF_RANGE_SCALE);
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
        List<Note> notes;
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
        for (final Note patternNote : notes) {
            this.returnArray.add(patternNote.getNoteWithOctave());
        }
        UnitDuration unitDuration = optionalArgs.get(2);
        boolean unitDurationSet = true;
        if (unitDuration == null) {
            unitDurationSet = false;
            unitDuration = UnitDurationInformation.getUnitDuration();
        }
        this.unitDuration = unitDuration;
        if (error.equals("")) {
            String returnMessage = this.processScale();
            if (unitDurationSet) {
                returnMessage += String.format(". Using \"%1$s%2$s\" as note duration.", unitDuration.getUnitDurationName(), unitDuration.getDots());
            }
            return returnMessage;
        }
        throw new ArgumentException(error);
    }
    
    @Override
    public String getCommandName() {
        return "playScale";
    }
    
    static {
        OUT_OF_RANGE_SCALE = CommandMessages.getMessage("OUT_OF_RANGE_SCALE");
        INVALID_OVERRIDE = CommandMessages.getMessage("INVALID_SWING_STYLE_OVERRIDE");
        INVALID_PLAY_SCALE_ORDER = CommandMessages.getMessage("INVALID_PLAY_SCALE_ORDER");
    }
}
