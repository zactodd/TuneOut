

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class QuestionNumParser implements ArgumentTypeParser
{
    private final Integer questionNumPos;
    private final Integer argsNeeded;
    private static final String INVALID_QUESTION_NUM;
    
    public QuestionNumParser() {
        this.questionNumPos = 0;
        this.argsNeeded = 1;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("[+-]?\\d+");
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final Integer questionNum = Integer.parseInt(args.get(this.questionNumPos));
        if (questionNum > 0 && questionNum <= 1000) {
            return questionNum;
        }
        throw new ArgumentException(String.format(QuestionNumParser.INVALID_QUESTION_NUM, 1000));
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return this.argsNeeded;
    }
    
    static {
        INVALID_QUESTION_NUM = CommandMessages.getMessage("INVALID_QUESTION_NUM");
    }
}
