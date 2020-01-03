

package Environment;

import java.util.ArrayList;
import Model.command.PlayMelodyCommand;
import Model.command.TutorCommands.TutorModeStartCommand;
import Model.command.AddPercussionLoopCommand;
import Model.command.PlayPercussionLoopCommand;
import Model.command.ListPercussionInstrumentsCommand;
import Model.command.PlayPercussionCommand;
import Model.command.SetMelodyCommand;
import Model.command.TutorCommands.ScaleModeTutorCommand;
import Model.command.ListUnitDurationsCommand;
import Model.command.SetUnitDurationCommand;
import Model.command.GetUnitDurationCommand;
import Model.command.TutorCommands.TutorStatsCommand;
import Model.command.ListPatternsCommand;
import Model.command.SetPatternCommand;
import Model.command.ModeOfCommand;
import Model.command.ParentOfCommand;
import Model.command.TutorCommands.ChordFunctionTutorCommand;
import Model.command.FunctionOfCommand;
import Model.command.ChordFunctionCommand;
import Model.command.QualityOfCommand;
import Model.command.SetInstrumentCommand;
import Model.command.ListInstrumentsCommand;
import Model.command.GetInstrumentCommand;
import Model.command.TutorCommands.ChordSpellingTutorCommand;
import Model.command.HasEnharmonicIntervalCommand;
import Model.command.EnharmonicIntervalCommand;
import Model.command.SetSwingCommand;
import Model.command.GetSwingCommand;
import Model.command.ChordFinderCommand;
import Model.command.TutorCommands.KeySignatureTutorCommand;
import Model.command.KeySignatureCommand;
import Model.command.TutorCommands.ChordTutorCommand;
import Model.command.RestCommand;
import Model.command.PlayChordCommand;
import Model.command.TutorCommands.ScaleTutorCommand;
import Model.command.TutorCommands.TutorModeSaveCommand;
import Model.command.TutorCommands.MusicalTermTutorCommand;
import Model.command.ChordCommand;
import Model.command.TutorCommands.IntervalTutorCommand;
import Model.command.PlayIntervalCommand;
import Model.command.IntervalCommand;
import Model.command.GetIntervalSemitoneCommand;
import Model.command.TutorCommands.TutorModeRunCommand;
import Model.command.TutorCommands.TutorModeStatsCommand;
import Model.command.TutorCommands.TutorModeQuestionCommand;
import Model.command.TutorCommands.TutorModeAnswerCommand;
import Model.command.TutorCommands.TutorModeRepeatCommand;
import Model.command.LanguageOfCommand;
import Model.command.MeaningOfCommand;
import Model.command.PlayScaleCommand;
import Model.command.CategoryOfCommand;
import Model.command.SetTermCommand;
import Model.command.PlayCommand;
import Model.command.SetTempoCommand;
import Model.command.GetCrotchetCommand;
import Model.command.GetTempoCommand;
import Model.command.TutorCommands.PitchTutorCommand;
import Model.command.HelpCommand;
import Model.command.ScaleCommand;
import Model.command.EnharmonicLowCommand;
import Model.command.EnharmonicHighCommand;
import Model.command.EnharmonicCommand;
import Model.command.HasEnharmonicCommand;
import Model.command.SemitoneCommand;
import Model.command.NoteCommand;
import Model.command.MidiCommand;
import java.util.List;
import Model.command.Command;
import Model.command.ShowVersionCommand;
import java_cup.runtime.Symbol;
import java.util.Stack;
import java_cup.runtime.SymbolFactory;
import java_cup.runtime.Scanner;
import java_cup.runtime.lr_parser;

public class DslParser extends lr_parser
{
    protected static final short[][] _production_table;
    protected static final short[][] _action_table;
    protected static final short[][] _reduce_table;
    protected CUP$DslParser$actions action_obj;
    
    @Override
    public final Class getSymbolContainer() {
        return DslSymbol.class;
    }
    
    @Deprecated
    public DslParser() {
    }
    
    @Deprecated
    public DslParser(final Scanner s) {
        super(s);
    }
    
    public DslParser(final Scanner s, final SymbolFactory sf) {
        super(s, sf);
    }
    
    @Override
    public short[][] production_table() {
        return DslParser._production_table;
    }
    
    @Override
    public short[][] action_table() {
        return DslParser._action_table;
    }
    
    @Override
    public short[][] reduce_table() {
        return DslParser._reduce_table;
    }
    
    @Override
    protected void init_actions() {
        this.action_obj = new CUP$DslParser$actions(this);
    }
    
