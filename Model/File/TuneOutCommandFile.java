// 
// Decompiled by Procyon v0.5.36
// 

package Model.File;

import java.util.Iterator;
import java.util.ArrayList;
import java.io.File;

public class TuneOutCommandFile extends TuneOutFile
{
    public static final String FILE_HEAD = "/* TuneOut Command File */";
    private static final String COMMAND = ">";
    
    public TuneOutCommandFile(final File file) {
        super(file);
    }
    
    @Override
    public boolean isValid(final String fileText) {
        return fileText.contains("/* TuneOut Command File */") && !fileText.isEmpty();
    }
    
    public String commandtoText(final ArrayList<String> content) {
        final String lineSep = System.getProperty("line.separator");
        String text = "/* TuneOut Command File */" + lineSep;
        for (final String str : content) {
            text = text + str + lineSep;
        }
        return text;
    }
}
