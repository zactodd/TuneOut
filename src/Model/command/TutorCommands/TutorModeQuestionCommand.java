

package Model.command.TutorCommands;

import Environment.Environment;

public class TutorModeQuestionCommand extends TutorCommand
{
    @Override
    public void execute(final Environment env) {
        if (env.isTutorMode()) {
            env.getTutor().generateQuestion();
            final String question = env.getTutor().getQuestionString();
            if (question.isEmpty()) {
                env.setResponse(noMoreQuestions());
            }
            else {
                env.setResponse(env.getTutor().getQuestionString());
            }
        }
        else {
            env.setResponse(notTutorMode());
        }
    }
}
