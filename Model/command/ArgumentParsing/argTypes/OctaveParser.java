// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing.argTypes;

import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class OctaveParser implements ArgumentTypeParser
{
    private final int octavePos = 0;
    private final int argsNeeded = 1;
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("oct\\p{Digit}+");
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        String octave = args.get(0);
        octave = octave.replace("oct", "");
        return Integer.parseInt(octave);
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return 1;
    }
}
