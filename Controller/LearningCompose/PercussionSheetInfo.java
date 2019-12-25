// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose;

import javafx.scene.image.Image;
import Model.Note.Note;
import Model.Percussion.Percussion;

public class PercussionSheetInfo
{
    private Percussion percussion;
    private PercussionCategory category;
    private Note positionOnStave;
    private Image elementImage;
    private Image cursorImage;
    
    public PercussionSheetInfo(final Percussion percussion, final PercussionCategory category, final Note positionOnStave, final Image elementImage, final Image cursorImage) {
        this.percussion = percussion;
        this.category = category;
        this.positionOnStave = positionOnStave;
        this.elementImage = elementImage;
        this.cursorImage = cursorImage;
    }
    
    public Percussion getPercussion() {
        return this.percussion;
    }
    
    public PercussionCategory getCategory() {
        return this.category;
    }
    
    public Note getPositionOnStave() {
        return this.positionOnStave;
    }
    
    public Image getElementImage() {
        return this.elementImage;
    }
    
    public Image getCursorImage() {
        return this.cursorImage;
    }
}
