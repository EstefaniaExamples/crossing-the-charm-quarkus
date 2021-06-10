jmeter -n -t "LoadNativeTests.jmx" -l "./src/test/resources/tmp/output-native.jtl"
/usr/local/Cellar/jmeter/5.4.1/libexec/bin/JMeterPluginsCMD.sh --tool Reporter --generate-csv "./src/test/resources/native/Report-.csv" --input-jtl "./src/test/resources/tmp/output-native.jtl" --plugin-type AggregateReport
