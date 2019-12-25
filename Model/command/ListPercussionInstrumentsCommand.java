// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import java.util.Iterator;
import Model.Percussion.Percussion;
import Model.Percussion.PercussionMap;

public class ListPercussionInstrumentsCommand extends Command
{
    public ListPercussionInstrumentsCommand() {
        this.returnValue = "\nPercussion Instruments\n-----------------------------------\n";
        for (final Percussion percussion : PercussionMap.getAllPercussions()) {
            final Integer midiNumber = percussion.getMidi();
            final String name = percussion.getInstrument();
            this.returnValue = this.returnValue + midiNumber + " - " + name + "\n";
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
}
