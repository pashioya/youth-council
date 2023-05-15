#!/bin/bash
# This script is used to deploy the application to the server
# Author: Filip Nowak
# Date: 04/05/2023


# Variables
#instance_name="youth-council-$(date +%s)"
instance_name="youth-council"
zone="europe-west1-b"
project="youth-council-cloud"
network="default"
tags="http-server"

# Delete instance if it exists
gcloud compute instances delete $instance_name --zone=$zone --quiet

# Create instance
gcloud compute instances create $instance_name \
--zone=$zone \
--project=$project \
--machine-type=e2-small \
--subnet=default \
--network=$network \
--tags=$tags \
--metadata BUCKET=yc-01 \
--metadata startup-script='#! /bin/bash
# Get the files we need
gsutil cp gs://yc-01/fatjar.jar .

# Install dependencies
apt-get update
apt-get -y --force-yes install openjdk-17-jdk
iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080

# Start server
java -jar fatjar.jar
'

# Run application
# END

