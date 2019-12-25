// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.unitDuration;

import Model.Project.PersistentStatus;

public class UnitDurationInformation
{
    public static final UnitDuration DEFAULT_UNIT_DURATION;
    private static UnitDuration unitDuration;
    private static Boolean isUpdated;
    private static PersistentStatus status;
    
    public static void clearDurationInfoUpdated() {
        UnitDurationInformation.isUpdated = false;
    }
    
    public static Boolean isDurationInfoUpdated() {
        return UnitDurationInformation.isUpdated;
    }
    
    public static PersistentStatus getStatus() {
        return UnitDurationInformation.status;
    }
    
    public static UnitDuration getUnitDuration() {
        return UnitDurationInformation.unitDuration;
    }
    
    public static void resetUnitDuration() {
        UnitDurationInformation.unitDuration = UnitDurationInformation.DEFAULT_UNIT_DURATION;
    }
    
    public static void setUnitDuration(final UnitDuration unitDuration) {
        UnitDurationInformation.unitDuration = unitDuration;
        UnitDurationInformation.isUpdated = true;
    }
    
    static {
        DEFAULT_UNIT_DURATION = UnitDurationMap.getUnitDurationByName("Crotchet");
        UnitDurationInformation.isUpdated = false;
        UnitDurationInformation.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                UnitDurationInformation.clearDurationInfoUpdated();
            }
            
            @Override
            public Boolean isUpdated() {
                return UnitDurationInformation.isDurationInfoUpdated();
            }
            
            @Override
            public void resetData() {
                UnitDurationInformation.resetUnitDuration();
            }
        };
        UnitDurationInformation.unitDuration = UnitDurationInformation.DEFAULT_UNIT_DURATION;
    }
}
