package model;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class DemoTalk extends ProcessReportBase {

    BiFunction<URL, String, Stream<JMeter>> toJmeter = (url, sufix) -> {

        return StreamSupport.stream(toCSVRecord.apply(url).spliterator(), false)
                .filter(row -> !row.toString().contains("TOTAL"))
                .map(record -> toJMeter(record, sufix));
    };

    @Test
    public void given_jmeter_results_when_compare_results_then_jvm_rules() {

        var stream1 = IntStream.rangeClosed(1, 10).boxed()
                .map(toJVMFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, " jvm"));

        var stream2 = IntStream.rangeClosed(1, 10).boxed()
                .map(toNativeFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, " native"));

        Stream.concat(stream1, stream2)
                .sorted((t1, t2) -> Float.compare(t2.getThroughput(), t1.getThroughput()))
                .map(jmeter -> String.valueOf(jmeter.getLabel() + " " + jmeter.getThroughput()))
                .map(String::valueOf)
                .limit(10)
                .forEach(LOGGER::info);
    }

    @Test
    public void given_jmeter_results_when_compare_results_with_jvm_and_native_then_exist_big_differences() {

        var stream1 = IntStream.rangeClosed(1, 10).boxed()
                .map(toJVMFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, " jvm"))
                .sorted((t1, t2) -> Float.compare(t2.getThroughput(), t1.getThroughput()))
                .limit(1);

        var stream2 = IntStream.rangeClosed(1, 10).boxed()
                .map(toNativeFileName)
                .map(toURL)
                .flatMap(url -> toJmeter.apply(url, " native"))
                .filter(jMeter -> !jMeter.getLabel().contains("Nodejs"))
                .sorted((t1, t2) -> Float.compare(t2.getThroughput(), t1.getThroughput()))
                .limit(1);

        Stream.concat(stream1, stream2)
                .map(jmeter -> String.valueOf(jmeter.getLabel() + " " + jmeter.getThroughput()))
                .map(String::valueOf)
                .forEach(LOGGER::info);
    }

}
