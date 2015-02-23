package structure.parser;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static structure.parser.ImportParser.getImportName;
import static structure.parser.ImportParser.isImport;

public class ImportParserTest {

    @Test
    public void starImportShouldWork() throws Exception {
        assertThat(isImport("import structure.util.*;"), is(true));
        assertThat(isImport("import  structure.util.*;"), is(true));
    }

    @Test
    public void classImportShouldWork() throws Exception {
        assertThat(isImport("import structure.util.Pelle;"), is(true));
        assertThat(isImport("import structure.util.Pelle.Kalle;"), is(true));
    }

    @Test
    public void staticImportShouldWork() throws Exception {
        assertThat(isImport("import static structure.util.Pelle.*;"), is(true));
        assertThat(isImport("import static structure.util.Pelle.kalle;"), is(true));
    }

    @Test
    public void illegalImportsShouldNotWork() throws Exception {
        assertThat(isImport("importstructure.util.*;"), is(false));
        assertThat(isImport("import structure.util"), is(false));
    }

    @Test
    public void extractImportNameFromLineShouldWork() throws Exception {
        String importName = getImportName("import structure.util.*;");
        assertThat(importName, is("structure.util"));
        importName = getImportName("import  structure.util.*;");
        assertThat(importName, is("structure.util"));
        importName = getImportName("import structure.util.Pelle;");
        assertThat(importName, is("structure.util"));
        importName = getImportName("import structure.util.Pelle.Kalle;");
        assertThat(importName, is("structure.util"));
        importName = getImportName("import static structure.util.Pelle.*;");
        assertThat(importName, is("structure.util"));
        importName = getImportName("import static structure.util.Pelle.kalle;");
        assertThat(importName, is("structure.util"));
    }
}