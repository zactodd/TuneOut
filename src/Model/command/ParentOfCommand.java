

package Model.command;

import Environment.Environment;
import Model.Note.Note;
import Model.Note.Scale.Scale;
import Model.Note.Scale.ScaleMap;
import Model.Note.NoteMap;
import Model.Note.Scale.ScaleMode.ScaleModeMap;
import Model.CommandMessages;
import java.util.List;

public class ParentOfCommand extends Command
{
    private final Integer scaleModePos;
    private final Integer notePos;
    private final Integer paraCount;
    private final String scaleNotFound;
    private final String noteNotFound;
    private final String incorrectNumberArg;
    private final String scaleModeNotMapping;
    private final String scaleModeParent = "%1$s %2$s";
    
    public ParentOfCommand(final List<String> arg) {
        this.scaleModePos = 1;
        this.notePos = 0;
        this.paraCount = 2;
        this.scaleNotFound = CommandMessages.getMessage("NO_SCALE_MODE_FOUND");
        this.noteNotFound = CommandMessages.getMessage("NO_NOTE_FOUND");
        this.incorrectNumberArg = CommandMessages.getMessage("INVALID_PARAMETERS");
        this.scaleModeNotMapping = CommandMessages.getMessage("OUT_OF_RANGE_SCALE_MODE");
        if (arg.size() == this.paraCount) {
            final String modeName = arg.get(this.scaleModePos).replace("\"", "");
            final Scale parentScale = ScaleModeMap.parentOf(modeName);
            final String inputNote = translate("C4", arg.get(this.notePos));
            final Note note = NoteMap.getNote(inputNote);
            if (parentScale == null) {
                this.returnValue = this.scaleNotFound;
            }
            else if (note == null) {
                this.returnValue = this.noteNotFound;
            }
            else {
                final Note ionianRootNote = ScaleModeMap.getIonianRootNote(parentScale, note, modeName);
                if (ionianRootNote == null) {
                    this.returnValue = this.scaleModeNotMapping;
                }
                else {
                    final String translatedNote = translate(arg.get(this.notePos), ionianRootNote.getNoteWithOctave());
                    this.returnValue = String.format("%1$s %2$s", translatedNote, ScaleMap.getScaleName(parentScale));
                }
            }
        }
        else {
            this.returnValue = String.format(this.incorrectNumberArg, this.paraCount, arg.size());
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
