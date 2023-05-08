#!/bin/bash
# This script is used to deploy the application to the server
# Author: Filip Nowak
# Date: 04/05/2023


# Variables
instance_name="youth-council-$(date +%s)"
zone="europe-west1-b"
project="youth-council-cloud"
tags="http-server, default-allow-http-8080"
network="default"


# Create firewall rules 8080 http

gcloud compute firewall-rules create default-allow-http-8080 \
    --allow tcp:8080 \
    --source-ranges 0.0.0.0/0 \
    --target-tags http-server \
    --description "Allow port 8080 access to http-server"


# Enable access
gcloud compute firewall-rules create allow-rdp-ingress-from-iap \
  --direction=INGRESS \
  --action=allow \
  --rules=tcp:3389 \
  --source-ranges=35.235.240.0/20


# Create instance
gcloud compute instances create $instance_name \
--zone=$zone \
--project=$project \
--tags=$tags \
--machine-type=f1-micro \
--network=$network \
--metadata startup-script='#!/bin/bash
apt-get update
mkdir ~/youth-council
ufw allow 8080/tcp
'

# Copy files to server
gcloud compute scp --zone=$zone /build/libs/fatjar.jar $instance_name:~/youth-council/fatjar.jar
--ssh-key-expire-after=2m

# Execute command on the cloud
gcloud compute ssh --zone=$zone $instance_name --command="sudo apt-get install -yq openjdk-17-jdk"
gcloud compute ssh --zone=$zone $instance_name --command="java -jar ~/youth-council/fatjar.jar &"

# Run application
# END

