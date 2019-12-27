

package Model.command;

import Environment.Environment;
import Model.Note.NoteMap;
import java.util.Iterator;
import Model.Note.Scale.KeySignature;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Model.CommandMessages;
import java.util.Arrays;
import java.util.List;

public class KeySignatureCommand extends Command
{
    private final Integer PARAM_COUNT;
    private final Integer OPTIONAL_PARAM_COUNT;
    private final String OVERRIDE = "-n";
    private final String SIG_OVERRIDE_MINOR_ONLY = "-m";
    private final String SIG_OVERRIDE_BOTH = "-b";
    private final String SIG_OVERRIDE_MAJOR = "-M";
    private final Integer OVERRIDE_POS;
    private final Integer NOTE_OR_SIG_POS;
    private final Integer SCALE_POS;
    private final List<String> VALID_SCALE;
    private final String REG_SIGNATURE_PATTERN = "[0-9]+[b|#]";
    private final String NUMBER_PATTERN = ".*\\d+.*";
    private final String NOTE_ARRAY_PATTERN = "^\\[.*\\]$";
    private final String KEY_SIG_PATTERN = ".*[a-zA-Z]+.*";
    private final String NO_SIGNATURE = "No key signature matches the input.";
    private final String invalidKey;
    private final String INVALID_OVERRIDE = "Invalid Override. Use -n for number of sharps/flats";
    private final String INVALID_OVERRIDE_KEYSIG = "Invalid Override. Use -m for minor only, -M for major only, or -b for both major and minor.";
    private final String INVALID_SYNTAX = "Incorrect Syntax";
    private final String INVALID_MIDI = "Midi numbers and octaves not allowed";
    private final String INVALID_NOTE = "Only one sharp/flat is allowed. Try their equivalent note.";
    private final String INVALID_SCALE = "Invalid scale";
    private final String INVALID_NUMBER = "Invalid number of flats/sharps. It must be 0-7.";
    private final String MAJOR = "maj";
    private final String MAJOR2 = "major";
    private final String MINOR = "min";
    private final String MINOR2 = "minor";
    private final String BOTH = "both";
    private final String DOUBLE_SHARP = "x";
    private final String DOUBLE_FLAT = "bb";
    
    public KeySignatureCommand(final List<String> value) {
        this.PARAM_COUNT = 2;
        this.OPTIONAL_PARAM_COUNT = 3;
        this.OVERRIDE_POS = 2;
        this.NOTE_OR_SIG_POS = 0;
        this.SCALE_POS = 1;
        this.VALID_SCALE = Arrays.asList("major", "minor", "maj", "min");
        this.invalidKey = CommandMessages.getMessage("INVALID_KEY");
        final Integer paramSize = value.size();
        final Pattern sigPattern = Pattern.compile("[0-9]+[b|#]");
        final Matcher matcher = sigPattern.matcher(value.get(this.NOTE_OR_SIG_POS));
        if (paramSize == this.OPTIONAL_PARAM_COUNT) {
            this.returnValue = this.processThreeParameters(value);
        }
        else if (paramSize == this.PARAM_COUNT) {
            this.returnValue = this.processTwoParameters(value);
        }
        else if (paramSize == 1 || matcher.matches()) {
            this.returnValue = this.processOneParameter(value);
        }
        else {
            this.wrongNumOfParamErrorRaiser();
        }
    }
    
    private String processNoteListInput(final List<String> value, final String mode) {
        final String notes = value.get(this.NOTE_OR_SIG_POS).replaceAll("\\s+|\\[|\\]", "").toLowerCase();
        final List<String> tempArray = Arrays.asList(notes.split(","));
        final List<String> noteArray = new ArrayList<String>();
        for (final String theNote : tempArray) {
            final String note = theNote;
            if (theNote.contains("\"")) {
                this.incorrectUseOfQuotesErrorRaiser();
            }
            this.returnValue = this.processDoubleFlatsAndSharps(theNote);
            if (this.returnValue != null) {
                return this.returnValue;
            }
            noteArray.add(theNote);
        }
        final String keysig = KeySignature.getKeySignatureFromNoteList(noteArray);
        if (keysig == null) {
            return "No key signature matches the input.";
        }
        if (mode == "maj") {
            return keysig;
        }
        if (mode == "min") {
            return KeySignature.getEquivalentMinor(keysig);
        }
        if (mode != "both") {
            return keysig;
        }
        final String majorScale = keysig;
        final String minorScale = KeySignature.getEquivalentMinor(keysig);
        if (majorScale == null || minorScale == null) {
            return "No key signature matches the input.";
        }
        return majorScale + " or " + minorScale;
    }
    
