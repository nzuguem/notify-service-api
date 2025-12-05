#!/usr/bin/env bash

echo "📜 Import / Update JVM Truststore"
export USE_SYSTEM_CA_CERTS=1
/__cacert_entrypoint.sh &> /dev/null

JAVA_OPTS="-Djava.security.edg=file:/dev/./urandom $JAVA_OPTS_APPEND"

export JAZ_EXIT_WITHOUT_FLUSH=1 # Avoid send telemetry to MS
echo "ℹ️️ JAZ dry-run..."
echo "$(JAZ_DRY_RUN=1 jaz ${JAVA_OPTS} -jar /application/app.jar || true)"

exec jaz ${JAVA_OPTS} -jar /application/app.jar  ${@}
