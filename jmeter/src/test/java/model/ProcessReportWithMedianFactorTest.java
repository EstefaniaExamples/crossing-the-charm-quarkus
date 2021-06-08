package model;

import com.codepoetics.protonpack.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class ProcessReportWithMedianFactorTest extends ProcessReportBase {

    BiFunction<URL, String, Stream<JMeter>> toJmeter = (url, sufix) -> {

        return StreamSupport.stream(toCSVRecord.apply(url).spliterator(), false)
            .filter(row -> !row.toString().contains("TOTAL"))
            .map(record -> toJMeter(record, sufix))
            .sorted(Comparator.comparing(JMeter::getMedian))
            .limit(1);
    };

    @Test
    public void given_jmeterResults_when_process_native_reports_then_Ok() throws IOException {

        var limit = getResourceFolderLength("reports/native");
        Map<String, Long> counters = IntStream.rangeClosed(1, limit).boxed()
                .map(toNativeFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, "-native"))
                .sorted(Comparator.comparing(JMeter::getMedian))
                .peek(obj -> LOGGER.info("{} : {}", obj.getLabel(), obj.getMedian()))
                .collect(Collectors.groupingBy(jMeter -> jMeter.getLabel(), Collectors.counting()));

        LOGGER.info("Results:");
        counters.entrySet().stream()
                .forEach(obj -> {
                    LOGGER.info(obj.getKey() + ":" + obj.getValue().toString());
                });
    }

    @Test
    public void given_jmeterResults_when_process_jvm_reports_then_Ok() throws IOException {

        var limit = getResourceFolderLength("reports/jvm");
        Map<String, Long> counters = IntStream.rangeClosed(1, limit).boxed()
                .map(toJVMFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, "-jvm"))
                .sorted(Comparator.comparing(JMeter::getMedian))
                .peek(obj -> LOGGER.info("{} : {}", obj.getLabel(), obj.getMedian()))
                .collect(Collectors.groupingBy(jMeter -> jMeter.getLabel(), Collectors.counting()));

        LOGGER.info("Results:");
        counters.entrySet().stream()
                .forEach(obj -> {
                    LOGGER.info(obj.getKey() + ":" + obj.getValue().toString());
                });
    }

    @Test
    public void given_jmeterResults_when_process_all_reports_then_Ok() throws IOException {

        var limit = getResourceFolderLength("reports/native");
        var nativeStream = IntStream.rangeClosed(1, limit).boxed()
                .map(toNativeFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, "-native"));

        var limitJVM = getResourceFolderLength("reports/jvm");
        var jvmStream = IntStream.rangeClosed(1, limitJVM).boxed()
                .map(toJVMFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, "-jvm"));

        Map<String, Long> counters = Stream.concat(nativeStream, jvmStream)
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
