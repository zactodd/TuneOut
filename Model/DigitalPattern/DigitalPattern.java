

package Model.DigitalPattern;

import java.util.HashMap;
import java.util.Map;
import Model.Project.PersistentStatus;

public class DigitalPattern
{
    private static Boolean isUpdated;
    private static PersistentStatus status;
    private String pattern;
    private String name;
    private static Map<String, String> patternHashMap;
    
    public static Boolean isDigitalPatternUpdated() {
        return DigitalPattern.isUpdated;
    }
    
    public static PersistentStatus getStatus() {
        return DigitalPattern.status;
    }
    
    public static void resetDigitalPatternUpdatedFlag() {
        DigitalPattern.isUpdated = false;
    }
    
    public DigitalPattern(final String pattern, final String name) {
        this.pattern = pattern;
        this.name = name.toLowerCase();
    }
    
    public static Map<String, String> getPatternHashMap() {
        return DigitalPattern.patternHashMap;
    }
    
    public static void setPatternHashMap(final Map<String, String> patternHashMap) {
        DigitalPattern.patternHashMap = patternHashMap;
    }
    
    public String getPatternName() {
        return this.name;
    }
    
    public static void addPattern(final DigitalPattern pattern) {
        DigitalPattern.patternHashMap.put(pattern.getPatternName().toLowerCase(), pattern.getDigitalPattern());
        DigitalPattern.isUpdated = true;
    }
    
    public String getDigitalPattern() {
        return this.pattern;
    }
    
    public static String getDigitalPattern(final String patternName) {
        return DigitalPattern.patternHashMap.get(patternName.toLowerCase());
    }
    
    public static boolean hasPatternName(final String patternName) {
        return DigitalPattern.patternHashMap.containsKey(patternName);
    }
    
    public static void resetPatterns() {
        DigitalPattern.patternHashMap = new HashMap<String, String>();
    }
    
    public static void setDigitalPatternsFromFile(final Map<String, String> digitalPatterns) {
        DigitalPattern.patternHashMap = digitalPatterns;
    }
    
    static {
        DigitalPattern.isUpdated = false;
        DigitalPattern.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                DigitalPattern.resetDigitalPatternUpdatedFlag();
            }
            
            @Override
            public Boolean isUpdated() {
                return DigitalPattern.isDigitalPatternUpdated();
            }
            
            @Override
            public void resetData() {
                DigitalPattern.resetPatterns();
            }
        };
        DigitalPattern.patternHashMap = new HashMap<String, String>();
    }
}
