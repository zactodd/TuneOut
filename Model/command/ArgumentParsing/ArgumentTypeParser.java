

package Model.command.ArgumentParsing;

import java.util.List;

public interface ArgumentTypeParser
{
    Object parseArg(final List<String> p0, final ArgumentParser p1) throws ArgumentException;
    
    Integer totalArgsNeeded();
    
    default Boolean matchType(final String argument) {
        return true;
    }
}
