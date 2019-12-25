// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Model.CommandMessages;
import Model.Percussion.PercussionLoop;
import Model.Percussion.PercussionLoopMap;
import Model.Percussion.Percussion;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.List;
import Model.command.ArgumentParsing.OnSuccess;

public class AddPercussionLoopCommand extends Command implements OnSuccess
{
    private static final Integer NAME_POS;
    private static final Integer INTRUMENT_POS;
    private static final Integer BEAT_POS;
    private static final Integer ARGUMENT_COUNT;
    private static final String CREATING_LOOP;
    private static final String ADDED_TO_LOOP;
    
    public AddPercussionLoopCommand(final List<String> args) throws ArgumentException {
        if (args.size() == AddPercussionLoopCommand.ARGUMENT_COUNT) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.STRING);
            argTypes.add(ArgType.PERCUSSION);
            argTypes.add(ArgType.BEAT);
            this.returnValue = new ArgumentParser().parseArgs(args, new ArrayList<String>(), argTypes, this);
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
        String returnMeassege = "";
        final String loopName = requiredArgs.get(AddPercussionLoopCommand.NAME_POS).replace("\"", "");
        final Percussion percussion = requiredArgs.get(AddPercussionLoopCommand.INTRUMENT_POS);
        final Integer midi = percussion.getMidi();
        final String beat = requiredArgs.get(AddPercussionLoopCommand.BEAT_POS).replace("\"", "");
        if (!PercussionLoopMap.containsPercussionLoop(loopName)) {
            returnMeassege += String.format(AddPercussionLoopCommand.CREATING_LOOP, loopName);
            final PercussionLoop percussionLoop = new PercussionLoop();
            percussionLoop.addLoop(midi, beat);
            PercussionLoopMap.add(loopName, percussionLoop);
        }
        else {
            PercussionLoopMap.updateMap(loopName, midi, beat);
        }
        returnMeassege += String.format(AddPercussionLoopCommand.ADDED_TO_LOOP, percussion.getInstrument(), beat, loopName);
        return returnMeassege;
    }
    
    @Override
    public String getCommandName() {
        return "addToLoop";
    }
    
    static {
        NAME_POS = 0;
        INTRUMENT_POS = 1;
        BEAT_POS = 2;
        ARGUMENT_COUNT = 3;
        CREATING_LOOP = CommandMessages.getMessage("CREATING_LOOP");
        ADDED_TO_LOOP = CommandMessages.getMessage("ADDED_TO_LOOP");
    }
}
