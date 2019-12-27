

package Model.command;

import Environment.Environment;
import Model.Note.Enharmonic.EnharmonicMap;
import Model.Note.NoteMap;
import java.util.List;

public class HasEnharmonicCommand extends Command
{
    private final int paramCount = 1;
    private final int pos = 0;
    
    public HasEnharmonicCommand(final List<String> note) {
        final String theNote = this.integerErrorRaiser(note.get(0));
        if (note.size() == 1) {
            final Boolean result = EnharmonicMap.checkSimpleEnharmonic(NoteMap.getNote(Command.defaultOctave(theNote)));
            this.returnValue = result.toString();
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
