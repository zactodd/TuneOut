

package Model.command;

import Model.Note.Intervals.Interval;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class GetIntervalSemitoneCommand extends Command implements OnSuccess
{
    private final int PARAM_COUNT = 1;
    private final int INTERVAL_POS = 0;
    
    public GetIntervalSemitoneCommand(final List<String> args) throws ArgumentException {
        if (args.size() == 1) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.INTERVAL);
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(args, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final Interval interval = requiredArgs.get(0);
        return interval.getSemitone().toString();
    }
    
    @Override
    public String getCommandName() {
        return "getIntervalSemitone";
    }
}
