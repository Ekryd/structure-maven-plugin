package structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bjorn
 * @since 15-02-07
 */
public class Configuration {
    private List<ConfigurationRules> ruleSet_AND = new ArrayList<>();

    public ConfigurationRules addRules() {
        ConfigurationRules rules = new ConfigurationRules();
        ruleSet_AND.add(rules);
        return rules;
    }

    public List<ConfigurationRules> getRuleSet() {
        return ruleSet_AND;
    }
}
