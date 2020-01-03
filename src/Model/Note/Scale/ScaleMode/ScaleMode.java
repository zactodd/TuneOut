

package Model.Note.Scale.ScaleMode;

import Model.Note.NoteMap;
import Model.Note.Note;
import java.util.List;
import Model.Note.Scale.Scale;

public class ScaleMode
{
    private String modeName;
    private Scale parentScale;
    private Integer quality;
    private List<Integer> adaptions;
    
    public ScaleMode(final String modeName, final Integer quality, final Scale parentScale, final List<Integer> adaptions) {
        this.quality = quality;
        this.modeName = modeName;
        this.parentScale = parentScale;
        this.adaptions = adaptions;
    }
    
    private List<Note> adaptScale(final Note note) {
        this.parentScale.setNotesInScale(note.getNoteWithOctave());
        if (!this.parentScale.isNull()) {
            final List<Note> scale = this.parentScale.getNotesInScale();
            for (Integer pos = 0; pos < this.adaptions.size(); ++pos) {
                final Integer adaption = this.adaptions.get(pos);
                String currentNote = NoteMap.getSemitone(scale.get(pos).getNoteWithOctave(), adaption);
                if (pos != 0 && currentNote != null) {
                    currentNote = this.parentScale.alphabetize(scale.get(pos - 1).getNoteName(), currentNote);
                }
                scale.set(pos, NoteMap.getNote(currentNote));
            }
            return scale;
        }
        return null;
    }
    
    public List<Note> getScaleMode(final Note note) {
        return this.adaptScale(note);
    }
    
    public String getModeName() {
        return this.modeName;
    }
    
    public Scale getParentScale() {
        return this.parentScale;
    }
    
    public Integer getQuality() {
        return this.quality;
    }
    
    public List<Integer> getAdaptions() {
        return this.adaptions;
    }
}
