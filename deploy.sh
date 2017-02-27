#!/bin/sh
sbt assembly

CURRENT_PATH= pwd
readonly CURRENT_PATH

spark-submit --class Boot \
--master local --deploy-mode client --executor-memory 1g \
--name dataparser --conf "spark.app.id=dataparser" \
${CURRENT_PATH}/target/scala-2.11/recommendation-system-assembly-1.0.jar $1 2