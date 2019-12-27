

package Model.command;

import Environment.Environment;
import Model.Note.Settings.SwingMap;
import Model.CommandMessages;
import java.util.List;

public class SetSwingCommand extends Command
{
    final int BASE_COMMAND_POS = 0;
    final int BASE_COMMAND_SIZE = 1;
    final String INVALID_SWING_TYPE;
    
    public SetSwingCommand(final List<String> args) {
        this.INVALID_SWING_TYPE = CommandMessages.getMessage("INVALID_SWING_TYPE");
        if (args.size() == 1) {
            if (args.get(0).contains("\"")) {
                String newSwing = args.get(0);
                newSwing = newSwing.replace("\"", "").toLowerCase();
                if (SwingMap.includes(newSwing)) {
                    SwingMap.setCurrentSwing(newSwing);
                    this.returnValue = "Swing is changed to " + SwingMap.getCurrentSwingNiceName();
                }
                else {
                    this.returnValue = this.INVALID_SWING_TYPE;
                }
            }
            else {
                this.incorrectUseOfQuotesErrorRaiser();
            }
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
