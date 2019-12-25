// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing;

import java.util.List;

public interface OnSuccess
{
    String onSuccess(final List<Object> p0, final List<Object> p1) throws ArgumentException;
    
    String getCommandName();
}
