// 
// Decompiled by Procyon v0.5.36
// 

package Model.Note.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import Model.Project.PersistentStatus;
import java.util.List;

public class SwingMap
{
    private static int DEFAULT_SWING_NUM;
    private static List<Swing> allSwing;
    private static Boolean isUpdated;
    private static PersistentStatus status;
    private static Swing currentSwing;
    
    public static void resetSwingUpdateFlag() {
        SwingMap.isUpdated = false;
    }
    
    public static Boolean isSwingUpdated() {
        return SwingMap.isUpdated;
    }
    
    public static PersistentStatus getStatus() {
        return SwingMap.status;
    }
    
    public static String getCurrentSwingNiceName() {
        final double swingPercent = SwingMap.currentSwing.getFirstNoteDurationPercent();
        for (final Swing swing : SwingMap.allSwing) {
            if (swing.isPrimaryName() && swing.getFirstNoteDurationPercent() == swingPercent) {
                return String.format("%s (%s)", swing.getName(), swing.getDescription());
            }
        }
        return "";
    }
    
    public static String getCurrentSwing() {
        final double swingPercent = SwingMap.currentSwing.getFirstNoteDurationPercent();
        for (final Swing swing : SwingMap.allSwing) {
            if (swing.isPrimaryName() && swing.getFirstNoteDurationPercent() == swingPercent) {
                return swing.getName();
            }
        }
        return "";
    }
    
    public static Double getCurrentSwingPercent() {
        return SwingMap.currentSwing.getFirstNoteDurationPercent();
    }
    
    public static void setCurrentSwing(final String swingLabel) {
        for (final Swing swing : SwingMap.allSwing) {
            if (swing.getName().equals(swingLabel)) {
                SwingMap.currentSwing = swing;
                SwingMap.isUpdated = true;
            }
        }
    }
    
    public static List<String> getAllSwingNames() {
        final List<String> allSwingNames = new ArrayList<String>();
        for (final Swing swing : SwingMap.allSwing) {
            if (swing.isPrimaryName()) {
                allSwingNames.add(swing.getName());
            }
        }
        return allSwingNames;
    }
    
    public static Boolean includes(final String nameToCheck) {
        for (final Swing swing : SwingMap.allSwing) {
            if (nameToCheck.equals(swing.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public static void resetDefaultSwing() {
        SwingMap.currentSwing = SwingMap.allSwing.get(SwingMap.DEFAULT_SWING_NUM);
    }
    
    static {
        SwingMap.DEFAULT_SWING_NUM = 0;
        SwingMap.isUpdated = false;
        SwingMap.status = new PersistentStatus() {
            @Override
            public void clearUpdateFlag() {
                SwingMap.resetSwingUpdateFlag();
            }
            
            @Override
            public Boolean isUpdated() {
                return SwingMap.isSwingUpdated();
            }
            
            @Override
            public void resetData() {
                SwingMap.resetDefaultSwing();
            }
        };
        SwingMap.allSwing = new ArrayList<Swing>();
        final double mediumSwingPercent = 0.667;
        final String mediumSwingDescription = "swing style will use an alternating 2/3 and 1/3 duration";
        SwingMap.allSwing.add(new Swing("medium", mediumSwingDescription, mediumSwingPercent, true));
        SwingMap.allSwing.add(new Swing("2/3 1/3", mediumSwingDescription, mediumSwingPercent, false));
        SwingMap.allSwing.add(new Swing("2:1", mediumSwingDescription, mediumSwingPercent, false));
        final double lightSwingPercent = 0.625;
        final String lightSwingDescription = "swing style will use an alternating 5/8 and 3/8 duration";
        SwingMap.allSwing.add(new Swing("light", lightSwingDescription, lightSwingPercent, true));
        SwingMap.allSwing.add(new Swing("5/8 3/8", lightSwingDescription, lightSwingPercent, false));
        SwingMap.allSwing.add(new Swing("5:3", lightSwingDescription, lightSwingPercent, false));
        final double heavySwingPercent = 0.75;
        final String heavySwingDescription = "swing style will use an alternating 3/4 and 1/4 duration";
        SwingMap.allSwing.add(new Swing("heavy", heavySwingDescription, heavySwingPercent, true));
        SwingMap.allSwing.add(new Swing("3/4 1/4", heavySwingDescription, heavySwingPercent, false));
        SwingMap.allSwing.add(new Swing("3:1", heavySwingDescription, heavySwingPercent, false));
        SwingMap.currentSwing = SwingMap.allSwing.get(SwingMap.DEFAULT_SWING_NUM);
    }
}