    @Override
    public Symbol do_action(final int act_num, final lr_parser parser, final Stack stack, final int top) throws Exception {
        return this.action_obj.CUP$DslParser$do_action(act_num, parser, stack, top);
    }
    
    @Override
    public int start_state() {
        return 0;
    }
    
    @Override
    public int start_production() {
        return 1;
    }
    
    @Override
    public int EOF_sym() {
        return 0;
    }
    
    @Override
    public int error_sym() {
        return 1;
    }
    
    @Override
    public void report_error(final String message, final Object info) {
    }
    
    @Override
    public void syntax_error(final Symbol cur_token) {
    }
    
    static {
        _production_table = lr_parser.unpackFromStrings(new String[] { "\u0000l\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\t\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\u0002\u0004\u0000\u0002\r\u0003\u0000\u0002\r\u0005\u0000\u0002\f\u0007\u0000\u0002\f\u0005\u0000\u0002\u000e\u0003\u0000\u0002\u000e\u0003\u0000\u0002\u000e\u0003\u0000\u0002\u000e\u0005\u0000\u0002\u000e\u0005\u0000\u0002\u000e\u0005\u0000\u0002\u000e\u0005\u0000\u0002\u000e\u0007\u0000\u0002\u000f\u0003\u0000\u0002\u000f\u0003\u0000\u0002\u000f\u0005\u0000\u0002\u000f\u0005\u0000\u0002\u0003\u0005\u0000\u0002\u0003\u0003\u0000\u0002\u0005\u0004\u0000\u0002\u0005\u0002\u0000\u0002\u0006\u0005\u0000\u0002\u0006\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0005\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u0007\u0003\u0000\u0002\u000b\u0004\u0000\u0002\u0004\u0005\u0000\u0002\b\u0005\u0000\u0002\b\u0003\u0000\u0002\t\u0005\u0000\u0002\t\u0003\u0000\u0002\n\u0003\u0000\u0002\n\u0003\u0000\u0002\n\u0003\u0000\u0002\n\u0003" });
        _action_table = lr_parser.unpackFromStrings(new String[] { "\u0000\u00ca\u0000\u0088\u0004$\u0005E\u0006%\u0007\t\b(\t\u0019\nF\u000b\b\f1\r,\u000e \u000f.\u0010\u0005\u0011\u0014\u0012\u000f\u0013)\u00147\u0015?\u0016+\u0017-\u0018<\u0019D\u001a:\u001b6\u001c\f\u001d\u0006\u001e5\u001f# \u001f!'\"2#A$&%\u0004&4'\u000b(!)9*\u001e+\u0015,\u0018-\".\u0017/G0\u00131/2\u000e3\u00104B5\u001d6=7*8\u00129;:>;\u001c<\u001b=\u0011>@?\u001a@\rA3BCC0D\u0007J\nK8\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004E¦\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004\u0002\u009a\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004Ea\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004Ew\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004Ea\u0001\u0002\u0000\u0004E_\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004EI\u0001\u0002\u0000\u0004\u0002\uffac\u0001\u0002\u0000\u0014FRHUOSPKQMRPSTTQVL\u0001\u0002\u0000\u0004\u0002\uffd7\u0001\u0002\u0000\bF\uffa4G\uffa4I\uffa4\u0001\u0002\u0000\bF\uffa0G\uffa0I\uffa0\u0001\u0002\u0000\bF\uffa6G\uffa6I\uffa6\u0001\u0002\u0000\bF\uffa8G\uffa8I\uffa8\u0001\u0002\u0000\u0006FZGW\u0001\u0002\u0000\bF\uffa3G\uffa3I\uffa3\u0001\u0002\u0000\bF\uffa1G\uffa1I\uffa1\u0001\u0002\u0000\u0004\u0002\uff9f\u0001\u0002\u0000\bF\uffa7G\uffa7I\uffa7\u0001\u0002\u0000\bF\uffa2G\uffa2I\uffa2\u0001\u0002\u0000\u0012HUOSPKQMRPSTTQVL\u0001\u0002\u0000\u0006GWIX\u0001\u0002\u0000\u0012HUOSPKQMRPSTTQVL\u0001\u0002\u0000\bF\uffa5G\uffa5I\uffa5\u0001\u0002\u0000\bF\uffa9G\uffa9I\uffa9\u0001\u0002\u0000\u0004\u0002\uffad\u0001\u0002\u0000\u0004\u0002\ufffa\u0001\u0002\u0000\u0004\u0002\uffff\u0001\u0002\u0000\u0004\u0002\uffef\u0001\u0002\u0000\u0004\u0002\uffc9\u0001\u0002\u0000\u0004FR\u0001\u0002\u0000\u0004\u0002\uff9c\u0001\u0002\u0000\fFROhQeRcUg\u0001\u0002\u0000\u0004\u0002\uffd1\u0001\u0002\u0000\u0006F\uff98G\uff98\u0001\u0002\u0000\u0006F\uff9aG\uff9a\u0001\u0002\u0000\u0006F\uff97G\uff97\u0001\u0002\u0000\u0006FjGi\u0001\u0002\u0000\u0006F\uff96G\uff96\u0001\u0002\u0000\u0006F\uff99G\uff99\u0001\u0002\u0000\nOhQeRcUg\u0001\u0002\u0000\u0004\u0002\uff9d\u0001\u0002\u0000\u0006F\uff9bG\uff9b\u0001\u0002\u0000\u0004\u0002\ufff0\u0001\u0002\u0000\u0004\u0002\uffcb\u0001\u0002\u0000\u0004\u0002\uffea\u0001\u0002\u0000\u0004\u0002\uffd3\u0001\u0002\u0000\u0004\u0002\uffcf\u0001\u0002\u0000\u0004\u0002\ufff8\u0001\u0002\u0000\u0004\u0002\uffce\u0001\u0002\u0000\u0004\u0002\uffe7\u0001\u0002\u0000\u0004\u0002\uffdf\u0001\u0002\u0000\u0004\u0002\uffbe\u0001\u0002\u0000\u0004\u0002\uffeb\u0001\u0002\u0000\u0004Rx\u0001\u0002\u0000\u0004Fy\u0001\u0002\u0000\u0004\u0002\uff9e\u0001\u0002\u0000\u0004\u0002\uffe5\u0001\u0002\u0000\u0004\u0002\uffe3\u0001\u0002\u0000\u0004\u0002\uffe1\u0001\u0002\u0000\u0004\u0002\uffc2\u0001\u0002\u0000\u0004\u0002\uffdb\u0001\u0002\u0000\u0004\u0002\ufff7\u0001\u0002\u0000\u0004\u0002\uffc1\u0001\u0002\u0000\u0004\u0002\uffc7\u0001\u0002\u0000\u0004\u0002\ufff2\u0001\u0002\u0000\u0004\u0002\uffe8\u0001\u0002\u0000\u0004\u0002\ufff5\u0001\u0002\u0000\u0004\u0002\uffe9\u0001\u0002\u0000\u0004\u0002\uffcd\u0001\u0002\u0000\u0004\u0002\uffe6\u0001\u0002\u0000\u0004\u0002\ufffc\u0001\u0002\u0000\u0004\u0002\uffdc\u0001\u0002\u0000\u0004\u0002\uffee\u0001\u0002\u0000\u0004\u0002\ufffe\u0001\u0002\u0000\u0004\u0002\u0001\u0001\u0002\u0000\u0004\u0002\ufff4\u0001\u0002\u0000\u0004\u0002\uffd9\u0001\u0002\u0000\u0004\u0002\uffde\u0001\u0002\u0000\u0004\u0002\ufff3\u0001\u0002\u0000\u0004\u0002\ufff1\u0001\u0002\u0000\u0004\u0002\uffdd\u0001\u0002\u0000\u0004\u0002\uffd0\u0001\u0002\u0000\u0004\u0002\uffd4\u0001\u0002\u0000\u0004\u0002\uffc5\u0001\u0002\u0000\u0004\u0002\uffca\u0001\u0002\u0000\u0004\u0002\ufffb\u0001\u0002\u0000\u0004\u0002\uffd8\u0001\u0002\u0000\u0004\u0002\uffd6\u0001\u0002\u0000\u0004\u0002\u0000\u0001\u0002\u0000\u0004\u0002\uffda\u0001\u0002\u0000\u0004\u0002\uffbf\u0001\u0002\u0000\u0004\u0002\uffc8\u0001\u0002\u0000\u0004\u0002\uffcc\u0001\u0002\u0000\u0004\u0002\uffd2\u0001\u0002\u0000\u0004\u0002\uffd5\u0001\u0002\u0000\u0004\u0002\uffec\u0001\u0002\u0000\u0004\u0002\uffc6\u0001\u0002\u0000\u0004\u0002\uffc3\u0001\u0002\u0000\u0004\u0002\uffe4\u0001\u0002\u0000\u0004\u0002\uffe0\u0001\u0002\u0000\u0004L§\u0001\u0002\u0000\nH´O³Q±Wµ\u0001\u0002\u0000\u0004Gª\u0001\u0002\u0000\u0004G\uffbd\u0001\u0002\u0000\u0006L§R«\u0001\u0002\u0000\u0006F\uffaaG\u00ad\u0001\u0002\u0000\u0004G\uffbc\u0001\u0002\u0000\u0012HUOSPKQMRPSTTQVL\u0001\u0002\u0000\u0004F¯\u0001\u0002\u0000\u0004\u0002\uffc4\u0001\u0002\u0000\u0006F\uffabGW\u0001\u0002\u0000\u0006G\uffb8M\uffb8\u0001\u0002\u0000\u0006G½M¾\u0001\u0002\u0000\u0006G\uffb9M\uffb9\u0001\u0002\u0000\u0006O¸Q¶\u0001\u0002\u0000\u0006G\uffb7M\uffb7\u0001\u0002\u0000\u0006G\uffb0I\uffb0\u0001\u0002\u0000\u0006G¹Iº\u0001\u0002\u0000\u0006G\uffb1I\uffb1\u0001\u0002\u0000\u0006O¼Q»\u0001\u0002\u0000\u0006G\uffb6M\uffb6\u0001\u0002\u0000\u0006G\uffaeI\uffae\u0001\u0002\u0000\u0006G\uffafI\uffaf\u0001\u0002\u0000\fH\u00c3O\u00c2Q\u00c0R¿W\u00c1\u0001\u0002\u0000\u0004G\uffba\u0001\u0002\u0000\u0004M\u00c6\u0001\u0002\u0000\u0006G\uffb4M\uffb4\u0001\u0002\u0000\u0006G\uffb3M\uffb3\u0001\u0002\u0000\u0006G\uffb5M\uffb5\u0001\u0002\u0000\u0006O¸Q¶\u0001\u0002\u0000\u0006G¹I\u00c5\u0001\u0002\u0000\u0006G\uffb2M\uffb2\u0001\u0002\u0000\u0004G\uffbb\u0001\u0002\u0000\u0004\u0002\ufffd\u0001\u0002\u0000\u0004\u0002\ufff9\u0001\u0002\u0000\u0004\u0002\uffc0\u0001\u0002\u0000\u0004\u0002\uffe2\u0001\u0002\u0000\u0004\u0002\ufff6\u0001\u0002\u0000\u0004\u0002\uffed\u0001\u0002" });
        _reduce_table = lr_parser.unpackFromStrings(new String[] { "\u0000\u00ca\u0000\u0004\u0002\u0015\u0001\u0001\u0000\u0006\u0003\u00cb\u000bG\u0001\u0001\u0000\u0006\u0003\u00ca\u000bG\u0001\u0001\u0000\u0006\u0003\u00c9\u000bG\u0001\u0001\u0000\u0006\u0003\u00c8\u000bG\u0001\u0001\u0000\u0006\u0003\u00c7\u000bG\u0001\u0001\u0000\u0006\u0003\u00c6\u000bG\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0003¤\u000bG\u0001\u0001\u0000\u0006\u0003£\u000bG\u0001\u0001\u0000\u0006\u0003¢\u000bG\u0001\u0001\u0000\u0004\u000b¡\u0001\u0001\u0000\u0004\u000b \u0001\u0001\u0000\u0006\u0003\u009f\u000bG\u0001\u0001\u0000\u0006\u0003\u009e\u000bG\u0001\u0001\u0000\u0006\u0003\u009d\u000bG\u0001\u0001\u0000\u0004\u000b\u009c\u0001\u0001\u0000\u0006\u0003\u009b\u000bG\u0001\u0001\u0000\u0006\u0003\u009a\u000bG\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0003\u0098\u000bG\u0001\u0001\u0000\u0006\u0003\u0097\u000bG\u0001\u0001\u0000\u0006\u0003\u0096\u000bG\u0001\u0001\u0000\u0006\u0003\u0095\u000bG\u0001\u0001\u0000\u0006\u0003\u0094\u000bG\u0001\u0001\u0000\u0006\u0003\u0093\u000bG\u0001\u0001\u0000\u0006\b\u0092\u000b_\u0001\u0001\u0000\u0006\u0003\u0091\u000bG\u0001\u0001\u0000\u0006\u0003\u0090\u000bG\u0001\u0001\u0000\u0006\u0003\u008f\u000bG\u0001\u0001\u0000\u0006\u0003\u008e\u000bG\u0001\u0001\u0000\u0004\u000b\u008d\u0001\u0001\u0000\u0004\u000b\u008c\u0001\u0001\u0000\u0004\u000b\u008b\u0001\u0001\u0000\u0006\u0003\u008a\u000bG\u0001\u0001\u0000\u0006\u0003\u0089\u000bG\u0001\u0001\u0000\u0006\u0003\u0088\u000bG\u0001\u0001\u0000\u0006\u0003\u0087\u000bG\u0001\u0001\u0000\u0006\u0003\u0086\u000bG\u0001\u0001\u0000\u0006\u0003\u0085\u000bG\u0001\u0001\u0000\u0004\u000b\u0084\u0001\u0001\u0000\u0004\u000b\u0083\u0001\u0001\u0000\u0006\u0003\u0082\u000bG\u0001\u0001\u0000\u0006\u0003\u0081\u000bG\u0001\u0001\u0000\u0006\u0003\u0080\u000bG\u0001\u0001\u0000\u0006\u0003\u007f\u000bG\u0001\u0001\u0000\u0006\u0003~\u000bG\u0001\u0001\u0000\u0006\u0003}\u000bG\u0001\u0001\u0000\u0004\u000b|\u0001\u0001\u0000\u0004\u000b{\u0001\u0001\u0000\u0006\u0003z\u000bG\u0001\u0001\u0000\u0006\u0003y\u000bG\u0001\u0001\u0000\u0004\u0004u\u0001\u0001\u0000\u0006\u0003t\u000bG\u0001\u0001\u0000\u0006\u0003s\u000bG\u0001\u0001\u0000\u0006\u0003r\u000bG\u0001\u0001\u0000\u0006\u0003q\u000bG\u0001\u0001\u0000\u0006\u0003p\u000bG\u0001\u0001\u0000\u0006\u0003o\u000bG\u0001\u0001\u0000\u0006\u0003n\u000bG\u0001\u0001\u0000\u0004\u000bm\u0001\u0001\u0000\u0006\u0003l\u000bG\u0001\u0001\u0000\u0006\u0003k\u000bG\u0001\u0001\u0000\u0006\ba\u000b_\u0001\u0001\u0000\u0004\u000b]\u0001\u0001\u0000\u0006\u0003\\\u000bG\u0001\u0001\u0000\u0006\u0003[\u000bG\u0001\u0001\u0000\u0006\u0003Z\u000bG\u0001\u0001\u0000\u0006\u0003I\u000bG\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0006N\u0007M\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0006U\u0007M\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\u0007X\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\te\nc\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\nj\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\f¨\r§\u0001\u0001\u0000\u0004\u000e±\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\f«\u0001\u0001\u0000\u0004\u0005\u00ad\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0006\u0006¯\u0007M\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\u000f¶\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0004\u000f\u00c3\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001\u0000\u0002\u0001\u0001" });
    }
    
