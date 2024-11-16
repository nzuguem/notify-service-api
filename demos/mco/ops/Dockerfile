FROM eclipse-temurin:21.0.5_11-jre

ARG UID=10001
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

USER appuser

WORKDIR /application

COPY demos/mco/ops/run.sh ./run.sh

ENTRYPOINT ["/application/run.sh"]