// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.Note.Intervals.IntervalMap;
import Model.CommandMessages;
import java.util.List;

public class EnharmonicIntervalCommand extends Command
{
    private final Integer PARAM_COUNT;
    private final Integer INTERVAL_NAME_POS;
    private final String NO_INTERVAL;
    private final String NO_ENHARMONIC_EQUIVALENT;
    
    public EnharmonicIntervalCommand(final List<String> arg) {
        this.PARAM_COUNT = 1;
        this.INTERVAL_NAME_POS = 0;
        this.NO_INTERVAL = CommandMessages.getMessage("NO_INTERVAL");
        this.NO_ENHARMONIC_EQUIVALENT = CommandMessages.getMessage("NO_ENHARMONIC_EQUIVALENT");
        if (arg.size() == this.PARAM_COUNT) {
            String intervalName = arg.get(this.INTERVAL_NAME_POS);
            intervalName = this.stringErrorRaiser(intervalName);
            if (IntervalMap.getIntervalWithIntervalName(intervalName) == null) {
                this.returnValue = this.NO_INTERVAL;
                return;
            }
            if (!IntervalMap.hasEnharmonicEquivalent(intervalName)) {
                this.returnValue = this.NO_ENHARMONIC_EQUIVALENT;
                return;
            }
            this.returnValue = IntervalMap.getEnharmonicEquivalent(intervalName).getPrettyIntervalName();
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
