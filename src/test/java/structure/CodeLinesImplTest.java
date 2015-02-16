package structure;

import org.junit.Before;
import org.junit.Test;
import structure.parser.EntityParser;
import structure.parser.ImportParser;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CodeLinesImplTest {

    private File file;
    private CodeLinesImpl codeLines;

    @Before
    public void setUp() throws Exception {
        file = new File("src/test/resources/Gurka.java");
        codeLines = new CodeLinesImpl(file);
    }
    
    @Test
    public void findTestResourceShouldWork() {
        assertThat(file.exists(), is(true));
    }
    
    @Test
    public void classDeclarationShouldBeReadFromFile() {
        Stream<String> fileStream = codeLines.parseFile(EntityParser::isEntity).stream();
        Optional<String> foundString = fileStream.filter(s -> s.equals("public class Gurka {")).findFirst();
        assertThat(foundString.isPresent(), is(true));
    }

    @Test
    public void ifWeStopAfterFirstImportThenClassShouldNotBeFound() {
        Stream<String> fileStream = codeLines.parseFile(ImportParser::isImport).stream();
        Optional<String> foundString = fileStream.filter(s -> s.equals("public class Gurka {")).findFirst();
        assertThat(foundString.isPresent(), is(false));
    }

    @Test
    public void rowsAfterClassDeclarationsShouldNotBeFound() {
        Stream<String> fileStream = codeLines.parseFile(EntityParser::isEntity).stream();
        Optional<String> foundString = fileStream.filter(s -> s.equals("    private Tomat tomat;")).findFirst();
        assertThat(foundString.isPresent(), is(false));
    }

}