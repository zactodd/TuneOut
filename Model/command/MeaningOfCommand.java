// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.Terms.Term;
import Model.Terms.MusicalTerms;
import Model.CommandMessages;
import java.util.List;

public class MeaningOfCommand extends Command
{
    final int PARAM_COUNT = 1;
    final int TERM_POS = 0;
    final String NO_MEANING;
    
    public MeaningOfCommand(final List<String> term) {
        this.NO_MEANING = CommandMessages.getMessage("NO_MEANING");
        if (term.size() == 1) {
            final Term givenTerm = MusicalTerms.getTerm(term.get(0).replace("\"", ""));
            if (givenTerm == null) {
                this.returnValue = this.NO_MEANING;
            }
            else {
                this.returnValue = givenTerm.getMeaning();
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
}
