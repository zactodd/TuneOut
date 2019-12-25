// 
// Decompiled by Procyon v0.5.36
// 

package Model.Tutor;

public class ScaleTutorOption extends Options
{
    public static final int MAX_NUM_OCTAVES = 4;
    public static final String ASCENDING_ORDER = "-a";
    public static final String DESCENDING_ORDER = "-d";
    public static final String BOTH_ORDERS = "-b";
    public static final String ALL_TYPES = "all";
    public static final String SCALES_ONLY = "scales";
    public static final String MAJOR_MODES_ONLY = "major modes";
    public static final String MINOR_MODES_ONLY = "minor modes";
    private String type;
    
    public ScaleTutorOption(final int numQuestions, final int numOctaves, final String order, final String type) {
        this.numQuestions = numQuestions;
        this.numOctaves = numOctaves;
        this.order = order;
        this.type = type;
    }
    
    public static boolean numOctavesValid(final String numOctavesToCheck) {
        int questions;
        try {
            questions = Integer.parseInt(numOctavesToCheck);
        }
        catch (Exception e) {
            return false;
        }
        return questions > 0 && questions <= 4;
    }
    
    public static boolean orderValid(String orderToCheck) {
        boolean result = false;
        orderToCheck = orderToCheck.toLowerCase();
        if (orderToCheck.equals("-a") || orderToCheck.equals("-d") || orderToCheck.equals("-b")) {
            result = true;
        }
        return result;
    }
    
    public String getType() {
        return this.type;
    }
}
