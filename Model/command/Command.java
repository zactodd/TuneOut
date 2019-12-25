// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import Model.Note.NoteMap;
import Environment.Environment;
import Model.CommandMessages;

public abstract class Command
{
    static final int NOTE_MIDDLE_POS = 4;
    static final String MIDI_FORMAT = "^(-)?[0-9]*";
    static final String NUMBER_FORMAT = ".*\\d+.*";
    protected String returnValue;
    private final String OUT_OF_RANGE_MIDI;
    
    public Command() {
        this.returnValue = "";
        this.OUT_OF_RANGE_MIDI = CommandMessages.getMessage("OUT_OF_RANGE_MIDI");
    }
    
    public abstract void execute(final Environment p0);
    
    public static String translate(final String inputString, final String outputString) {
        if (outputString == null) {
            return null;
        }
        if (!checkMidiInput(outputString)) {
            return checkMidiInput(inputString) ? NoteMap.getMidi(outputString).toString() : translateOctave(inputString, defaultOctave(outputString));
        }
        return checkMidiInput(inputString) ? outputString : NoteMap.getNoteString(Integer.parseInt(outputString));
    }
    
    protected static String translateOctave(final String inputNote, final String outputNote) {
        return checkOctave(inputNote) ? outputNote : removeDefaultOctave(outputNote);
    }
    
    protected static String defaultNote(final String inputString) {
        return checkMidiInput(inputString) ? NoteMap.getNoteString(Integer.parseInt(inputString)) : defaultOctave(inputString);
    }
    
    public static String defaultOctave(String note) {
        note = note.substring(0, 1).toUpperCase() + note.substring(1);
        return (checkOctave(note) || checkMidiInput(note)) ? note : (note + 4);
    }
    
    protected String parseInputNote(final String noteOrMidi) {
        if (noteOrMidi.contains("\"")) {
            this.incorrectUseOfQuotesErrorRaiser();
        }
        String inputNote;
        try {
            final Integer number = Integer.parseInt(noteOrMidi);
            inputNote = NoteMap.getNoteString(Integer.parseInt(noteOrMidi));
            if (inputNote == null) {
                this.returnValue = this.OUT_OF_RANGE_MIDI;
            }
        }
        catch (NumberFormatException e) {
            inputNote = defaultNote(noteOrMidi);
        }
        return inputNote;
    }
    
    private static String removeDefaultOctave(final String noteWithOctave) {
        final int length = noteWithOctave.length() - 1;
        return (Integer.toString(4).equalsIgnoreCase(noteWithOctave.substring(length)) && Character.isAlphabetic(noteWithOctave.charAt(0))) ? noteWithOctave.substring(0, length) : noteWithOctave;
    }
    
    private static Boolean checkOctave(final String note) {
        final int length = note.length() - 1;
        return Character.isDigit(note.charAt(length)) && Character.isAlphabetic(note.charAt(0));
    }
    
    public static Boolean checkMidiInput(final String inputString) {
        return inputString.matches("^(-)?[0-9]*");
    }
    
    protected void wrongNumOfParamErrorRaiser() {
        throw new NullPointerException();
    }
    
    protected void incorrectUseOfQuotesErrorRaiser() {
        throw new NullPointerException();
    }
    
    protected String integerErrorRaiser(final String theNote) {
        if (theNote.contains("\"")) {
            this.incorrectUseOfQuotesErrorRaiser();
        }
        try {
            final Integer note = Integer.parseInt(theNote);
            throw new NullPointerException();
        }
        catch (NumberFormatException e) {
            return theNote;
        }
    }
    
    protected String stringErrorRaiser(final String string) {
        if (!string.startsWith("\"") || !string.endsWith("\"")) {
            throw new NullPointerException();
        }
        return string.replace("\"", "");
    }
    
    protected boolean checkForIntegerInput(final String maybeSemitone) {
        try {
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
