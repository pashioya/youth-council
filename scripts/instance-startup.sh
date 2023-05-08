#!/bin/sh

# Set the metadata server to the get project id

# Get the files we need
gsutil cp gs://yc-01/fatjar.jar .

# Install dependencies
apt-get update
apt-get -y --force-yes install openjdk-17-jdk
apt-get install ufw
ufw allow 8080

# Start server
java -jar fatjar.jar