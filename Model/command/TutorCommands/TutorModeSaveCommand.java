

package Model.command.TutorCommands;

import Environment.Environment;

public class TutorModeSaveCommand extends TutorCommand
{
    private String returnValue;
    
    public TutorModeSaveCommand() {
        this.returnValue = "Saved tutor";
    }
    
    @Override
    public void execute(final Environment env) {
    }
}
