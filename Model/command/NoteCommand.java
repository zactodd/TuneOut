

package Model.command;

import Environment.Environment;
import Model.Note.NoteMap;
import Model.CommandMessages;
import java.util.List;

public class NoteCommand extends Command
{
    final int PARAM_COUNT = 1;
    final int NOTE_POS = 0;
    final String OUT_OF_RANGE_MIDI;
    
    public NoteCommand(final List<String> midiNumber) {
        this.OUT_OF_RANGE_MIDI = CommandMessages.getMessage("OUT_OF_RANGE_MIDI");
        if (midiNumber.size() == 1) {
            final Integer midi = Integer.parseInt(midiNumber.get(0));
            this.returnValue = NoteMap.getNoteString(midi);
            if (this.returnValue == null) {
                this.returnValue = this.OUT_OF_RANGE_MIDI;
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
