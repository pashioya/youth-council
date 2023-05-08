#!/bin/bash
# This script is used to deploy the application to the server
# Author: Filip Nowak
# Date: 04/05/2023


# Variables
instance_name="youth-council-$(date +%m)"
zone="europe-west1-b"
project="youth-council-cloud"
tags="http-server"

# Create firewall rules
gcloud compute firewall-rules list | grep http-server
if [ $? -eq 1 ]; then
    gcloud compute firewall-rules create http-server --allow tcp:8080
fi

# Create instance
gcloud compute instances create $instance_name \
--zone=$zone \
--project=$project \
--tags=$tags \
--machine-type=f1-micro
# Copy files to server
gcloud compute scp --zone=$zone --recurse build/libs/fatjar.jar $instance_name:~/youth-council
gcloud instances add-metadata $instance_name --zone=$zone --metadata startup-script='java -jar ~/youth-council/fatjar.jar &'
# Run application
# END

