#!/bin/bash
# Name: deploy.sh
# Description: Deploy app to google cloud
# Author: Filip Nowak
# Date: 24/04/2023

# Variables
name="youth-council-cloud"
project="youth-council-cloud"
zone="europe-west1-b"
machine_type="e2-micro"
image_project="ubuntu-os-cloud"
image_family="ubuntu-2204-lts"

# Create instance
gcloud compute instances create $name \
    --project=$project \
    --zone=$zone \
    --machine-type=$machine_type \
    --image-project=$image_project \
    --image-family=$image_family

echo "Deployed instance $name"

