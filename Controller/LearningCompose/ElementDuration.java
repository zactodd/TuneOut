// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import Model.Note.unitDuration.UnitDuration;

public class ElementDuration
{
    private UnitDuration unitDuration;
    private Image elementImage;
    private Image cursorImage;
    private ElementType elementType;
    private ToggleButton toggleButton;
    private Boolean dotted;
    private Double offsetY;
    
    protected ElementDuration(final UnitDuration unitDuration, final Image elementImage, final Image cursorImage, final ElementType elementType, final Boolean dotted) {
        this.offsetY = 0.0;
        this.unitDuration = unitDuration;
        this.elementImage = elementImage;
        this.cursorImage = cursorImage;
        this.elementType = elementType;
        this.dotted = dotted;
    }
    
    protected ElementDuration(final UnitDuration unitDuration, final Image elementImage, final Image cursorImage, final ElementType elementType, final Boolean dotted, final Double offsetY) {
        this.offsetY = 0.0;
        this.unitDuration = unitDuration;
        this.elementImage = elementImage;
        this.cursorImage = cursorImage;
        this.elementType = elementType;
        this.dotted = dotted;
        this.offsetY = offsetY;
    }
    
    public void setToggleButton(final ToggleButton toggleButton) {
        this.toggleButton = toggleButton;
    }
    
    public UnitDuration getUnitDuration() {
        return this.unitDuration;
    }
    
    public Image getElementImage() {
        return this.elementImage;
    }
    
    public Image getCursorImage() {
        return this.cursorImage;
    }
    
    public ElementType getElementType() {
        return this.elementType;
    }
    
    public ToggleButton getToggleButton() {
        return this.toggleButton;
    }
    
    public Boolean getDotted() {
        return this.dotted;
    }
    
    public Double getOffsetY() {
        return this.offsetY;
    }
    
    public enum ElementType
    {
        NOTE, 
        REST;
    }
}
