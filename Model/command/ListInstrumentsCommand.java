

package Model.command;

import Environment.Environment;
import Model.CommandMessages;
import Model.instrument.InstrumentsMap;
import java.util.List;

public class ListInstrumentsCommand extends Command
{
    private final String fullListOverride = "-f";
    private final int argPosition = 0;
    private final boolean availableInstruments = true;
    private final boolean allInstruments = false;
    
    public ListInstrumentsCommand(final List<String> input) {
        if (input == null) {
            this.returnValue = InstrumentsMap.getList(true);
        }
        else if (input.get(0).equals("-f")) {
            this.returnValue = InstrumentsMap.getList(false);
        }
        else if (!input.get(0).equals("-f")) {
            this.returnValue = CommandMessages.getMessage("INCORRECT_OVERRIDE");
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
