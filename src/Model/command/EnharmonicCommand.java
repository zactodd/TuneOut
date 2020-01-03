

package Model.command;

import Environment.Environment;
import Model.Note.Note;
import Model.CommandMessages;
import Model.Note.Enharmonic.EnharmonicMap;
import Model.Note.NoteMap;
import java.util.List;

public class EnharmonicCommand extends Command
{
    private static final int PARAM_COUNT = 1;
    private static final int POS = 0;
    
    public EnharmonicCommand(final List<String> arg) {
        final String inputNote = this.integerErrorRaiser(arg.get(0));
        if (arg.size() == 1) {
            final Note note = EnharmonicMap.getEquivalentEnharmonic(NoteMap.getNote(defaultOctave(inputNote)));
            if (note != null) {
                this.returnValue = translateOctave(inputNote, note.getNoteWithOctave());
            }
            if (this.returnValue.equals("")) {
                this.returnValue = CommandMessages.getMessage("NO_SIMPLE_EQUIVALENT");
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
