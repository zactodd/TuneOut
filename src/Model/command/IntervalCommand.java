

package Model.command;

import Model.CommandMessages;
import Model.Note.NoteMap;
import Model.Note.Intervals.Interval;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.Note.Note;
import Model.command.ArgumentParsing.OnSuccess;

public class IntervalCommand extends Command implements OnSuccess
{
    protected static final int PARAM_COUNT = 2;
    private static final int INTERVAL_POS = 1;
    protected static final int NOTE_POS = 0;
    private static final String OUT_OF_RANGE_INTERVAL;
    private String inputNote;
    protected Note nextNote;
    protected Note note;
    
    public IntervalCommand() {
    }
    
    public IntervalCommand(final List<String> args) throws ArgumentException {
        if (args.size() == 2) {
            this.inputNote = args.get(0);
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.NOTE_MIDI);
            argTypes.add(ArgType.INTERVAL);
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(args, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    protected void setInputNote(final String inputNote) {
        this.inputNote = inputNote;
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    protected String correctOutput(final String inputNote, final String translatedNote, final String outputNote) {
        return translate(inputNote, outputNote);
    }
    
    private String processNote(final String inputNote, final Interval interval, final Note note) {
        String returnMessage = "";
        this.nextNote = NoteMap.getNote(NoteMap.getSemitone(note.getNoteWithOctave(), interval.getSemitone()));
        if (this.nextNote != null) {
            returnMessage = this.correctOutput(inputNote, note.getNoteWithOctave(), this.nextNote.getNoteWithOctave());
        }
        return returnMessage;
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        this.note = requiredArgs.get(0);
        final Interval interval = requiredArgs.get(1);
        final String returnMessage = this.processNote(this.inputNote, interval, this.note);
        if (!returnMessage.equals("")) {
            return returnMessage;
        }
        throw new ArgumentException(IntervalCommand.OUT_OF_RANGE_INTERVAL);
    }
    
    @Override
    public String getCommandName() {
        return "interval";
    }
    
    static {
        OUT_OF_RANGE_INTERVAL = CommandMessages.getMessage("OUT_OF_RANGE_INTERVAL");
    }
}
