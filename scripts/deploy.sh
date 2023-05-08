#!/bin/bash
# This script is used to deploy the application to the server
# Author: Filip Nowak
# Date: 04/05/2023


# Variables
instance_name="youth-council-$(date +%s)"
zone="europe-west1-b"
project="youth-council-cloud"
tags="http-server"
network="default"


# Create firewall rules 8080 http
gcloud compute firewall-rules list | grep http-server
if [ $? -eq 1 ]; then
    gcloud compute firewall-rules create http-server --allow tcp:8080
fi

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
apt-get install -y openjdk17
mkdir /youth-council
gcloud compute scp --recurse ../build/libs/fatjar.jar '+'{instance_name}'+':/youth-council/fatjar.jar
ufw allow 8080/tcp
java -jar /youth-council/fatjar.jar &
'
# Copy files to server
gcloud compute scp --zone=$zone --recurse ../build/libs/fatjar.jar "$instance_name":/youth-council

# Execute command on the cloud
gcloud compute ssh --zone=$zone $instance_name --command="java -jar /youth-council/fatjar.jar &"


# Restart instance
gcloud compute instances reset $instance_name --zone=$zone

# Run application
# END

