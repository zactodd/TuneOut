// 
// Decompiled by Procyon v0.5.36
// 

package Model.Project;

public abstract class PersistentStatus
{
    public abstract void clearUpdateFlag();
    
    public abstract Boolean isUpdated();
    
    public abstract void resetData();
}
