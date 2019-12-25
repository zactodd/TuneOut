// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Settings;

import Model.Project.PersistentStatus;

public class TempoInformation
{
    public static final int STAND_MIN_TEMPO = 20;
    public static final int STAND_MAX_TEMPO = 300;
    public static final int DEFAULT_TEMPO = 120;
    private static final double MIN_TO_MS = 60.0;
    private static final int CROTCHET = 1;
    private static final double S_TO_MS = 1000.0;
    private static PersistentStatus status;
    private static int tempInBpm;
    private static Boolean isTempoUpdated;
    
    public static int getTempInBpm() {
        return TempoInformation.tempInBpm;
    }
    
    public static boolean isTempoInfoUpdated() {
        return TempoInformation.isTempoUpdated;
    }
    
    public static void clearUpdateFlag() {
        TempoInformation.isTempoUpdated = false;
    }
    
    public static PersistentStatus getStatus() {
        return TempoInformation.status;
    }
    
    public static void resetTempo() {
        TempoInformation.tempInBpm = 120;
    }
    
    public static void setTempInBpm(final int tempInBpm) {
        TempoInformation.isTempoUpdated = true;
        TempoInformation.tempInBpm = tempInBpm;
    }
    
    public static double crotchetBpmToMs(final int tempInBpm) {
        final double duration = 1.0 / (tempInBpm / 60.0) * 1000.0;
        return (double)Math.round(duration);
    }
    
    public static Boolean checkTempoInSuitableRange(final int testTempo) {
        return testTempo >= 20 && testTempo <= 300;
    }
    
    static {
        TempoInformation.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                TempoInformation.clearUpdateFlag();
            }
            
            @Override
            public Boolean isUpdated() {
                return TempoInformation.isTempoInfoUpdated();
            }
            
            @Override
            public void resetData() {
                TempoInformation.resetTempo();
            }
        };
        TempoInformation.isTempoUpdated = false;
        TempoInformation.tempInBpm = 120;
    }
}
