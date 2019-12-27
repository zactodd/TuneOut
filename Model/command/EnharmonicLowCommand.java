

package Model.command;

import Model.CommandMessages;
import Environment.Environment;
import Model.Note.Note;
import Model.Note.Enharmonic.EnharmonicMap;
import Model.Note.NoteMap;
import java.util.List;

public class EnharmonicLowCommand extends Command
{
    private static final int PARAM_COUNT = 1;
    private static final int POS = 0;
    private static final String NO_LOWER_EQUIVALENT;
    
    public EnharmonicLowCommand(final List<String> note) {
        if (note.size() == 1) {
            final String inputNote = this.integerErrorRaiser(note.get(0));
            final Note outputNote = EnharmonicMap.getLowerEquivalentEnharmonic(NoteMap.getNote(Command.defaultOctave(inputNote)));
            if (outputNote != null) {
                this.returnValue = Command.translateOctave(inputNote, outputNote.getNoteWithOctave());
            }
            if (this.returnValue.equals("")) {
                this.returnValue = EnharmonicLowCommand.NO_LOWER_EQUIVALENT;
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
    
    static {
        NO_LOWER_EQUIVALENT = CommandMessages.getMessage("NO_LOWER_EQUIVALENT");
    }
}
