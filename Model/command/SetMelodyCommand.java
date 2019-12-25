// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Model.Note.Melody.MelodyMap;
import Model.Note.Melody.Melody;
import Model.instrument.Instrument;
import Model.Note.Melody.NoteCollection;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import java.util.Iterator;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.Collection;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class SetMelodyCommand extends Command implements OnSuccess
{
    private final Integer namePos;
    private final Integer melodyPos;
    private final Integer instrumentPos;
    private final Integer tempoPos;
    private List<String> melodies;
    
    public SetMelodyCommand(final List<String> args) throws ArgumentException {
        this.namePos = 1;
        this.melodyPos = 0;
        this.instrumentPos = 0;
        this.tempoPos = 1;
        if (args.size() >= 2) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.MELODY);
            argTypes.add(ArgType.STRING);
            argTypes.add(ArgType.INSTRUMENT);
            String last = "";
            for (final String arg : args) {
                if (arg.startsWith("{") && arg.endsWith("}")) {
                    last = arg;
                }
            }
            final Integer melodyEnd = args.indexOf(last) + 2;
            this.melodies = args.subList(0, melodyEnd - 1);
            final List<String> requiredArgs = new ArrayList<String>();
            requiredArgs.addAll(args.subList(0, melodyEnd));
            final List<String> optionalArgs = new ArrayList<String>();
            try {
                optionalArgs.addAll(args.subList(melodyEnd, args.size()));
            }
            catch (IllegalArgumentException ex) {}
            final ArgumentParser argumentParser = new ArgumentParser();
            this.returnValue = argumentParser.parseArgs(requiredArgs, optionalArgs, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final String melodyName = requiredArgs.get(this.namePos);
        final List<NoteCollection> noteCollections = requiredArgs.get(this.melodyPos);
        final Instrument instrument = optionalArgs.get(this.instrumentPos);
        final Melody melody = new Melody(melodyName);
        melody.setInstrument(instrument);
        melody.setNoteCollection(noteCollections);
        MelodyMap.addMelody(melody);
        String melodiesString = "";
        boolean first = true;
        for (final String melodyString : this.melodies) {
            if (!first) {
                melodiesString += ", ";
            }
            first = false;
            melodiesString += melodyString;
        }
        String instrumentName = "";
        if (instrument != null) {
            instrumentName = String.format(" using \"%1$s\" instrument", instrument.getInstrumentName());
        }
        return String.format("Set %1$s melody to \"%2$s\"%3$s.", melodyName, melodiesString, instrumentName);
    }
    
    @Override
    public String getCommandName() {
        return "setMelody";
    }
}
