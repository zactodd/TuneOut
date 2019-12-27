

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import java.util.Iterator;
import Model.command.ArgumentParsing.ArgumentException;
import Model.Note.Chord.ChordMap;
import Model.command.ArgumentParsing.ArgType;
import Model.Note.Note;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class ChordParser implements ArgumentTypeParser
{
    private static final String INVALID_CHORD;
    private static final String OUT_OF_RANGE_CHORD;
    private final Integer argsNeeded;
    private final Integer chordPos;
    
    public ChordParser() {
        this.argsNeeded = 1;
        this.chordPos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final String chordName = args.get(this.chordPos).replace("\"", "").toLowerCase();
        final Note note = (Note)parser.getRequiredArg(ArgType.NOTE_MIDI);
        if (ChordMap.isValidChordName(chordName) && note != null) {
            final List<Note> chord = ChordMap.notesInChord(note, chordName);
            for (final Note chordNote : chord) {
                if (chordNote == null) {
                    throw new ArgumentException(ChordParser.OUT_OF_RANGE_CHORD);
                }
            }
            return chord;
        }
        if (note == null) {
            return null;
        }
        throw new ArgumentException(ChordParser.INVALID_CHORD);
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        INVALID_CHORD = CommandMessages.getMessage("INVALID_CHORD");
        OUT_OF_RANGE_CHORD = CommandMessages.getMessage("OUT_OF_RANGE_CHORD");
    }
}
