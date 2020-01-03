

package Model.command;

import Environment.Environment;
import Model.Note.unitDuration.UnitDuration;
import Model.Note.unitDuration.UnitDurationMap;

public class ListUnitDurationsCommand extends Command
{
    public ListUnitDurationsCommand() {
        this.returnValue = "\nUnit Durations\n-----------------------------------\n";
        for (final UnitDuration ud : UnitDurationMap.getAvailableUnitDurationName()) {
            final Double dividerInDouble = ud.getUnitDurationDivider();
            this.returnValue = this.returnValue + ud.getUnitDurationName() + ", value: " + UnitDurationMap.dividerToString(dividerInDouble) + "\n";
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
