# Crossing the chasm native images

This project has been configured to let you generate a lightweight container running a native executable.
You can find the presentation in the following link:

- Slides: https://estefaniaexamples.github.io/crossing-the-chasm-native-images
- Gitpod: http://gitpod.io/#https://github.com/EstefaniaExamples/crossing-the-chasm-native-images
- [![Java CI](https://github.com/EstefaniaExamples/crossing-the-chasm-native-images/actions/workflows/build.yml/badge.svg)](https://github.com/EstefaniaExamples/crossing-the-chasm-native-images/actions/workflows/build.yml)

## How to compile

The script compile all the project, generate the docker containers and push them into docker repository.
```
./compile.sh
```

## How to build

```
IP_LOCAL=$(ipconfig getifaddr en0)
echo $IP_LOCAL 
./build.sh $IP_LOCAL
```

## Generating JMeter Aggregate Report from the command-line

To do this we need to install 2 plugins:
- JMeterPluginsCMD Command Line Tool (Command-Line Graph Plotting Tool)
- Synthesis Report

The first plugin, sort of, lets you refer and use the second plugin, which actually builds the reports.
The easiest way to install these plugins is using the JMeter Plugins Manager.
After installing both plugins use the command:
```
$ jmeter -n -t "LoadNativeTests.jmx" -l "./src/test/resources/tmp/output-native.jtl"
$ [jmeter-dir]/bin/JMeterPluginsCMD.sh — generate-csv your-output.csv — input-jtl input.jtl — plugin-type AggregateReport
```

You can find more documentation about this plugins [here](https://medium.com/@abhimanyuPathania/automating-the-automatic-with-jmeter-at-logos-76f721faba4d)

The script ```jmeter/build.sh``` run the Jmeter ten times and generate the aggregated report automatically. 
