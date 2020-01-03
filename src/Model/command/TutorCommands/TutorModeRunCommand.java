

package Model.command.TutorCommands;

import Environment.Environment;
import java.util.List;

public class TutorModeRunCommand extends TutorCommand
{
    final int PARAM_COUNT = 1;
    final int gapAtStart_POS = 0;
    int gapAtStart;
    
    public TutorModeRunCommand(final List<String> arg) {
        if (arg.size() == 1) {
            try {
                this.gapAtStart = Integer.parseInt(arg.get(0));
            }
            catch (Exception e) {
                this.integerErrorRaiser(arg.get(0));
            }
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (env.isTutorMode()) {
            env.setResponse("Running additional question commands");
            this.runQuestionFromCommand(env);
        }
        else {
            env.setResponse(notTutorMode());
        }
    }
    
    public void runQuestionFromCommand(final Environment env) {
        env.getTutor().runQuestion(this.gapAtStart);
    }
}
