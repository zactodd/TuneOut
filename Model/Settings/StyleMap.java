

package Model.Settings;

import java.util.HashMap;
import java.util.Collection;
import java.util.Map;

public class StyleMap
{
    private static Map<String, String> styles;
    private static String currentStyleName;
    private static String STYLES_LOCATION;
    
    public static String getCurrentStyle() {
        return StyleMap.styles.get(StyleMap.currentStyleName);
    }
    
    public static void setCurrentStyle(final String styleName) {
        StyleMap.currentStyleName = styleName;
    }
    
    public static String getCurrentStyleName() {
        return StyleMap.currentStyleName;
    }
    
    public static Collection<String> getStyleNames() {
        return StyleMap.styles.keySet();
    }
    
    static {
        StyleMap.currentStyleName = "default";
        StyleMap.STYLES_LOCATION = "/View/";
        (StyleMap.styles = new HashMap<String, String>()).put("default", StyleMap.STYLES_LOCATION + "default.css");
        StyleMap.styles.put("dark", StyleMap.STYLES_LOCATION + "darkTheme.css");
        StyleMap.styles.put("gray", StyleMap.STYLES_LOCATION + "grayTheme.css");
        StyleMap.styles.put("light gray", StyleMap.STYLES_LOCATION + "lightGrayTheme.css");
        StyleMap.styles.put("candy", StyleMap.STYLES_LOCATION + "candy.css");
    }
}
