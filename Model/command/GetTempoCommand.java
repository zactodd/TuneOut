

package Model.command;

import Environment.Environment;
import Model.Note.Settings.TempoInformation;

public class GetTempoCommand extends Command
{
    public GetTempoCommand() {
        this.returnValue = Integer.toString(TempoInformation.getTempInBpm());
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
