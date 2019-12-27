

package Model.instrument;

import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class InstrumentsMap
{
    private static List<Instrument> instruments;
    private static Map<String, Instrument> instrumentMap;
    
    public static Instrument getInstrument(final Integer index) {
        return InstrumentsMap.instruments.get(index);
    }
    
    public static Instrument getInstrument(final String instrument) {
        return InstrumentsMap.instrumentMap.get(instrument);
    }
    
    public static boolean instAvailability(final Integer index) {
        final Instrument inst = getInstrument(index);
        return inst.getAvailability();
    }
    
    public static String getList(final Boolean availableList) {
        int inGroupCount = 0;
        String groupList = "";
        final int lineLength = 35;
        final String lineDecoration = "-";
        String line = "";
        for (int index = 0; index < lineLength; ++index) {
            line += lineDecoration;
        }
        line += "\n";
        String previousGroup = InstrumentsMap.instruments.get(0).getInstrumentGroup();
        String output = "\n";
        if (availableList) {
            output += "List of available instruments\n";
        }
        else {
            output += "Full list of midi instruments\n";
        }
        output = output + previousGroup + ":\n" + line;
        for (int index = 0; index < InstrumentsMap.instruments.size(); ++index) {
            final Instrument inst = getInstrument(index);
            final String nextGroup = inst.getInstrumentGroup();
            if (!nextGroup.equals(previousGroup)) {
                if (inGroupCount > 0) {
                    output += groupList;
                }
                previousGroup = nextGroup;
                inGroupCount = 0;
                groupList = "\n" + inst.getInstrumentGroup() + "\n" + line;
            }
            if (availableList) {
                if (inst.getAvailability()) {
                    groupList = groupList + Integer.toString(index) + "\t" + inst.getInstrumentName() + "\t\n";
                    ++inGroupCount;
                }
            }
            else {
                groupList = groupList + Integer.toString(index) + "\t" + inst.getInstrumentName();
                if (!inst.getAvailability()) {
                    groupList += " (unavailable)";
                }
                groupList += "\t\n";
                ++inGroupCount;
            }
            if (index == InstrumentsMap.instruments.size() - 1 && inGroupCount > 0) {
                output += groupList;
            }
        }
        return output;
    }
    
    public static List<Instrument> getAllInstruments() {
        return InstrumentsMap.instruments;
    }
    
    static {
        InstrumentsMap.instruments = new ArrayList<Instrument>();
        InstrumentsMap.instrumentMap = new HashMap<String, Instrument>();
        InstrumentsMap.instruments.add(new Instrument(0, "Acoustic Grand Piano", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(1, "Bright Acoustic Piano", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(2, "Electric Grand Piano", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(3, "Honky-tonk Piano", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(4, "Electric Piano 1", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(5, "Electric Piano 2", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(6, "Harpsichord", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(7, "Clavinet", "Piano", true));
        InstrumentsMap.instruments.add(new Instrument(8, "Celesta", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(9, "Glockenspiel", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(10, "Music Box", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(11, "Vibraphone", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(12, "Marimba", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(13, "Xylophone", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(14, "Tubular Bells", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(15, "Dulcimer", "Chromatic Percussion", true));
        InstrumentsMap.instruments.add(new Instrument(16, "Drawbar Organ", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(17, "Percussive Organ", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(18, "Rock Organ", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(19, "Church Organ", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(20, "Reed Organ", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(21, "Accordion", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(22, "Harmonica", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(23, "Tango Accordion", "Organ", true));
        InstrumentsMap.instruments.add(new Instrument(24, "Acoustic Guitar (nylon)", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(25, "Acoustic Guitar (steel)", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(26, "Electric Guitar (jazz)", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(27, "Electric Guitar (clean)", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(28, "Electric Guitar (muted)", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(29, "Overdriven Guitar", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(30, "Distortion Guitar", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(31, "Guitar Harmonics", "Guitar", true));
        InstrumentsMap.instruments.add(new Instrument(32, "Acoustic Bass", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(33, "Electric Bass (finger)", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(34, "Electric Bass (pick)", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(35, "Fretless Bass", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(36, "Slap Bass 1", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(37, "Slap Bass 2", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(38, "Synth Bass 1", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(39, "Synth Bass 2", "Bass", true));
        InstrumentsMap.instruments.add(new Instrument(40, "Violin", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(41, "Viola", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(42, "Cello", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(43, "Contrabass", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(44, "Tremolo Strings", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(45, "Pizzicato Strings", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(46, "Orchestral Harp", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(47, "Timpani", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(48, "String Ensemble 1", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(49, "String Ensemble 2", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(50, "Synth Strings 1", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(51, "Synth Strings 2", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(52, "Choir Aahs", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(53, "Voice Oohs", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(54, "Synth Voice", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(55, "Orchestra Hit", "Strings", true));
        InstrumentsMap.instruments.add(new Instrument(56, "Trumpet", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(57, "Trombone", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(58, "Tuba", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(59, "Muted Trumpet", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(60, "French Horn", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(61, "Brass Section", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(62, "Synth Brass 1", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(63, "Synth Brass 2", "Brass", true));
        InstrumentsMap.instruments.add(new Instrument(64, "Soprano Sax", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(65, "Alto Sax", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(66, "Tenor Sax", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(67, "Baritone Sax", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(68, "Oboe", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(69, "English Horn", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(70, "Bassoon", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(71, "Clarinet", "Reed", true));
        InstrumentsMap.instruments.add(new Instrument(72, "Piccolo", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(73, "Flute", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(74, "Recorder", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(75, "Pan Flute", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(76, "Blown Bottle", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(77, "Shakuhachi", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(78, "Whistle", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(79, "Ocarina", "Pipe", true));
        InstrumentsMap.instruments.add(new Instrument(80, "Lead 1 (square)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(81, "Lead 2 (sawtooth)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(82, "Lead 3 (calliope)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(83, "Lead 4 (chiff)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(84, "Lead 5 (charang)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(85, "Lead 6 (voice)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(86, "Lead 7 (fifths)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(87, "Lead 8 (bass + lead)", "Synth Lead", true));
        InstrumentsMap.instruments.add(new Instrument(88, "Pad 1 (new age)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(89, "Pad 2 (warm)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(90, "Pad 3 (polysynth)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(91, "Pad 4 (choir)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(92, "Pad 5 (bowed)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(93, "Pad 6 (metallic)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(94, "Pad 7 (halo)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(95, "Pad 8 (sweep)", "Synth Pad", true));
        InstrumentsMap.instruments.add(new Instrument(96, "FX 1 (rain)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(97, "FX 2 (soundtrack)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(98, "FX 3 (crystal)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(99, "FX 4 (atmosphere)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(100, "FX 5 (brightness)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(101, "FX 6 (goblins)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(102, "FX 7 (echoes)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(103, "FX 8 (sci-fi)", "Synth Effects", true));
        InstrumentsMap.instruments.add(new Instrument(104, "Sitar", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(105, "Banjo", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(106, "Shamisen", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(107, "Koto", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(108, "Kalimba", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(109, "Bag pipe", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(110, "Fiddle", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(111, "Shanai", "Ethnic", true));
        InstrumentsMap.instruments.add(new Instrument(112, "Tinkle Bell", "Percussive", false));
        InstrumentsMap.instruments.add(new Instrument(113, "Agogo", "Percussive", false));
        InstrumentsMap.instruments.add(new Instrument(114, "Steel Drums", "Percussive", false));
        InstrumentsMap.instruments.add(new Instrument(115, "Woodblock", "Percussive", false));
        InstrumentsMap.instruments.add(new Instrument(116, "Taiko Drum", "Percussive", false));
        InstrumentsMap.instruments.add(new Instrument(117, "Melodic Tom", "Percussive", false));
        InstrumentsMap.instruments.add(new Instrument(118, "Synth Drum", "Percussive", false));
        InstrumentsMap.instruments.add(new Instrument(119, "Reverse Cymbal", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(120, "Guitar Fret Noise", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(121, "Breath Noise", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(122, "Seashore", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(123, "Bird Tweet", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(124, "Telephone Ring", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(125, "Helicopter", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(126, "Applause", "Sound Effects", false));
        InstrumentsMap.instruments.add(new Instrument(127, "Gunshot", "Sound Effects", false));
        for (final Instrument instrument : InstrumentsMap.instruments) {
            InstrumentsMap.instrumentMap.put(instrument.getInstrumentName(), instrument);
        }
    }
}