    class CUP$DslParser$actions
    {
        private final DslParser parser;
        
        CUP$DslParser$actions(final DslParser parser) {
            this.parser = parser;
        }
        
        public final Symbol CUP$DslParser$do_action_part00000000(final int CUP$DslParser$act_num, final lr_parser CUP$DslParser$parser, final Stack CUP$DslParser$stack, final int CUP$DslParser$top) throws Exception {
            switch (CUP$DslParser$act_num) {
                case 0: {
                    Command RESULT = null;
                    RESULT = new ShowVersionCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 1: {
                    Object RESULT2 = null;
                    final int start_valleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int start_valright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final Command start_val = (Command)(RESULT2 = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("$START", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT2);
                    CUP$DslParser$parser.done_parsing();
                    return CUP$DslParser$result;
                }
                case 2: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new MidiCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 3: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new NoteCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 4: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new SemitoneCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 5: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new HasEnharmonicCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 6: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new EnharmonicCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 7: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new EnharmonicHighCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 8: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new EnharmonicLowCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 9: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ScaleCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 10: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new HelpCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 11: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PitchTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 12: {
                    Command RESULT = null;
                    RESULT = new GetTempoCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 13: {
                    Command RESULT = null;
                    RESULT = new GetCrotchetCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 14: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new SetTempoCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 15: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PlayCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 16: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new SetTermCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 17: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new CategoryOfCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 18: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PlayScaleCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 19: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new MeaningOfCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 20: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new LanguageOfCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 21: {
                    Command RESULT = null;
                    RESULT = new TutorModeRepeatCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 22: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final String e2 = (String)CUP$DslParser$stack.peek().value;
                    RESULT = new TutorModeAnswerCommand(e2);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 23: {
                    Command RESULT = null;
                    RESULT = new TutorModeQuestionCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 24: {
                    Command RESULT = null;
                    RESULT = new TutorModeStatsCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 25: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new TutorModeRunCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 26: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new GetIntervalSemitoneCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 27: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new IntervalCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 28: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PlayIntervalCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 29: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new IntervalTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 30: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ChordCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 31: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new MusicalTermTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 32: {
                    Command RESULT = null;
                    RESULT = new TutorModeSaveCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 33: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ScaleTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 34: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PlayChordCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 35: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new RestCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 36: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ChordTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 37: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new KeySignatureCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 38: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new KeySignatureTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 39: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ChordFinderCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 40: {
                    Command RESULT = null;
                    RESULT = new GetSwingCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 41: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new SetSwingCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 42: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new EnharmonicIntervalCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 43: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new HasEnharmonicIntervalCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 44: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ChordSpellingTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 45: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new GetInstrumentCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 46: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ListInstrumentsCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 47: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new SetInstrumentCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 48: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new QualityOfCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 49: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ChordFunctionCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 50: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new FunctionOfCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 51: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ChordFunctionTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 52: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ParentOfCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 53: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ModeOfCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 54: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new SetPatternCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 55: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ListPatternsCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 56: {
                    Command RESULT = null;
                    RESULT = new TutorStatsCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 57: {
                    Command RESULT = null;
                    RESULT = new GetUnitDurationCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 58: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new SetUnitDurationCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 59: {
                    Command RESULT = null;
                    RESULT = new ListUnitDurationsCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 60: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new ScaleModeTutorCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 61: {
                    Command RESULT = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4)).right;
                    final List<String> list = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4)).value;
                    final int sleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int sright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final String s = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int oleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int oright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final List<String> o = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    list.add(s);
                    list.addAll(o);
                    RESULT = new SetMelodyCommand(list);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 6), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 62: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PlayPercussionCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 63: {
                    Command RESULT = null;
                    RESULT = new ListPercussionInstrumentsCommand();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 64: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PlayPercussionLoopCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 65: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new AddPercussionLoopCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 66: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new TutorModeStartCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 67: {
                    Command RESULT = null;
                    final int eleft = CUP$DslParser$stack.peek().left;
                    final int eright = CUP$DslParser$stack.peek().right;
                    final List<String> e = (List<String>)CUP$DslParser$stack.peek().value;
                    RESULT = new PlayMelodyCommand(e);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("command", 0, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT);
                    return CUP$DslParser$result;
                }
                case 68: {
                    List<String> RESULT3 = null;
                    final int stringleft = CUP$DslParser$stack.peek().left;
                    final int stringright = CUP$DslParser$stack.peek().right;
                    final String string = (String)CUP$DslParser$stack.peek().value;
                    RESULT3 = new ArrayList<String>();
                    RESULT3.add(string);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("eventList", 11, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 69: {
                    List<String> RESULT3 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final List<String> list = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int stringleft2 = CUP$DslParser$stack.peek().left;
                    final int stringright2 = CUP$DslParser$stack.peek().right;
                    final String string2 = (String)CUP$DslParser$stack.peek().value;
                    list.add(string2);
                    RESULT3 = list;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("eventList", 11, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 70: {
                    String RESULT4 = null;
                    final int notesleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 3)).left;
                    final int notesright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 3)).right;
                    final String notes = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 3)).value;
                    final int durationleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int durationright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final String duration = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    RESULT4 = "{" + notes + "," + duration + "}";
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("event", 10, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 71: {
                    String RESULT4 = null;
                    final int notesleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int notesright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final String notes = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    RESULT4 = "{" + notes + "}";
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("event", 10, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 72: {
                    String RESULT4 = null;
                    final int noteleft = CUP$DslParser$stack.peek().left;
                    final int noteright = CUP$DslParser$stack.peek().right;
                    final String note = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 73: {
                    String RESULT4 = null;
                    final int midileft = CUP$DslParser$stack.peek().left;
                    final int midiright = CUP$DslParser$stack.peek().right;
                    final Integer midi = (Integer)CUP$DslParser$stack.peek().value;
                    RESULT4 = midi.toString();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 74: {
                    String RESULT4 = null;
                    final int restleft = CUP$DslParser$stack.peek().left;
                    final int restright = CUP$DslParser$stack.peek().right;
                    final String rest = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 75: {
                    String RESULT4 = null;
                    final int stringleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int stringright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final String string = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    RESULT4 = "[" + string + "]";
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 76: {
                    String RESULT4 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final String list2 = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int noteleft2 = CUP$DslParser$stack.peek().left;
                    final int noteright2 = CUP$DslParser$stack.peek().right;
                    final String note2 = (String)CUP$DslParser$stack.peek().value;
                    RESULT4 = list2 + "," + note2;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 77: {
                    String RESULT4 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final String list2 = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int midileft2 = CUP$DslParser$stack.peek().left;
                    final int midiright2 = CUP$DslParser$stack.peek().right;
                    final Integer midi2 = (Integer)CUP$DslParser$stack.peek().value;
                    RESULT4 = list2 + "," + midi2;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 78: {
                    String RESULT4 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final String list2 = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int restleft2 = CUP$DslParser$stack.peek().left;
                    final int restright2 = CUP$DslParser$stack.peek().right;
                    final String rest2 = (String)CUP$DslParser$stack.peek().value;
                    RESULT4 = list2 + "," + rest2;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 79: {
                    String RESULT4 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4)).right;
                    final String list2 = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4)).value;
                    final int stringleft2 = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int stringright2 = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final String string2 = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    RESULT4 = list2 + ",[" + string2 + "]";
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noteList", 12, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 4), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 80: {
                    String RESULT4 = null;
                    final int noteleft = CUP$DslParser$stack.peek().left;
                    final int noteright = CUP$DslParser$stack.peek().right;
                    final String note = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("simultaneousNotes", 13, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 81: {
                    String RESULT4 = null;
                    final int midileft = CUP$DslParser$stack.peek().left;
                    final int midiright = CUP$DslParser$stack.peek().right;
                    final Integer midi = (Integer)CUP$DslParser$stack.peek().value;
                    RESULT4 = midi.toString();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("simultaneousNotes", 13, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 82: {
                    String RESULT4 = null;
                    final int chordleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int chordright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final String chord = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int noteleft2 = CUP$DslParser$stack.peek().left;
                    final int noteright2 = CUP$DslParser$stack.peek().right;
                    final String note2 = (String)CUP$DslParser$stack.peek().value;
                    RESULT4 = chord + "," + note2;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("simultaneousNotes", 13, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 83: {
                    String RESULT4 = null;
                    final int chordleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int chordright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final String chord = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int midileft2 = CUP$DslParser$stack.peek().left;
                    final int midiright2 = CUP$DslParser$stack.peek().right;
                    final Integer midi2 = (Integer)CUP$DslParser$stack.peek().value;
                    RESULT4 = chord + "," + midi2;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("simultaneousNotes", 13, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 84: {
                    List<String> RESULT3 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final List<String> list = RESULT3 = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("expr", 1, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 85: {
                    List<String> RESULT3 = null;
                    RESULT3 = null;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("expr", 1, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 86: {
                    List<String> RESULT3 = null;
                    final int listleft = CUP$DslParser$stack.peek().left;
                    final int listright = CUP$DslParser$stack.peek().right;
                    final List<String> list = RESULT3 = (List<String>)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("option", 3, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 87: {
                    List<String> RESULT3 = null;
                    RESULT3 = new ArrayList<String>();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("option", 3, CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 88: {
                    List<String> RESULT3 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final List<String> list = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int cleft = CUP$DslParser$stack.peek().left;
                    final int cright = CUP$DslParser$stack.peek().right;
                    final String c = (String)CUP$DslParser$stack.peek().value;
                    list.add(c);
                    RESULT3 = list;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("csv", 4, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 89: {
                    List<String> RESULT3 = null;
                    final int cleft2 = CUP$DslParser$stack.peek().left;
                    final int cright2 = CUP$DslParser$stack.peek().right;
                    final String c2 = (String)CUP$DslParser$stack.peek().value;
                    RESULT3 = new ArrayList<String>();
                    RESULT3.add(c2);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("csv", 4, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 90: {
                    String RESULT4 = null;
                    final int noteleft = CUP$DslParser$stack.peek().left;
                    final int noteright = CUP$DslParser$stack.peek().right;
                    final String note = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 91: {
                    String RESULT4 = null;
                    final int nleft = CUP$DslParser$stack.peek().left;
                    final int nright = CUP$DslParser$stack.peek().right;
                    final Integer n = (Integer)CUP$DslParser$stack.peek().value;
                    RESULT4 = n.toString();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 92: {
                    String RESULT4 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final List<String> list = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    RESULT4 = list.toString();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 93: {
                    String RESULT4 = null;
                    final int oleft2 = CUP$DslParser$stack.peek().left;
                    final int oright2 = CUP$DslParser$stack.peek().right;
                    final String o2 = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 94: {
                    String RESULT4 = null;
                    final int sleft2 = CUP$DslParser$stack.peek().left;
                    final int sright2 = CUP$DslParser$stack.peek().right;
                    final String s2 = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 95: {
                    String RESULT4 = null;
                    final int octleft = CUP$DslParser$stack.peek().left;
                    final int octright = CUP$DslParser$stack.peek().right;
                    final String oct = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 96: {
                    String RESULT4 = null;
                    final int sigleft = CUP$DslParser$stack.peek().left;
                    final int sigright = CUP$DslParser$stack.peek().right;
                    final String sig = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 97: {
                    String RESULT4 = null;
                    final int arrayleft = CUP$DslParser$stack.peek().left;
                    final int arrayright = CUP$DslParser$stack.peek().right;
                    final String array = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const", 5, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 98: {
                    List<String> RESULT3 = null;
                    RESULT3 = null;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("noParameter", 9, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 99: {
                    String RESULT4 = null;
                    final int eleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int eright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final String e2 = RESULT4 = (String)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("tutor_expr", 2, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 100: {
                    List<String> RESULT3 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).right;
                    final List<String> list = RESULT3 = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 1)).value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("chord_function_expr", 6, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 101: {
                    List<String> RESULT3 = null;
                    RESULT3 = null;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("chord_function_expr", 6, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 102: {
                    List<String> RESULT3 = null;
                    final int listleft = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).left;
                    final int listright = ((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).right;
                    final List<String> list = (List<String>)((Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2)).value;
                    final int cleft = CUP$DslParser$stack.peek().left;
                    final int cright = CUP$DslParser$stack.peek().right;
                    final String c = (String)CUP$DslParser$stack.peek().value;
                    list.add(c);
                    RESULT3 = list;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("csv_function", 7, (Symbol)CUP$DslParser$stack.elementAt(CUP$DslParser$top - 2), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 103: {
                    List<String> RESULT3 = null;
                    final int cleft2 = CUP$DslParser$stack.peek().left;
                    final int cright2 = CUP$DslParser$stack.peek().right;
                    final String c2 = (String)CUP$DslParser$stack.peek().value;
                    RESULT3 = new ArrayList<String>();
                    RESULT3.add(c2);
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("csv_function", 7, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT3);
                    return CUP$DslParser$result;
                }
                case 104: {
                    String RESULT4 = null;
                    final int noteleft = CUP$DslParser$stack.peek().left;
                    final int noteright = CUP$DslParser$stack.peek().right;
                    final String note = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const_function", 8, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 105: {
                    String RESULT4 = null;
                    final int sleft2 = CUP$DslParser$stack.peek().left;
                    final int sright2 = CUP$DslParser$stack.peek().right;
                    final String s2 = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const_function", 8, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 106: {
                    String RESULT4 = null;
                    final int nleft = CUP$DslParser$stack.peek().left;
                    final int nright = CUP$DslParser$stack.peek().right;
                    final Integer n = (Integer)CUP$DslParser$stack.peek().value;
                    RESULT4 = n.toString();
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const_function", 8, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                case 107: {
                    String RESULT4 = null;
                    final int funcleft = CUP$DslParser$stack.peek().left;
                    final int funcright = CUP$DslParser$stack.peek().right;
                    final String func = RESULT4 = (String)CUP$DslParser$stack.peek().value;
                    final Symbol CUP$DslParser$result = this.parser.getSymbolFactory().newSymbol("const_function", 8, CUP$DslParser$stack.peek(), CUP$DslParser$stack.peek(), RESULT4);
                    return CUP$DslParser$result;
                }
                default: {
                    throw new Exception("Invalid action number " + CUP$DslParser$act_num + "found in internal parse table");
                }
            }
        }
        
        public final Symbol CUP$DslParser$do_action(final int CUP$DslParser$act_num, final lr_parser CUP$DslParser$parser, final Stack CUP$DslParser$stack, final int CUP$DslParser$top) throws Exception {
            return this.CUP$DslParser$do_action_part00000000(CUP$DslParser$act_num, CUP$DslParser$parser, CUP$DslParser$stack, CUP$DslParser$top);
        }
    }
}