    private String processEmptyNoteListInput(final String mode) {
        final String keysig = KeySignature.getKeySignatureWithNoSharpsorFlats();
        if (mode == "maj") {
            return keysig;
        }
        if (mode == "min") {
            return KeySignature.getEquivalentMinor(keysig);
        }
        if (mode == "both") {
            final String majorScale = keysig;
            final String minorScale = KeySignature.getEquivalentMinor(keysig);
            return majorScale + " or " + minorScale;
        }
        return keysig;
    }
    
    private List<String> processStandardInput(final List<String> value) {
        if (!this.VALID_SCALE.contains(value.get(this.SCALE_POS).replace("\"", "").toLowerCase())) {
            return null;
        }
        String scale = value.get(this.SCALE_POS).replace("\"", "").toLowerCase();
        final String inputNote = value.get(this.NOTE_OR_SIG_POS);
        if (inputNote.matches(".*\\d+.*")) {
            return null;
        }
        final String note = this.parseInputNote(value.get(this.NOTE_OR_SIG_POS));
        final String noteName = NoteMap.getNote(note).getNoteName().toLowerCase();
        if (scale.equals("maj")) {
            scale = "major";
        }
        else if (scale.equals("min")) {
            scale = "minor";
        }
        final List<String> keysig = KeySignature.getKeySignature(noteName, scale);
        if (keysig.equals(null)) {
            return null;
        }
        final List<String> newkeysig = new ArrayList<String>();
        for (final String noteInKeySig : keysig) {
            final String newNote = noteInKeySig.substring(0, 1).toUpperCase() + noteInKeySig.substring(1);
            newkeysig.add(newNote);
        }
        return newkeysig;
    }
    
    private String processOtherInput(final List<String> value, final String mode) {
        final String modifier = value.get(this.NOTE_OR_SIG_POS);
        if (mode.equals("maj")) {
            return KeySignature.getKeySignatureFromModifierMajor(modifier);
        }
        if (mode.equals("min")) {
            return KeySignature.getKeySignatureFromModifierMinor(modifier);
        }
        if (mode.equals("both")) {
            final String major = KeySignature.getKeySignatureFromModifierMajor(modifier);
            final String minor = KeySignature.getKeySignatureFromModifierMinor(modifier);
            return major + " and " + minor;
        }
        return null;
    }
    
    private String processThreeParameters(final List<String> input) {
        final String keyOrNote = input.get(this.NOTE_OR_SIG_POS);
        final String scale = input.get(this.SCALE_POS).replace("\"", "").toLowerCase();
        final String override = input.get(this.OVERRIDE_POS).replace("\"", "").toLowerCase();
        this.returnValue = this.processDoubleFlatsAndSharps(keyOrNote);
        if (this.returnValue != null) {
            return this.returnValue;
        }
        if (!this.VALID_SCALE.contains(scale)) {
            return this.returnValue = "Invalid scale";
        }
        if (!override.equals("-n")) {
            return this.returnValue = "Invalid Override. Use -n for number of sharps/flats";
        }
        if (!this.checkIfNoteisinKeySigMap(scale, keyOrNote)) {
            return "No key signature matches the input.";
        }
        final List<String> signature = this.processStandardInput(input);
        if (signature.equals(null)) {
            this.returnValue = "Incorrect Syntax";
        }
        return this.returnValue = KeySignature.getNumberOfmModifier(signature);
    }
    
