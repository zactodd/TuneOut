// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.TutorCommands;

import java.util.Iterator;
import Environment.Environment;

public class TutorModeStatsCommand extends TutorCommand
{
    @Override
    public void execute(final Environment env) {
        if (env.isTutorMode()) {
            String returnValue = "";
            for (final String stat : env.getTutor().getStats()) {
                returnValue = returnValue + stat + "\n";
            }
            env.setResponse(returnValue);
        }
        else {
            env.setResponse(TutorCommand.notTutorMode());
        }
    }
}
