

package Model.Note.Scale;

import java.util.ArrayList;
import java.util.List;

public class MelodicMinorScale extends Scale
{
    List<Integer> MELODIC_MINOR_SCALE;
    
    public MelodicMinorScale() {
        this.MELODIC_MINOR_SCALE = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(2);
                this.add(3);
                this.add(5);
                this.add(7);
                this.add(9);
                this.add(11);
                this.add(12);
            }
        };
        this.scale = this.MELODIC_MINOR_SCALE;
        this.name = "melodic minor";
        this.sameScaleMode = "Minormajor";
    }
}
