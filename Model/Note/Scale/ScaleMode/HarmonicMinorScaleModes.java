// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Scale.ScaleMode;

import java.util.List;
import java.util.ArrayList;
import Model.Note.Scale.HarmonicMinorScale;

public class HarmonicMinorScaleModes extends ScaleModes
{
    public HarmonicMinorScaleModes() {
        this.parentScale = new HarmonicMinorScale();
        final List<Integer> adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(-1);
                this.add(1);
                this.add(0);
                this.add(0);
                this.add(-1);
                this.add(-1);
            }
        };
        this.firstMode = new ScaleMode("Mixolydian b2 b6", 5, this.parentScale, adaptions);
        this.scaleModes.add(this.firstMode);
    }
}
