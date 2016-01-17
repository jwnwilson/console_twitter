#!/usr/bin/env bash

if [ ! -d target ]
then
    echo "Creating target dir"
    mkdir target
fi

cd src/main/java
echo "Compiling java classes to target dir"
javac -cp .:../../../lib/json-20151123.jar:../../../lib/joda-time-2.3.jar com/jwnwilson/*.java -d ../../../target
echo "Console Twitter compilied."
cd ../../../
cd target
echo "Creating Twitter jar."
jar cfm ../ConsoleTwitter.jar ../META-INF/MANIFEST.MF com/jwnwilson/*.class
echo "Console Twitter jar created."
cd ..