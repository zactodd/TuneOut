

package Model.command;

import Environment.Environment;
import Model.Note.Chord.ChordMap;
import Model.Note.Scale.KeySignature;
import Model.CommandMessages;
import java.util.List;

public class ChordFunctionCommand extends Command
{
    private final int paramCount = 3;
    private final int functionPos = 0;
    private final int notePos = 1;
    private final int scalePos = 2;
    private final String expectNoteNoOctaveOrMidi;
    private final String invalidKey;
    
    public ChordFunctionCommand(final List<String> args) {
        this.expectNoteNoOctaveOrMidi = CommandMessages.getMessage("EXPECT_NOTE_NO_OCTAVE_OR_MIDI");
        this.invalidKey = CommandMessages.getMessage("INVALID_KEY");
        if (args.size() == 3) {
            final String function = args.get(0);
            final String note = args.get(1);
            final String key = args.get(2).replaceAll("\"", "");
            if (note.matches(".*\\d+.*")) {
                this.returnValue = this.expectNoteNoOctaveOrMidi;
            }
            else if (!KeySignature.isValidKeyName(key)) {
                this.returnValue = this.invalidKey;
            }
            else {
                this.returnValue = ChordMap.chordFunction(function, note, key.toLowerCase());
            }
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
