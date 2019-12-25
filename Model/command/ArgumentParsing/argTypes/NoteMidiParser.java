// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.Note.Note;
import Model.command.ArgumentParsing.ArgumentException;
import Model.Note.NoteMap;
import Model.command.Command;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class NoteMidiParser implements ArgumentTypeParser
{
    private static final String NOTE = "C4";
    private static final String OUT_OF_RANGE_MIDI;
    private static final String NO_NOTE_FOUND;
    private final Integer notePos;
    private final Integer argsNeeded;
    
    public NoteMidiParser() {
        this.notePos = 0;
        this.argsNeeded = 1;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final String noteWithOctave = Command.translate("C4", args.get(this.notePos));
        final Note note = NoteMap.getNote(noteWithOctave);
        if (note != null) {
            return note;
        }
        if (args.get(this.notePos).matches("[+-]?\\d+")) {
            throw new ArgumentException(NoteMidiParser.OUT_OF_RANGE_MIDI);
        }
        throw new ArgumentException(NoteMidiParser.NO_NOTE_FOUND);
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        OUT_OF_RANGE_MIDI = CommandMessages.getMessage("OUT_OF_RANGE_MIDI");
        NO_NOTE_FOUND = CommandMessages.getMessage("NO_NOTE_FOUND");
    }
}
