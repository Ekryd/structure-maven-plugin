package structure.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bjorn
 * @since 15-02-13
 */
public class ImportParser {
    private static final Pattern IMPORT_REGEX = Pattern.compile("^\\s*import\\s+(?:static\\s+)?(.+?)((\\.\\*)|(\\.[A-Z][\\w.*]*));$");

    public static boolean isImport(String line) {
        return IMPORT_REGEX.matcher(line).matches();
    }
    
    public static String getImportName(String line) {
        Matcher matcher = IMPORT_REGEX.matcher(line);
        matcher.find();
        return matcher.group(1);
    }
}
