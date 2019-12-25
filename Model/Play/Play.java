// 
// Decompiled by Procyon v0.5.36
// 

package Model.Play;

import java.util.Set;
import Model.Note.Settings.SwingMap;
import Model.Note.NoteMap;
import java.util.List;
import Model.instrument.Instrument;
import java.util.Collections;
import Model.Note.Melody.PlayStyle;
import Model.Note.Note;
import Model.Note.Melody.NoteCollection;
import Model.instrument.InstrumentInformation;
import Model.Note.Melody.Melody;
import java.util.AbstractList;
import java.util.Iterator;
import Model.Note.Settings.TempoInformation;
import java.util.ArrayList;
import Model.Note.unitDuration.UnitDuration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Collection;
import org.apache.log4j.Logger;
import java.util.concurrent.ThreadPoolExecutor;

public class Play
{
    private Thread playNotesThread;
    private ThreadPoolExecutor fixedPool;
    private PlayType playType;
    private PlayThread playThreadObj;
    private static final String ORDER_BOTH = "-b";
    private static final String ORDER_DESCENDING = "-d";
    private static final Integer enfersizedVolume;
    private static final Integer tripletFeelPosition;
    private static final int standardMidiChannel = 0;
    private static final int percussionMidiChannel = 9;
    private static Logger log;
    
    public Play(final PlayType playType) {
        this.playType = playType;
    }
    
    private void playEvents(final Collection<PlayEvent> inputEvents, final int tempo) {
        final Thread prevPlayNotesThread = this.playNotesThread;
        this.playThreadObj = new PlayThread(inputEvents, tempo);
        this.playNotesThread = new Thread(this.playThreadObj);
        switch (this.playType) {
            case QUEUED: {
                this.initialiseFixedPool();
                this.fixedPool.submit(this.playNotesThread);
                break;
            }
            case REPLACE: {
                if (prevPlayNotesThread != null) {
                    prevPlayNotesThread.interrupt();
                    try {
                        prevPlayNotesThread.join();
                    }
                    catch (InterruptedException e) {
                        Play.log.error(e.toString());
                    }
                }
                this.playNotesThread.start();
                break;
            }
            case OVERLAPPING: {
                this.playNotesThread.start();
                break;
            }
        }
    }
    
    private void initialiseFixedPool() {
        if (this.fixedPool != null && this.fixedPool.isTerminating()) {
            try {
                this.fixedPool.awaitTermination(2147483647L, TimeUnit.NANOSECONDS);
            }
            catch (InterruptedException e) {
                Play.log.error(e.toString());
            }
        }
        if (this.fixedPool == null || this.fixedPool.isTerminated()) {
            this.fixedPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
        }
    }
    
    public void stopAllPlayingThreads() {
        if (this.fixedPool != null && !this.fixedPool.isTerminating() && !this.fixedPool.isTerminated()) {
            this.fixedPool.shutdownNow();
        }
        if (this.playNotesThread != null) {
            this.playNotesThread.interrupt();
        }
    }
    
    public void playNote(final int midi, final int tempo, final UnitDuration unitDuration, final boolean fromKeyboard) {
        final Collection<Integer> note = new ArrayList<Integer>();
        note.add(midi);
        final Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        final long ticks = this.unitDurationToTicks(unitDuration);
        events.add(new PlayEvent(note, ticks, fromKeyboard, 0));
        this.playEvents(events, tempo);
    }
    
    public void playChordSimultaneous(final ArrayList<Integer> chord, final int initialGap, final UnitDuration unitDuration, final boolean fromKeyboard) {
        Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        final long ticks = this.unitDurationToTicks(unitDuration);
        events = this.insertSilenceNotes(events, initialGap, ticks);
        events.add(new PlayEvent(chord, ticks, fromKeyboard, 0));
        this.playEvents(events, TempoInformation.getTempInBpm());
    }
    
    private Collection<PlayEvent> insertSilenceNotes(final Collection<PlayEvent> events, final int numSilenceNotes, final long ticks) {
        final Collection<Integer> silenceNote = new ArrayList<Integer>();
        silenceNote.add(-1);
        for (int x = 0; x < numSilenceNotes; ++x) {
            events.add(new PlayEvent(silenceNote, ticks, false, 0));
        }
        return events;
    }
    
    private Collection<PlayEvent> insertStartBuffer(final Collection<PlayEvent> events) {
        final Collection<Integer> silenceNote = new ArrayList<Integer>();
        silenceNote.add(-1);
        events.add(new PlayEvent(silenceNote, 3072L, false, 0));
        return events;
    }
    
