

package Model.command.TutorCommands;

import Environment.Environment;

public class TutorModeAnswerCommand extends TutorCommand
{
    private String answer;
    
    public TutorModeAnswerCommand(final String answer) {
        this.answer = answer;
    }
    
    @Override
    public void execute(final Environment env) {
        if (env.isTutorMode()) {
            final String answerFromModel = env.getTutor().getAnswer();
            if (env.getTutor().checkAnswer(this.answer)) {
                env.setResponse("You were correct");
            }
            else if (answerFromModel.isEmpty()) {
                env.setResponse(TutorCommand.noQuestionToAnswer());
            }
            else {
                env.setResponse(String.format("You were incorrect. Your answer was '%s'. The correct answer was '%s'", this.answer.replace("\"", ""), answerFromModel));
            }
        }
        else {
            env.setResponse(TutorCommand.notTutorMode());
        }
    }
}
