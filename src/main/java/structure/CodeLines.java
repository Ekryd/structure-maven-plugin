package structure;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author bjorn
 * @since 15-02-16
 */
public interface CodeLines {
    default Stream<String> parseCodeLines(Predicate<String> endParseCondition) {
        return parseFile(endParseCondition).stream();
    }

    Collection<String> parseFile(Predicate<String> endParseCondition);
}
