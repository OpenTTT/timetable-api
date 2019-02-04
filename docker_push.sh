#!/usr/bin/env bash
# This script is meant to be exlusively run by travis-ci.
# Password and username will be set by build environment.
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push openttt/openttt-backend