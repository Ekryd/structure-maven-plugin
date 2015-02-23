package structure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author bjorn
 * @since 15-02-07
 */

public class JsonConfigurationTest {
    @Test
    public void testColelctionFramework() {
        Configuration configuration = new Configuration();

        ConfigurationRules areaRules = configuration.addRules();
        areaRules.allowReference("se.gurka.*", "se.tomat.*");
        areaRules.allowReference("se.tomat.*", "se.mixer.*");

        ConfigurationRules typeRules = configuration.addRules();
        typeRules.allowReference("se.*.facade", "se.*.service");

        assertThat(configuration.getRuleSet().size(), is(2));

        Set<Map.Entry<String, String>> entries = configuration.getRuleSet().stream()
                .flatMap(rs -> rs.getAllowRoles().entrySet().stream())
                .collect(toCollection(LinkedHashSet::new));

        Set<String> keys = entries.stream()
                .map(e -> e.getKey())
                .collect(toCollection(LinkedHashSet::new));

        assertThat(entries.size(), is(3));
        assertThat(keys, contains("se.gurka.*", "se.tomat.*", "se.*.facade"));
    }

    @Test
    public void niceClassStructureShouldGenerateJson() {
        Configuration configuration = new Configuration();

        ConfigurationRules areaRules = configuration.addRules();
        areaRules.allowReference("se.gurka.*", "se.tomat.*");
        areaRules.allowReference("se.tomat.*", "se.mixer.*");

        ConfigurationRules typeRules = configuration.addRules();
        typeRules.allowReference("se.*.facade", "se.*.service");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(configuration);
        assertThat(json, is("" +
                "{\n" +
                "  \"ruleSet_AND\": [\n" +
                "    {\n" +
                "      \"allowRoles_OR\": {\n" +
                "        \"se.gurka.*\": \"se.tomat.*\",\n" +
                "        \"se.tomat.*\": \"se.mixer.*\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"allowRoles_OR\": {\n" +
                "        \"se.*.facade\": \"se.*.service\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}"));
    }


    @Test
    public void fromJsonToClassStructureShouldWork() {
        String json = "" +
                "{ \"ruleSet_AND\": [ {" +
                "      \"allowRoles_OR\": {" +
                "        \"se.gurka.*\": \"se.tomat.*\"," +
                "        \"se.tomat.*\": \"se.mixer.*\"" +
                "      }" +
                "    },{" +
                "      \"allowRoles_OR\": {" +
                "        \"se.*.facade\": \"se.*.service\"" +
                "      }" +
                "    }" +
                "  ]" +
                "}";
        Gson gson = new Gson();
        Configuration configuration = gson.fromJson(json, Configuration.class);
        assertThat(configuration.getRuleSet().size(), is(2));

        Set<Map.Entry<String, String>> entries = configuration.getRuleSet().stream()
                .flatMap(rs -> rs.getAllowRoles().entrySet().stream())
                .collect(toCollection(LinkedHashSet::new));

        Set<String> keys = entries.stream()
                .map(Map.Entry::getKey)
                .collect(toCollection(LinkedHashSet::new));

        assertThat(entries.size(), is(3));
        assertThat(keys, contains("se.gurka.*", "se.tomat.*", "se.*.facade"));
    }
}
