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
db_instance="ycdb"
tags="http-server"
bucket="yc-01"

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
--scopes=https://www.googleapis.com/auth/cloud-platform \
--metadata BUCKET=$bucket,startup-script='#!/bin/bash
# Install dependencies
apt-get update
apt-get -y install openjdk-17-jdk
iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080

# Get the files we need
gsutil cp gs://$BUCKET/fatjar.jar .

gcloud sql instances patch ycdb --add authorized-networks=$(curl -s icanhazip.com) --quiet


mkdir duckdns
echo url="https://www.duckdns.org/update?domains=youth-council&token=d19f34c6-3d1d-4911-8f8b-44f335c18612&ip=" | curl -k -o ~/duckdns/duck.log -K -

# Start server
java -jar -Dspring.profiles.active=prod fatjar.jar
'
