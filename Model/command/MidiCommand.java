

package Model.command;

import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.Note.Note;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class MidiCommand extends Command implements OnSuccess
{
    private static final int PARAM_COUNT = 1;
    private static final int POS = 0;
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) {
        final Note note = requiredArgs.get(0);
        return note.getMidiNumber().toString();
    }
    
    @Override
    public String getCommandName() {
        return "midi";
    }
    
    public MidiCommand(final List<String> note) throws ArgumentException {
        if (note.size() == 1) {
            final ArgumentParser argumentParser = new ArgumentParser();
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.NOTE);
            this.returnValue = argumentParser.parseArgs(note, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
