

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.Note.Note;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgType;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class InversionParser implements ArgumentTypeParser
{
    private static final String OUT_OF_RANGE_INVERSION;
    private final Integer argsNeeded;
    private final Integer inversionPos;
    
    public InversionParser() {
        this.argsNeeded = 1;
        this.inversionPos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final Integer inversion = Integer.parseInt(args.get(this.inversionPos));
        final List<Note> chord = (List<Note>)parser.getRequiredArg(ArgType.CHORD);
        if (inversion <= 0 || (chord != null && inversion > chord.size() - 1)) {
            throw new ArgumentException(InversionParser.OUT_OF_RANGE_INVERSION);
        }
        return inversion;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("-?\\p{Digit}+");
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        OUT_OF_RANGE_INVERSION = CommandMessages.getMessage("OUT_OF_RANGE_INVERSION");
    }
}
