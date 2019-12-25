// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import Environment.Environment;
import Model.CommandMessages;
import java.util.List;

public class HelpCommand extends Command
{
    private String helpString;
    private int KEYWORD_POS;
    private String helpFile;
    private String NO_COMMAND;
    
    public HelpCommand(final List<String> arg) {
        this.KEYWORD_POS = 0;
        this.helpFile = "/Help/commands.txt";
        this.NO_COMMAND = CommandMessages.getMessage("NO_COMMAND");
        if (arg == null || arg.size() == 1) {
            if (arg == null) {
                this.helpString = this.searchCommandInFile(null, this.helpFile);
            }
            else if (arg.get(this.KEYWORD_POS).startsWith("\"") && arg.get(this.KEYWORD_POS).endsWith("\"")) {
                String stringToSearch = arg.get(this.KEYWORD_POS).replace("\"", "");
                if (stringToSearch.equals("")) {
                    stringToSearch = null;
                }
                this.helpString = this.searchCommandInFile(stringToSearch, this.helpFile);
                if (this.helpString.equals("")) {
                    this.helpString = this.NO_COMMAND;
                }
            }
            else {
                this.wrongNumOfParamErrorRaiser();
            }
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.helpString);
    }
    
    private String searchCommandInFile(final String command, final String file) {
        boolean processCommand = false;
        final StringBuilder result = new StringBuilder("");
        final InputStream input = this.getClass().getResourceAsStream(file);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (command != null) {
                    if ((!line.startsWith("~ ") || !line.toLowerCase().contains(command.toLowerCase()) || line.toLowerCase().indexOf(command.toLowerCase()) >= line.indexOf("(")) && !processCommand) {
                        continue;
                    }
                    if (line.equals("")) {
                        result.append("\n");
                        processCommand = false;
                    }
                    else {
                        result.append(line);
                        result.append("\n");
                        processCommand = true;
                    }
                }
                else {
                    result.append(line);
                    result.append("\n");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
