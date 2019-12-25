// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing;

import Model.command.ArgumentParsing.argTypes.IntegerParser;
import Model.command.ArgumentParsing.argTypes.BeatParser;
import Model.command.ArgumentParsing.argTypes.PercussionParser;
import Model.command.ArgumentParsing.argTypes.UnitDurationParser;
import Model.command.ArgumentParsing.argTypes.TempoParser;
import Model.command.ArgumentParsing.argTypes.StringParser;
import Model.command.ArgumentParsing.argTypes.ScaleParser;
import Model.command.ArgumentParsing.argTypes.QuestionNumParser;
import Model.command.ArgumentParsing.argTypes.OverrideParser;
import Model.command.ArgumentParsing.argTypes.OctaveParser;
import Model.command.ArgumentParsing.argTypes.NoteMidiParser;
import Model.command.ArgumentParsing.argTypes.NoteParser;
import Model.command.ArgumentParsing.argTypes.MelodyParser;
import Model.command.ArgumentParsing.argTypes.InversionParser;
import Model.command.ArgumentParsing.argTypes.IntervalParser;
import Model.command.ArgumentParsing.argTypes.InstrumentParser;
import Model.command.ArgumentParsing.argTypes.DigitalPatternParser;
import Model.command.ArgumentParsing.argTypes.ChordParser;
import java.util.Iterator;
import Model.CommandMessages;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class ArgumentParser
{
    private static final String ERROR_MESSAGE = "Usage: %1$s(%2$s)%n%3$s";
    private static final String ERROR = "\t%1$s: %2$s";
    private static final String INCORRECT_OPTIONS;
    private List<Object> requiredList;
    private List<ArgType> allTypes;
    private Integer index;
    private static Map<ArgType, ArgumentTypeParser> argumentTypeParserMap;
    
    public ArgumentParser() {
        this.requiredList = null;
        this.allTypes = null;
        this.index = 0;
    }
    
    public Object getRequiredArg(final ArgType type) {
        Integer typeIndex = -1;
        for (int index = 0; index < this.allTypes.size(); ++index) {
            if (this.allTypes.get(index).equals(type)) {
                typeIndex = index;
                break;
            }
        }
        if (typeIndex != -1 && typeIndex < this.requiredList.size()) {
            return this.requiredList.get(typeIndex);
        }
        return null;
    }
    
    public Object getRequiredArg(final ArgType type, final List<String> args) throws ArgumentException {
        return ArgumentParser.argumentTypeParserMap.get(type).parseArg(args, this);
    }
    
    public String parseArgs(final List<String> requiredArgs, final List<ArgType> types, final OnSuccess onSuccess) throws ArgumentException {
        return this.parseArgs(requiredArgs, null, types, onSuccess);
    }
    
    public String parseArgs(final List<String> requiredArgs, final List<String> optionalArgs, final List<ArgType> types, final OnSuccess onSuccess) throws ArgumentException {
        this.allTypes = types;
        final List<Object> optionalList = new ArrayList<Object>();
        final Map<ArgType, String> errors = new HashMap<ArgType, String>();
        Integer typeCount = 0;
        this.index = 0;
        if (requiredArgs != null) {
            typeCount = this.parseRequiredArgs(requiredArgs, types, errors);
        }
        if (optionalArgs != null) {
            this.parseOptionalArgs(optionalArgs, types, typeCount, optionalList, errors);
        }
        if (!errors.isEmpty()) {
            throw new ArgumentException(this.parseErrors(onSuccess, types, errors));
        }
        return onSuccess.onSuccess(this.requiredList, optionalList);
    }
    
    private String parseErrors(final OnSuccess onSuccess, final List<ArgType> types, final Map<ArgType, String> errors) {
        String commandArgs = "";
        String warningList = "";
        boolean first = true;
        for (final ArgType type : types) {
            if (!first) {
                commandArgs += ", ";
            }
            commandArgs += CommandMessages.getMessage(type.name());
            first = false;
        }
        Integer count = 0;
        Integer errorsLength = errors.size();
        if (errorsLength > 1 && errors.containsKey(ArgType.NON_EXISTENT)) {
            errors.remove(ArgType.NON_EXISTENT);
            --errorsLength;
        }
        for (final Map.Entry<ArgType, String> errorEntry : errors.entrySet()) {
            String stringFormat = "\t%1$s: %2$s";
            ++count;
            if (!count.equals(errorsLength)) {
                stringFormat += "%n";
            }
            warningList += String.format(stringFormat, CommandMessages.getMessage(errorEntry.getKey().name()), errorEntry.getValue());
        }
        return String.format("Usage: %1$s(%2$s)%n%3$s", onSuccess.getCommandName(), commandArgs, warningList);
    }
    
    private Integer parseRequiredArgs(final List<String> requiredArgs, final List<ArgType> types, final Map<ArgType, String> errors) {
        this.requiredList = new ArrayList<Object>();
        final Integer requiredArgsSize = requiredArgs.size();
        Integer typeCount = 0;
        for (final ArgType type : types) {
            ++typeCount;
            final ArgumentTypeParser argumentTypeParser = ArgumentParser.argumentTypeParserMap.get(type);
            final List<String> argList = new ArrayList<String>();
            for (Integer argCount = 0; argCount < argumentTypeParser.totalArgsNeeded(); ++argCount) {
                if (this.index < requiredArgsSize) {
                    argList.add(requiredArgs.get(this.index));
                }
                ++this.index;
            }
            if (argumentTypeParser.totalArgsNeeded() == -1) {
                while (this.index < requiredArgs.size() && argumentTypeParser.matchType(requiredArgs.get(this.index))) {
                    argList.add(requiredArgs.get(this.index));
                    ++this.index;
                }
            }
            try {
                this.requiredList.add(argumentTypeParser.parseArg(argList, this));
            }
            catch (ArgumentException exp) {
                errors.put(type, exp.getMessage());
            }
            if (this.index.equals(requiredArgs.size())) {
                break;
            }
        }
        return typeCount;
    }
    
    private List<String> parseOptionalType(final ArgumentTypeParser argumentTypeParser, final List<String> optionalArgs) {
        final List<String> argList = new ArrayList<String>();
        if (argumentTypeParser.totalArgsNeeded() != -1) {
            for (Integer argCount = 0; argCount < argumentTypeParser.totalArgsNeeded(); ++argCount) {
                for (final String optionalArg : optionalArgs) {
                    if (argumentTypeParser.matchType(optionalArg)) {
                        argList.add(optionalArg);
                        break;
                    }
                }
            }
        }
        else {
            for (final String optionArg : optionalArgs) {
                if (argumentTypeParser.matchType(optionArg)) {
                    argList.add(optionArg);
                }
            }
        }
        return argList;
    }
    
    private void parseOptionalArgs(final List<String> optionalArgs, final List<ArgType> types, final Integer startIndex, final List<Object> optionalList, final Map<ArgType, String> errors) {
        Integer optionsUsed = 0;
        for (final ArgType type : types.subList(startIndex, types.size())) {
            final ArgumentTypeParser argumentTypeParser = ArgumentParser.argumentTypeParserMap.get(type);
            final List<String> argList = this.parseOptionalType(argumentTypeParser, optionalArgs);
            if (!argumentTypeParser.totalArgsNeeded().equals(argList.size())) {
                if (!argumentTypeParser.totalArgsNeeded().equals(-1) || argList.size() <= 0) {
                    optionalList.add(null);
                    continue;
                }
            }
            try {
                optionsUsed += argList.size();
                optionalList.add(argumentTypeParser.parseArg(argList, this));
            }
            catch (ArgumentException exp) {
                errors.put(type, exp.getMessage());
            }
        }
        if (optionsUsed != optionalArgs.size()) {
            Integer paramCount = 0;
            String parameters = "";
            for (final String optionalArg : optionalArgs) {
                if (!paramCount.equals(0)) {
                    parameters += ", ";
                }
                parameters += optionalArg;
                ++paramCount;
            }
            final String setOf = (paramCount > 1) ? "set of " : "";
            final String multiple = (paramCount > 1) ? "s" : "";
            errors.put(ArgType.NON_EXISTENT, String.format(ArgumentParser.INCORRECT_OPTIONS, parameters, setOf, multiple));
        }
    }
    
    static {
        INCORRECT_OPTIONS = CommandMessages.getMessage("INCORRECT_OPTIONS");
        ArgumentParser.argumentTypeParserMap = new HashMap<ArgType, ArgumentTypeParser>() {
            {
                ((HashMap<ArgType, ChordParser>)this).put(ArgType.CHORD, new ChordParser());
                ((HashMap<ArgType, DigitalPatternParser>)this).put(ArgType.DIGITAL_PATTERN, new DigitalPatternParser());
                ((HashMap<ArgType, InstrumentParser>)this).put(ArgType.INSTRUMENT, new InstrumentParser());
                ((HashMap<ArgType, IntervalParser>)this).put(ArgType.INTERVAL, new IntervalParser());
                ((HashMap<ArgType, InversionParser>)this).put(ArgType.INVERSION, new InversionParser());
                ((HashMap<ArgType, MelodyParser>)this).put(ArgType.MELODY, new MelodyParser());
                ((HashMap<ArgType, NoteParser>)this).put(ArgType.NOTE, new NoteParser());
                ((HashMap<ArgType, NoteMidiParser>)this).put(ArgType.NOTE_MIDI, new NoteMidiParser());
                ((HashMap<ArgType, OctaveParser>)this).put(ArgType.OCTAVE, new OctaveParser());
                ((HashMap<ArgType, OverrideParser>)this).put(ArgType.OVERRIDE, new OverrideParser());
                ((HashMap<ArgType, QuestionNumParser>)this).put(ArgType.QUESTION_NUM, new QuestionNumParser());
                ((HashMap<ArgType, ScaleParser>)this).put(ArgType.SCALE, new ScaleParser());
                ((HashMap<ArgType, StringParser>)this).put(ArgType.STRING, new StringParser());
                ((HashMap<ArgType, TempoParser>)this).put(ArgType.TEMPO, new TempoParser());
                ((HashMap<ArgType, UnitDurationParser>)this).put(ArgType.UNIT_DURATION, new UnitDurationParser());
                ((HashMap<ArgType, PercussionParser>)this).put(ArgType.PERCUSSION, new PercussionParser());
                ((HashMap<ArgType, BeatParser>)this).put(ArgType.BEAT, new BeatParser());
                ((HashMap<ArgType, IntegerParser>)this).put(ArgType.INTEGER, new IntegerParser());
            }
        };
    }
}
