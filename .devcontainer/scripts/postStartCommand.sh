#!/bin/bash

set -e

# Sourcing of fzf
echo "source <(fzf --bash)" >> /home/$USER/.bashrc

docker compose -f external-dependencies/compose.yml up --wait

mvn clean package -DskipTests

sdk env install