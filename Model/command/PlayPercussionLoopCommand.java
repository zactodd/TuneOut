

package Model.command;

import Model.CommandMessages;
import Model.Note.Settings.TempoInformation;
import Model.Note.unitDuration.UnitDurationInformation;
import Model.Percussion.PercussionLoopMap;
import Environment.Environment;
import Model.command.ArgumentParsing.ArgumentException;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.Collection;
import Model.command.ArgumentParsing.ArgType;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import Model.Note.unitDuration.UnitDuration;
import Model.command.ArgumentParsing.OnSuccess;

public class PlayPercussionLoopCommand extends Command implements OnSuccess
{
    private static final Integer NAME_POS;
    private static final Integer REPEAT_POS;
    private static final Integer REQUIRED_ARG_COUNT;
    private static final Integer UNIT_DURATION_POS;
    private static final Integer OPTIONAL_ARG_COUNT;
    private static final String REPEATIOMNS_STRING = " %d times";
    private UnitDuration unitDuration;
    private List<Set<Integer>> events;
    private Integer tempo;
    private static final String LOOP_EXIST_ERROR;
    private static final String PLAYING_LOOP;
    private Boolean isSucces;
    
    public PlayPercussionLoopCommand(final List<String> args) throws ArgumentException {
        this.isSucces = false;
        if (args.size() >= PlayPercussionLoopCommand.REQUIRED_ARG_COUNT && args.size() <= PlayPercussionLoopCommand.OPTIONAL_ARG_COUNT) {
            final List<ArgType> argTypes = new ArrayList<ArgType>();
            argTypes.add(ArgType.STRING);
            argTypes.add(ArgType.INTEGER);
            argTypes.add(ArgType.UNIT_DURATION);
            final List<String> requiredArgs = new ArrayList<String>();
            requiredArgs.add(args.get(0));
            final List<String> optionalArgs = new ArrayList<String>();
            try {
                optionalArgs.addAll(args.subList(1, args.size()));
            }
            catch (IllegalArgumentException ex) {}
            final ArgumentParser argParser = new ArgumentParser();
            this.returnValue = argParser.parseArgs(requiredArgs, optionalArgs, argTypes, this);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        if (this.isSucces) {
            env.getPlay().playPercussion(this.events, this.tempo, this.unitDuration);
        }
        env.setResponse(this.returnValue);
    }
    
    @Override
    public String onSuccess(final List<Object> requiredArgs, final List<Object> optionalArgs) throws ArgumentException {
        final String loopName = requiredArgs.get(PlayPercussionLoopCommand.NAME_POS).replace("\"", "");
        Integer numberOfLoops = optionalArgs.get(PlayPercussionLoopCommand.REPEAT_POS);
        numberOfLoops = ((numberOfLoops == null) ? 1 : numberOfLoops);
        if (numberOfLoops < 1) {
            throw new NullPointerException();
        }
        if (!PercussionLoopMap.containsPercussionLoop(loopName)) {
            return String.format(PlayPercussionLoopCommand.LOOP_EXIST_ERROR, loopName);
        }
        this.unitDuration = UnitDurationInformation.getUnitDuration();
        this.tempo = TempoInformation.getTempInBpm();
        this.updateEvents(numberOfLoops, loopName);
        String returnMessage = String.format(PlayPercussionLoopCommand.PLAYING_LOOP, loopName, (numberOfLoops > 1) ? String.format(" %d times", numberOfLoops) : "");
        final UnitDuration tempUnitDuration = optionalArgs.get(PlayPercussionLoopCommand.UNIT_DURATION_POS);
        if (tempUnitDuration != null) {
            this.unitDuration = tempUnitDuration;
            returnMessage += String.format(". Using \"%1$s%2$s\" as note duration.", this.unitDuration.getUnitDurationName(), this.unitDuration.getDots());
        }
        return returnMessage;
    }
    
    @Override
    public String getCommandName() {
        return "playPercussionLoop";
    }
    
    private void updateEvents(final Integer repeat, final String loopName) {
        this.events = new ArrayList<Set<Integer>>();
        final List<Set<Integer>> singleEvent = PercussionLoopMap.getPercussionLoop(loopName).getEvents();
        for (Integer decrement = repeat; decrement > 0; --decrement) {
            this.events.addAll(singleEvent);
        }
        this.isSucces = true;
    }
    
    static {
        NAME_POS = 0;
        REPEAT_POS = 0;
        REQUIRED_ARG_COUNT = 1;
        UNIT_DURATION_POS = 1;
        OPTIONAL_ARG_COUNT = 3;
        LOOP_EXIST_ERROR = CommandMessages.getMessage("LOOP_NAME_DOESNT_EXIST");
        PLAYING_LOOP = CommandMessages.getMessage("SUCCESS_PLAYING_LOOP");
    }
}
