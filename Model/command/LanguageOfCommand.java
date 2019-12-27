

package Model.command;

import Environment.Environment;
import Model.Terms.Term;
import Model.Terms.MusicalTerms;
import Model.CommandMessages;
import java.util.List;

public class LanguageOfCommand extends Command
{
    final int PARAM_COUNT = 1;
    final int TERM_POS = 0;
    final String NO_LANGUAGE;
    
    public LanguageOfCommand(final List<String> term) {
        this.NO_LANGUAGE = CommandMessages.getMessage("NO_LANGUAGE");
        if (term.size() == 1) {
            final Term givenTerm = MusicalTerms.getTerm(term.get(0).replace("\"", ""));
            if (givenTerm == null) {
                this.returnValue = this.NO_LANGUAGE;
            }
            else {
                this.returnValue = givenTerm.getSourceLanguage();
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
