// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Settings;

public class Swing
{
    private String name;
    private String description;
    private Double firstNoteDurationPercent;
    private Boolean isPrimaryName;
    
    public Swing(final String name, final String description, final Double firstNoteDurationPercent, final Boolean isPrimaryName) {
        this.name = name;
        this.description = description;
        this.firstNoteDurationPercent = firstNoteDurationPercent;
        this.isPrimaryName = isPrimaryName;
    }
    
    public String getName() {
        return this.name;
    }
    
    protected String getDescription() {
        return this.description;
    }
    
    protected Double getFirstNoteDurationPercent() {
        return this.firstNoteDurationPercent;
    }
    
    protected Boolean isPrimaryName() {
        return this.isPrimaryName;
    }
}
