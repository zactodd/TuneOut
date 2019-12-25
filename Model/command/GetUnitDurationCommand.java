// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.Note.unitDuration.UnitDuration;
import Model.Note.unitDuration.UnitDurationMap;
import Model.Note.unitDuration.UnitDurationInformation;

public class GetUnitDurationCommand extends Command
{
    public GetUnitDurationCommand() {
        final UnitDuration unitDuration = UnitDurationInformation.getUnitDuration();
        this.returnValue = unitDuration.getUnitDurationName() + ", value: ";
        final Double divider = unitDuration.getUnitDurationDivider();
        this.returnValue += UnitDurationMap.dividerToString(divider);
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
