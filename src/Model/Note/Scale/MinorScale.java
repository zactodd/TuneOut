

package Model.Note.Scale;

import java.util.ArrayList;
import java.util.List;

public class MinorScale extends Scale
{
    private List<Integer> MINOR_SCALE;
    
    public MinorScale() {
        this.MINOR_SCALE = new ArrayList<Integer>() {
            {
                this.add(0);
                this.add(2);
                this.add(3);
                this.add(5);
                this.add(7);
                this.add(8);
                this.add(10);
                this.add(12);
            }
        };
        this.scale = this.MINOR_SCALE;
        this.name = "minor";
    }
}
