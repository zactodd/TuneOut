// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.CommandStorage;

import java.util.ArrayList;

public class CommandHistory
{
    private int currentPos;
    private int endPos;
    private ArrayList<String> commands;
    
    public CommandHistory() {
        this.currentPos = 0;
        this.endPos = 0;
        (this.commands = new ArrayList<String>()).add("");
    }
    
    public String getPreviousCommand() {
        if (this.currentPos > 0) {
            final String current = this.commands.get(this.currentPos);
            --this.currentPos;
            return current;
        }
        return null;
    }
    
    public String getNextCommand() {
        if (this.currentPos < this.endPos - 1) {
            ++this.currentPos;
            return this.commands.get(this.currentPos + 1);
        }
        this.currentPos = this.endPos;
        return "";
    }
    
    public void addCommand(final String command) {
        ++this.endPos;
        this.currentPos = this.endPos;
        this.commands.add(this.endPos, command);
    }
    
    public String searchCommand(final String command) {
        Integer tempPos;
        String match;
        ArrayList<String> commands;
        Integer n;
        for (tempPos = this.currentPos, match = ""; tempPos != 0 && !match.toLowerCase().contains(command.toLowerCase()); --tempPos, match = commands.get(n)) {
            commands = this.commands;
            n = tempPos;
        }
        if (tempPos == 0 && !match.toLowerCase().contains(command.toLowerCase())) {
            return command;
        }
        return match;
    }
    
    public boolean isEmpty() {
        return this.commands.size() <= 1;
    }
}
