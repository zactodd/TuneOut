

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.Note.Scale.ScaleMode.ScaleMode;
import Model.Note.Scale.Scale;
import Model.Note.Scale.ScaleMode.ScaleModeMap;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgType;
import Model.Note.Note;
import Model.Note.Scale.ScaleMap;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class ScaleParser implements ArgumentTypeParser
{
    private static final String NO_SCALE;
    private static final String OUT_OF_RANGE_SCALE;
    private final Integer argsNeeded;
    private final Integer scalePos;
    
    public ScaleParser() {
        this.argsNeeded = 1;
        this.scalePos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final String scaleName = args.get(this.scalePos).replace("\"", "").toLowerCase();
        final Scale scale = ScaleMap.getScale(scaleName);
        final Note note = (Note)parser.getRequiredArg(ArgType.NOTE_MIDI);
        if (scale != null && note != null) {
            scale.setNotesInScale(note.getNoteWithOctave());
            if (scale.isNull()) {
                throw new ArgumentException(ScaleParser.OUT_OF_RANGE_SCALE);
            }
            return scale;
        }
        else {
            if (note == null) {
                return null;
            }
            final ScaleMode scaleMode = ScaleModeMap.getScaleMode(scaleName);
            if (scaleMode == null) {
                throw new ArgumentException(ScaleParser.NO_SCALE);
            }
            final Scale parentScale = scaleMode.getParentScale();
            parentScale.setNotesInScale(note.getNoteWithOctave());
            if (!parentScale.isNull()) {
                return scaleMode;
            }
            throw new ArgumentException(ScaleParser.OUT_OF_RANGE_SCALE);
        }
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        NO_SCALE = CommandMessages.getMessage("NO_SCALE");
        OUT_OF_RANGE_SCALE = CommandMessages.getMessage("OUT_OF_RANGE_SCALE");
    }
}
