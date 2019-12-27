

package Model.command;

import Model.CommandMessages;
import Environment.Environment;
import Model.Note.Chord.ChordMap;
import java.util.List;

public class QualityOfCommand extends Command
{
    static final int PARAM_COUNT = 1;
    private static final int FUNCTION_POS = 0;
    private static final String WRONG_TYPE_ENTERED;
    
    public QualityOfCommand(final List<String> function) {
        if (function.size() == 1) {
            final String quality = ChordMap.qualityName(function.get(0));
            if (quality.isEmpty()) {
                this.returnValue = QualityOfCommand.WRONG_TYPE_ENTERED;
            }
            else {
                this.returnValue = quality;
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
    
    static {
        WRONG_TYPE_ENTERED = CommandMessages.getMessage("WRONG_FUNCTION_TYPE");
    }
}
