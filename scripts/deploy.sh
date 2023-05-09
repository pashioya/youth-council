#!/bin/bash
# This script is used to deploy the application to the server
# Author: Filip Nowak
# Date: 04/05/2023


# Variables
instance_name="youth-council-$(date +%s)"
zone="europe-west1-b"
project="youth-council-cloud"
network="default"


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
--machine-type=e2-small \
--network=$network \
--metadata-from-file startup-script=instance-startup.sh \
--metadata BUCKET=yc-01

# Create firewall rules 8080 http
gcloud compute instances add-tags $instance_name --tags=webapp

# Create firewall rules 8080 http
gcloud compute firewall-rules create webapp-rule \
  --source-ranges=0.0.0.0/0 \
  --target-tags=webapp \
  --allow=tcp:8080

# Run application
# END

