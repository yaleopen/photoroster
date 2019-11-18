#!/bin/bash
# Container runtime configuration script
# Substitutes env variables into parameterized config files

CONF='/usr/local/tomcat/conf'

echo 'Substituting environment variables in config files ...'

# if we got an S3URL, use that to download our config file
# (otherwise assume they got passed as separate env variables)
if [ -n "$S3URL" ]; then
  curl "$S3URL" > config.properties
  set -a
  source config.properties
  rm -f config.properties
fi

# Would rather use envsubst, but it's not in the container, so perl to the rescue ...
perl -pe 's/\$\{(\w+)\}/(exists $ENV{$1}?$ENV{$1}:"missing variable $1")/eg' < ${CONF}/context.xml.template > ${CONF}/context.xml
perl -pe 's/\$\{(\w+)\}/(exists $ENV{$1}?$ENV{$1}:"missing variable $1")/eg' < ${CONF}/roster.properties.template > ${CONF}/roster.properties

chmod 600 ${CONF}/context.xml ${CONF}/roster.properties
