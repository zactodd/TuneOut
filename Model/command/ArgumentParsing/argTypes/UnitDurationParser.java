

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.command.ArgumentParsing.ArgumentException;
import Model.Note.unitDuration.UnitDuration;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.unitDuration.UnitDurationMap;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class UnitDurationParser implements ArgumentTypeParser
{
    private static final String NO_UNIT_DURATION_FOUND;
    private final Integer argsNeeded;
    private final Integer durationPos;
    
    public UnitDurationParser() {
        this.argsNeeded = 1;
        this.durationPos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        String duration = args.get(this.durationPos).replace("\"", "");
        final Integer numberOfDots = UnitDurationMap.dotsInName(duration);
        duration = duration.substring(0, duration.length() - numberOfDots);
        UnitDuration unitDuration = UnitDurationMap.getUnitDurationByName(duration);
        if (unitDuration == null) {
            unitDuration = UnitDurationMap.getUnitDurationByDivider(duration);
        }
        if (unitDuration == null && (numberOfDots > 0 || duration.equals(""))) {
            unitDuration = UnitDurationInformation.getUnitDuration();
        }
        if (unitDuration != null) {
            unitDuration = new UnitDuration(unitDuration.getUnitDurationName(), unitDuration.getUnitDurationDivider(), true, numberOfDots);
        }
        if (unitDuration == null) {
            throw new ArgumentException(UnitDurationParser.NO_UNIT_DURATION_FOUND);
        }
        return new UnitDuration(unitDuration.getUnitDurationName(), unitDuration.getUnitDurationDivider(), true, numberOfDots);
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        if (argument.startsWith("\"") && argument.endsWith("\"")) {
            final String duration = argument.replace("\"", "");
            return UnitDurationMap.isValidName(duration.replace(".", "")) || duration.matches("\\p{Digit}+/\\p{Digit}+.{0,3}") || duration.matches(".{0,3}");
        }
        return false;
    }
    
    static {
        NO_UNIT_DURATION_FOUND = CommandMessages.getMessage("NO_UNIT_DURATION_FOUND");
    }
}
