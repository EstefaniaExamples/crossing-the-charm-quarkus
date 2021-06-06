package model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class ProcessReportWithThroughputFactorTest {

    Function<Integer, URL> toURL = index -> {
        final String fileName = "reports/Report-" + index + ".csv";
        return getClass().getClassLoader().getResource(fileName);
    };

    Function<URL, Stream<JMeter>> toJmeter = url -> {

        final String[] HEADERS = {
                "Label","# Samples","Average","Median","90% Line","95% Line","99% Line",
                "Min","Max","Error %","Throughput","Received KB/sec","Sent KB/sec"
        };
        Iterable<CSVRecord> records = null;
        try {
            Reader in = new FileReader(url.getFile());
            records =  CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);

        } catch (IOException ex) {
            LOGGER.error(ex.getLocalizedMessage(), ex);
        }
        return StreamSupport.stream(records.spliterator(), false)
            .filter(row -> !row.toString().contains("TOTAL"))
            .map(ProcessReportWithThroughputFactorTest::toJMeter)
            .sorted(Comparator.comparing(JMeter::getThroughput))
            .peek(obj -> LOGGER.info("{} : {}", obj.getLabel(), obj.getMin()))
            .limit(1);
    };

    @Test
    public void given_jmeterResults_when_process_then_Ok() throws IOException {

        Map<String, Long> counters = IntStream.rangeClosed(1, 7).boxed()
                .map(toURL)
                .flatMap(toJmeter)
                .collect(Collectors.groupingBy(jMeter -> jMeter.getLabel(), Collectors.counting()));

        LOGGER.info("Results:");
        counters.entrySet().stream()
                .forEach(obj -> {
                    LOGGER.info(obj.getKey() + ":" + obj.getValue().toString());
                });
    }

    private static JMeter toJMeter(CSVRecord record) {
        return JMeter.builder()
                .label(record.get(0))
                .samples(Long.valueOf(record.get(1)))
                .average(Float.valueOf(record.get(2)))
                .median(Float.valueOf(record.get(3)))
                .line90(Float.valueOf(record.get(4)))
                .line95(Float.valueOf(record.get(5)))
                .line99(Float.valueOf(record.get(6)))
                .min(Float.valueOf(record.get(7)))
                .max(Float.valueOf(record.get(8)))
                .error(record.get(9))
                .throughput(Float.valueOf(record.get(10)))
                .received(Float.valueOf(record.get(11)))
                .sent(Float.valueOf(record.get(12)))
                .build();
    }

}
