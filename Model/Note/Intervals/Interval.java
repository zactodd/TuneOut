// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Intervals;

import java.util.Comparator;
import java.util.List;

public class Interval
{
    private List<String> intervalNames;
    private Integer semitone;
    private boolean primary;
    private final int RAW_NAME_POS = 0;
    private final int THEORY_NAME_POS = 1;
    private final int PRETTY_NAME_POS = 2;
    
    Interval(final List<String> intervalNames, final Integer semitone, final boolean primary) {
        this.intervalNames = intervalNames;
        this.semitone = semitone;
        this.primary = primary;
    }
    
    public String getRawIntervalName() {
        return this.intervalNames.get(0);
    }
    
    public String getTheoryIntervalName() {
        return this.intervalNames.get(1);
    }
    
    public String getPrettyIntervalName() {
        return this.intervalNames.get(2);
    }
    
    public boolean isPrimary() {
        return this.primary;
    }
    
    public Integer getSemitone() {
        return this.semitone;
    }
    
    public static Comparator<Interval> sortBySemitone() {
        final Comparator comp = new Comparator<Interval>() {
            @Override
            public int compare(final Interval i1, final Interval i2) {
                return i1.semitone - i2.semitone;
            }
        };
        return (Comparator<Interval>)comp;
    }
}
