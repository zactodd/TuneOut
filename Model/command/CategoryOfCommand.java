// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Environment.Environment;
import Model.Terms.Term;
import Model.Terms.MusicalTerms;
import Model.CommandMessages;
import java.util.List;

public class CategoryOfCommand extends Command
{
    private final int PARAM_COUNT = 1;
    private final int TERM_POS = 0;
    private final String NO_CATEGORY;
    
    public CategoryOfCommand(final List<String> term) {
        this.NO_CATEGORY = CommandMessages.getMessage("NO_CATEGORY");
        if (term.size() == 1) {
            if (!term.get(0).startsWith("\"") || !term.get(0).endsWith("\"")) {
                this.wrongNumOfParamErrorRaiser();
            }
            final Term givenTerm = MusicalTerms.getTerm(term.get(0).replace("\"", ""));
            if (givenTerm == null) {
                this.returnValue = this.NO_CATEGORY;
            }
            else {
                this.returnValue = givenTerm.getCategory();
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
