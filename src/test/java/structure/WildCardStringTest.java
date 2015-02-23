package structure;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WildCardStringTest {

    @Test
    public void dotShouldBeReplacedWithSlashDot() throws Exception {
        String replaced = WildCardString.replaceSpecialCharacters("java.lang");
        assertThat(replaced, is("^java\\.lang$"));

        Pattern compile = Pattern.compile(replaced);
        assertThat(compile.matcher("java.lang").matches(), is(true));
        assertThat(compile.matcher("javaplang").matches(), is(false));
    }

    @Test
    public void starShouldBeReplacedWithWordCharacter() throws Exception {
        String replaced = WildCardString.replaceSpecialCharacters("java.*");
        assertThat(replaced, is("^java\\.[\\w.]*$"));

        Pattern compile = Pattern.compile(replaced);
        assertThat(compile.matcher("java.lang").matches(), is(true));
        assertThat(compile.matcher("java.long").matches(), is(true));
        assertThat(compile.matcher("java.extra42.long").matches(), is(true));
        assertThat(compile.matcher("javaplang").matches(), is(false));
    }

    @Test
    public void equalsWithDotShouldOnlyBeDot() throws Exception {
        WildCardString wildCardString = new WildCardString("java.lang");

        assertThat(wildCardString.matches("java.lang"), is(true));
        assertThat(wildCardString.matches("java.lang.long"), is(false));
        assertThat(wildCardString.matches("javaplang"), is(false));
    }

    @Test
    public void equalsWithStarAtEndShouldAcceptAnyString() throws Exception {
        WildCardString wildCardString = new WildCardString("java.*");

        assertThat(wildCardString.matches("java.lang"), is(true));
        assertThat(wildCardString.matches("java.long"), is(true));
        assertThat(wildCardString.matches("java.lang.long"), is(true));
        assertThat(wildCardString.matches("javaplang"), is(false));
        assertThat(wildCardString.matches("se.java.lang"), is(false));
    }

    @Test
    public void equalsWithStarAtBeginningShouldAcceptAnyString() throws Exception {
        WildCardString wildCardString = new WildCardString("*.lang");

        assertThat(wildCardString.matches("java.lang"), is(true));
        assertThat(wildCardString.matches("se.lang"), is(true));
        assertThat(wildCardString.matches("se.java.lang"), is(true));
        assertThat(wildCardString.matches("javaplang"), is(false));
        assertThat(wildCardString.matches("java.lang.long"), is(false));
    }

    @Test
    public void equalsWithStarInTheMiddleShouldAcceptAnyString() throws Exception {
        WildCardString wildCardString = new WildCardString("java.*.lang");

        assertThat(wildCardString.matches("java.long.lang"), is(true));
        assertThat(wildCardString.matches("java.se.long.lang"), is(true));
        assertThat(wildCardString.matches("java.lang"), is(false));
        assertThat(wildCardString.matches("java.a.lang"), is(true));
        assertThat(wildCardString.matches("a.java.a.lang"), is(false));
        assertThat(wildCardString.matches("java.a.lang.a"), is(false));
    }
}