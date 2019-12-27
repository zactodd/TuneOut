

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.Note.Intervals.IntervalMap;
import Model.Note.Intervals.Interval;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class IntervalParser implements ArgumentTypeParser
{
    private static final String INVALID_INTERVAL;
    private static final String INVALID_INTERVAL_SEMITONE;
    private final Integer argsNeeded;
    private final Integer intervalPos;
    
    public IntervalParser() {
        this.argsNeeded = 1;
        this.intervalPos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final String intervalName = args.get(this.intervalPos);
        Interval interval = this.processIntervalName(intervalName);
        if (interval == null) {
            interval = this.processIntervalSemitone(intervalName);
        }
        if (interval == null) {
            throw new ArgumentException(String.format(IntervalParser.INVALID_INTERVAL, intervalName));
        }
        return interval;
    }
    
    private Interval processIntervalName(final String intervalName) {
        if (intervalName.startsWith("\"") && intervalName.endsWith("\"")) {
            final String interval = intervalName.replace("\"", "");
            return IntervalMap.getIntervalWithIntervalName(interval);
        }
        return null;
    }
    
    private Interval processIntervalSemitone(final String intervalName) throws ArgumentException {
        Interval interval;
        try {
            final Integer semitone = Integer.parseInt(intervalName);
            interval = IntervalMap.getIntervalWithSemitone(semitone);
            if (interval == null) {
                throw new ArgumentException(IntervalParser.INVALID_INTERVAL_SEMITONE);
            }
        }
        catch (NumberFormatException exp) {
            interval = null;
        }
        return interval;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        INVALID_INTERVAL = CommandMessages.getMessage("INVALID_INTERVAL");
        INVALID_INTERVAL_SEMITONE = CommandMessages.getMessage("INVALID_INTERVAL_SEMITONE");
    }
}
