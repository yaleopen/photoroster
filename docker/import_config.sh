#!/bin/bash
# Container runtime configuration script
# Gets config file from S3, decrypt and export env variables, then
#   substitutes env variables into parameterized config files
# This script expects S3URL env variable with the full S3 path to the encrypted config file

CONF='/usr/local/tomcat/conf'

if [ -n "$S3URL" ]; then
  echo "Getting config file from S3 (${S3URL}) ..."
  aws --version
  if [[ $? -ne 0 ]]; then
    echo "ERROR: aws-cli not found!"
    exit 1
  fi
  aws --region us-east-1 s3 cp ${S3URL} ./config.encrypted
  aws --region us-east-1 kms decrypt --ciphertext-blob fileb://config.encrypted --output text --query Plaintext | base64 --decode > config.txt
  set -a
  source config.txt
  rm -f config.txt config.encrypted

  echo 'Substituting environment variables in config files ...'
  perl -pe 's/\$\{(\w+)\}/(exists $ENV{$1}?$ENV{$1}:"missing variable $1")/eg' < ${CONF}/context.xml.template > ${CONF}/context.xml
  perl -pe 's/\$\{(\w+)\}/(exists $ENV{$1}?$ENV{$1}:"missing variable $1")/eg' < ${CONF}/roster.properties.template > ${CONF}/roster.properties
  chmod 600 ${CONF}/context.xml ${CONF}/roster.properties

else
  echo "ERROR: S3URL variable not set!"
  exit 1
fi


