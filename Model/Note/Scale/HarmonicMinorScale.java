

package Model.Note.Scale;

import java.util.ArrayList;
import java.util.List;

public class HarmonicMinorScale extends Scale
{
    private final List<Integer> harmonicMinorScale;
    
    public HarmonicMinorScale() {
        this.harmonicMinorScale = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(2);
                this.add(3);
                this.add(5);
                this.add(7);
                this.add(8);
                this.add(11);
                this.add(12);
            }
        };
        this.scale = this.harmonicMinorScale;
        this.name = "harmonic minor";
    }
}
