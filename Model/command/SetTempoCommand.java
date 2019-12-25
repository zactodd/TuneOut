// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.Note.Settings.TempoInformation;
import Model.CommandMessages;
import java.util.List;

public class SetTempoCommand extends Command
{
    final int baseCommandPos = 0;
    final int overridePos = 1;
    final int baseCommandSize = 1;
    final int fullSize = 2;
    private final String successTempo;
    final String expectPositiveNumber;
    final String incorrectOverride;
    final String overrideKey = "-f";
    
    public SetTempoCommand(final List<String> tempo) {
        this.successTempo = CommandMessages.getMessage("SUCCESS_TEMPO");
        this.expectPositiveNumber = CommandMessages.getMessage("EXPECT_POSITIVE_NUMBER");
        this.incorrectOverride = CommandMessages.getMessage("INCORRECT_OVERRIDE");
        if (tempo.size() == 1) {
            final int newTempo = Integer.parseInt(tempo.get(0));
            if (newTempo <= 0) {
                this.returnValue = this.expectPositiveNumber;
            }
            else if (TempoInformation.checkTempoInSuitableRange(newTempo)) {
                TempoInformation.setTempInBpm(Integer.parseInt(tempo.get(0)));
                this.returnValue = this.hasSetTempo(newTempo);
            }
            else {
                this.returnValue = this.warningNewTempo(newTempo);
            }
        }
        else if (tempo.size() == 2) {
            final int newTempo = Integer.parseInt(tempo.get(0));
            if (newTempo <= 0) {
                this.returnValue = this.expectPositiveNumber;
            }
            else if (!tempo.get(1).equals("-f")) {
                this.returnValue = this.incorrectOverride;
            }
            else {
                TempoInformation.setTempInBpm(newTempo);
                this.returnValue = this.hasSetTempo(newTempo);
            }
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    private String hasSetTempo(final int newTempo) {
        return this.successTempo + newTempo;
    }
    
    private String warningNewTempo(final int newTempo) {
        return String.format(CommandMessages.getMessage("OUT_OF_SENSIBLE_RANGE_TEMPO"), String.valueOf(newTempo));
    }
}
