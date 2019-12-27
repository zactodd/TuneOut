

package Model.File;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public abstract class Persistable extends TuneOutFile
{
    private static final String PERSISTENT_DIRECTORY_NAME_LINUX = ".tuneout";
    private static final String PERSISTENT_DIRECTORY_NAME_WINDOWS = "TuneOut";
    protected static final String TUTOR_DIRECTORY = "tutor_data";
    File file;
    
    public Persistable() {
        super.file = this.file;
    }
    
    protected Persistable(final File file) {
        super(file);
    }
    
    protected File getPersistentDirectory() {
        final List<String> subDirs = new ArrayList<String>();
        subDirs.add("tutor_data");
        String dir = System.getenv("APPDATA");
        File persistentDir;
        if (dir == null) {
            dir = System.getProperty("user.home");
            persistentDir = new File(dir + File.separator + ".tuneout");
        }
        else {
            persistentDir = new File(dir + File.separator + "TuneOut");
        }
        for (final String subDir : subDirs) {
            final File tempFile = new File(persistentDir + File.separator + subDir);
            if (!tempFile.exists()) {
                try {
                    final Boolean created = tempFile.mkdirs();
                    if (created) {
                        continue;
                    }
                    Persistable.log.error("Could not create persistent directory");
                }
                catch (Exception e) {
                    Persistable.log.error(e.toString());
                }
            }
        }
        return persistentDir;
    }
    
    protected abstract File getPersistentFile(final String p0);
    
    @Override
    public File getFile() {
        return this.file;
    }
}
