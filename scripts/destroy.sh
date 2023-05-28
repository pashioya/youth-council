#!/bin/bash
# This script deletes the resources created by the deploy.sh script
# Author: Filip Nowak
# Date: 23/05/2023

# ------------------------------Variables---------------------------
instance_name="youth-council"
zone="europe-west1-b"
project="youth-council-cloud"

# -----------------------------Functions----------------------------
delete_vm() {
  echo "Deleting VM instance"
  # Delete instance if it exists
  gcloud compute instances delete $instance_name --zone=$zone --quiet
}

delete_sql() {
  echo "Deleting SQL instance"
  # Delete instance if it exists
  gcloud sql instances delete ycdb --quiet
}

# -------------------------------Main-------------------------------
delete_vm
delete_sql

# ------------------------------End--------------------------------
