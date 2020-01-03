

package Model.File;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import com.google.gson.Gson;
import Model.Project.User;
import java.util.TreeMap;
import java.io.File;

public class TuneOutPersistentFile extends Persistable
{
    private static final String PERSISTENT_FILE_NAME = "user_data";
    
    public TuneOutPersistentFile() {
        super.file = this.getPersistentFile("user_data");
    }
    
    public TuneOutPersistentFile(final File file) {
        super(file);
    }
    
    @Override
    protected File getPersistentFile(final String filename) {
        final File persistentDir = this.getPersistentDirectory();
        final File persistentFile = new File(persistentDir.getPath() + File.separator + filename);
        if (!persistentFile.exists()) {
            this.textToFile(this.convertToJson(new TreeMap<String, User>()), persistentFile);
        }
        return persistentFile;
    }
    
    public String convertToJson(final TreeMap<String, User> profiles) {
        final Gson gson = new Gson();
        final String json = gson.toJson(profiles);
        return json;
    }
    
    public Map<String, User> convertFromJson(final String json) {
        final Gson gson = new Gson();
        Map<String, User> users = new TreeMap<String, User>();
        try {
            users = gson.fromJson(json, new TypeToken<TreeMap<String, User>>() {}.getType());
        }
        catch (Exception e) {
            log.error(e.toString());
        }
        return users;
    }
    
    @Override
    public boolean isValid(final String fileText) {
        if (fileText.isEmpty()) {
            return false;
        }
        final Gson gson = new Gson();
        try {
            final Map<String, User> users = gson.fromJson(fileText, new TypeToken<TreeMap<String, User>>() {}.getType());
            for (final Map.Entry<String, User> u : users.entrySet()) {
                if (!u.getKey().equals(u.getValue().getUserName())) {
                    return false;
                }
            }
            return true;
        }
        catch (JsonSyntaxException ex) {
            return false;
        }
    }
    
    public boolean isEmpty(final String fileText) {
        if (this.isValid(fileText)) {
            final Map<String, User> users = this.convertFromJson(fileText);
            return users.isEmpty();
        }
        return false;
    }
}
