

package Model.Note.Scale.ScaleMode;

import java.util.List;
import java.util.ArrayList;
import Model.Note.Scale.MelodicMinorScale;

public class MelodicMinorScaleModes extends ScaleModes
{
    public MelodicMinorScaleModes() {
        this.parentScale = new MelodicMinorScale();
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
        this.firstMode = new ScaleMode("Minormajor", 1, this.parentScale, adaptions);
        this.scaleModes.add(this.firstMode);
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(-1);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Dorian b2", 2, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(1);
                this.add(1);
                this.add(1);
                this.add(0);
                this.add(0);
            }
        };
        this.scaleModes.add(new ScaleMode("Lydian #5", 3, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(1);
                this.add(1);
                this.add(0);
                this.add(0);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Lydian dominant", 4, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(1);
                this.add(0);
                this.add(0);
                this.add(-1);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Mixolydian b6", 5, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(0);
                this.add(-1);
                this.add(-1);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Locrian #2", 6, this.parentScale, adaptions));
        adaptions = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(-1);
                this.add(0);
                this.add(-1);
                this.add(-1);
                this.add(-1);
                this.add(-1);
            }
        };
        this.scaleModes.add(new ScaleMode("Altered", 7, this.parentScale, adaptions));
    }
}
