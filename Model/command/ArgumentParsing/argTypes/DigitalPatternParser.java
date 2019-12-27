

package Model.command.ArgumentParsing.argTypes;

import java.util.ArrayList;
import Model.command.ArgumentParsing.ArgumentException;
import Model.DigitalPattern.DigitalPattern;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class DigitalPatternParser implements ArgumentTypeParser
{
    private final Integer argsNeeded;
    private final Integer patternPos;
    private static final String ORDER_BOTH = "both";
    private static final String ORDER_DESCENDING = "descending";
    private static final String ORDER_ASCENDING = "ascending";
    
    public DigitalPatternParser() {
        this.argsNeeded = 1;
        this.patternPos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final String digitalPattern = args.get(this.patternPos).replace("\"", "");
        final List<Integer> pattern = this.processPattern(digitalPattern);
        if (DigitalPattern.hasPatternName(digitalPattern)) {
            return this.processPattern(DigitalPattern.getDigitalPattern(digitalPattern));
        }
        if (pattern != null) {
            return pattern;
        }
        return digitalPattern;
    }
    
    private List<Integer> processPattern(final String pattern) {
        final String[] items = pattern.split(" ");
        final List<Integer> itemList = new ArrayList<Integer>();
        Boolean validPattern = true;
        try {
            for (final String item : items) {
                final Integer singleItem = Integer.parseInt(item);
                if (singleItem < 1 || singleItem > 96) {
                    validPattern = false;
                }
                else {
                    itemList.add(singleItem);
                }
            }
        }
        catch (NumberFormatException exp) {
            validPattern = false;
        }
        if (validPattern) {
            return itemList;
        }
        return null;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        if (argument.startsWith("\"") && argument.endsWith("\"")) {
            final String pattern = argument.replace("\"", "");
            return DigitalPattern.hasPatternName(pattern) || this.matchPattern(pattern) || pattern.equalsIgnoreCase("ascending") || pattern.equalsIgnoreCase("descending") || pattern.equalsIgnoreCase("both");
        }
        return false;
    }
    
    private Boolean matchPattern(final String pattern) {
        final String[] items = pattern.split(" ");
        try {
            for (final String item : items) {
                final Integer singleItem = Integer.parseInt(item);
                if (singleItem < 1 || singleItem > 96) {
                    return false;
                }
            }
        }
        catch (NumberFormatException exp) {
            return false;
        }
        return true;
    }
}
