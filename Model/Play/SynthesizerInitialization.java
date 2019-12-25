// 
// Decompiled by Procyon v0.5.36
// 

package Model.Play;

import java.io.InputStream;
import java.io.BufferedInputStream;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class SynthesizerInitialization
{
    public static Synthesizer synth;
    private static Soundbank defaultSoundBank;
    private static Soundbank newSoundBank;
    
    public static Synthesizer initialization() {
        try {
            (SynthesizerInitialization.synth = MidiSystem.getSynthesizer()).open();
        }
        catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        SynthesizerInitialization.defaultSoundBank = SynthesizerInitialization.synth.getDefaultSoundbank();
        try {
            final InputStream bufferedInput = new BufferedInputStream(SynthesizerInitialization.class.getResourceAsStream("/FluidR3-GM2-2.sf2"));
            SynthesizerInitialization.newSoundBank = MidiSystem.getSoundbank(bufferedInput);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        SynthesizerInitialization.synth.unloadAllInstruments(SynthesizerInitialization.defaultSoundBank);
        SynthesizerInitialization.synth.loadAllInstruments(SynthesizerInitialization.newSoundBank);
        return SynthesizerInitialization.synth;
    }
}
