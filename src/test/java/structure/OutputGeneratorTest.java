package structure;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OutputGeneratorTest {
    @Test
    public void messageWithPackageNameShouldHaveDotBetween() {
        Function<String, String> messageGenerator = OutputGenerator.generateOutput.apply(Optional.of("structure"), "OutputGenerator");
        String message = messageGenerator.apply("java.util.function");
        assertThat(message, is("structure.OutputGenerator refers to package java.util.function"));
    }

    @Test
    public void messageWithoutPackageNameShouldNotStartWithDot() {
        Function<String, String> messageGenerator = OutputGenerator.generateOutput.apply(Optional.empty(), "OutputGenerator");
        String message = messageGenerator.apply("java.util.function");
        assertThat(message, is("OutputGenerator refers to package java.util.function"));
    }

    @Test
    public void useOutputGeneratorInLambda() {
        Stream<String> stringStream = Stream.of(
                "java.lang",
                "java.util",
                "se.arbetsformedlingen.utils.log");

        Function<String, String> messageGenerator = OutputGenerator.generateOutput.apply(Optional.of("structure"), "OutputGenerator");

        List<String> messages = stringStream.map(messageGenerator).collect(Collectors.toList());
        assertThat(messages, contains(
                "structure.OutputGenerator refers to package java.lang",
                "structure.OutputGenerator refers to package java.util",
                "structure.OutputGenerator refers to package se.arbetsformedlingen.utils.log"
        ));

    }
}