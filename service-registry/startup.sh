#!/bin/bash
java -XX:InitialRAMPercentage=10.0 -XX:MaxRAMPercentage=90.0 \
 -Dspring.profiles.active=$APPLICATION_PROFILE \
 -Deureka.instance.hostname=$(hostname -i) \
 -Duser.language=pt -Duser.region=BR -Duser.timezone=GMT-4 -Dfile.encoding=UTF-8 \
 -jar /opt/app/*.jar