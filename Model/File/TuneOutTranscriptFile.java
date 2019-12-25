// 
// Decompiled by Procyon v0.5.36
// 

package Model.File;

import java.io.File;

public class TuneOutTranscriptFile extends TuneOutFile
{
    private static final String FILE_HEAD = "TuneOut Transcript";
    
    public TuneOutTranscriptFile(final File file) {
        super(file);
    }
    
    @Override
    public boolean isValid(final String fileText) {
        return fileText.contains("TuneOut Transcript") && !fileText.isEmpty();
    }
}
