// 
// Decompiled by Procyon v0.5.36
// 

package Model.File;

import java.io.IOException;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.log4j.Logger;
import java.io.File;

public abstract class TuneOutFile
{
    public File file;
    static Logger log;
    
    TuneOutFile(final File file) {
        this.file = file;
    }
    
    TuneOutFile() {
    }
    
    public abstract boolean isValid(final String p0);
    
    public String fileToText(final File file) {
        String allText = "";
        try {
            final BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                allText = allText + line + "\n";
            }
        }
        catch (Exception ex) {
            TuneOutFile.log.error(ex.toString());
        }
        return allText;
    }
    
    public void textToFile(final String content, final File file) {
        try {
            final FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        }
        catch (IOException ex) {
            TuneOutFile.log.error(ex.toString());
        }
    }
    
    public void appendTextToFile(final String content, final File file) {
        try {
            final FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(content);
            fileWriter.close();
        }
        catch (IOException ex) {
            TuneOutFile.log.error(ex.toString());
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    static {
        TuneOutFile.log = Logger.getLogger(TuneOutFile.class.getName());
    }
}
