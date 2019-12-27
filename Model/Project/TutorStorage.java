

package Model.Project;

import java.util.Map;

public class TutorStorage
{
    private Map<String, String> tutorNameToColorMap;
    
    public TutorStorage(final Map<String, String> tutorNameToColorMap) {
        this.tutorNameToColorMap = tutorNameToColorMap;
    }
    
    public TutorStorage() {
    }
    
    public Map<String, String> getTutorNameToColorMap() {
        return this.tutorNameToColorMap;
    }
}
