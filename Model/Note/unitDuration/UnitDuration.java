

package Model.Note.unitDuration;

public class UnitDuration
{
    private String unitDurationName;
    private double unitDurationDivider;
    private Integer numberOfDots;
    
    public UnitDuration(final String unitDurationName, final double unitDurationDivider, final boolean mutable) {
        this.unitDurationName = unitDurationName;
        this.unitDurationDivider = unitDurationDivider;
        this.numberOfDots = 0;
    }
    
    public UnitDuration(final String unitDurationName, final double unitDurationDivider, final boolean mutable, final Integer dots) {
        this.unitDurationName = unitDurationName;
        this.unitDurationDivider = unitDurationDivider;
        this.numberOfDots = dots;
    }
    
    public String getUnitDurationName() {
        return this.unitDurationName;
    }
    
    public String getDots() {
        return new String(new char[(int)this.numberOfDots]).replace("\u0000", ".");
    }
    
    public double getUnitDurationDivider() {
        return this.unitDurationDivider;
    }
    
    public Integer getNumberOfDots() {
        return this.numberOfDots;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final UnitDuration that = (UnitDuration)o;
        return this.getUnitDurationName().equals(that.getUnitDurationName()) && ((this.getNumberOfDots() != null) ? this.getNumberOfDots().equals(that.getNumberOfDots()) : (that.getNumberOfDots() == null));
    }
}
