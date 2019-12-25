// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing.argTypes;

import Model.CommandMessages;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class TempoParser implements ArgumentTypeParser
{
    private static final String INVALID_TEMPO_NEGATIVE;
    private static final String INVALID_TEMPO;
    private static final String INVALID_TEMPO_PARAMETERS;
    private final Integer defaultTempoPos;
    
    public TempoParser() {
        this.defaultTempoPos = 0;
    }
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final Integer overrideIndex = args.indexOf("-f");
        if (overrideIndex != -1 && args.size() == 2) {
            final String tempo = args.get(1 - overrideIndex);
            final Integer bpm = Integer.parseInt(tempo);
            if (bpm > 0) {
                return bpm;
            }
            throw new ArgumentException(TempoParser.INVALID_TEMPO_NEGATIVE);
        }
        else {
            if (overrideIndex != -1 || args.size() != 1) {
                throw new ArgumentException(TempoParser.INVALID_TEMPO_PARAMETERS);
            }
            final String tempo = args.get(this.defaultTempoPos);
            final Integer bpm = Integer.parseInt(tempo);
            if (this.validTempo(bpm)) {
                return bpm;
            }
            throw new ArgumentException(TempoParser.INVALID_TEMPO);
        }
    }
    
    private Boolean validTempo(final Integer bpm) {
        return bpm >= 20 && bpm <= 300;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return -1;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.matches("[+-]?\\d+") || argument.matches("-f");
    }
    
    static {
        INVALID_TEMPO_NEGATIVE = CommandMessages.getMessage("INVALID_TEMPO_NEGATIVE");
        INVALID_TEMPO = CommandMessages.getMessage("INVALID_TEMPO");
        INVALID_TEMPO_PARAMETERS = CommandMessages.getMessage("INVALID_TEMPO_PARAMETERS");
    }
}
