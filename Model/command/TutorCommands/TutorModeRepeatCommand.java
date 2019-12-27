

package Model.command.TutorCommands;

import Environment.Environment;

public class TutorModeRepeatCommand extends TutorCommand
{
    @Override
    public void execute(final Environment env) {
        if (env.isTutorMode()) {
            if (env.getTutor().checkAndRepeatQuestions()) {
                env.setResponse("Repeating incorrect or skipped questions");
            }
            else {
                env.setResponse(TutorCommand.cannotRepeatQuestions());
            }
        }
        else {
            env.setResponse(TutorCommand.notTutorMode());
        }
    }
}
