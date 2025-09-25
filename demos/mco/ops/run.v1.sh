#!/usr/bin/env bash

echo "Do something..."

JAVA_OPTS="-Djava.security.edg=file:/dev/./urandom $JAVA_OPTS_APPEND"

exec java ${JAVA_OPTS} -jar /application/app.jar  ${@}
