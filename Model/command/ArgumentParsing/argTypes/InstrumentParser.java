

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.instrument.Instrument;
import Model.command.ArgumentParsing.ArgumentException;
import Model.instrument.InstrumentsMap;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class InstrumentParser implements ArgumentTypeParser
{
    private static final String NON_EXISTENT_INSTRUMENT;
    private static final String OUT_OF_RANGE_INSTRUMENT;
    private static final String UNAVAILABLE_INSTRUMENT;
    private final int instrumentPos = 0;
    private final int argsNeeded = 1;
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final String instrumentString = args.get(0);
        Instrument instrument;
        if (instrumentString.startsWith("\"")) {
            instrument = InstrumentsMap.getInstrument(instrumentString.replace("\"", ""));
            if (instrument == null) {
                throw new ArgumentException(InstrumentParser.NON_EXISTENT_INSTRUMENT);
            }
        }
        else {
            final Integer instrumentId = Integer.parseInt(instrumentString);
            if (instrumentId < 0 || instrumentId > 127) {
                throw new ArgumentException(InstrumentParser.OUT_OF_RANGE_INSTRUMENT);
            }
            instrument = InstrumentsMap.getInstrument(instrumentId);
        }
        if (!instrument.getAvailability()) {
            throw new ArgumentException(InstrumentParser.UNAVAILABLE_INSTRUMENT);
        }
        return instrument;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return 1;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("[+-]?\\d+") || (argument.startsWith("\"") && argument.endsWith("\""));
    }
    
    static {
        NON_EXISTENT_INSTRUMENT = CommandMessages.getMessage("NON_EXISTENT_INSTRUMENT");
        OUT_OF_RANGE_INSTRUMENT = CommandMessages.getMessage("OUT_OF_RANGE_INSTRUMENT");
        UNAVAILABLE_INSTRUMENT = CommandMessages.getMessage("UNAVAILABLE_INSTRUMENT");
    }
}
