

package Model.command.CommandStorage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.ArrayList;
import java.util.TreeSet;
import java.text.Collator;
import java.util.List;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandMap
{
    private Map<String, Integer> commandUsageMap;
    private final String HELP_FILE = "/Help/commands.txt";
    private final String COMMAND_PREFIX = "~ ";
    
    public CommandMap() throws IOException {
        this.commandUsageMap = new HashMap<String, Integer>();
        this.initCommands();
    }
    
    public void increaseCount(final String commandName) {
        if (this.commandUsageMap.containsKey(commandName)) {
            this.commandUsageMap.put(commandName, this.commandUsageMap.get(commandName) + 1);
        }
    }
    
    public List<String> closestCommands(final String startsWithText) {
        Integer maxUser = 0;
        String key = "";
        final Set<String> commands = new TreeSet<String>(Collator.getInstance());
        for (final Map.Entry<String, Integer> commandEntry : this.commandUsageMap.entrySet()) {
            final String tempKey = commandEntry.getKey();
            final Integer tempValue = commandEntry.getValue();
            if (tempKey.toLowerCase().startsWith(startsWithText.toLowerCase())) {
                if (tempValue > maxUser) {
                    maxUser = tempValue;
                    key = tempKey;
                }
                commands.add(tempKey);
            }
        }
        final List<String> results = new ArrayList<String>();
        results.addAll(commands);
        if (!key.isEmpty()) {
            results.remove(key);
            results.add(0, key);
        }
        return results;
    }
    
    private void initCommands() throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/Help/commands.txt")));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("~ ")) {
                    this.commandUsageMap.put(line.substring(line.indexOf("~ ") + "~ ".length(), line.indexOf("(")), 0);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
