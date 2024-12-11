#!/usr/bin/env bash

echo "Do something..."

exec java ${JAVA_OPTS} -jar /application/app.jar  ${@}
