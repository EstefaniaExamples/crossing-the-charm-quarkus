package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class JMeter {

    //"Label","# Samples","Average","Median","90% Line","95% Line","99% Line",
    //"Min","Max","Error %","Throughput","Received KB/sec","Sent KB/sec"

    private String label;
    private Long samples;
    private float average;
    private float median;
    private float line90;
    private float line95;
    private float line99;
    private float min;
    private float max;
    private String error;
    private float throughput;
    private float received;
    private float sent;

}
