package structure.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bjorn
 * @since 15-02-13
 */
public class EntityParser {
    private static final Pattern ENTITY_REGEX = Pattern.compile("^\\s*public\\s+(?:enum|class|interface)\\s+(\\w+).*?$");

    public static boolean isEntity(String line) {
        return ENTITY_REGEX.matcher(line).matches();
    }
    
    public static String getEntityName(String line) {
        Matcher matcher = ENTITY_REGEX.matcher(line);
        matcher.find();
        return matcher.group(1);
    }
}
