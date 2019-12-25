// 
// Decompiled by Procyon v0.5.36
// 

package Model.File;

import java.io.File;

public class TuneOutGeneralFile extends TuneOutFile
{
    public TuneOutGeneralFile(final File file) {
        super(file);
    }
    
    public TuneOutGeneralFile(final String fileText) {
    }
    
    @Override
    public boolean isValid(final String fileText) {
        return !fileText.isEmpty();
    }
}
