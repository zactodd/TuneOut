// 
// Decompiled by Procyon v0.5.36
// 

package Model.command.ArgumentParsing;

public class ArgumentException extends Exception
{
    public ArgumentException() {
    }
    
    public ArgumentException(final String message) {
        super(message);
    }
    
    public ArgumentException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public ArgumentException(final Throwable cause) {
        super(cause);
    }
}
