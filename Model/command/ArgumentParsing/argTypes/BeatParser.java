

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class BeatParser implements ArgumentTypeParser
{
    private final Integer stringPos;
    private final Integer argsNeeded;
    private static final String INVALID_BEAT;
    
    public BeatParser() {
        this.stringPos = 0;
        this.argsNeeded = 1;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("[x|X|-]+");
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final String stringArg = args.get(this.stringPos).replace("\"", "");
        if (this.matchType(stringArg)) {
            return stringArg;
        }
        throw new ArgumentException(BeatParser.INVALID_BEAT);
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        INVALID_BEAT = CommandMessages.getMessage("INVAILD_BEAT");
    }
}
