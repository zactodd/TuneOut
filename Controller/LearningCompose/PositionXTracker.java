// 
// Decompiled by Procyon v0.5.36
// 

package Controller.LearningCompose;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class PositionXTracker
{
    Set<Double> xcoordTrackerList;
    
    public PositionXTracker() {
        this.xcoordTrackerList = new HashSet<Double>();
    }
    
    public void removeXCoord(final Double xcoord) {
        this.xcoordTrackerList.remove(xcoord);
    }
    
    public void addXCoord(final Double xcoord) {
        this.xcoordTrackerList.add(xcoord);
    }
    
    public void clearPositionXTracker() {
        this.xcoordTrackerList.clear();
    }
    
    public Boolean isXCoordTaken(final Double xcoord) {
        for (final Double note : this.xcoordTrackerList) {
            if (xcoord.equals(note)) {
                return true;
            }
        }
        return false;
    }
}
