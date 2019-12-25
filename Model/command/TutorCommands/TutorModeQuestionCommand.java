// 
// Decompiled by Procyon v0.5.36
// 

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
                env.setResponse(TutorCommand.noMoreQuestions());
            }
            else {
                env.setResponse(env.getTutor().getQuestionString());
            }
        }
        else {
            env.setResponse(TutorCommand.notTutorMode());
        }
    }
}
