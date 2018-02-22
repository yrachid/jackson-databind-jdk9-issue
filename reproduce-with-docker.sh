#! /bin/sh

docker build \
  -t jackson-jdk9-error-reproducer \
  .

docker run \
  -v ${PWD}:/usr/src/app \
  jackson-jdk9-error-reproducer
