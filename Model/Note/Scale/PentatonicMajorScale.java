// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Scale;

import java.util.Arrays;
import java.util.Collection;

public class PentatonicMajorScale extends Pentatonic
{
    private static final Collection<Integer> NOT_IN_PENTATONIC;
    private static final Integer SCALE_SIZE;
    
    public PentatonicMajorScale() {
        this.scale = new MajorScale().scale;
        this.notInScalePositions = PentatonicMajorScale.NOT_IN_PENTATONIC;
        this.scaleSize = PentatonicMajorScale.SCALE_SIZE;
        this.name = "major pentatonic";
    }
    
    static {
        NOT_IN_PENTATONIC = Arrays.asList(3, 6);
        SCALE_SIZE = 6;
    }
}
