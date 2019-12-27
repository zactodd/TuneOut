

package Model.command;

import Environment.Environment;
import Model.Note.Melody.MelodyMap;
import Model.CommandMessages;
import java.util.List;
import Model.Note.Melody.Melody;

public class PlayMelodyCommand extends Command
{
    private final int PARAM_COUNT = 1;
    private final int NAME_POS = 0;
    private String INVALID_INPUT;
    private boolean melodyExists;
    private Melody melody;
    
    public PlayMelodyCommand(final List<String> args) {
        this.INVALID_INPUT = CommandMessages.getMessage("INVALID_INPUT");
        if (args != null && args.size() == 1) {
            final String rawName = args.get(0);
            final String nameNoQuotes = rawName.replace("\"", "");
            final String nameLowerCase = nameNoQuotes.toLowerCase();
            this.melodyExists = this.processName(nameLowerCase);
            if (!rawName.matches("\".*\"") || nameLowerCase.isEmpty()) {
                this.returnValue = CommandMessages.getMessage("INVALID_MELODY_STRING");
            }
            else if (!this.melodyExists) {
                this.returnValue = String.format(CommandMessages.getMessage("INVALID_MELODY_NAME"), nameNoQuotes);
            }
            else {
                this.melody = MelodyMap.getMelody(nameLowerCase);
                this.returnValue = String.format(CommandMessages.getMessage("PLAYING_MELODY"), nameNoQuotes);
            }
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    private boolean processName(final String name) {
        final String rawName = name.replace("\"", "");
        Boolean nameValid = true;
        if (MelodyMap.getMelody(rawName) == null) {
            nameValid = false;
        }
        return nameValid;
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
        if (this.melodyExists) {
            env.getPlay().playMelody(this.melody);
        }
    }
}
