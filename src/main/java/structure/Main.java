package structure;

import java.util.stream.Stream;

/**
 * @author bjorn
 * @since 15-02-16
 */
public class Main {
    public static void main(String[] args) {
        StructurePlugin structurePlugin = new StructurePlugin(args[0], args[1]);
        structurePlugin.process();
        Stream<String> output = structurePlugin.getOutput();
        output.forEach(System.out::println);
    }
}
