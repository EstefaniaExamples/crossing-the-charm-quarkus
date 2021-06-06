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
public class ProcessReportWithMedianFactorTest extends ProcessReportBase {

    Function<URL, Stream<JMeter>> toJmeter = url -> {

        return StreamSupport.stream(toCSVRecord.apply(url).spliterator(), false)
            .filter(row -> !row.toString().contains("TOTAL"))
            .map(ProcessReportBase::toJMeter)
            .sorted(Comparator.comparing(JMeter::getMedian))
            .limit(1);
    };

    @Test
    public void given_jmeterResults_when_process_then_Ok() throws IOException {

        Map<String, Long> counters = IntStream.rangeClosed(1, 7).boxed()
                .map(toURL)
                .flatMap(toJmeter)
                .sorted(Comparator.comparing(JMeter::getMedian))
                .peek(obj -> LOGGER.info("{} : {}", obj.getLabel(), obj.getMedian()))
                .collect(Collectors.groupingBy(jMeter -> jMeter.getLabel(), Collectors.counting()));

        LOGGER.info("Results:");
        counters.entrySet().stream()
                .forEach(obj -> {
                    LOGGER.info(obj.getKey() + ":" + obj.getValue().toString());
                });
    }

}
