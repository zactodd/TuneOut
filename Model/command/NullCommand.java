// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.CommandMessages;

public class NullCommand extends Command
{
    private String message;
    
    public NullCommand() {
        this.message = CommandMessages.getMessage("INCORRECT_SYNTAX");
    }
    
    public NullCommand(final String message) {
        this.message = CommandMessages.getMessage("INCORRECT_SYNTAX");
        this.message = message;
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.message);
    }
}
