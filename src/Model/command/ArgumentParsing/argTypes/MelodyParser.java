

package Model.command.ArgumentParsing.argTypes;

import Model.command.ArgumentParsing.ArgType;
import Model.Note.unitDuration.UnitDuration;
import Model.Note.Melody.PlayStyle;
import Model.Note.Note;
import Model.command.ArgumentParsing.ArgumentException;
import Model.Note.Melody.NoteCollection;
import java.util.ArrayList;
import Model.command.ArgumentParsing.ArgumentParser;
import java.util.List;
import Model.command.ArgumentParsing.ArgumentTypeParser;

public class MelodyParser implements ArgumentTypeParser
{
    private final int argsNeeded = -1;
    
    @Override
    public Object parseArg(final List<String> args, final ArgumentParser parser) throws ArgumentException {
        final List<NoteCollection> noteCollections = new ArrayList<NoteCollection>();
        for (final String melody : args) {
            noteCollections.addAll(this.getNoteCollections(melody, parser));
        }
        return noteCollections;
    }
    
    private List<NoteCollection> getNoteCollections(String melody, final ArgumentParser parser) throws ArgumentException {
        melody = melody.replace("{", "").replace("}", "");
        final String[] noteStrings = melody.split(",(?![^\\[\\]]*\\])");
        final List<NoteCollection> noteCollections = new ArrayList<NoteCollection>();
        List<Note> notes = new ArrayList<Note>();
        UnitDuration unitDuration = null;
        Boolean durationFound = false;
        for (final String noteString : noteStrings) {
            if (noteString.startsWith("[")) {
                final NoteCollection noteCollection = new NoteCollection();
                noteCollection.setPlayStyle(PlayStyle.ARPEGGIO);
                noteCollection.setNotes(notes);
                noteCollections.add(noteCollection);
                notes = new ArrayList<Note>();
                noteCollections.add(this.getSimultaneousNotes(noteString, parser));
            }
            else if (durationFound || noteString.startsWith("\"")) {
                durationFound = true;
                final List<String> duration = new ArrayList<String>();
                duration.add(noteString);
                unitDuration = (UnitDuration)parser.getRequiredArg(ArgType.UNIT_DURATION, duration);
            }
            else {
                try {
                    final List<String> noteList = new ArrayList<String>();
                    noteList.add(noteString);
                    notes.add((Note)parser.getRequiredArg(ArgType.NOTE_MIDI, noteList));
                }
                catch (ArgumentException exp) {
                    if (!noteString.matches("R|r|-1")) {
                        throw exp;
                    }
                    final Note note = new Note("R", -1, -1, true);
                    notes.add(note);
                }
            }
        }
        if (notes.size() > 0) {
            final NoteCollection noteCollection2 = new NoteCollection();
            noteCollection2.setPlayStyle(PlayStyle.ARPEGGIO);
            noteCollection2.setNotes(notes);
            noteCollections.add(noteCollection2);
        }
        for (final NoteCollection noteCollection3 : noteCollections) {
            noteCollection3.setUnitDuration(unitDuration);
        }
        return noteCollections;
    }
    
    private NoteCollection getSimultaneousNotes(String simultaneousNotes, final ArgumentParser parser) throws ArgumentException {
        simultaneousNotes = simultaneousNotes.replace("[", "").replace("]", "");
        final String[] noteStrings = simultaneousNotes.split(",");
        final List<Note> notes = new ArrayList<Note>();
        for (final String noteString : noteStrings) {
            final List<String> noteList = new ArrayList<String>();
            noteList.add(noteString);
            notes.add((Note)parser.getRequiredArg(ArgType.NOTE_MIDI, noteList));
        }
        final NoteCollection noteCollection = new NoteCollection();
        noteCollection.setPlayStyle(PlayStyle.SIMULTANEOUS);
        noteCollection.setNotes(notes);
        return noteCollection;
    }
    
    @Override
    public Integer totalArgsNeeded() {
        return -1;
    }
    
    @Override
    public Boolean matchType(final String argument) {
        return argument.startsWith("{") && argument.endsWith("}");
    }
}
