

package Model.Note.Scale;

import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import Model.Note.Note;
import java.util.List;
import java.util.Collection;
import java.util.Map;

public class ScaleMap
{
    private static Map<String, Scale> scaleMap;
    
    public static Scale getScale(final String scaleName) {
        return ScaleMap.scaleMap.get(scaleName.toLowerCase());
    }
    
    public static Boolean containsScale(final String scaleName) {
        return ScaleMap.scaleMap.containsKey(scaleName);
    }
    
    public static Collection<String> getScales() {
        return ScaleMap.scaleMap.keySet();
    }
    
    public static String getScaleName(final Scale scale) {
        for (final Map.Entry<String, Scale> mapEntry : ScaleMap.scaleMap.entrySet()) {
            if (mapEntry.getValue().getClass().equals(scale.getClass())) {
                return mapEntry.getKey();
            }
        }
        return null;
    }
    
    public static List<Note> descendScale(final List<Note> scale) {
        final List<Note> reversedScale = new ArrayList<Note>(scale);
        Collections.reverse(reversedScale);
        return reversedScale;
    }
    
    public static List<Note> ascendDescendScale(final List<Note> scale) {
        final List<Note> reverseScale = new ArrayList<Note>(scale);
        Collections.reverse(reverseScale);
        final List<Note> bothScale = new ArrayList<Note>(scale);
        bothScale.addAll(reverseScale.subList(1, scale.size()));
        return bothScale;
    }
    
    public static List<Note> reorderScaleToPattern(final List<Note> scale, final List<Integer> pattern) {
        final List<Note> reorderedScale = new ArrayList<Note>();
        for (final int i : pattern) {
            reorderedScale.add(scale.get(i - 1));
        }
        return reorderedScale;
    }
    
    static {
        ScaleMap.scaleMap = new HashMap<String, Scale>();
        final Scale majorScale = new MajorScale();
        ScaleMap.scaleMap.put(majorScale.getName(), majorScale);
        final Scale minorScale = new MinorScale();
        ScaleMap.scaleMap.put(minorScale.name, minorScale);
        final Scale pentatonicMajorScale = new PentatonicMajorScale();
        ScaleMap.scaleMap.put(pentatonicMajorScale.getName(), pentatonicMajorScale);
        final Scale pentatonicMinorScale = new PentatonicMinorScale();
        ScaleMap.scaleMap.put(pentatonicMinorScale.getName(), pentatonicMinorScale);
        final Scale blueScale = new BlueScale();
        ScaleMap.scaleMap.put(blueScale.getName(), blueScale);
        final Scale melodicMinorScale = new MelodicMinorScale();
        ScaleMap.scaleMap.put(melodicMinorScale.getName(), melodicMinorScale);
        final Scale harmonicMinorScale = new HarmonicMinorScale();
        ScaleMap.scaleMap.put(harmonicMinorScale.getName(), harmonicMinorScale);
    }
}
