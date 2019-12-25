// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.TutorCommands;

import Environment.Environment;

public class TutorStatsCommand extends TutorCommand
{
    private String commandResponse;
    
    public TutorStatsCommand() {
        this.commandResponse = "Opening Tutor Stats";
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.commandResponse);
        env.checkAndOpenTutorStats();
    }
}
