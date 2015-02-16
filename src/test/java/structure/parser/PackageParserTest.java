package structure.parser;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static structure.parser.PackageParser.getPackageName;
import static structure.parser.PackageParser.isPackage;

public class PackageParserTest {

    @Test
    public void testIsPackage() throws Exception {
        assertThat(isPackage("package structure.util;"), is(true));
        assertThat(isPackage("package  structure.util;"), is(true));
        assertThat(isPackage("packagestructure.util;"), is(false));
        assertThat(isPackage("import structure.util.*"), is(false));
    }

    @Test
    public void extractPackageNameFromLineShouldWork() throws Exception {
        String packageName = getPackageName("package structure.util;");
        assertThat(packageName, is("structure.util"));
        packageName = getPackageName("package  structure.util;");
        assertThat(packageName, is("structure.util"));
    }
}