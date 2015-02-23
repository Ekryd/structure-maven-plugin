package structure;

import org.junit.Before;
import org.junit.Test;
import structure.parser.EntityParser;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ScanFileTest {

    private static final String TESTFILE_NAME = "src/test/resources/ExcelCSVSkrivare.java";
    private Collection<String> lines;

    @Before
    public void setUp() throws Exception {
        CodeLinesImpl codeLines = new CodeLinesImpl(new File(TESTFILE_NAME));
        lines = codeLines.parseFile(EntityParser::isEntity);
    }

    @Test
    public void packageNameShouldReturnNameFromFile() throws Exception {
/*
package se.arbetsformedlingen.elin.rapport.util;
 */

        ScanFile scanFile = new ScanFile(lines, new WildCardImportList2());

        assertThat(scanFile.getPackageName().get(), is("se.arbetsformedlingen.elin.rapport.util"));
    }

    @Test
    public void entityNameShouldReturnClassName() throws Exception {
/*
public class ExcelCSVSkrivare<T> {
 */

        ScanFile scanFile = new ScanFile(lines, new WildCardImportList2());

        assertThat(scanFile.getEntityName(), is("ExcelCSVSkrivare"));
    }

    @Test
    public void importShouldReturnAllImportsFromFile() throws Exception {
/*
import org.apache.commons.lang3.StringUtils;                    import 1

import se.arbetsformedlingen.utils.log.Logger;                  import 2
import se.arbetsformedlingen.utils.log.LoggerFactory;           duplicate import

import se.arbetsformedlingen.elin.rapport.util.SomethingElse    same package import
import java.lang.reflect.Field;                                 import 3
import java.text.SimpleDateFormat;                              import 4
import java.util.*;                                             import 5

import static java.lang.String.format;                          import 6
 */

        ScanFile scanFile = new ScanFile(lines, new WildCardImportList2());

        List<String> collect = scanFile.getIllegalImports().collect(Collectors.toList());
        assertThat(collect, contains(
                "java.lang",
                "java.lang.reflect",
                "java.text",
                "java.util",
                "org.apache.commons.lang3",
                "se.arbetsformedlingen.utils.log"
        ));

    }

    @Test
    public void filteredImportsShouldBeRemoved() throws Exception {
        WildCardImportList2 filter = Stream.of(
                "java.lang",
                "java.util",
                "se.arbetsformedlingen.utils.log")
                .collect(Collectors.toCollection(WildCardImportList2::new));

        ScanFile scanFile = new ScanFile(lines, filter);

        List<String> collect = scanFile.getIllegalImports().collect(Collectors.toList());
        assertThat(collect, contains(
                "java.lang.reflect",
                "java.text",
                "org.apache.commons.lang3"
        ));

    }

}