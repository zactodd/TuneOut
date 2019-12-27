

package Model.command.ArgumentParsing.argTypes;

import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class IntegerParser implements ArgumentTypeParser
{
    private final Integer argsNeeded;
    private final Integer integerPos;
    
    public IntegerParser() {
        this.argsNeeded = 1;
        this.integerPos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final Integer integer = Integer.parseInt(args.get(this.integerPos));
        return integer;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("\\d+");
    }
}
