// 
// Decompiled by Procyon v0.5.36
// 

package Model.Percussion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PercussionMap
{
    private static final List<Percussion> PERCUSSION_INSTRUMENTS;
    
    public static Percussion getPercussion(final Integer midi) {
        for (final Percussion percussion : PercussionMap.PERCUSSION_INSTRUMENTS) {
            if (percussion.getMidi().equals(midi)) {
                return percussion;
            }
        }
        return null;
    }
    
    public static Percussion getPercussion(final String instrument) {
        for (final Percussion percussion : PercussionMap.PERCUSSION_INSTRUMENTS) {
            if (percussion.getInstrument().equalsIgnoreCase(instrument)) {
                return percussion;
            }
        }
        return null;
    }
    
    public static Boolean containsPercussion(final String instrument) {
        for (final Percussion percussion : PercussionMap.PERCUSSION_INSTRUMENTS) {
            if (percussion.getInstrument().equalsIgnoreCase(instrument)) {
                return true;
            }
        }
        return false;
    }
    
    public static Boolean containsPercussion(final Integer midi) {
        for (final Percussion percussion : PercussionMap.PERCUSSION_INSTRUMENTS) {
            if (percussion.getMidi().equals(midi)) {
                return true;
            }
        }
        return false;
    }
    
    public static List<Percussion> getAllPercussions() {
        return PercussionMap.PERCUSSION_INSTRUMENTS;
    }
    
    static {
        PERCUSSION_INSTRUMENTS = new ArrayList<Percussion>() {
            {
                this.add(new Percussion(33, "Metronome Click"));
                this.add(new Percussion(34, "Metronome Bell"));
                this.add(new Percussion(35, "Bass Drum 2"));
                this.add(new Percussion(36, "Bass Drum 1"));
                this.add(new Percussion(37, "Side Stick/Rimshot"));
                this.add(new Percussion(38, "Snare Drum 1"));
                this.add(new Percussion(39, "Hand Clap"));
                this.add(new Percussion(40, "Snare Drum 2"));
                this.add(new Percussion(41, "Low Tom 2"));
                this.add(new Percussion(42, "Closed Hi-hat"));
                this.add(new Percussion(43, "Low Tom 1"));
                this.add(new Percussion(44, "Pedal Hi-hat"));
                this.add(new Percussion(45, "Mid Tom 2"));
                this.add(new Percussion(46, "Open Hi-hat"));
                this.add(new Percussion(47, "Mid Tom 1"));
                this.add(new Percussion(48, "High Tom 2"));
                this.add(new Percussion(49, "Crash Cymbal 1"));
                this.add(new Percussion(50, "High Tom 1"));
                this.add(new Percussion(51, "Ride Cymbal 1"));
                this.add(new Percussion(52, "Chinese Cymbal"));
                this.add(new Percussion(53, "Ride Bell"));
                this.add(new Percussion(54, "Tambourine"));
                this.add(new Percussion(55, "Splash Cymbal"));
                this.add(new Percussion(56, "Cowbell"));
                this.add(new Percussion(57, "Crash Cymbal 2"));
                this.add(new Percussion(58, "Vibra Slap"));
                this.add(new Percussion(59, "Ride Cymbal 2"));
                this.add(new Percussion(60, "High Bongo"));
                this.add(new Percussion(61, "Low Bongo"));
                this.add(new Percussion(62, "Mute High Conga"));
                this.add(new Percussion(63, "Open High Conga"));
                this.add(new Percussion(64, "Low Conga"));
                this.add(new Percussion(65, "High Timbale"));
                this.add(new Percussion(66, "Low Timbale"));
                this.add(new Percussion(67, "High Agogo"));
                this.add(new Percussion(68, "Low Agogo"));
                this.add(new Percussion(69, "Cabasa"));
                this.add(new Percussion(70, "Maracas"));
                this.add(new Percussion(71, "Short Whistle"));
                this.add(new Percussion(72, "Long Whistle"));
                this.add(new Percussion(73, "Short Guiro"));
                this.add(new Percussion(74, "Long Guiro"));
                this.add(new Percussion(75, "Claves"));
                this.add(new Percussion(76, "High Wood Block"));
                this.add(new Percussion(77, "Low Wood Block"));
                this.add(new Percussion(78, "Mute Cuica"));
                this.add(new Percussion(79, "Open Cuica"));
                this.add(new Percussion(80, "Mute Triangle"));
                this.add(new Percussion(81, "Open Triangle"));
            }
        };
    }
}
