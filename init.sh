./gradlew assemble
gsutil rm -a gs://yc-01/fatjar.jar
gsutil cp build/libs/fatjar.jar  gs://yc-01/fatjar.jar
./scripts/deploy.sh
