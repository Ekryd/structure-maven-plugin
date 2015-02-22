package structure;

import structure.parser.EntityParser;
import structure.parser.ImportParser;
import structure.parser.PackageParser;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author bjorn
 * @since 15-02-22
 */
public class ScanFile {
    private final Collection<String> lines;
    private final Set<String> allowedPackages;

    public ScanFile(Collection<String> lines, Set<String> allowedPackages) {
        this.lines = lines;
        this.allowedPackages = allowedPackages;
    }

    public Stream<String> getIllegalImports() {
        return lines.stream()
                .filter(ImportParser::isImport)
                .map(ImportParser::getImportName)
                .filter(i -> !allowedPackages.contains(i))
                .distinct()
                .sorted(Comparator.naturalOrder());
    }

    public Optional<String> getPackageName() {
        return lines.stream()
                .filter(PackageParser::isPackage)
                .map(PackageParser::getPackageName)
                .findFirst();
    }

    public String getEntityName() {
        return lines.stream()
                .filter(EntityParser::isEntity)
                .map(EntityParser::getEntityName)
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
