package structure;

import org.junit.Before;
import org.junit.Test;
import structure.parser.ImportParser;
import structure.parser.PackageParser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CodeLinesTest {

    private CodeLinesMock codeLinesMock;

    static class CodeLinesMock implements CodeLines {

        @Override
        public Collection<String> parseFile(Predicate<String> endParseCondition) {
            return Arrays.asList("package structure.util;",
                    "",
                    "import java.util.List;",
                    "import java.util.Map;",
                    "import structure.util.Tomat;",
                    "import structure.*;",
                    "",
                    "",
                    "public class Gurka {");
        }
    }
    
    @Before
    public void setUp() throws Exception {
        codeLinesMock = new CodeLinesMock();
    }

    @Test
    public void extractPackageNameShouldBeSameAsInStream() throws Exception {
        Optional<String> name = codeLinesMock.parseCodeLines(null)
                .filter(PackageParser::isPackage)
                .map(PackageParser::getPackageName)
                .findFirst();
        assertThat(name.get(), is("structure.util"));
    }

    @Test
    public void extractImportsShouldBeSameAsInStream() throws Exception {
        Set<String> collect = codeLinesMock.parseCodeLines(null)
                .filter(ImportParser::isImport)
                .map(ImportParser::getImportName)
                .collect(Collectors.toSet());
        assertThat(collect, contains(
                "java.util",
                "structure.util",
                "structure"));
    }

}