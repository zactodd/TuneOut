

package Model.command.TutorCommands;

import Environment.Environment;
import Model.command.Command;

public abstract class TutorCommand extends Command
{
    @Override
    public abstract void execute(final Environment p0);
    
    static String notTutorMode() {
        return "Cannot send a tutor command outside tutor mode";
    }
    
    public static String cannotRepeatQuestions() {
        return "There are no incorrect or skipped questions to repeat";
    }
    
    public static String noMoreQuestions() {
        return "There are no more questions";
    }
    
    static String noQuestionToAnswer() {
        return "There is no question";
    }
    
    static String tutorAlreadyOpen() {
        return "The tutor is already open in another tab";
    }
}
