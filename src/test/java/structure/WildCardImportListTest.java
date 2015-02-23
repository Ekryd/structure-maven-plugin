package structure;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WildCardImportListTest {

    @Test
    public void emptyListShouldNotMatch() throws Exception {
        WildCardImportList2 wildCardImportList = new WildCardImportList2();
        assertThat(wildCardImportList.matches("java.lang"), is(false));
    }

    @Test
    public void wrongImportShouldNotMatch() throws Exception {
        WildCardImportList2 wildCardImportList = new WildCardImportList2();
        wildCardImportList.add("java.long");
        assertThat(wildCardImportList.matches("java.lang"), is(false));
    }

    @Test
    public void wildcardImportShouldMatch() throws Exception {
        WildCardImportList2 wildCardImportList = new WildCardImportList2();
        wildCardImportList.add("java.*");
        assertThat(wildCardImportList.matches("java.lang"), is(true));
    }
}