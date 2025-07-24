## docker build -t nzuguem/java-runtime:21.0.5_11-jre -f demos/mco/ops/Dockerfile --push .
FROM eclipse-temurin:21.0.5_11-jre


ARG GLOWROOT_VERSION=0.14.0
ARG GLOWROOT_ZIP_NAME="glowroot-${GLOWROOT_VERSION}-dist.zip"
ARG GLOWROOT_URL="https://github.com/glowroot/glowroot/releases/download/v${GLOWROOT_VERSION}/${GLOWROOT_ZIP_NAME}"

ARG UID=10001

WORKDIR /application

RUN <<EOF
adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
EOF

ADD $GLOWROOT_URL ./

RUN <<EOF
apt-get update 
apt-get install -y unzip
unzip $GLOWROOT_ZIP_NAME
rm $GLOWROOT_ZIP_NAME
chown -R appuser:appuser glowroot
apt-get remove -y unzip
apt-get autoremove -y
rm -rf /var/lib/apt/lists/*
EOF

USER appuser

ONBUILD ARG APP_NAME
ONBUILD COPY target/$APP_NAME.jar ./app-original.jar
ONBUILD RUN <<EOF
java -Djarmode=tools -jar app-original.jar extract --application-filename app.jar --force --destination ./
EOF

COPY demos/mco/ops/run.sh ./run.sh

ENTRYPOINT ["/application/run.sh"]
