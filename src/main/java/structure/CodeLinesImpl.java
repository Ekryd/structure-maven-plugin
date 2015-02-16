package structure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author bjorn
 * @since 15-02-12
 */
public class CodeLinesImpl implements CodeLines {
    private final File file;

    public CodeLinesImpl(File file) {
        this.file = file;
    }

    @Override
    public Collection<String> parseFile(Predicate<String> endParseCondition) {
        try (Stream<String> stream = Files.lines(file.toPath())) {
            LinesStorageConsumer linesStorageConsumer = new LinesStorageConsumer();
            stream.peek(linesStorageConsumer).filter(endParseCondition).findFirst();
            return linesStorageConsumer.getLines();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
