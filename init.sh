./gradlew assemble
gsutil rm -a gs://yc-01/fatjar.jar
gsutil cp build/libs/fatjar.jar  gs://yc-01/fatjar.jar
gsutil cp build/resources/main/sql/data_prod.sql gs://yc-01/data_prod.sql
./scripts/deploy.sh
