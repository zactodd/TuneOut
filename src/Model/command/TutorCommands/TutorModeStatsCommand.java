

package Model.command.TutorCommands;

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
            env.setResponse(notTutorMode());
        }
    }
}
