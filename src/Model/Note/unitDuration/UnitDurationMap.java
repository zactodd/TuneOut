

package Model.Note.unitDuration;

import java.util.ArrayList;
import java.util.List;

public class UnitDurationMap
{
    private static List<UnitDuration> unitDurations;
    private static final String singleDot = ".";
    private static final String doubleDot = "";
    private static final String trippleDot = "...";
    
    public static UnitDuration getUnitDurationById(final Integer index) {
        return UnitDurationMap.unitDurations.get(index);
    }
    
    public static UnitDuration getUnitDurationByName(final String name) {
        UnitDuration output = null;
        for (final UnitDuration ud : UnitDurationMap.unitDurations) {
            if (ud.getUnitDurationName().toLowerCase().equals(name.toLowerCase())) {
                output = ud;
                break;
            }
        }
        return output;
    }
    
    public static UnitDuration getUnitDurationByDivider(final String divider) {
        UnitDuration output = null;
        for (final UnitDuration ud : UnitDurationMap.unitDurations) {
            final Double dividerInDouble = ud.getUnitDurationDivider();
            final String dividerInString = dividerToString(dividerInDouble);
            if (dividerInString.equals(divider)) {
                output = ud;
                break;
            }
        }
        return output;
    }
    
    public static String dividerToString(final Double divider) {
        String value = "";
        if (divider > 1.0) {
            value = value + "1/" + divider.intValue();
        }
        else {
            value += (int)(1.0 / divider);
        }
        return value;
    }
    
    public static int dotsInName(final String unitDurationName) {
        int result = 0;
        final int unitDurationLength = unitDurationName.length();
        if (unitDurationLength >= 3 && unitDurationName.substring(unitDurationLength - 3).equals("...")) {
            result = 3;
        }
        else if (unitDurationLength >= 2 && unitDurationName.substring(unitDurationLength - 2).equals("")) {
            result = 2;
        }
        else if (unitDurationLength >= 1 && unitDurationName.substring(unitDurationLength - 1).equals(".")) {
            result = 1;
        }
        return result;
    }
    
    public static Boolean isValidName(final String nameToCheck) {
        for (final UnitDuration dur : UnitDurationMap.unitDurations) {
            if (nameToCheck.equalsIgnoreCase(dur.getUnitDurationName())) {
                return true;
            }
        }
        return false;
    }
    
    public static List<UnitDuration> getAvailableUnitDurationName() {
        return UnitDurationMap.unitDurations;
    }
    
    static {
        (UnitDurationMap.unitDurations = new ArrayList<UnitDuration>()).add(new UnitDuration("Octuple whole note", 0.125, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Quadruple whole note", 0.25, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Breve", 0.5, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Semibreve", 1.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Minim", 2.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Crotchet", 4.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Quaver", 8.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Semiquaver", 16.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Demisemiquaver", 32.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Hemidemisemiquaver", 64.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Semihemidemisemiquaver", 128.0, false));
        UnitDurationMap.unitDurations.add(new UnitDuration("Demisemihemidemisemiquaver", 256.0, false));
    }
}
