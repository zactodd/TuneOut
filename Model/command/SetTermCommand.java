

package Model.command;

import Environment.Environment;
import Model.Terms.MusicalTerms;
import Model.Terms.Term;
import Model.CommandMessages;
import java.util.List;

public class SetTermCommand extends Command
{
    final int PARAM_COUNT = 4;
    final int TERM_POS = 0;
    final int LANG_POS = 1;
    final int MEAN_POS = 2;
    final int CATE_POS = 3;
    final String SUCCESS_DEFINED_MSG;
    final String EMPTY_TERM;
    final String EMPTY_LANG;
    final String EMPTY_MEAN;
    final String EMPTY_STRING = "\"\"";
    
    public SetTermCommand(final List<String> args) {
        this.SUCCESS_DEFINED_MSG = CommandMessages.getMessage("SUCCESS_DEFINED");
        this.EMPTY_TERM = CommandMessages.getMessage("EMPTY_TERM");
        this.EMPTY_LANG = CommandMessages.getMessage("EMPTY_LANG");
        this.EMPTY_MEAN = CommandMessages.getMessage("EMPTY_MEAN");
        if (args.size() == 4) {
            Boolean hasError = false;
            this.returnValue = "";
            if (args.get(0).equals("\"\"")) {
                this.returnValue += this.EMPTY_TERM;
                hasError = true;
            }
            if (args.get(1).equals("\"\"")) {
                this.returnValue = this.addNewLineIfNotEmpty(this.returnValue);
                this.returnValue += this.EMPTY_LANG;
                hasError = true;
            }
            if (args.get(2).equals("\"\"")) {
                this.returnValue = this.addNewLineIfNotEmpty(this.returnValue);
                this.returnValue += this.EMPTY_MEAN;
                hasError = true;
            }
            if (!hasError) {
                final Term term = new Term(args.get(0).replace("\"", ""), args.get(1).replace("\"", ""), args.get(2).replace("\"", ""), args.get(3).replace("\"", ""));
                MusicalTerms.addTerm(term);
                this.returnValue = this.SUCCESS_DEFINED_MSG;
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
    
    private String addNewLineIfNotEmpty(final String str) {
        return str.equals("") ? str : (str + "\n");
    }
}
