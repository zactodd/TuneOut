

package Model.command;

import Environment.Environment;
import Model.Note.Scale.KeySignature;
import Model.Note.Chord.ChordMap;
import Model.CommandMessages;
import java.util.List;

public class FunctionOfCommand extends Command
{
    private final int paramCount = 4;
    private final int chordNotePos = 0;
    private final int chordPos = 1;
    private final int scaleNotePos = 2;
    private final int scalePos = 3;
    private final String noFunctionFound;
    private final String expectNoteNoOctaveOrMidi;
    private final String invalidKey;
    private final String invalidChord;
    
    public FunctionOfCommand(final List<String> args) {
        this.noFunctionFound = CommandMessages.getMessage("NO_FUNCTION_FOUND");
        this.expectNoteNoOctaveOrMidi = CommandMessages.getMessage("EXPECT_NOTE_NO_OCTAVE_OR_MIDI");
        this.invalidKey = CommandMessages.getMessage("INVALID_KEY");
        this.invalidChord = CommandMessages.getMessage("INVALID_CHORD");
        if (args.size() == 4) {
            final String chordNote = args.get(0);
            final String chord = args.get(1).replaceAll("\"", "");
            final String keyNote = args.get(2);
            final String key = args.get(3).replaceAll("\"", "");
            if (chordNote.matches(".*\\d+.*") || keyNote.matches(".*\\d+.*")) {
                this.returnValue = this.expectNoteNoOctaveOrMidi;
            }
            else if (!ChordMap.isValidChordName(chord.toLowerCase())) {
                this.returnValue = this.invalidChord;
            }
            else if (!KeySignature.isValidKeyName(key)) {
                this.returnValue = this.invalidKey;
            }
            else {
                this.returnValue = ChordMap.functionOf(chordNote, chord.toLowerCase(), keyNote, key.toLowerCase());
                if (this.returnValue == null) {
                    this.returnValue = this.noFunctionFound;
                }
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