    public void playChordArpeggiated(final ArrayList<Integer> chord, final int initialGap, final UnitDuration unitDuration) {
        Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        final long ticks = this.unitDurationToTicks(unitDuration);
        events = this.insertStartBuffer(events);
        events = this.insertSilenceNotes(events, initialGap, ticks);
        for (final Integer note : chord) {
            final Collection<Integer> noteArray = new ArrayList<Integer>();
            noteArray.add(note);
            events.add(new PlayEvent(noteArray, ticks / 2L, false, 0));
        }
        this.playEvents(events, TempoInformation.getTempInBpm());
    }
    
    public void playChordArpeggiatedThenSimultaneous(final ArrayList<Integer> chord, final int initialGap, final UnitDuration unitDuration) {
        Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        final long ticks = this.unitDurationToTicks(unitDuration);
        events = this.insertStartBuffer(events);
        events = this.insertSilenceNotes(events, initialGap, ticks);
        for (final Integer note : chord) {
            final Collection<Integer> noteArray = new ArrayList<Integer>();
            noteArray.add(note);
            events.add(new PlayEvent(noteArray, ticks / 2L, false, 0));
        }
        events = this.insertSilenceNotes(events, 2, ticks);
        events.add(new PlayEvent(chord, ticks, false, 0));
        this.playEvents(events, TempoInformation.getTempInBpm());
    }
    
    public void playTwoNotes(final int initialGap, final int start, final int middleGap, final int end, final UnitDuration unitDuration) {
        final int sizeOfArr = initialGap + middleGap + 2;
        final AbstractList<Integer> twoNoteArray = new ArrayList<Integer>(sizeOfArr);
        for (int i = 0; i < initialGap; ++i) {
            twoNoteArray.add(i, -1);
        }
        twoNoteArray.add(initialGap, start);
        for (int j = 0; j < middleGap; ++j) {
            twoNoteArray.add(initialGap + 1 + j, -1);
        }
        twoNoteArray.add(sizeOfArr - 1, end);
        final long ticks = this.unitDurationToTicks(unitDuration);
        final Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        for (final Integer midi : twoNoteArray) {
            final Collection<Integer> note = new ArrayList<Integer>();
            note.add(midi);
            events.add(new PlayEvent(note, ticks, false, 0));
        }
        this.playEvents(events, TempoInformation.getTempInBpm());
    }
    
    public void playMelody(final Melody melody) {
        final Instrument oldInstrument = InstrumentInformation.getInstrument();
        InstrumentInformation.setInstrumentId(melody.getInstrument().getInstrumentNumber(), false);
        Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        events = this.insertStartBuffer(events);
        for (final NoteCollection noteCollection : melody.getNoteCollections()) {
            final long ticks = this.unitDurationToTicks(noteCollection.getUnitDuration());
            final List<Integer> notesMidi = new ArrayList<Integer>();
            for (final Note note : noteCollection.getNotes()) {
                if (note != null) {
                    notesMidi.add(note.getMidiNumber());
                }
            }
            if (noteCollection.getPlayStyle().equals(Model.Note.Melody.PlayStyle.ARPEGGIO)) {
                for (final Integer note2 : notesMidi) {
                    events.add(new PlayEvent(new ArrayList<Integer>(Collections.singletonList(note2)), ticks, false, 0));
                }
            }
            else {
                events.add(new PlayEvent(notesMidi, ticks, false, 0));
            }
        }
        this.playEvents(events, TempoInformation.getTempInBpm());
        InstrumentInformation.setInstrumentId(oldInstrument.getInstrumentNumber(), false);
    }
    
