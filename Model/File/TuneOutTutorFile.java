// 
// Decompiled by Procyon v0.5.36
// 

package Model.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.File;

public class TuneOutTutorFile extends Persistable
{
    private static final String FILE_HEAD = "TuneOut Tutor File";
    private static final String FILE_EXTENSION = ".txt";
    
    public TuneOutTutorFile() {
        super.file = null;
    }
    
    public TuneOutTutorFile(final String username) {
        super.file = this.getPersistentFile(username);
    }
    
    public TuneOutTutorFile(final File file) {
        super(file);
    }
    
    @Override
    protected File getPersistentFile(final String user) {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        final String date = dateFormat.format(cal.getTime());
        final String filename = user + "_" + date + ".txt";
        final File persistentDir = this.getPersistentDirectory();
        final File tutorFile = new File(persistentDir.getPath() + File.separator + "tutor_data" + File.separator + filename);
        if (!tutorFile.exists()) {
            final String header = String.format("TuneOut Tutor File %1$tY/%1$tb/%1$td%n%n", Calendar.getInstance());
            this.appendTextToFile(header, tutorFile);
        }
        return tutorFile;
    }
    
    @Override
    public boolean isValid(final String fileText) {
        return fileText.contains("TuneOut Tutor File") && !fileText.isEmpty();
    }
    
    public File getTutorDirectory() {
        return new File(this.getPersistentDirectory().getPath() + File.separator + "tutor_data");
    }
}
