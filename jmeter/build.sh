#!/bin/bash

for i in {11..12}
do
  jmeter -n -t "LoadNativeTests.jmx" -l "./src/test/resources/tmp/output-native-$i.jtl"
  /usr/local/Cellar/jmeter/5.4.1/libexec/bin/JMeterPluginsCMD.sh --tool Reporter --generate-csv "./src/test/resources/native/Report-$i.csv" --input-jtl "./src/test/resources/tmp/output-native-$i.jtl" --plugin-type AggregateReport
  sleep 5
done

rm -rf ./src/test/resources/tmp

