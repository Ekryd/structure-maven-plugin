package structure;

import structure.parser.EntityParser;
import structure.parser.ImportParser;
import structure.parser.PackageParser;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author bjorn
 * @since 15-02-16
 */
public class StructurePlugin {

    private final CodeLinesImpl codeLines;
    
    private Collection<String> lines;
    private Optional<String> packageName;
    private Stream<String> outputStream;

    public StructurePlugin(String fileName) {
        //TODO: Files.walk
        codeLines = new CodeLinesImpl(new File(fileName));
    }

    public void process() {
        //TODO: Measure time
        lines = codeLines.parseFile(EntityParser::isEntity);
        packageName = lines.stream()
                .filter(PackageParser::isPackage)
                .map(PackageParser::getPackageName)
                .findFirst();

        if (isUtilPackage()) {
            processUtilPackage();
        }
    }

    private boolean isUtilPackage() {
        return packageName.orElse("").endsWith("util");
    }

    private void processUtilPackage() {
        Optional<String> optPackageName = lines.stream()
                .filter(EntityParser::isEntity)
                .map(EntityParser::getEntityName)
                .findFirst();
        String entityName = optPackageName.orElseThrow(RuntimeException::new);
        
        Stream<String> importStream = lines.stream()
                .filter(ImportParser::isImport)
                .map(ImportParser::getImportName);
        
        //TODO: Stringjoiner
        outputStream = importStream
                .filter(i -> samePackage(i))
                .distinct()
                .sorted(Comparator.naturalOrder())
                .map(generateWarningMessage(entityName));
    }

    private boolean samePackage(String i) {
        return !i.equals(packageName.get());
    }

    private Function<String, String> generateWarningMessage(String entityName) {
        return imp -> String.format("%s.%s refers to package %s", 
                packageName.get(), entityName, imp);
    }

    public Stream<String> getOutput() {
        return outputStream;
    }
}
