

package Model.Note.Scale;

import java.util.ArrayList;
import java.util.List;

public class MajorScale extends Scale
{
    private static List<Integer> MAJOR_SCALE;
    
    public MajorScale() {
        this.scale = MajorScale.MAJOR_SCALE;
        this.name = "major";
        this.sameScaleMode = "Ionian";
    }
    
    static {
        MajorScale.MAJOR_SCALE = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(2);
                this.add(4);
                this.add(5);
                this.add(7);
                this.add(9);
                this.add(11);
                this.add(12);
            }
        };
    }
}
