package structure;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author bjorn
 * @since 15-02-16
 */
public class LinesStorageConsumer implements Consumer<String> {
    private final List<String> lines = new LinkedList<>();

    /**
     * Performs this operation on the given argument.
     *
     * @param line the input argument
     */
    @Override
    public void accept(String line) {
        lines.add(line);
    }


    public List<String> getLines() {
        return lines;
    }
}
