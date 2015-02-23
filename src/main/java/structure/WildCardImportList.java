package structure;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * @author bjorn
 * @since 15-02-23
 */
public class WildCardImportList extends AbstractList<String> {
    private ArrayList<WildCardString> list = new ArrayList<>();

    @Override
    public boolean add(String imp) {
        return list.add(new WildCardString(imp));
    }

    public boolean matches(String imp) {
        return list.stream()
                .filter(i -> i.matches(imp))
                .findFirst()
                .isPresent();
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException("This list only supports 'add' and 'matches''");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("This list only supports 'add' and 'matches''");
    }
}
