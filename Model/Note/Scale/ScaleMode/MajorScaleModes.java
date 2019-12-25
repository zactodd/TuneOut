// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Scale.ScaleMode;

import java.util.List;
import java.util.ArrayList;
import Model.Note.Scale.MajorScale;

public class MajorScaleModes extends ScaleModes
{
    public MajorScaleModes() {
        this.parentScale = new MajorScale();
        List<Integer> adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
            }
        };
        this.firstMode = new ScaleMode("Ionian", 1, this.parentScale, adaptions);
        this.scaleModes.add(this.firstMode);
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(-1);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Dorian", 2, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(-1);
                this.add(-1);
                this.add(0);
                this.add(0);
                this.add(-1);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Phrygian", 3, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(1);
                this.add(0);
                this.add(0);
                this.add(0);
            }
        };
        this.scaleModes.add(new ScaleMode("Lydian", 4, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Mixolydian", 5, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(-1);
                this.add(0);
                this.add(0);
                this.add(-1);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Aeolian", 6, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(-1);
                this.add(-1);
                this.add(0);
                this.add(-1);
                this.add(-1);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Locrian", 7, this.parentScale, adaptions));
    }
}
