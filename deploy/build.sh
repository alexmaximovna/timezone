#!/bin/sh
cd ..
mvn clean install -DskipTests
mkdir -p /etc/lineate-bench-timezones/
cp api/src/main/resources/cities.json /etc/lineate-bench-timezones/
cp api/target/api-0.0.1-SNAPSHOT.jar /etc/lineate-bench-timezones/
cp deploy/timezones.sh /etc/lineate-bench-timezones/
cp deploy/timezones.service  /etc/systemd/system/timezones.service
cp deploy/restart.sh /etc/lineate-bench-timezones/
mkdir -p /etc/lineate-bench-timezones/static/
cd ui
npm install
npm run-script build
cp dist/* /etc/lineate-bench-timezones/static


