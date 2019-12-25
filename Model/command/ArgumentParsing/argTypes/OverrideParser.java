// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing.argTypes;

import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class OverrideParser implements ArgumentTypeParser
{
    private final Integer overridePos;
    private final Integer argsNeeded;
    
    public OverrideParser() {
        this.overridePos = 0;
        this.argsNeeded = -1;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("(-([A-Za-z])){1}");
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        return args;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
}
