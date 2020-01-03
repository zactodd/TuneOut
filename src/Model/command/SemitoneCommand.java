

package Model.command;

import Environment.Environment;
import Model.Note.NoteMap;
import java.util.List;

public class SemitoneCommand extends Command
{
    private static final int PARAM_COUNT = 2;
    private static final int NOTE_POS = 0;
    private static final int SEMITONE_POS = 1;
    private static final String NO_SEMITONE_MSG = "Could not find note after going down/up semitone";
    
    public SemitoneCommand(final List<String> args) {
        if (args.size() == 2) {
            final int semitone = Integer.parseInt(args.get(1));
            final String inputNote = args.get(0);
            this.returnValue = translate(inputNote, NoteMap.getSemitone(defaultNote(inputNote), semitone));
            if (this.returnValue == null) {
                this.returnValue = "Could not find note after going down/up semitone";
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
