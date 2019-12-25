// 
// Decompiled by Procyon v0.5.36
// 

package Model.instrument;

public class Instrument
{
    private Integer instrumentNumber;
    private String instrumentName;
    private String instrumentType;
    private Boolean availability;
    
    public Instrument(final Integer instrumentNumber, final String instrumentName, final String instrumentType, final Boolean availability) {
        this.instrumentNumber = instrumentNumber;
        this.instrumentName = instrumentName;
        this.instrumentType = instrumentType;
        this.availability = availability;
    }
    
    public String getInstrumentName() {
        return this.instrumentName;
    }
    
    public Integer getInstrumentNumber() {
        return this.instrumentNumber;
    }
    
    public String getInstrumentGroup() {
        return this.instrumentType;
    }
    
    public Boolean getAvailability() {
        return this.availability;
    }
    
    @Override
    public String toString() {
        return String.format("Name: %s\nGroup: %s", this.instrumentName, this.instrumentType);
    }
}
