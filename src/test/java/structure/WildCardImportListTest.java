package structure;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WildCardImportListTest {

    @Test
    public void emptyListShouldNotMatch() throws Exception {
        WildCardImportList wildCardImportList = new WildCardImportList();
        assertThat(wildCardImportList.matches("java.lang"), is(false));
    }

    @Test
    public void wrongImportShouldNotMatch() throws Exception {
        WildCardImportList wildCardImportList = new WildCardImportList();
        wildCardImportList.add("java.long");
        assertThat(wildCardImportList.matches("java.lang"), is(false));
    }

    @Test
    public void wildcardImportShouldMatch() throws Exception {
        WildCardImportList wildCardImportList = new WildCardImportList();
        wildCardImportList.add("java.*");
        assertThat(wildCardImportList.matches("java.lang"), is(true));
    }
}