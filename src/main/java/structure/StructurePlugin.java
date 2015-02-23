package structure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import structure.parser.EntityParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author bjorn
 * @since 15-02-16
 */
public class StructurePlugin {

    private static final Gson GSON = new Gson();
    private final CodeLinesImpl codeLines;
    private final WildCardImportList allowedImports;

    private Stream<String> outputStream;

    public StructurePlugin(String fileName, String configurationName) {
        //TODO: Files.walk
        codeLines = new CodeLinesImpl(new File(fileName));
        allowedImports = getAllowedPackagesFromFile(configurationName);
    }

    private WildCardImportList getAllowedPackagesFromFile(String configurationName) {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(configurationName));
            LinkedHashSet<String> set = GSON.fromJson(jsonReader, LinkedHashSet.class);
            return set.stream()
                    .collect(Collectors.toCollection(WildCardImportList::new));
        } catch (FileNotFoundException fex) {
            throw new RuntimeException(fex);
        }
    }

    public void process() {
        //TODO: Measure time
        ScanFile scanFile = new ScanFile(codeLines.parseFile(EntityParser::isEntity), allowedImports);

        Function<String, String> warningGenerator = OutputGenerator.generateOutput.apply(
                scanFile.getPackageName(), scanFile.getEntityName());

        outputStream = scanFile.getIllegalImports().map(warningGenerator);
    }

    public Stream<String> getOutput() {
        return outputStream;
    }
}
