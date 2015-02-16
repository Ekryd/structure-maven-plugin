package structure.parser;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static structure.parser.EntityParser.getEntityName;

public class EntityParserTest {

    @Test
    public void testIsPackage() throws Exception {
        assertThat(EntityParser.isEntity("public class EntityParserTest"), is(true));
        assertThat(EntityParser.isEntity("public class EntityParserTest {"), is(true));
        assertThat(EntityParser.isEntity("public  class  EntityParserTest  {"), is(true));
        assertThat(EntityParser.isEntity("public  interface  EntityParserTest  {"), is(true));
        assertThat(EntityParser.isEntity("public  enum  EntityParserTest  {"), is(true));
    }

    @Test
    public void extractPackageNameFromLineShouldWork() throws Exception {
        String name = getEntityName("public class EntityParserTest");
        assertThat(name, is("EntityParserTest"));
        name = getEntityName("public  interface EntityParserTest {");
        assertThat(name, is("EntityParserTest"));
    }
}