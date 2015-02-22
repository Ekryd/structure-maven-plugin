package structure;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author bjorn
 * @since 15-02-07
 */
public class ConfigurationRules {
    private Map<String, String> allowRoles_OR = new TreeMap<>();

    public void allowReference(String from, String to) {
        allowRoles_OR.put(from, to);
    }

    public Map<String, String> getAllowRoles() {
        return allowRoles_OR;
    }
}
