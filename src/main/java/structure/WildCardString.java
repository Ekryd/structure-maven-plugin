package structure;

import java.util.function.IntFunction;
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

    static String replaceSpecialCharacters(String str) {
        IntFunction<String> replacer = c -> {
            if (c == '.') {
                return "\\.";
            }
            if (c == '*') {
                return "[\\w.]*";
            }
            return String.valueOf((char) c);
        };

        return str.chars().mapToObj(replacer).collect(Collectors.joining("", "^", "$"));
    }

    public boolean matches(String str) {
        return regExPattern.matcher(str).matches();

    }

    public String getString() {
        return regExPattern.pattern();
    }
}
