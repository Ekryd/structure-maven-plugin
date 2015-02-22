package structure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import structure.parser.EntityParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author bjorn
 * @since 15-02-16
 */
public class StructurePlugin {

    private final CodeLinesImpl codeLines;
    private final Set<String> allowedPackages;

    private Stream<String> outputStream;

    public StructurePlugin(String fileName, String configurationName) {
        //TODO: Files.walk
        codeLines = new CodeLinesImpl(new File(fileName));
        allowedPackages = getAllowedPackagesFromFile(configurationName);
    }

    private Set<String> getAllowedPackagesFromFile(String configurationName) {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(configurationName));
            Gson gson = new Gson();
            return gson.fromJson(jsonReader, HashSet.class);
        } catch (FileNotFoundException fex) {
            throw new RuntimeException(fex);
        }
    }

    public void process() {
        //TODO: Measure time
        ScanFile scanFile = new ScanFile(codeLines.parseFile(EntityParser::isEntity), allowedPackages);

        Function<String, String> warningGenerator = OutputGenerator.generateOutput.apply(
                scanFile.getPackageName(), scanFile.getEntityName());

        outputStream = scanFile.getIllegalImports().map(warningGenerator);
    }

    public Stream<String> getOutput() {
        return outputStream;
    }
}
