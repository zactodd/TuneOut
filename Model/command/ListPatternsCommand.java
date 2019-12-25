// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import java.util.Iterator;
import java.util.Map;
import Model.DigitalPattern.DigitalPattern;
import Model.CommandMessages;

public class ListPatternsCommand extends Command
{
    private final String EMPTY_PATTERN;
    
    public ListPatternsCommand() {
        this.EMPTY_PATTERN = CommandMessages.getMessage("EMPTY_PATTERN");
        final Map<String, String> patterns = DigitalPattern.getPatternHashMap();
        String listPatterns = "";
        for (final Map.Entry<String, String> entry : patterns.entrySet()) {
            final String patternName = entry.getKey();
            final String digitalPattern = entry.getValue();
            listPatterns = listPatterns + patternName + ":\n\t" + digitalPattern + "\n";
        }
        if (listPatterns.equals("")) {
            this.returnValue = this.EMPTY_PATTERN;
        }
        else {
            this.returnValue = listPatterns;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
