# Crossing the chasm native images

This project has been configured to let you generate a lightweight container running a native executable.
You can find the presentation in the following link:

- Slides: https://estefaniaexamples.github.io/crossing-the-chasm-native-images
- Gitpod: http://gitpod.io/#https://github.com/EstefaniaExamples/crossing-the-chasm-native-images
- [![Java CI](https://github.com/EstefaniaExamples/crossing-the-chasm-native-images/actions/workflows/build.yml/badge.svg)](https://github.com/EstefaniaExamples/crossing-the-chasm-native-images/actions/workflows/build.yml)

## How to build

```
IP_LOCAL=$(ipconfig getifaddr en0)
echo $IP_LOCAL 
./build.sh $IP_LOCAL
```

## Generating JMeter Aggregate Report from the command-line

To do this we need to install 2 plugins:
- JMeterPluginsCMD Command Line Tool
- Synthesis Report

The first plugin, sort of, lets you refer and use the second plugin, which actually builds the reports.
The easiest way to install these plugins is using the JMeter Plugins Manager.
After installing both plugins use the command:
```
$ jmeter -n -t "LoadNativeTests.jmx" -l "./src/test/resources/tmp/output-native.jtl"
$ [jmeter-dir]/bin/JMeterPluginsCMD.sh — generate-csv your-output.csv — input-jtl input.jtl — plugin-type AggregateReport
```

https://jmeter-plugins.org/wiki/JMeterPluginsCMD/
https://medium.com/@abhimanyuPathania/automating-the-automatic-with-jmeter-at-logos-76f721faba4d