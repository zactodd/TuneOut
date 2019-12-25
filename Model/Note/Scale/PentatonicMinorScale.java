// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Scale;

import java.util.Arrays;
import java.util.Collection;

public class PentatonicMinorScale extends Pentatonic
{
    private static final Collection<Integer> NOT_IN_PENTATONIC;
    private static final Integer SCALE_SIZE;
    
    public PentatonicMinorScale() {
        this.scale = new MinorScale().scale;
        this.notInScalePositions = PentatonicMinorScale.NOT_IN_PENTATONIC;
        this.scaleSize = PentatonicMinorScale.SCALE_SIZE;
        this.name = "minor pentatonic";
    }
    
    static {
        NOT_IN_PENTATONIC = Arrays.asList(1, 5);
        SCALE_SIZE = 6;
    }
}
