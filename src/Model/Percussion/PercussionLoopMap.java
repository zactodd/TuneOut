

package Model.Percussion;

import java.util.HashMap;
import Model.Project.PersistentStatus;
import java.util.Map;

public class PercussionLoopMap
{
    private static Map<String, PercussionLoop> percussionLoopMap;
    private static Map<String, PercussionLoop> defaultPercussionLoopMap;
    private static Boolean isUpdated;
    private static PersistentStatus status;
    
    private static Map<String, PercussionLoop> createSamplePercussionLoops() {
        final Map<String, PercussionLoop> samplePercussionLoops = new HashMap<String, PercussionLoop>();
        PercussionLoop newPl = new PercussionLoop();
        String loopName = "feel the beat";
        newPl.addLoop(46, "X-X-X-X-X-X-X-X-");
        newPl.addLoop(37, "----X-------X---");
        newPl.addLoop(36, "X---X---X---X---");
        newPl.addLoop(44, "--X---X---X---X-");
        samplePercussionLoops.put(loopName, newPl);
        newPl = new PercussionLoop();
        loopName = "ending";
        newPl.addLoop(38, "XX--");
        newPl.addLoop(49, "---X");
        newPl.addLoop(35, "---X");
        samplePercussionLoops.put(loopName, newPl);
        newPl = new PercussionLoop();
        loopName = "queen";
        newPl.addLoop(38, "--X-");
        newPl.addLoop(35, "XX--");
        samplePercussionLoops.put(loopName, newPl);
        newPl = new PercussionLoop();
        loopName = "song 2";
        newPl.addLoop(42, "X-X-X-X-X-X-X-X-X-X-X-X-X-X-X---X-X-X-X-X-X-X-X-X-X-X-X-X-X-X---");
        newPl.addLoop(44, "------------------------------X-------------------------------X-");
        newPl.addLoop(50, "------------------------------X-------------------------------X-");
        newPl.addLoop(45, "--------------X-------------------------------X-----------------");
        newPl.addLoop(38, "----X-------X-------X-------X-X-----X-------X-------X-------X-X-");
        newPl.addLoop(36, "X-------X-X-----X-X---X---X-----X-------X-X-----X-X---X---X-----");
        samplePercussionLoops.put(loopName, newPl);
        return samplePercussionLoops;
    }
    
    public static void resetPercussionUpdatedFlag() {
        PercussionLoopMap.isUpdated = false;
    }
    
    public static Boolean isPercussionLoopExist(final String name) {
        if (getPercussionLoopMap().containsKey(name.toLowerCase())) {
            return true;
        }
        return false;
    }
    
    public static void setPercussionLoopMapFromFile(final Map<String, PercussionLoop> newPercussionLoopMap) {
        (PercussionLoopMap.percussionLoopMap = newPercussionLoopMap).putAll(PercussionLoopMap.defaultPercussionLoopMap);
    }
    
    public static PersistentStatus getStatus() {
        return PercussionLoopMap.status;
    }
    
    public static Boolean isPercussionLoopUpdated() {
        return PercussionLoopMap.isUpdated;
    }
    
    public static PercussionLoop getPercussionLoop(final String name) {
        return PercussionLoopMap.percussionLoopMap.get(name.toLowerCase());
    }
    
    public static Boolean containsPercussionLoop(final String name) {
        return PercussionLoopMap.percussionLoopMap.containsKey(name.toLowerCase());
    }
    
    public static Boolean updateMap(final String name, final Integer instrument, final String beat) {
        if (containsPercussionLoop(name)) {
            PercussionLoopMap.percussionLoopMap.get(name.toLowerCase()).addLoop(instrument, beat);
            PercussionLoopMap.isUpdated = true;
            return true;
        }
        return false;
    }
    
    public static void removePercussionLoop(final String percussionLoop) {
        PercussionLoopMap.percussionLoopMap.remove(percussionLoop.toLowerCase());
        PercussionLoopMap.isUpdated = true;
    }
    
    public static void resetPercussionLoopMap() {
        PercussionLoopMap.percussionLoopMap = new HashMap<String, PercussionLoop>();
    }
    
    public static void add(final String name, final PercussionLoop percussionLoop) {
        PercussionLoopMap.percussionLoopMap.put(name.toLowerCase(), percussionLoop);
        PercussionLoopMap.isUpdated = true;
    }
    
    public static Map<String, PercussionLoop> getPercussionLoopMap() {
        return PercussionLoopMap.percussionLoopMap;
    }
    
    public static Map<String, PercussionLoop> getDefaultPercussionLoopMap() {
        return PercussionLoopMap.defaultPercussionLoopMap;
    }
    
    static {
        PercussionLoopMap.percussionLoopMap = new HashMap<String, PercussionLoop>();
        PercussionLoopMap.defaultPercussionLoopMap = new HashMap<String, PercussionLoop>();
        final PercussionLoop newPl = new PercussionLoop();
        newPl.addLoop(35, "x");
        PercussionLoopMap.defaultPercussionLoopMap.put("default percussion loop", newPl);
        PercussionLoopMap.percussionLoopMap.putAll(PercussionLoopMap.defaultPercussionLoopMap);
        PercussionLoopMap.percussionLoopMap.putAll(createSamplePercussionLoops());
        PercussionLoopMap.isUpdated = false;
        PercussionLoopMap.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                PercussionLoopMap.resetPercussionUpdatedFlag();
            }
            
            @Override
            public Boolean isUpdated() {
                return PercussionLoopMap.isPercussionLoopUpdated();
            }
            
            @Override
            public void resetData() {
                PercussionLoopMap.resetPercussionUpdatedFlag();
            }
        };
    }
}
