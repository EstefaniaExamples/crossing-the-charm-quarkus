package model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.function.Function;

@Slf4j
public abstract class ProcessReportBase {

    Function<Integer, URL> toURL = index -> {
        final String fileName = "reports/Report-" + index + ".csv";
        return getClass().getClassLoader().getResource(fileName);
    };

    Function<URL, Iterable<CSVRecord>> toCSVRecord = url ->  {

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

        return records;
    };

    static JMeter toJMeter(CSVRecord record) {
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
