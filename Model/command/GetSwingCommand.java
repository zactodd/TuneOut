

package Model.command;

import Environment.Environment;
import Model.Note.Settings.SwingMap;

public class GetSwingCommand extends Command
{
    public GetSwingCommand() {
        this.returnValue = "Swing type is " + SwingMap.getCurrentSwingNiceName();
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
