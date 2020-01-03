

package Model.instrument;

import Model.Project.PersistentStatus;

public class InstrumentInformation
{
    public static final int DEFAULT_INSTRUMENT = 0;
    private static int instrumentId;
    private static Instrument instrument;
    private static Boolean isInstrumentUpdated;
    private static PersistentStatus status;
    
    public static Instrument getInstrument() {
        return InstrumentInformation.instrument;
    }
    
    public static void clearInstrumentInfoUpdated() {
        InstrumentInformation.isInstrumentUpdated = false;
    }
    
    public static Boolean isInstrumentInfoUpdated() {
        return InstrumentInformation.isInstrumentUpdated;
    }
    
    public static int getInstrumentId() {
        return InstrumentInformation.instrumentId;
    }
    
    public static void resetInstrumentInfo() {
        InstrumentInformation.instrumentId = 0;
        InstrumentInformation.instrument = InstrumentsMap.getInstrument(InstrumentInformation.instrumentId);
    }
    
    public static PersistentStatus getStatus() {
        return InstrumentInformation.status;
    }
    
    public static void setInstrumentId(final int id, final Boolean updateStatusOrNot) {
        InstrumentInformation.instrumentId = id;
        InstrumentInformation.instrument = InstrumentsMap.getInstrument(id);
        if (updateStatusOrNot) {
            InstrumentInformation.isInstrumentUpdated = true;
        }
    }
    
    static {
        InstrumentInformation.isInstrumentUpdated = false;
        InstrumentInformation.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                InstrumentInformation.clearInstrumentInfoUpdated();
            }
            
            @Override
            public Boolean isUpdated() {
                return InstrumentInformation.isInstrumentInfoUpdated();
            }
            
            @Override
            public void resetData() {
                InstrumentInformation.resetInstrumentInfo();
            }
        };
        InstrumentInformation.instrumentId = 0;
        InstrumentInformation.instrument = InstrumentsMap.getInstrument(InstrumentInformation.instrumentId);
    }
}
