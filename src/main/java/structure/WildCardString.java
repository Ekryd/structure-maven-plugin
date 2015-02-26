package structure;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author bjorn
 * @since 15-02-22
 */
public class WildCardString {
    private final Pattern regExPattern;

    public WildCardString(String str) {
        this.regExPattern = Pattern.compile(replaceSpecialCharacters(str));
    }

    private static String replaceSpecialCharacters(String str) {
        return str.chars().mapToObj(WildCardString::characterReplacer).collect(Collectors.joining("", "^", "$"));
    }

    private static String characterReplacer(final int ch) {
        if (ch == '.') {
            return "\\.";
        }
        if (ch == '*') {
            return "[\\w.]*";
        }
        return String.valueOf((char) ch);
    }

    public boolean matches(String str) {
        return regExPattern.matcher(str).matches();
    }

    public String getString() {
        return regExPattern.pattern();
    }

}
