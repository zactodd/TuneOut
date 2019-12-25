// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.Note.unitDuration.UnitDuration;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Note.unitDuration.UnitDurationMap;
import Model.CommandMessages;
import java.util.List;

public class SetUnitDurationCommand extends Command
{
    private final int baseCommandPos = 0;
    private final int baseCommandSize = 1;
    private final String invalidUnitDurationType;
    private final String incorrectSyntax;
    
    public SetUnitDurationCommand(final List<String> args) {
        this.invalidUnitDurationType = CommandMessages.getMessage("INVALID_UNIT_DURATION_TYPE");
        this.incorrectSyntax = CommandMessages.getMessage("INCORRECT_SYNTAX");
        if (args.size() == 1 && args.get(0).contains("\"")) {
            String newUnitDurationName = args.get(0);
            newUnitDurationName = newUnitDurationName.replace("\"", "");
            final UnitDuration unitDurationFromName = UnitDurationMap.getUnitDurationByName(newUnitDurationName);
            final UnitDuration unitDurationFromDivider = UnitDurationMap.getUnitDurationByDivider(newUnitDurationName);
            if (unitDurationFromName != null) {
                UnitDurationInformation.setUnitDuration(unitDurationFromName);
                this.returnValue = this.returnValueGenerator(unitDurationFromName);
            }
            else if (unitDurationFromDivider != null) {
                UnitDurationInformation.setUnitDuration(unitDurationFromDivider);
                this.returnValue = this.returnValueGenerator(unitDurationFromDivider);
            }
            else {
                this.returnValue = this.invalidUnitDurationType;
            }
        }
        else {
            this.returnValue = this.incorrectSyntax;
        }
    }
    
    private String returnValueGenerator(final UnitDuration ud) {
        final String message = "Unit Duration is changed to " + ud.getUnitDurationName() + ", value: " + UnitDurationMap.dividerToString(ud.getUnitDurationDivider());
        return message;
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
