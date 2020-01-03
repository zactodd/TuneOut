

package Model.File;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import Model.command.CommandData;
import java.util.List;

public class CommandHelpParser
{
    private static String commandText;
    private List<CommandData> allCommands;
    private List<CommandData> appList;
    private List<CommandData> infoList;
    private List<CommandData> setList;
    private List<CommandData> playList;
    private List<CommandData> tutList;
    private Logger log;
    
    public CommandHelpParser() {
        this.allCommands = new ArrayList<CommandData>();
        this.appList = new ArrayList<CommandData>();
        this.infoList = new ArrayList<CommandData>();
        this.setList = new ArrayList<CommandData>();
        this.playList = new ArrayList<CommandData>();
        this.tutList = new ArrayList<CommandData>();
        this.log = Logger.getLogger(CommandHelpParser.class.getName());
    }
    
    public void start() {
        this.readCommandFile();
        this.sortCommandList();
    }
    
    private void readCommandFile() {
        try {
            final InputStream inputSteam = this.getClass().getResourceAsStream("/Help/commands.txt");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputSteam));
            try {
                String category = "";
                String command = "";
                String descrip = "";
                String details = "";
                boolean hasCommand = false;
                String commandLine;
                while ((commandLine = reader.readLine()) != null) {
                    if (commandLine.startsWith("##")) {
                        category = commandLine.substring(3);
                    }
                    else if (commandLine.startsWith("~")) {
                        hasCommand = true;
                        command = commandLine.substring(2);
                        descrip = reader.readLine();
                    }
                    else if (commandLine.trim().length() == 0 && hasCommand) {
                        final int a = command.indexOf("(");
                        final String input = command.substring(0, a) + "()";
                        final CommandData newCommand = new CommandData(command.trim(), category.trim(), descrip.trim(), details.trim(), input);
                        this.allCommands.add(newCommand);
                        hasCommand = false;
                        details = "";
                    }
                    else {
                        if (!hasCommand) {
                            continue;
                        }
                        details += commandLine.trim();
                        details += System.lineSeparator();
                    }
                }
            }
            catch (IOException e) {
                this.log.error(e.toString());
            }
        }
        catch (Exception e2) {
            this.log.error(e2.toString());
        }
    }
    
    private void sortCommandList() {
        for (final CommandData command : this.allCommands) {
            final String category = command.getCategory();
            if (category.startsWith("App")) {
                this.appList.add(command);
            }
            else if (category.startsWith("Info")) {
                this.infoList.add(command);
            }
            else if (category.startsWith("Set")) {
                this.setList.add(command);
            }
            else if (category.startsWith("Play")) {
                this.playList.add(command);
            }
            else {
                if (!category.startsWith("Tut")) {
                    continue;
                }
                this.tutList.add(command);
            }
        }
    }
    
    public List<CommandData> getAppList() {
        return this.appList;
    }
    
    public List<CommandData> getTutList() {
        return this.tutList;
    }
    
    public List<CommandData> getPlayList() {
        return this.playList;
    }
    
    public List<CommandData> getInfoList() {
        return this.infoList;
    }
    
    public List<CommandData> getSetList() {
        return this.setList;
    }
    
    static {
        CommandHelpParser.commandText = "";
    }
}
