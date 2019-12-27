

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.Percussion.Percussion;
import Model.command.ArgumentParsing.ArgumentException;
import Model.Percussion.PercussionMap;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class PercussionParser implements ArgumentTypeParser
{
    private static final String NO_PERCUSSION_FOUND;
    private final Integer pos;
    private final Integer argsNeeded;
    
    public PercussionParser() {
        this.pos = 0;
        this.argsNeeded = 1;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        Percussion percussion = PercussionMap.getPercussion(args.get(this.pos).replace("\"", ""));
        if (percussion == null) {
            try {
                final Integer midiNumber = Integer.valueOf(args.get(this.pos));
                percussion = PercussionMap.getPercussion(midiNumber);
            }
            catch (NumberFormatException exp) {
                percussion = null;
            }
        }
        if (percussion == null) {
            throw new ArgumentException(PercussionParser.NO_PERCUSSION_FOUND);
        }
        return percussion;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        NO_PERCUSSION_FOUND = CommandMessages.getMessage("NO_PERCUSSION_FOUND");
    }
}