    private String processTwoParameters(final List<String> input) {
        final String keyOrNote = input.get(this.NOTE_OR_SIG_POS);
        final String overrideParam = input.get(this.SCALE_POS).replace("\"", "");
        final String scale = overrideParam.toLowerCase();
        final Pattern sigPattern = Pattern.compile("[0-9]+[b|#]");
        final Matcher matcher = sigPattern.matcher(keyOrNote);
        final Pattern noteArrayPattern = Pattern.compile("^\\[.*\\]$");
        final Matcher noteArrayMatcher = noteArrayPattern.matcher(keyOrNote);
        if (!this.VALID_SCALE.contains(scale)) {
            if (overrideParam.equals("-b") || overrideParam.equals("-m") || overrideParam.equals("-M")) {
                if (matcher.matches()) {
                    final int num = Integer.parseInt(keyOrNote.replaceAll("\\D+", ""));
                    if (num > 7 || num < 0) {
                        return "Invalid number of flats/sharps. It must be 0-7.";
                    }
                    final List<String> inputNotes = new ArrayList<String>();
                    inputNotes.add(keyOrNote);
                    if (overrideParam.equals("-m")) {
                        this.returnValue = this.processOtherInput(inputNotes, "min");
                    }
                    else if (overrideParam.equals("-b")) {
                        this.returnValue = this.processOtherInput(inputNotes, "both");
                    }
                    else if (overrideParam.equals("-M")) {
                        this.returnValue = this.processOtherInput(inputNotes, "maj");
                    }
                    else {
                        this.returnValue = "Invalid Override. Use -m for minor only, -M for major only, or -b for both major and minor.";
                    }
                    return this.returnValue;
                }
                else {
                    if (!keyOrNote.matches(".*[a-zA-Z]+.*") && (overrideParam.equals("-b") || overrideParam.equals("-m") || overrideParam.equals("-M"))) {
                        String mode = "";
                        if (overrideParam.equals("-b")) {
                            mode = "both";
                        }
                        else if (overrideParam.equals("-m")) {
                            mode = "min";
                        }
                        else if (overrideParam.equals("-M")) {
                            mode = "maj";
                        }
                        return this.returnValue = this.processEmptyNoteListInput(mode);
                    }
                    if (!keyOrNote.matches(".*[a-zA-Z]+.*")) {
                        return this.returnValue = "Invalid Override. Use -n for number of sharps/flats";
                    }
                    if (overrideParam.equals("-m")) {
                        return this.returnValue = this.processNoteListInput(input, "min");
                    }
                    if (overrideParam.equals("-M")) {
                        return this.returnValue = this.processNoteListInput(input, "maj");
                    }
                    if (overrideParam.equals("-b")) {
                        return this.returnValue = this.processNoteListInput(input, "both");
                    }
                    if (this.returnValue == null) {
                        return this.returnValue = "Invalid Override. Use -m for minor only, -M for major only, or -b for both major and minor.";
                    }
                }
            }
            else if (matcher.matches() || noteArrayMatcher.matches()) {
                return "Invalid Override. Use -m for minor only, -M for major only, or -b for both major and minor.";
            }
            return this.returnValue = this.invalidKey;
        }
        this.returnValue = this.processDoubleFlatsAndSharps(keyOrNote);
        if (this.returnValue != null) {
            return this.returnValue;
        }
        if (!this.checkIfNoteisinKeySigMap(scale, keyOrNote)) {
            return "No key signature matches the input.";
        }
        final List<String> signature = this.processStandardInput(input);
        if (signature == null) {
            return this.returnValue = "No key signature matches the input.";
        }
        return this.returnValue = signature.toString();
    }
    
    private String processOneParameter(final List<String> input) {
        final String keyOrNote = input.get(this.NOTE_OR_SIG_POS);
        String keySig = "";
        final Pattern sigPattern = Pattern.compile("[0-9]+[b|#]");
        final Matcher sigMatcher = sigPattern.matcher(keyOrNote);
        final Pattern noteArrayPattern = Pattern.compile("^\\[.*\\]$");
        final Matcher noteArrayMatcher = noteArrayPattern.matcher(keyOrNote);
        if (sigMatcher.matches()) {
            final int num = Integer.parseInt(keyOrNote.replaceAll("\\D+", ""));
            if (num > 7 || num < 0) {
                return "Invalid number of flats/sharps. It must be 0-7.";
            }
            return this.processOtherInput(input, "maj");
        }
        else {
            if (!noteArrayMatcher.matches()) {
                return "Incorrect Syntax";
            }
            if (!keyOrNote.matches(".*[a-zA-Z]+.*")) {
                keySig = this.processEmptyNoteListInput("maj");
            }
            else {
                keySig = this.processNoteListInput(input, "maj");
            }
            if (keySig == null) {
                return this.returnValue = "No key signature matches the input.";
            }
            return keySig;
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    private String processDoubleFlatsAndSharps(final String theNote) {
        if (theNote.matches(".*\\d+.*")) {
            return "Midi numbers and octaves not allowed";
        }
        if (theNote.length() > 3 || theNote.contains("x") || (theNote.contains("bb") && theNote.length() >= 3)) {
            return "Only one sharp/flat is allowed. Try their equivalent note.";
        }
        return null;
    }
    
    private boolean checkIfNoteisinKeySigMap(final String scale, final String note) {
        if (scale.equals("maj") || scale.equals("major")) {
            if (KeySignature.isInMajKeySigMap(note)) {
                return true;
            }
        }
        else if ((scale.equals("min") || scale.equals("minor")) && KeySignature.isInMinKeySigMap(note)) {
            return true;
        }
        return false;
    }
}
