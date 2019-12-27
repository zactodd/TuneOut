

package Model.command.TutorCommands;

import Environment.Environment;

public class TutorModeExitCommand extends TutorCommand
{
    private String commandResponse;
    
    public TutorModeExitCommand() {
        this.commandResponse = "Exited tutor mode";
    }
    
    @Override
    public void execute(final Environment env) {
        if (env.isTutorMode()) {
            env.setResponse(this.commandResponse);
            env.setTutorMode(false);
        }
        else {
            env.setResponse(TutorCommand.notTutorMode());
        }
    }
}