    public void playScale(final List<String> inputArray, final String order, final int initialGap, final PlayStyle style, final UnitDuration unitDuration, final boolean fromKeyboard) {
        final List<String> noteArray = arrayForPlay(inputArray, order);
        final int sizeOfArr = initialGap + noteArray.size();
        final List<String> noteArrayWithDelay = new ArrayList<String>();
        for (int silentIndex = 0; silentIndex < initialGap; ++silentIndex) {
            noteArrayWithDelay.add(silentIndex, " ");
        }
        int oldScaleIndex = 0;
        for (int newScaleIndex = initialGap; newScaleIndex < sizeOfArr; ++newScaleIndex) {
            noteArrayWithDelay.add(newScaleIndex, noteArray.get(oldScaleIndex));
            ++oldScaleIndex;
        }
        final long ticks = this.unitDurationToTicks(unitDuration);
        Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        events = this.insertStartBuffer(events);
        int counter = 1;
        for (final Integer midi : NoteMap.getMidiArray(noteArrayWithDelay)) {
            final Collection<Integer> note = new ArrayList<Integer>();
            note.add(midi);
            if (style == PlayStyle.SWING) {
                final long firstNoteDuration = (long)(ticks * SwingMap.getCurrentSwingPercent());
                if (counter % 2 == 1) {
                    events.add(new PlayEvent(note, firstNoteDuration, fromKeyboard, 0));
                }
                else {
                    events.add(new PlayEvent(note, ticks - firstNoteDuration, fromKeyboard, 0));
                }
            }
            else if (style == PlayStyle.BLUES) {
                if ((counter - 1) % Play.tripletFeelPosition == 0) {
                    events.add(new PlayEvent(note, Play.enfersizedVolume, ticks, fromKeyboard, 0));
                }
                else {
                    events.add(new PlayEvent(note, ticks / 3L, fromKeyboard, 0));
                }
            }
            else {
                events.add(new PlayEvent(note, ticks / 2L, fromKeyboard, 0));
            }
            ++counter;
        }
        this.playEvents(events, TempoInformation.getTempInBpm());
    }
    
    public static List<String> arrayForPlay(final List<String> inputArray, final String order) {
        List<String> noteArray = inputArray;
        if (order.equals("-b")) {
            noteArray = ascendingDescendingArray(inputArray);
        }
        else if (order.equals("-d")) {
            noteArray = reverseArray(inputArray);
        }
        return noteArray;
    }
    
    private static List<String> reverseArray(final List<String> inputArray) {
        final int length = inputArray.size();
        final List<String> reversedArray = new ArrayList<String>();
        for (int index = 0; index < length; ++index) {
            reversedArray.add(index, inputArray.get(length - index - 1));
        }
        return reversedArray;
    }
    
    private static List<String> ascendingDescendingArray(final List<String> inputArray) {
        final int notesInScale = inputArray.size();
        final List<String> bothArray = new ArrayList<String>();
        int notesEndIndex = notesInScale - 2;
        for (int noteIndex = 0; noteIndex < notesInScale * 2 - 1; ++noteIndex) {
            if (noteIndex < notesInScale) {
                bothArray.add(noteIndex, inputArray.get(noteIndex));
            }
            else {
                bothArray.add(noteIndex, inputArray.get(notesEndIndex));
                --notesEndIndex;
            }
        }
        return bothArray;
    }
    
    public List<PlayEvent> getPlayEvents() {
        List<PlayEvent> events = new ArrayList<PlayEvent>();
        if (this.playThreadObj != null) {
            events = (List<PlayEvent>)(List)this.playThreadObj.getInputEvent();
        }
        return events;
    }
    
    private long unitDurationToTicks(final UnitDuration unitDuration) {
        double result = 24576.0 / unitDuration.getUnitDurationDivider();
        switch (unitDuration.getNumberOfDots()) {
            case 1: {
                result += result / 2.0;
                break;
            }
            case 2: {
                result += result / 2.0 + result / 4.0;
                break;
            }
            case 3: {
                result += result / 2.0 + result / 4.0 + result / 8.0;
                break;
            }
        }
        return (long)result;
    }
    
    public void playPercussion(final int instrument, final int tempo, final UnitDuration unitDuration, final boolean fromDrumPad) {
        final Collection<Integer> percussion = new ArrayList<Integer>();
        percussion.add(instrument);
        final Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        final long ticks = this.unitDurationToTicks(unitDuration);
        events.add(new PlayEvent(percussion, ticks, fromDrumPad, 9));
        this.playEvents(events, tempo);
    }
    
    public void playPercussion(final List<Set<Integer>> instruments, final int tempo, final UnitDuration unitDuration) {
        final Collection<PlayEvent> events = new ArrayList<PlayEvent>();
        final long ticks = this.unitDurationToTicks(unitDuration);
        instruments.forEach(instrument -> events.add(new PlayEvent(instrument, ticks, false, 9)));
        this.playEvents(events, tempo);
    }
    
    public ThreadPoolExecutor getFixedPool() {
        return this.fixedPool;
    }
    
    static {
        enfersizedVolume = 127;
        tripletFeelPosition = 3;
        Play.log = Logger.getLogger(Play.class.getName());
    }
    
    public enum PlayType
    {
        QUEUED, 
        REPLACE, 
        OVERLAPPING;
    }
    
    public enum PlayStyle
    {
        NONE, 
        SWING, 
        BLUES;
    }
}
