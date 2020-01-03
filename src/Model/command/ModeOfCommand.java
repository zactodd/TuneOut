

package Model.command;

import Environment.Environment;
import Model.Note.Note;
import Model.Note.Scale.Scale;
import Model.Note.Scale.ScaleMode.ScaleModeMap;
import Model.Note.NoteMap;
import Model.Note.Scale.ScaleMap;
import Model.CommandMessages;
import java.util.List;

public class ModeOfCommand extends Command
{
    private final int notePos = 0;
    private final int scalePos = 1;
    private final int degreePos = 2;
    private final int totalArgs = 3;
    private final String scaleNotFound;
    private final String noteNotFound;
    private final String invalidDegree;
    private final String invalidParameters;
    private final String modeName = "%1$s %2$s";
    private final String noScaleModeFound;
    private final String scaleOutOfRange;
    
    public ModeOfCommand(final List<String> arg) {
        this.scaleNotFound = CommandMessages.getMessage("NO_SCALE");
        this.noteNotFound = CommandMessages.getMessage("NO_NOTE_FOUND");
        this.invalidDegree = CommandMessages.getMessage("INVALID_DEGREE");
        this.invalidParameters = String.format(CommandMessages.getMessage("INVALID_PARAMETERS"), 3, CommandMessages.getMessage("MODE_OF"));
        this.noScaleModeFound = CommandMessages.getMessage("NO_SCALE_MODE_FOUND");
        this.scaleOutOfRange = CommandMessages.getMessage("OUT_OF_RANGE_SCALE");
        if (arg.size() == 3) {
            final Scale parentScale = ScaleMap.getScale(arg.get(1).replace("\"", "").toLowerCase());
            final String inputNote = translate("C4", arg.get(0));
            final Note note = NoteMap.getNote(inputNote);
            String mode = "";
            int quality = -1;
            try {
                quality = Integer.parseInt(arg.get(2));
            }
            catch (NumberFormatException exp) {
                this.returnValue = this.invalidDegree;
            }
            if (parentScale == null) {
                this.returnValue = this.scaleNotFound;
            }
            else if (note == null) {
                this.returnValue = this.noteNotFound;
            }
            else if (this.returnValue.isEmpty()) {
                mode = ScaleModeMap.modeOf(parentScale, quality);
                if (mode != null) {
                    final Note rootNote = ScaleModeMap.getRootNote(parentScale, note, quality);
                    if (rootNote != null) {
                        final String translatedNote = translate(arg.get(0), rootNote.getNoteWithOctave());
                        this.returnValue = String.format("%1$s %2$s", translatedNote, mode);
                    }
                    else {
                        this.returnValue = this.scaleOutOfRange;
                    }
                }
            }
            if (this.returnValue.equals("") || mode == null) {
                this.returnValue = this.noScaleModeFound;
            }
        }
        else {
            this.returnValue = this.invalidParameters;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
