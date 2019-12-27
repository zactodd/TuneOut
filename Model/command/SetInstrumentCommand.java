

package Model.command;

import Model.CommandMessages;
import Model.instrument.InstrumentInformation;
import Model.instrument.Instrument;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class SetInstrumentCommand extends Command implements OnSuccess
{
    private static final String SAME_INSTRUMENT;
    private final int baseCommandSize = 1;
    private final int baseCommandPos = 0;
    
    public SetInstrumentCommand(final List<String> arg) throws ArgumentException {
        if (arg.size() == 1) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.INSTRUMENT);
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(arg, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Instrument instrument = requiredArgs.get(0);
        final Instrument currentInstrument = InstrumentInformation.getInstrument();
        if (currentInstrument.equals(instrument)) {
            throw new ArgumentException(SetInstrumentCommand.SAME_INSTRUMENT);
        }
        InstrumentInformation.setInstrumentId(instrument.getInstrumentNumber(), true);
        return instrument.getInstrumentName() + " set up instead of " + currentInstrument.getInstrumentName();
    }
    
    @Override
    public String getCommandName() {
        return "setInstrument";
    }
    
    static {
        SAME_INSTRUMENT = CommandMessages.getMessage("SAME_INSTRUMENT");
    }
}
