

package Model.Note.Scale;

import java.util.Iterator;
import java.util.ArrayList;
import Model.Note.Note;
import java.util.List;
import java.util.Collection;

public class Pentatonic extends Scale
{
    protected Collection<Integer> notInScalePositions;
    protected Integer scaleSize;
    
    @Override
    protected List<Note> afterProcess() {
        final List<Note> newNoteInScale = new ArrayList<Note>();
        Integer index = 0;
        for (final Note note : this.notesInScale) {
            if (!this.notInScalePositions.contains(index)) {
                newNoteInScale.add(note);
            }
            ++index;
        }
        return newNoteInScale;
    }
}
