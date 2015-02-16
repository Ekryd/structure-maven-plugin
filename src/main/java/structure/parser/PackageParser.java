package structure.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bjorn
 * @since 15-02-13
 */
public class PackageParser {
    private static final Pattern PACKAGE_REGEX = Pattern.compile("^\\s*package\\s+(.+);$");

    public static boolean isPackage(String line) {
        return PACKAGE_REGEX.matcher(line).matches();
    }

    public static String getPackageName(String line) {
        Matcher matcher = PACKAGE_REGEX.matcher(line);
        matcher.find();
        return matcher.group(1);
    }
}
