

package Model.command;

import Environment.Environment;
import Model.CommandMessages;
import Model.instrument.InstrumentsMap;
import Model.instrument.InstrumentInformation;
import java.util.List;

public class GetInstrumentCommand extends Command
{
    private String returnValue;
    private final String withGroupOverride = "-g";
    private final Integer overridePos;
    private final int argCount = 1;
    private Integer id;
    
    public GetInstrumentCommand(final List<String> arg) {
        this.overridePos = 0;
        this.id = InstrumentInformation.getInstrumentId();
        if (arg == null) {
            new InstrumentsMap();
            this.returnValue = InstrumentsMap.getInstrument(this.id).getInstrumentName();
        }
        else if (arg.size() == 1 && arg.get(this.overridePos).equals("-g")) {
            this.returnValue = "midi code: " + Integer.toString(this.id) + "\n";
            final StringBuilder append = new StringBuilder().append(this.returnValue);
            new InstrumentsMap();
            this.returnValue = append.append(InstrumentsMap.getInstrument(this.id).toString()).toString();
        }
        else if (arg.size() == 1 && !arg.get(this.overridePos).equals("-g")) {
            this.returnValue = CommandMessages.getMessage("INCORRECT_OVERRIDE");
        }
        else {
            this.returnValue = CommandMessages.getMessage("INCORRECT_SYNTAX");
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
