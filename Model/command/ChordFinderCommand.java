

package Model.command;

import Model.CommandMessages;
import Environment.Environment;
import java.util.Iterator;
import Model.Note.Chord.ChordMap;
import Model.Note.NoteMap;
import Model.Note.Note;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChordFinderCommand extends Command
{
    private static final int PARAM_COUNT = 3;
    private static final int NOTES_COUNT = 1;
    private static final int INVERSION_COUNT = 2;
    private static final int ENHARMONIC_COUNT = 3;
    private static final int NOTES_POS = 0;
    private static final int INVERSION_POS = 1;
    private static final int ENHARMONIC_POS = 2;
    private static final String OVERRIDE_ENHARMONIC_ONE = "-f";
    private static final String OVERRIDE_ENHARMONIC_ALL = "-e";
    private static final String OVERRIDE_NO_INVERSION = "-n";
    private static final String OVERRIDE_WITH_INVERSION = "-i";
    private static final String OUT_OF_RANGE_MIDI;
    private static final String NO_CHORD_FOUND;
    private static final String EXPECT_FIRST_PARAM_NO_BRACKETS;
    private static final String EXPECT_NOTE_NO_OCTAVE_OR_MIDI;
    private static final String EXPECT_INVERSION_OR_ENHARMONIC_MSG;
    private Boolean inverted;
    private Boolean allEnharmonic;
    
    public ChordFinderCommand(final List<String> args) {
        this.inverted = false;
        this.allEnharmonic = true;
        Boolean error = false;
        if (args.size() > 0 && args.size() <= 3) {
            if (args.size() == 2) {
                if (args.get(1).equals("-n") || args.get(1).equals("-i")) {
                    this.inverted = this.processInversion(args.get(1));
                }
                else {
                    this.allEnharmonic = this.processEnharmonic(args.get(1));
                }
            }
            else if (args.size() == 3) {
                if (args.get(1).equals("-n") || args.get(1).equals("-i")) {
                    this.inverted = this.processInversion(args.get(1));
                    this.allEnharmonic = this.processEnharmonic(args.get(2));
                }
                else {
                    this.allEnharmonic = this.processEnharmonic(args.get(1));
                    this.inverted = this.processInversion(args.get(2));
                }
            }
            if (!args.get(0).contains("[") && !args.get(0).contains("]")) {
                this.returnValue = ChordFinderCommand.EXPECT_FIRST_PARAM_NO_BRACKETS;
            }
            else if (this.inverted == null || this.allEnharmonic == null) {
                this.returnValue = ChordFinderCommand.EXPECT_INVERSION_OR_ENHARMONIC_MSG;
            }
            else {
                final String notes = args.get(0).replaceAll("\\s+|\\[|\\]", "");
                final List<String> tempArray = Arrays.asList(notes.split(","));
                final List<Note> noteArray = new ArrayList<Note>();
                if (notes.matches(".*\\d.*")) {
                    this.returnValue = ChordFinderCommand.EXPECT_NOTE_NO_OCTAVE_OR_MIDI;
                }
                else {
                    for (final String note : tempArray) {
                        final String theNote = Command.defaultNote(note);
                        if (theNote == null) {
                            this.returnValue = ChordFinderCommand.OUT_OF_RANGE_MIDI;
                            error = true;
                            break;
                        }
                        if (theNote.contains("\"")) {
                            this.incorrectUseOfQuotesErrorRaiser();
                        }
                        else {
                            noteArray.add(NoteMap.getNote(theNote));
                        }
                    }
                    if (!error) {
                        final List<String> foundChords = ChordMap.findChordsFromNotes(noteArray, this.inverted);
                        if (foundChords.size() == 0) {
                            this.returnValue = ChordFinderCommand.NO_CHORD_FOUND;
                        }
                        else if (this.allEnharmonic) {
                            this.returnValue = foundChords.toString().replaceAll("\\[|\\]", "");
                        }
                        else {
                            final String onlyOne = foundChords.get(0);
                            this.returnValue = onlyOne.replaceAll("\\[|\\]", "");
                        }
                    }
                }
            }
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    private Boolean processInversion(final String str) {
        if (str.equals("-i")) {
            return true;
        }
        if (str.equals("-n")) {
            return false;
        }
        return null;
    }
    
    private Boolean processEnharmonic(final String str) {
        if (str.equals("-e")) {
            return true;
        }
        if (str.equals("-f")) {
            return false;
        }
        return null;
    }
    
    static {
        OUT_OF_RANGE_MIDI = CommandMessages.getMessage("OUT_OF_RANGE_MIDI");
        NO_CHORD_FOUND = CommandMessages.getMessage("NO_CHORD_FOUND");
        EXPECT_FIRST_PARAM_NO_BRACKETS = CommandMessages.getMessage("EXPECT_FIRST_PARAM_NO_BRACKETS");
        EXPECT_NOTE_NO_OCTAVE_OR_MIDI = CommandMessages.getMessage("EXPECT_NOTE_NO_OCTAVE_OR_MIDI");
        EXPECT_INVERSION_OR_ENHARMONIC_MSG = String.format("Valid parameters are: %s (return inversions) or %s (no inversion), and %s (return all enharmonic chords) or %s (first only)", "-i", "-n", "-e", "-f");
    }
}
