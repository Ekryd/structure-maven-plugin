package structure;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author bjorn
 * @since 15-02-22
 */
public class OutputGenerator {

    static BiFunction<Optional<String>, String, Function<String, String>> generateOutput =
            (packageName, entityName) -> importName ->
                    String.format("%s%s refers to package %s",
                            packageName.map(s -> s + ".").orElse(""), entityName, importName);

}
