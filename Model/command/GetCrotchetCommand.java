// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.Note.Settings.TempoInformation;
import java.text.DecimalFormat;

public class GetCrotchetCommand extends Command
{
    public GetCrotchetCommand() {
        final DecimalFormat crotchetFormat = new DecimalFormat("0.##");
        final Double crotchet = TempoInformation.crotchetBpmToMs(TempoInformation.getTempInBpm());
        this.returnValue = crotchetFormat.format(crotchet);
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
