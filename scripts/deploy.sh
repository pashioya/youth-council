#!/bin/bash
# This script is used to deploy the application to the server
# Author: Filip Nowak
# Date: 04/05/2023


# Variables
instance_name="youth-council-instance"
zone="europe-west1-b"

# Remove folder and create it again
gcloud compute ssh --zone=$zone $instance_name --command "rm -rf ~/youth-council && mkdir ~/youth-council"


# Copy files to server
gcloud compute scp --zone=$zone --recurse build/libs/fatjar.jar $instance_name:~/youth-council

# Run application
gcloud compute ssh --zone=$zone $instance_name --command "java -jar ~/youth-council/*.jar"


