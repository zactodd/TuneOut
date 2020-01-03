

package Environment;

import Model.command.NullCommand;
import Model.command.ArgumentParsing.ArgumentException;

import java.io.StringReader;
import Model.command.Command;
import org.apache.log4j.Logger;

public class GrammarParser
{
    private Environment environment;
    private static Logger log;
    
    public GrammarParser(final Environment env) {
        this.environment = env;
    }
    
    public void executeCommand(final Command command) {
        command.execute(this.environment);
    }
    
    public void executeCommand(final String commandString) {
        this.executeCommand(this.parseCommandString(commandString));
    }
    
    private Command parseCommandString(final String commandString) {
        this.environment.appendToTranscript(commandString);
        final DslParser parser = new DslParser(new DslLexer(new StringReader(commandString)));
        Object parseResult = null;
        try {
            parseResult = parser.parse().value;
        }
        catch (Exception exp) {
            if (exp instanceof ArgumentException) {
                return new NullCommand(exp.getMessage());
            }
            GrammarParser.log.error("Failed to parse Model.command: " + commandString);
        }
        if (parseResult instanceof Command) {
            return (Command)parseResult;
        }
        GrammarParser.log.error("Expected Model.command object but got " + parseResult);
        return new NullCommand();
    }
    
    static {
        GrammarParser.log = Logger.getLogger(GrammarParser.class.getName());
    }
}
