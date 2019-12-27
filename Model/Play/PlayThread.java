

package Model.Play;

import Controller.OuterTemplateController;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import seng302.App;
import javax.sound.midi.Transmitter;
import javax.sound.midi.MidiSystem;
import java.util.Iterator;
import Model.Note.Settings.TempoInformation;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import Model.instrument.InstrumentInformation;
import org.apache.log4j.Logger;
import javax.sound.midi.Track;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.util.Collection;

public class PlayThread implements Runnable
{
    protected static final int TICK_RESOLUTION = 6144;
    private Collection<PlayEvent> inputEvent;
    private Integer instrumentId;
    private Sequencer sequencer;
    private Sequence seq;
    private Track track;
    private int sequencerTempo;
    private final int endBufferLower = 150;
    private final int endBufferThreshold = 400;
    private final int endBuffer = 500;
    private static Logger log;
    public static boolean toDrumPadAnimation;
    
    PlayThread(final Collection<PlayEvent> inputEvents, final int tempo) {
        this.inputEvent = inputEvents;
        this.sequencerTempo = tempo;
        this.instrumentId = InstrumentInformation.getInstrumentId();
    }
    
    @Override
    public void run() {
        try {
            this.startPlaying();
            final ShortMessage instrumentChange = new ShortMessage();
            instrumentChange.setMessage(192, 0, this.instrumentId, 0);
            this.track.add(new MidiEvent(instrumentChange, 0L));
            int counter = 0;
            long currentTicks = 0L;
            for (final PlayEvent event : this.inputEvent) {
                final long endEventTicks = currentTicks + event.getTicks();
                if (event.getMidiChannel() == 9) {
                    PlayThread.toDrumPadAnimation = true;
                }
                else {
                    PlayThread.toDrumPadAnimation = false;
                }
                if (!event.getNotes().contains(-1)) {
                    if (!event.getfromKeyboard()) {
                        final MetaMessage mt = new MetaMessage();
                        final String midiStr = event.getNotes().toString();
                        mt.setMessage(127, midiStr.getBytes(), midiStr.length());
                        final MidiEvent meStart = new MidiEvent(mt, currentTicks);
                        if (!PlayThread.toDrumPadAnimation) {
                            final MidiEvent meEnd = new MidiEvent(mt, endEventTicks);
                            final MidiEvent meMiddle = new MidiEvent(mt, currentTicks + (endEventTicks - currentTicks) / 2L);
                            if (counter > 4) {
                                this.track.add(meMiddle);
                            }
                            else if (counter > 7) {
                                this.track.add(meEnd);
                            }
                            else {
                                this.track.add(meStart);
                            }
                        }
                        else {
                            this.track.add(meStart);
                        }
                    }
                    for (final Integer midi : event.getNotes()) {
                        final ShortMessage noteOn = new ShortMessage();
                        noteOn.setMessage(144, event.getMidiChannel(), midi, event.getVolume());
                        final MidiEvent noteOnEvent = new MidiEvent(noteOn, currentTicks);
                        this.track.add(noteOnEvent);
                        final ShortMessage noteOff = new ShortMessage();
                        noteOff.setMessage(128, event.getMidiChannel(), midi, event.getVolume());
                        final MidiEvent noteOffEvent = new MidiEvent(noteOff, endEventTicks);
                        this.track.add(noteOffEvent);
                    }
                    ++counter;
                }
                currentTicks += event.getTicks();
            }
            this.sequencer.setSequence(this.seq);
            this.sequencer.start();
            this.sequencer.addMetaEventListener(new PlayNotify());
            final double units = currentTicks / 6144.0;
            final long playingTime = (long)(units * TempoInformation.crotchetBpmToMs((int)this.sequencer.getTempoInBPM()));
            long n;
            if (playingTime < 400L) {
                n = 150L;
            }
            else {
                this.getClass();
                n = 500L;
            }
            final long endBuffer = n;
            Thread.sleep(playingTime + endBuffer);
        }
        catch (InterruptedException e2) {
            this.sequencer.stop();
            this.sequencer.close();
        }
        catch (Exception e) {
            PlayThread.log.error(e.toString());
        }
        this.closePlaying();
    }
    
    private void startPlaying() throws MidiUnavailableException, InvalidMidiDataException {
        (this.sequencer = MidiSystem.getSequencer()).open();
        for (final Transmitter transmitter : this.sequencer.getTransmitters()) {
            transmitter.setReceiver(App.synth.getReceiver());
        }
        this.sequencer.setTempoInBPM((float)this.sequencerTempo);
        this.seq = new Sequence(0.0f, 6144);
        this.track = this.seq.createTrack();
    }
    
    private void closePlaying() {
        this.sequencer.close();
        OuterTemplateController.highlightsDisable();
    }
    
    public Collection<PlayEvent> getInputEvent() {
        return this.inputEvent;
    }
    
    static {
        PlayThread.log = Logger.getLogger(PlayThread.class.getName());
    }
}
