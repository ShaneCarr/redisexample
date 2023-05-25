#!/bin/sh

OPTIONS="-Djava.net.preferIPv4Stack=true
         -Dnetworkaddress.cache.ttl=10
         -XX:+FlightRecorder
         -XX:StartFlightRecording=duration=200s,filename=app.jfr"

echo "dw" > $HOME/logs/app.log.type
echo "nginx" > $HOME/logs/requests.log.type

exec java $OPTIONS -jar app.jar server app.yml