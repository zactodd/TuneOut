

package Model.Note.Scale.ScaleMode;

import java.util.List;
import Model.Note.Note;
import java.util.LinkedHashSet;
import java.util.Set;
import Model.Note.Scale.Scale;

public abstract class ScaleModes
{
    Scale parentScale;
    Set<ScaleMode> scaleModes;
    ScaleMode firstMode;
    
    public ScaleModes() {
        this.scaleModes = new LinkedHashSet<ScaleMode>();
    }
    
    public List<Note> getScale(final Note note, final Integer quality) {
        List<Note> scale = null;
        for (final ScaleMode scaleMode : this.scaleModes) {
            if (scaleMode.getQuality().equals(quality)) {
                scale = scaleMode.getScaleMode(note);
                break;
            }
        }
        return scale;
    }
    
    public List<Note> getScale(final Note note, final String modeName) {
        List<Note> scale = null;
        for (final ScaleMode scaleMode : this.scaleModes) {
            if (scaleMode.getModeName().equalsIgnoreCase(modeName)) {
                scale = scaleMode.getScaleMode(note);
                break;
            }
        }
        return scale;
    }
    
    public ScaleMode getScaleMode(final String modeName) {
        ScaleMode mode = null;
        for (final ScaleMode scaleMode : this.scaleModes) {
            if (scaleMode.getModeName().equalsIgnoreCase(modeName)) {
                mode = scaleMode;
                break;
            }
        }
        return mode;
    }
    
    public Scale getParentScale(final String modeName) {
        for (final ScaleMode scaleMode : this.scaleModes) {
            if (scaleMode.getModeName().toLowerCase().equals(modeName.toLowerCase())) {
                return this.parentScale;
            }
        }
        return null;
    }
    
    public Integer getQuality(final String modeName) {
        for (final ScaleMode scaleMode : this.scaleModes) {
            if (scaleMode.getModeName().equalsIgnoreCase(modeName)) {
                return scaleMode.getQuality();
            }
        }
        return null;
    }
    
    public String modeOf(final Integer quality) {
        for (final ScaleMode scaleMode : this.scaleModes) {
            if (scaleMode.getQuality().equals(quality)) {
                return scaleMode.getModeName();
            }
        }
        return null;
    }
    
    public ScaleMode getFirstMode() {
        return this.firstMode;
    }
    
    public Set<ScaleMode> getScaleModes() {
        return this.scaleModes;
    }
}
