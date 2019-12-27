

package Model.Percussion;

import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class PercussionLoop
{
    private List<Set<Integer>> events;
    private static final Integer PERCUSSION_CHANNEL;
    private static final Integer REST;
    private static final String REST_INDICATOR = "-";
    private static final String PLAY_INDICATOR = "x";
    private static final String LOOP_PATTERN = "[x|X|-]+";
    
    public PercussionLoop() {
        this.events = new ArrayList<Set<Integer>>();
        this.events = new ArrayList<Set<Integer>>();
    }
    
    public Boolean addLoop(final Integer instrument, final String beat) {
        if (instrument == null || beat == null) {
            throw new NullPointerException();
        }
        if (!PercussionMap.containsPercussion(instrument) || !beat.matches("[x|X|-]+")) {
            return false;
        }
        Integer count = 0;
        for (final String pointInTime : beat.split("")) {
            final Integer point;
            final Integer newInstrument = point = this.readPoint(pointInTime, instrument);
            final Integer time = count;
            ++count;
            this.updateEvents(point, time);
        }
        return true;
    }
    
    private Integer readPoint(final String point, final Integer midi) {
        if (point.equalsIgnoreCase("-")) {
            return PercussionLoop.REST;
        }
        if (point.equalsIgnoreCase("x")) {
            return midi;
        }
        return null;
    }
    
    private void updateEvents(final Integer newInstrument, final Integer time) {
        if (this.events.size() > time) {
            final Set<Integer> currentInstrument = this.events.get(time);
            if (newInstrument != PercussionLoop.REST) {
                currentInstrument.remove(PercussionLoop.REST);
                currentInstrument.add(newInstrument);
            }
        }
        else {
            final Set<Integer> instrumentSet = new HashSet<Integer>();
            instrumentSet.add(newInstrument);
            this.events.add(instrumentSet);
        }
    }
    
    public List<Set<Integer>> getEvents() {
        return this.events;
    }
    
    public void setLoopNumber(final Integer number) {
        if (!this.events.isEmpty()) {
            final List<Set<Integer>> eventsCopy = new ArrayList<Set<Integer>>(this.events);
            for (int x = 1; x < number; ++x) {
                this.events.addAll(eventsCopy);
            }
        }
    }
    
    static {
        PERCUSSION_CHANNEL = 9;
        REST = -1;
    }
}
