#!/usr/bin/env bash

echo "📜 Import / Update JVM Truststore"
export USE_SYSTEM_CA_CERTS=1
/__cacert_entrypoint.sh &> /dev/null

JAVA_OPTS="-Djava.security.edg=file:/dev/./urandom $JAVA_OPTS_APPEND"

exec java ${JAVA_OPTS} -jar /application/app.jar  ${@}
