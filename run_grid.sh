#!/bin/sh

SELENIUM_JAR=selenium-server-standalone-3.141.59.jar

test -f $SELENIUM_JAR || curl http://selenium-release.storage.googleapis.com/3.141/selenium-server-standalone-3.141.59.jar --output $SELENIUM_JAR

java -jar "$SELENIUM_JAR"
 
