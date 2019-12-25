// 
// Decompiled by Procyon v0.5.36
// 

package Model;

import java.util.ResourceBundle;
import java.util.Locale;

public class CommandMessages
{
    private static final String FILE_NAME = "Messages";
    private static Locale locale;
    
    public static String getMessage(final String msg) {
        final ResourceBundle messages = ResourceBundle.getBundle("Messages", CommandMessages.locale);
        final String message = messages.getString(msg);
        return message;
    }
    
    static {
        CommandMessages.locale = Locale.ENGLISH;
    }
}
