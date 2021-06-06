package model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class ProcessReportWithThroughputFactorTest extends ProcessReportBase {

    Function<URL, Stream<JMeter>> toJmeter = url -> {

        return StreamSupport.stream(toCSVRecord.apply(url).spliterator(), false)
            .filter(row -> !row.toString().contains("TOTAL"))
            .map(ProcessReportWithThroughputFactorTest::toJMeter)
            .sorted((t1, t2) -> Float.compare(t2.getThroughput(), t1.getThroughput()))
            .limit(3);
    };

    @Test
    public void given_jmeterResults_when_process_then_Ok() throws IOException {

        Map<String, Long> counters = IntStream.rangeClosed(1, 7).boxed()
                .map(toURL)
                .flatMap(toJmeter)
                .sorted((t1, t2) -> Float.compare(t2.getThroughput(), t1.getThroughput()))
                .peek(obj -> LOGGER.info("{} : {}", obj.getLabel(), obj.getThroughput()))
                .collect(Collectors.groupingBy(jMeter -> jMeter.getLabel(), Collectors.counting()));

        LOGGER.info("Results:");
        counters.entrySet().stream()
                .forEach(obj -> {
                    LOGGER.info(obj.getKey() + ":" + obj.getValue().toString());
                });
    }

}
