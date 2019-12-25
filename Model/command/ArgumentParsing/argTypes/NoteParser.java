// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.Note.Note;
import Model.Note.NoteMap;
import Model.command.Command;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class NoteParser implements ArgumentTypeParser
{
    private static final String NOTE = "C4";
    private static final String NO_CORRESPOND_MIDI;
    private static final String INCORRECT_SYNTAX;
    private final Integer notePos;
    private final Integer argsNeeded;
    
    public NoteParser() {
        this.notePos = 0;
        this.argsNeeded = 1;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        Note note = null;
        if (args.get(this.notePos).matches("[+-]?\\d+")) {
            throw new ArgumentException(NoteParser.INCORRECT_SYNTAX);
        }
        final String noteWithOctave = Command.translate("C4", args.get(this.notePos));
        note = NoteMap.getNote(noteWithOctave);
        if (note == null) {
            throw new ArgumentException(NoteParser.NO_CORRESPOND_MIDI);
        }
        return note;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        NO_CORRESPOND_MIDI = CommandMessages.getMessage("NO_CORRESPOND_MIDI");
        INCORRECT_SYNTAX = CommandMessages.getMessage("INCORRECT_SYNTAX");
    }
}
