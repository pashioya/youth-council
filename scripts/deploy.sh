#!/bin/bash
# This script is used to deploy the application to the server
# Author: Filip Nowak
# Date: 04/05/2023


# Variables
instance_name="youth-council-instance"

# Remove folder
gcloud compute ssh --zone europe-west2-a $instance_name --command "rm -rf ~/youth-council"


# Copy files to server
gcloud compute scp --recurse --zone europe-west2-a build/libs/*.jar $instance_name:~/youth-council

# Run application
gcloud compute ssh --zone europe-west2-a $instance_name --command "java -jar ~/youth-council/*.jar"


