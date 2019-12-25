// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.TutorCommands;

import Environment.Environment;
import java.util.List;

public class TutorModeStartCommand extends TutorCommand
{
    private String returnValue;
    private List<String> args;
    private boolean hasOptions;
    
    public TutorModeStartCommand(final List<String> args) {
        this.hasOptions = false;
        if (args == null) {
            this.returnValue = "Starting tutor";
        }
        else {
            this.args = args;
            this.returnValue = "Starting tutor with changed options";
            this.hasOptions = true;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (env.isTutorMode()) {
            if (this.hasOptions) {
                env.getTutor().setOptions(this.args);
            }
            env.setResponse(this.returnValue);
        }
        else {
            env.setResponse(TutorCommand.notTutorMode());
        }
    }
}
